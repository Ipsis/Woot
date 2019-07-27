package ipsis.woot.factory.layout;

import ipsis.woot.Woot;
import ipsis.woot.common.WootConfig;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryComponentProvider;
import ipsis.woot.factory.Tier;
import ipsis.woot.factory.blocks.ControllerBlock;
import ipsis.woot.factory.blocks.ControllerTileEntity;
import ipsis.woot.factory.multiblock.MultiBlockGlueProvider;
import ipsis.woot.factory.multiblock.MultiBlockMaster;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;

public class FactoryHelper {

    public static AbsolutePattern compareToWorldQuick(AbsolutePattern absolutePattern, World world) {

        int controllerCount = 0;
        for (PatternBlock p : absolutePattern.getBlocks()) {

            // Don't load an unloaded chunk
            if (!world.isBlockLoaded(p.getBlockPos()))
                return null;

            BlockState currState = world.getBlockState(p.getBlockPos());
            Block currBlock = currState.getBlock();

            if (!(currBlock instanceof FactoryComponentProvider)) {
                Woot.LOGGER.info("compareToWorldQuick: not a component {}", currBlock);
                return null;
            }

            FactoryComponentProvider factoryComponent = (FactoryComponentProvider)currBlock;
            if (!FactoryComponent.isSameComponentFuzzy(factoryComponent.getFactoryComponent(), p.getFactoryComponent())) {
                Woot.LOGGER.info("compareToWorldQuick: not same component {} {}", factoryComponent.getFactoryComponent(), p.getFactoryComponent());
                return null;
            }

            /**
             * Fails if the controller is not valid for the tier
             */
            if (p.getFactoryComponent() == FactoryComponent.CONTROLLER) {
                TileEntity te = world.getTileEntity(p.getBlockPos());
                if (te instanceof ControllerTileEntity) {
                    FakeMob fakeMob = ((ControllerTileEntity) te).getFakeMob();
                    Tier mobTier = WootConfig.get().getMobTier(fakeMob, world);
                    if (!absolutePattern.getTier().isValidForTier(mobTier)) {
                        Woot.LOGGER.info("compareToWorldQuick: mob {} not valid for {}",
                                fakeMob, absolutePattern.getTier());
                        return null;
                    } else {
                        controllerCount++;
                    }
                    // TODO blacklisted ???
                }
            }
        }

        if (controllerCount == 0) {
            Woot.LOGGER.info("compareToWorldQuick: no valid controllers");
            return null;
        }

        return absolutePattern;
    }

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
            if (world.isBlockLoaded(pb.getBlockPos())) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof MultiBlockGlueProvider)
                    ((MultiBlockGlueProvider)te).getGlue().setMaster(master);
            }
        }
    }

    static boolean isCorrectBlock(FactoryComponentProvider provider, FactoryComponent component) {
        return provider.getFactoryComponent() == component;
    }

    public static boolean tryBuild(World world, BlockPos pos, PlayerEntity playerEntity, Direction facing, Tier tier) {

        AbsolutePattern absolutePattern = AbsolutePattern.create(world, tier, pos, facing);
        for (PatternBlock pb : absolutePattern.getBlocks()) {
            BlockState currState = world.getBlockState(pb.getBlockPos());
            Block currBlock = currState.getBlock();

            // Is it the correct block already
            if (currBlock instanceof FactoryComponentProvider) {
                if (isCorrectBlock((FactoryComponentProvider)currBlock, pb.getFactoryComponent()))
                    continue;
            }

            // TODO check player has block
            if (world.isBlockModifiable(playerEntity, pb.getBlockPos()) && (world.isAirBlock(pb.getBlockPos()) || currState.getMaterial().isReplaceable())) {
                // TODO block snapshot for intern
                world.setBlockState(pb.getBlockPos(), getComponentBlock(pb.getFactoryComponent()).getDefaultState());
                // TODO take from player
                return true;
            } else {
                // cannot replace block
                PlayerHelper.sendChatMessage(playerEntity, "Cannot replace block");
                return false;
            }
        }
        return false;
    }

    public static void tryValidate(World world, BlockPos pos, PlayerEntity playerEntity, Direction facing, Tier tier) {

        AbsolutePattern absolutePattern = AbsolutePattern.create(world, tier, pos, facing);
        for (PatternBlock pb : absolutePattern.getBlocks()) {
            BlockState currState = world.getBlockState(pb.getBlockPos());
            Block currBlock = currState.getBlock();

            String found = StringHelper.translate(currBlock.getTranslationKey());
            String expected = StringHelper.translate(pb.getFactoryComponent().getTranslationKey());
            if (world.isAirBlock(pb.getBlockPos())) {
                PlayerHelper.sendChatMessage(playerEntity,
                        StringHelper.translateFormat(
                                "chat.woot.intern.validate.missing",
                                expected,
                                pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getZ()));
            } else  if (currBlock instanceof FactoryComponentProvider) {
                if (!isCorrectBlock((FactoryComponentProvider) currBlock, pb.getFactoryComponent())) {
                    // Wrong type of block
                    PlayerHelper.sendChatMessage(playerEntity,
                            StringHelper.translateFormat("chat.woot.intern.validate.incorrect",
                                    expected, pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getY(), found));
                }
            } else {
                // Not a factory block
                PlayerHelper.sendChatMessage(playerEntity,
                        StringHelper.translateFormat("chat.woot.intern.validate.incorrect",
                        expected, pb.getBlockPos().getX(), pb.getBlockPos().getY(), pb.getBlockPos().getY(), found));
            }
        }
    }

    static Block getComponentBlock(FactoryComponent component) {
        switch (component) {
            case FACTORY_A: return ModBlocks.FACTORY_A_BLOCK;
            case FACTORY_B: return ModBlocks.FACTORY_B_BLOCK;
            case FACTORY_C: return ModBlocks.FACTORY_C_BLOCK;
            case FACTORY_D: return ModBlocks.FACTORY_D_BLOCK;
            case FACTORY_E: return ModBlocks.FACTORY_E_BLOCK;
            case FACTORY_CONNECT: return ModBlocks.FACTORY_CONNECT_BLOCK;
            case FACTORY_CTR_BASE: return ModBlocks.FACTORY_CTR_BASE_BLOCK;
            case FACTORY_UPGRADE: return ModBlocks.FACTORY_UPGRADE_BLOCK;
            case HEART: return ModBlocks.HEART_BLOCK;
            case CAP_A: return ModBlocks.CAP_A_BLOCK;
            case CAP_B: return ModBlocks.CAP_B_BLOCK;
            case CAP_C: return ModBlocks.CAP_C_BLOCK;
            case CAP_D: return ModBlocks.CAP_D_BLOCK;
            case IMPORT: return ModBlocks.IMPORT_BLOCK;
            case EXPORT: return ModBlocks.EXPORT_BLOCK;
            case CONTROLLER: return ModBlocks.CONTROLLER_BLOCK;
            default: return ModBlocks.FACTORY_A_BLOCK;
        }
    }



}
