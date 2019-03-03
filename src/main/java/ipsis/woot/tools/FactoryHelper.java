package ipsis.woot.tools;

import ipsis.woot.factory.FactoryTier;
import ipsis.woot.factory.layout.*;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;

public class FactoryHelper {

    private static boolean isCorrectBlock(IFactoryBlockProvider curr, FactoryBlock factoryBlock) {
        if (factoryBlock.isCell())
            return curr.getFactoryBlock().isCell();
        return curr.getFactoryBlock() == factoryBlock;
    }

    public static boolean tryBuild(World world, BlockPos pos, EntityPlayer entityPlayer, EnumFacing facing, FactoryTier tier) {

        AbsolutePattern absolutePattern = AbsolutePatternBuilder.createAbsolutePattern(world, tier, pos, facing);
        for (PatternBlock pb : absolutePattern.getBlocks()) {

            IBlockState currState = world.getBlockState(pb.getBlockPos());
            Block currBlock = currState.getBlock();

            // Is it the correct block already
            if (currBlock instanceof IFactoryBlockProvider) {
                if (isCorrectBlock((IFactoryBlockProvider)currBlock, pb.getFactoryBlock()))
                    continue;
            }

            if (!PlayerHelper.hasFactoryBlock(entityPlayer, pb.getFactoryBlock(), true)) {
                PlayerHelper.sendChatMessage(entityPlayer,
                        StringHelper.translateFormat("chat.woot.intern.missingblock",
                            StringHelper.translate(pb.getFactoryBlock().getTranslationkey())));
                return false;
            }

            if (world.isBlockModifiable(entityPlayer, pb.getBlockPos()) && (world.isAirBlock(pb.getBlockPos()) || currState.getMaterial().isReplaceable())) {
                // @todo block snapshot for intern
                BlockSnapshot blockSnapshot = BlockSnapshot.getBlockSnapshot(world, pb.getBlockPos());
                world.setBlockState(pb.getBlockPos(), ModBlocks.getFactoryBlockDefaultState(pb.getFactoryBlock()));
/*                if (ForgeEventFactory.onPlayerBlockPlace(entityPlayer, blockSnapshot, EnumFacing.UP, EnumHand.MAIN_HAND).isCancelable() ) {
                    PlayerHelper.sendChatMessage(entityPlayer, StringHelper.translateFormat("chat.woot.intern.noreplace", StringHelper.translate(currBlock.getTranslationKey()), pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getZ()));
                    blockSnapshot.restore(true, false);
                    return false;
                } */
                PlayerHelper.takeFactoryBlock(entityPlayer, pb.getFactoryBlock(), true);
                return true;
            } else {
                // cannot replace block
                PlayerHelper.sendChatMessage(entityPlayer,
                        StringHelper.translateFormat("chat.woot.intern.noreplace",
                            StringHelper.translate(currBlock.getTranslationKey()),
                                pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getZ()));
                return false;
            }
        }
        return false;
    }

    public static boolean tryValidate(World world, BlockPos pos, EntityPlayer entityPlayer, EnumFacing facing, ItemIntern.ToolMode toolMode) {

        if (toolMode.isValidateTierMode()) {
            FactoryTier tier = toolMode.getFactoryTier();
            AbsolutePattern absolutePattern = AbsolutePatternBuilder.createAbsolutePattern(world, tier, pos, facing);

            for (PatternBlock pb : absolutePattern.getBlocks()) {
                IBlockState currState = world.getBlockState(pb.getBlockPos());
                Block currBlock = currState.getBlock();
                if (world.isAirBlock(pb.getBlockPos())) {
                    // missing block
                    PlayerHelper.sendChatMessage(entityPlayer,
                            StringHelper.translateFormat("chat.woot.intern.validate.missing",
                                StringHelper.translate(pb.getFactoryBlock().getTranslationkey()),
                                pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getZ()));
                } else if (currBlock instanceof IFactoryBlockProvider) {
                    // wrong type of factory block
                    if (!isCorrectBlock((IFactoryBlockProvider)currBlock, pb.getFactoryBlock())) {
                        String found = StringHelper.translate(currBlock.getTranslationKey());
                        String expected = StringHelper.translate(pb.getFactoryBlock().getTranslationkey());
                        PlayerHelper.sendChatMessage(entityPlayer,
                                StringHelper.translateFormat("chat.woot.intern.validate.incorrect",
                                    expected, pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getY(), found));
                    }
                } else {
                    // not a factory block
                    String found = StringHelper.translate(currBlock.getTranslationKey());
                    String expected = StringHelper.translate(pb.getFactoryBlock().getTranslationkey());
                    PlayerHelper.sendChatMessage(entityPlayer,
                            StringHelper.translateFormat("chat.woot.intern.validate.incorrect",
                                expected, pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getY(), found));
                }
            }
        } else {

        }
        return false;
    }
}
