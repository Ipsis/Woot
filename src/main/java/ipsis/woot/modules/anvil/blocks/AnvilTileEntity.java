package ipsis.woot.modules.anvil.blocks;

import ipsis.woot.crafting.AnvilRecipe;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.mod.ModItems;
import ipsis.woot.modules.factory.items.MobShardItem;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        markDirty();
        if (world != null)
            WorldHelper.updateClient(world, pos);
    }

    public ItemStack[] getIngredients() { return ingredients; }

    public ItemStack getBaseItem() { return baseItem.copy(); }
    public boolean addIngredient(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;

        for (int i = 0; i < ingredients.length; i++) {
            if (ingredients[i].isEmpty()) {
                ingredients[i] = itemStack.copy();
                markDirty();
                if (world != null)
                    WorldHelper.updateClient(world, pos);
                return true;
            }
        }

        return false;
    }

    public void dropContents(World world, BlockPos pos) {
        if (!baseItem.isEmpty()) {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), baseItem);
            baseItem = ItemStack.EMPTY;
        }

        for (int i = 0; i < ingredients.length; i++) {
            if (!ingredients[i].isEmpty()) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), ingredients[i]);
                ingredients[i] = ItemStack.EMPTY;
            }
        }

        markDirty();
        if (world != null)
            WorldHelper.updateClient(world, pos);
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
            markDirty();
            if (world != null)
                WorldHelper.updateClient(world, pos);

            if (!playerEntity.inventory.addItemStackToInventory(itemStack)) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            } else {
                playerEntity.openContainer.detectAndSendChanges();
            }
        }
    }

    public void tryCraft(PlayerEntity playerEntity) {

        /**
         * Check anvil is hot
         */
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.getBlock() != Blocks.MAGMA_BLOCK) {
            PlayerHelper.sendActionBarMessage(
                    playerEntity,
                    new TranslationTextComponent("chat.woot.anvil.cold").getFormattedText());
            return;
        }

        AnvilRecipe recipe = AnvilRecipe.findRecipe(baseItem);
        if (recipe == null)
            return;

        if (!recipe.checkIngredients(ingredients))
            return;

        ItemStack output = recipe.outputItem.copy();
        /**
         * Handle the shard programming
         */
        if (baseItem.getItem() == ModItems.MOB_SHARD_ITEM) {
            FakeMob fakeMob = MobShardItem.getProgrammedMob(baseItem);
            output = ControllerTileEntity.getItemStack(fakeMob);
        }

        baseItem = ItemStack.EMPTY;
        for (int i = 0; i < ingredients.length; i++)
            ingredients[i] = ItemStack.EMPTY;

        markDirty();
        WorldHelper.updateClient(world, pos);
        ItemEntity itemEntity = new ItemEntity(world,
                pos.getX(), pos.getY() + 1, pos.getZ(),
                output);
        itemEntity.setDefaultPickupDelay();
        world.addEntity(itemEntity);
    }

    /**
     * NBT
     */
    @Override
    public void read(CompoundNBT compoundNBT) {
        super.read(compoundNBT);
        if (compoundNBT.hasUniqueId("base")) {

            ListNBT listNBT = compoundNBT.getList("ingredients", 10);
            for (int i = 0; i < listNBT.size(); i++) {
                CompoundNBT itemTags = listNBT.getCompound(i);
                int j = itemTags.getInt("slot");
                if (j >= 0 && j < ingredients.length) {
                    ingredients[j] = ItemStack.read(itemTags);
                }
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        CompoundNBT compoundNBT1 = new CompoundNBT();
        baseItem.write(compoundNBT1);
        compoundNBT.put("base", compoundNBT1);

        ListNBT listNBT = new ListNBT();
        for (int i = 0; i < ingredients.length; i++) {
            if (!ingredients[i].isEmpty()) {
                CompoundNBT itemTags = new CompoundNBT();
                itemTags.putInt("slot", i);
                ingredients[i].write(itemTags);
                listNBT.add(itemTags);
            }
        }
        compoundNBT.put("ingredients", listNBT);
        return compoundNBT;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compoundNBT = new CompoundNBT();
        this.write(compoundNBT);
        return new SUpdateTileEntityPacket(getPos(), 1, compoundNBT);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
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
