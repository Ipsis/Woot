package ipsis.woot.modules.factory.layout;

import ipsis.woot.advancements.Advancements;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.multiblock.MultiBlockGlueProvider;
import ipsis.woot.modules.factory.multiblock.MultiBlockMaster;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;
import java.util.List;

public class FactoryHelper {

    public static void disconnectOld(World world, AbsolutePattern absolutePattern) {
        for (PatternBlock pb : absolutePattern.getBlocks()) {
            if (world.isBlockLoaded(pb.getBlockPos())) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof MultiBlockGlueProvider)
                    ((MultiBlockGlueProvider)te).getGlue().clearMaster();
            }
        }
    }

    public static void connectNew(World world, AbsolutePattern absolutePattern, MultiBlockMaster master) {
        for (PatternBlock pb : absolutePattern.getBlocks()) {
            if (pb.getFactoryComponent() == FactoryComponent.CONTROLLER && !absolutePattern.isValidControllerPos(pb.getBlockPos()))
                continue;

            if (world.isBlockLoaded(pb.getBlockPos())) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof MultiBlockGlueProvider)
                    ((MultiBlockGlueProvider)te).getGlue().setMaster(master);
            }
        }
    }

    static boolean isCorrectBlock(Block block, List<Block> validBlocks) {
        return validBlocks.contains(block);
    }


    public enum BuildResult {
        ERROR,
        SUCCESS,
        NO_BLOCK_IN_INV,
        ALL_BLOCKS_PLACED
    }
    public static BuildResult tryBuild(World world, BlockPos pos, PlayerEntity playerEntity, Direction facing, Tier tier) {
        if (!playerEntity.isAllowEdit()) {
            playerEntity.sendStatusMessage(
                    new TranslationTextComponent("chat.woot.intern.noedit"), false);
            return BuildResult.ERROR;
        }

        boolean allPlaced = true;
        AbsolutePattern absolutePattern = AbsolutePattern.create(world, tier, pos, facing);
        for (PatternBlock pb : absolutePattern.getBlocks()) {
            BlockState currState = world.getBlockState(pb.getBlockPos());
            Block currBlock = currState.getBlock();

            List<Block> correctBlocks = pb.getFactoryComponent().getBlocks();
            List<ItemStack> correctItemStacks = pb.getFactoryComponent().getStacks();

            // Is it the correct block already
            if (isCorrectBlock(currBlock, correctBlocks))
                continue;

            if (pb.getFactoryComponent() == FactoryComponent.CONTROLLER || pb.getFactoryComponent() == FactoryComponent.CELL)
                continue;

            allPlaced = false;

            Block placeBlock = correctBlocks.get(0);
            if (!PlayerHelper.playerHasFactoryComponent(playerEntity, correctItemStacks)) {
                playerEntity.sendStatusMessage(
                        new TranslationTextComponent("chat.woot.intern.missingblock",
                                StringHelper.translate(pb.getFactoryComponent().getTranslationKey())), false);
                return BuildResult.NO_BLOCK_IN_INV;
            }
            if (world.isBlockModifiable(playerEntity, pb.getBlockPos()) && (world.isAirBlock(pb.getBlockPos()) || currState.getMaterial().isReplaceable())) {
                ItemStack takenStack = PlayerHelper.takeFactoryComponent(playerEntity, correctItemStacks);
                if (!takenStack.isEmpty()) {

                    BlockSnapshot blockSnapshot = BlockSnapshot.create(world.getDimensionKey(), world, pos);
                    world.setBlockState(pb.getBlockPos(), placeBlock.getDefaultState(), 11);
                    if (ForgeEventFactory.onBlockPlace(playerEntity, blockSnapshot, Direction.UP)) {
                        blockSnapshot.restore(true, false);
                        return BuildResult.ERROR;
                    }
                }
                return BuildResult.SUCCESS;
            } else {
                // cannot replace block
                playerEntity.sendStatusMessage(
                        new TranslationTextComponent("chat.woot.intern.noreplace",
                                StringHelper.translate(pb.getFactoryComponent().getTranslationKey()),
                                pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getZ()), false);
                return BuildResult.ERROR;
            }
        }
        return allPlaced ? BuildResult.ALL_BLOCKS_PLACED : BuildResult.ERROR;
    }

    public static void tryValidate(World world, BlockPos pos, PlayerEntity playerEntity, Direction facing, Tier tier) {

        playerEntity.sendStatusMessage(
                new TranslationTextComponent(
                "chat.woot.intern.validate.start",
                        StringHelper.translate(tier.getTranslationKey())),
                true);

        List<String> feedback = new ArrayList<>();
        AbsolutePattern absolutePattern = AbsolutePattern.create(world, tier, pos, facing);
        if (!FactoryScanner.compareToWorld(absolutePattern, world, feedback)) {
            playerEntity.sendStatusMessage(new StringTextComponent("----"), false);
            playerEntity.sendStatusMessage(
                    new TranslationTextComponent(
                    "chat.woot.intern.validate.invalid", StringHelper.translate(tier.getTranslationKey())),
            true);
            for (String s : feedback)
                playerEntity.sendStatusMessage(new StringTextComponent(s), false);
            playerEntity.sendStatusMessage(new StringTextComponent("----"), false);
        } else {
            playerEntity.sendStatusMessage(
                    new TranslationTextComponent(
                    "chat.woot.intern.validate.valid", StringHelper.translate(tier.getTranslationKey())),
                    true);
            if (playerEntity instanceof ServerPlayerEntity)
                Advancements.TIER_VALIDATE_TRIGGER.trigger((ServerPlayerEntity)playerEntity, tier);
        }
    }
}
