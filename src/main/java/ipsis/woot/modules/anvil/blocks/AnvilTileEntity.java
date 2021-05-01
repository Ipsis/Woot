package ipsis.woot.modules.anvil.blocks;

import ipsis.woot.crafting.AnvilRecipe;
import ipsis.woot.mod.ModNBT;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.items.MobShardItem;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static ipsis.woot.crafting.AnvilRecipe.ANVIL_TYPE;

public class AnvilTileEntity extends TileEntity implements WootDebug {

    public AnvilTileEntity() {
        super(AnvilSetup.ANVIL_BLOCK_TILE.get());
    }

    private ItemStack baseItem = ItemStack.EMPTY;
    private ItemStack[] ingredients = { ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY };

    public boolean hasBaseItem() { return !baseItem.isEmpty(); }

    public void setBaseItem(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return;

        this.baseItem = itemStack.copy();
        setChanged();
        if (level != null)
            WorldHelper.updateClient(level, worldPosition);
    }

    public ItemStack[] getIngredients() { return ingredients; }

    public ItemStack getBaseItem() { return baseItem.copy(); }
    public boolean addIngredient(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;

        for (int i = 0; i < ingredients.length; i++) {
            if (ingredients[i].isEmpty()) {
                ingredients[i] = itemStack.copy();
                setChanged();
                if (level != null)
                    WorldHelper.updateClient(level, worldPosition);
                return true;
            }
        }

        return false;
    }

    public void dropContents(World world, BlockPos blockPos) {
        if (!baseItem.isEmpty()) {
            InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), baseItem);
            baseItem = ItemStack.EMPTY;
        }

        for (int i = 0; i < ingredients.length; i++) {
            if (!ingredients[i].isEmpty()) {
                InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ingredients[i]);
                ingredients[i] = ItemStack.EMPTY;
            }
        }

        setChanged();
        if (world != null)
            WorldHelper.updateClient(world, blockPos);
    }

    public void dropItem(PlayerEntity playerEntity) {

        ItemStack itemStack = ItemStack.EMPTY;

        for (int i = 0; i < ingredients.length; i++) {
            if (!ingredients[i].isEmpty()) {
                itemStack = ingredients[i].copy();
                ingredients[i] = ItemStack.EMPTY;
                break;
            }
        }

        if (itemStack.isEmpty() && !baseItem.isEmpty()) {
            itemStack = baseItem;
            baseItem = ItemStack.EMPTY;
        }

        if (!itemStack.isEmpty()) {
            setChanged();
            if (level != null)
                WorldHelper.updateClient(level, worldPosition);

            if (!playerEntity.inventory.add(itemStack)) {
                InventoryHelper.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), itemStack);
            } else {
                playerEntity.containerMenu.broadcastChanges();
            }
        }
    }

    public void tryCraft(PlayerEntity playerEntity) {

        /**
         * Check anvil is hot
         */
        if (!AnvilSetup.ANVIL_BLOCK.get().isAnvilHot(level, worldPosition)) {
            playerEntity.displayClientMessage(new TranslationTextComponent("chat.woot.anvil.cold"), true);
            return;
        }

        AnvilRecipe recipe = level.getRecipeManager().getRecipeFor(ANVIL_TYPE,
                new Inventory(baseItem, ingredients[0], ingredients[1], ingredients[2], ingredients[3]), level).orElse(null);
        if (recipe == null)
            return;

        ItemStack output = recipe.getOutput();
        /**
         * Handle the shard programming
         */
        if (baseItem.getItem() == FactorySetup.MOB_SHARD_ITEM.get()) {
            FakeMob fakeMob = MobShardItem.getProgrammedMob(baseItem);
            output = ControllerTileEntity.getItemStack(fakeMob);
        }

        baseItem = ItemStack.EMPTY;
        for (int i = 0; i < ingredients.length; i++)
            ingredients[i] = ItemStack.EMPTY;

        setChanged();
        WorldHelper.updateClient(level, worldPosition);
        ItemEntity itemEntity = new ItemEntity(level,
                worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(),
                output);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }

    /**
     * NBT
     */
    @Override
    public void load(BlockState blockState, CompoundNBT compoundNBT) {
        super.load(blockState, compoundNBT);
        readFromNBT(compoundNBT);
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        super.deserializeNBT(compoundNBT);
        readFromNBT(compoundNBT);
    }

    private void readFromNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains(ModNBT.Anvil.BASE_ITEM_TAG)) {
            ListNBT listNBT = compoundNBT.getList(ModNBT.INVENTORY_TAG, 10);
            for (int i = 0; i < listNBT.size(); i++) {
                CompoundNBT itemTags = listNBT.getCompound(i);
                int j = itemTags.getInt(ModNBT.INVENTORY_SLOT_TAG);
                if (j >= 0 && j < ingredients.length) {
                    ingredients[j] = ItemStack.of(itemTags);
                }
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        super.save(compoundNBT);

        if (!baseItem.isEmpty()) {
            CompoundNBT compoundNBT1 = new CompoundNBT();
            baseItem.save(compoundNBT1);
            compoundNBT.put(ModNBT.Anvil.BASE_ITEM_TAG, compoundNBT1);

            ListNBT listNBT = new ListNBT();
            for (int i = 0; i < ingredients.length; i++) {
                if (!ingredients[i].isEmpty()) {
                    CompoundNBT itemTags = new CompoundNBT();
                    itemTags.putInt(ModNBT.INVENTORY_SLOT_TAG, i);
                    ingredients[i].save(itemTags);
                    listNBT.add(itemTags);
                }
            }
            compoundNBT.put(ModNBT.INVENTORY_TAG, listNBT);
        }
        return compoundNBT;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compoundNBT = new CompoundNBT();
        this.save(compoundNBT);
        return new SUpdateTileEntityPacket(getBlockPos(), 1, compoundNBT);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.deserializeNBT(pkt.getTag());
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> AnvilTileEntity");
        debug.add("      base: " + baseItem);
        for (int i = 0; i < ingredients.length; i++)
            debug.add("      ingredient: " + ingredients[i]);
        return debug;
    }
}
