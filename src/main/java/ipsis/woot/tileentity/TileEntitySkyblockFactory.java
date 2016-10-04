package ipsis.woot.tileentity;

import ipsis.woot.block.BlockSkyblockFactory;
import ipsis.woot.util.ItemStackHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileEntitySkyblockFactory extends TileEntity implements ITickable {
	
	int remainingTicks = 0;
	int ticksToLoot;
	int LOOT_EVERY_TICKS = 300;
	
	

	@Override
	public void update() {
		for(EntityItem itementity : TileEntityHopper.getCaptureItems(worldObj, pos.getX(), pos.getY(), pos.getZ())) {
			if(TileEntityFurnace.isItemFuel(itementity.getEntityItem()) && remainingTicks <= 0) {
				consumeOneItem(itementity);
				remainingTicks += TileEntityFurnace.getItemBurnTime(itementity.getEntityItem());
			}
		}
		if(remainingTicks > 0) {
			remainingTicks--;
			if(worldObj.isRemote) return;
			ticksToLoot++;
			if(ticksToLoot >= LOOT_EVERY_TICKS) produceLoot();
		}
	}
	
	private void produceLoot() {
		ticksToLoot = 0;
		outputLoot(new ItemStack(Items.ROTTEN_FLESH));
	}

	private void outputLoot(ItemStack itemStack) {
		EnumFacing f = worldObj.getBlockState(pos).getValue(BlockSkyblockFactory.FACING);
		if(worldObj.isBlockLoaded(getPos().offset(f))) {
			TileEntity te = worldObj.getTileEntity(this.getPos().offset(f));
			if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite())) {
				IItemHandler h = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());
				ItemStack result = ItemHandlerHelper.insertItem(h, itemStack, false);
				if(result != null) {
					ItemStackHelper.spawnInWorld(worldObj, getPos().offset(EnumFacing.UP), result);
				}
				return;
			}
		}
		ItemStackHelper.spawnInWorld(worldObj, getPos().offset(EnumFacing.UP), itemStack);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		remainingTicks = compound.getInteger("remainingTicks");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("remainingTicks", remainingTicks);
		return compound;
	}
	
	private void consumeOneItem(EntityItem itementity) {
		if(worldObj.isRemote) return;
		ItemStack itemStack = itementity.getEntityItem();
		itemStack.stackSize--;
		if(itemStack.stackSize == 0) {
			itementity.setDead();
		}
	}
	public int getRemainingTicks() {
		return remainingTicks;
	}

}
