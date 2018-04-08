package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.crafting.AnvilHelper;
import ipsis.woot.crafting.IAnvilRecipe;
import ipsis.woot.item.ItemEnderShard;
import ipsis.woot.util.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityAnvil extends TileEntity {

    private ItemStack itemStack = ItemStack.EMPTY;


    public @Nonnull ItemStack getBaseItem() {

        return itemStack;
    }

    public void setBaseItem(ItemStack itemStack) {

        this.itemStack = itemStack;
        markDirty();
        if (getWorld() != null) {
            IBlockState state = getWorld().getBlockState(getPos());
            getWorld().notifyBlockUpdate(getPos(), state, state, 3);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {

        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {

        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        if (compound.hasKey("baseItem"))
            itemStack = new ItemStack(compound.getCompoundTag("baseItem"));
        else
            itemStack = ItemStack.EMPTY;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        if (!itemStack.isEmpty()) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            itemStack.writeToNBT(nbtTagCompound);
            compound.setTag("baseItem", nbtTagCompound);
        }

        return compound;
    }

    public void tryCraft(EntityPlayer entityPlayer) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.ANVIL_CRAFTING, "tryCraft", itemStack);

        if (!AnvilHelper.isAnvilHot(getWorld(), getPos())) {
            entityPlayer.sendStatusMessage(new TextComponentString(StringHelper.localize("chat.woot.anvil.nomagma")), false);
            return;
        }

        List<EntityItem> entityItemList = AnvilHelper.getItems(getWorld(), getPos());
        List<ItemStack> ingredients = new ArrayList<>();
        for (EntityItem e : entityItemList)
            ingredients.add(e.getItem());

        if (ItemEnderShard.isEnderShard(itemStack) && !ItemEnderShard.isFull(itemStack)) {

            entityPlayer.sendStatusMessage(new TextComponentString(StringHelper.localize("chat.woot.anvil.emptyshard")), false);
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return;
        }

        IAnvilRecipe recipe = Woot.anvilManager.getRecipe(itemStack, ingredients);
        if (recipe != null) {

            ItemStack output = recipe.getCopyOutput();
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.ANVIL_CRAFTING, "tryCraft", "Output " + output);

            if (ItemEnderShard.isFull(itemStack)) {
                WootMob tmpWootMob = WootMobBuilder.create(itemStack.getTagCompound());
                NBTTagCompound tag = new NBTTagCompound();
                WootMobBuilder.writeToNBT(tmpWootMob, tag);
                output.setTagCompound(tag);
            }

            if (!recipe.shouldPreserveBase())
                setBaseItem(ItemStack.EMPTY);

            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            WorldHelper.spawnInWorld(world, pos, output);

            if (!ingredients.isEmpty()) {
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.ANVIL_CRAFTING, "tryCraft", "Leftovers " + ingredients);
            }

            /**
             * Remove the used items from the incoming entity items
             * This has already been checked to ensure that the items are in the list
              */
            for (ItemStack recipeStack : recipe.getInputs()) {
                int count = recipeStack.getCount();
                for (EntityItem entityItem : entityItemList) {
                    ItemStack itemStack = entityItem.getItem();
                    if (ItemStack.areItemsEqual(itemStack, recipeStack) && ItemStack.areItemStackTagsEqual(itemStack, recipeStack)) {
                        if (entityItem.getItem().getCount() >= count) {
                            entityItem.getItem().setCount(entityItem.getItem().getCount() - count);
                            count = 0;
                        } else {
                            count -= entityItem.getItem().getCount();
                            entityItem.getItem().setCount(0);
                        }

                        if (entityItem.getItem().getCount() <= 0)
                            entityItem.setDead();
                    }

                    if (count == 0)
                        break;
                }
            }
        }  else {

            entityPlayer.sendStatusMessage(new TextComponentString(StringHelper.localize("chat.woot.anvil.invalid")), false);
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
}
