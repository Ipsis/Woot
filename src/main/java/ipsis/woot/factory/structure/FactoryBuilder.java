package ipsis.woot.factory.structure;

import ipsis.woot.ModBlocks;
import ipsis.woot.factory.structure.locator.IMultiBlockGlueProvider;
import ipsis.woot.factory.structure.locator.IMultiBlockMaster;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.factory.structure.pattern.ScannedPattern;
import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.helpers.PlayerHelper;
import ipsis.woot.util.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class FactoryBuilder {

    public static void disconnectOld(World world, BlockPos origin, ScannedPattern scannedPattern) {

        for (BlockPos pos : scannedPattern.getBlocks()) {
            if (world.isBlockLoaded(pos)) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof IMultiBlockGlueProvider)
                    ((IMultiBlockGlueProvider)te).getIMultiBlockGlue().clearMaster();
            }
        }
    }

    public static void connectNew(World world, BlockPos origin, ScannedPattern scannedPattern) {

        IMultiBlockMaster master = (IMultiBlockMaster)world.getTileEntity(origin);

        for (BlockPos pos : scannedPattern.getBlocks()) {
            if (world.isBlockLoaded(pos)) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof IMultiBlockGlueProvider)
                    ((IMultiBlockGlueProvider)te).getIMultiBlockGlue().setMaster(master);
            }
        }
    }

    /**
     * Places a single block from the factory pattern
     * return true if a block was placed
     */
    public static boolean autoBuildSingleBlock(EntityPlayer entityPlayer, @Nonnull World world, AbsolutePattern absolutePattern) {

        for (AbsolutePattern.AbsoluteBlock absoluteBlock : absolutePattern.getBlocks()) {

            IBlockState currBlockState = world.getBlockState(absoluteBlock.getPos());
            Block currBlock = currBlockState.getBlock();

            if (FactoryBlock.getFactoryBlock(currBlock) == absoluteBlock.getFactoryBlock())
                continue;

            if (!PlayerHelper.hasFactoryBlock(entityPlayer, absoluteBlock.getFactoryBlock())) {
                PlayerHelper.sendChatMessage(TextFormatting.RED, entityPlayer, StringHelper.localise("chat.woot.build.missing", ModBlocks.getBlockFromFactoryBlock(absoluteBlock.getFactoryBlock()).getLocalizedName()));
                return false;
            }

            if (currBlock.isAir(currBlockState, world, absoluteBlock.getPos()) || currBlock.isReplaceable(world, absoluteBlock.getPos())) {
                world.setBlockState(absoluteBlock.getPos(), ModBlocks.getBlockFromFactoryBlock(absoluteBlock.getFactoryBlock()).getDefaultState());
                PlayerHelper.takeFactoryBlock(entityPlayer, absoluteBlock.getFactoryBlock());
                return true;

            } else {
                PlayerHelper.sendChatMessage(TextFormatting.RED, entityPlayer, StringHelper.localise("chat.woot.build.noreplace", currBlock.getLocalizedName(), absoluteBlock.getPos(), ModBlocks.getBlockFromFactoryBlock(absoluteBlock.getFactoryBlock()).getLocalizedName()));
                return false;
            }
        }

        return false;
    }

}
