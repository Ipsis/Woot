package ipsis.woot.tileentity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntitySkyblockFactory extends TileEntity implements ITickable {
	
	int remainingTicks = 0;

	@Override
	public void update() {
		for(EntityItem itementity : TileEntityHopper.getCaptureItems(worldObj, pos.getX(), pos.getY(), pos.getZ())) {
			if(TileEntityFurnace.isItemFuel(itementity.getEntityItem()) && remainingTicks <= 0) {
				consumeOneItem(itementity);
				remainingTicks += TileEntityFurnace.getItemBurnTime(itementity.getEntityItem());
			}
		}
		if(remainingTicks > 0) remainingTicks--;
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
		System.out.println(remainingTicks);
	}

}
