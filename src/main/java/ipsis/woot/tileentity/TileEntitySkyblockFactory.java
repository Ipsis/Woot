package ipsis.woot.tileentity;

import java.util.Map;
import java.util.Random;

import ipsis.woot.block.BlockSkyblockFactory;
import ipsis.woot.reference.Settings;
import ipsis.woot.util.ItemStackHelper;
import net.minecraft.entity.item.EntityItem;
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
	static final Random rand = new Random();
	
	
	@Override
	public void update() {
		for(EntityItem itementity : TileEntityHopper.getCaptureItems(worldObj, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5)) {
			if(TileEntityFurnace.isItemFuel(itementity.getEntityItem()) && remainingTicks <= 0) {
				consumeOneItem(itementity);
				remainingTicks += TileEntityFurnace.getItemBurnTime(itementity.getEntityItem());
			}
		}
		if(remainingTicks > 0) {
			remainingTicks--;
			if(worldObj.isRemote) return;
			ticksToLoot++;
			if(ticksToLoot >= Settings.skyblockProductionTime) produceLoot();
		}
	}
	
	private void produceLoot() {
		ticksToLoot = 0;
		outputLoot(getWeightedRandom(Settings.skyblockLootMap, rand));
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
	
	public static <E> E getWeightedRandom(Map<E, Double> weights, Random random) {
	    E result = null;
	    double bestValue = Double.MAX_VALUE;

	    for (E element : weights.keySet()) {
	        double value = -Math.log(random.nextDouble()) / weights.get(element);
	        if (value < bestValue) {
	            bestValue = value;
	            result = element;
	        }
	    }

	    return result;
	}
	
	public int getRemainingTicks() {
		return remainingTicks;
	}

}
