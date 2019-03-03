package ipsis.woot.util;

import ipsis.woot.Woot;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WootBlock extends Block {

    public WootBlock(Block.Properties properties, String basename) {
        super(properties);
        setRegistryName(Woot.MODID, basename);
    }

    /**
     * Workaround for TE being null when broken.
     * @todo Currently this is broken due to 5570
     */
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest, IFluidState fluid) {
        if (willHarvest)
            return true; // delay deletion of the block until after getDrops

        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    /**
     * Ensure NBT data is written to itemstack on block break
     */
    @Override
    public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IRestorableTileEntity) {
            ItemStack itemStack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            ((IRestorableTileEntity)te).writeRestorableToNBT(nbtTagCompound);
            itemStack.setTag(nbtTagCompound);
            drops.add(itemStack);
        } else {
            super.getDrops(state, drops, world, pos, fortune);
        }
    }

    /**
     * Ensure NBT data is restored from itemstack on block place
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof IRestorableTileEntity) {
            NBTTagCompound nbtTagCompound = stack.getTag();
            if (nbtTagCompound != null)
                ((IRestorableTileEntity)te).readRestorableFromNBT(nbtTagCompound);
        }
    }
}
