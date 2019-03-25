package ipsis.woot.util;

import ipsis.woot.Woot;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class WootBlock extends Block {

    public WootBlock(Block.Properties properties, String basename) {
        super(properties);
        setRegistryName(Woot.MODID, basename);
    }

    /**
     * Workaround for TE being null when broken.
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

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (WorldHelper.isClientWorld(worldIn))
            return true;

        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof IInteractionObject))
            return false;

        NetworkHooks.openGui((EntityPlayerMP)player, (IInteractionObject)te, buf -> buf.writeBlockPos(te.getPos()));

        return true;
    }
}
