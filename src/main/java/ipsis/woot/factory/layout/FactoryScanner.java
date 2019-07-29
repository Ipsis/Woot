package ipsis.woot.factory.layout;

import ipsis.woot.Woot;
import ipsis.woot.common.Config;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryComponentProvider;
import ipsis.woot.factory.Tier;
import ipsis.woot.factory.blocks.ControllerTileEntity;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FactoryScanner {

    /**
     * Scan for the specified tier.
     */
    public static @Nullable AbsolutePattern scanTier(World world, Tier tier, BlockPos origin, Direction facing) {
        AbsolutePattern absolutePattern = AbsolutePattern.create(world, tier, origin, facing);
        return compareToWorldQuick(absolutePattern, world);
    }

    public static @Nullable AbsolutePattern scanForTier(World world, BlockPos origin, Direction facing) {

        // Remember to skip invalid
        for (int i = Tier.VALUES.length - 1; i >= 0; i--) {
            Tier tier = Tier.byIndex(i);
            if (tier == Tier.UNKNOWN)
                break;

            Woot.LOGGER.info("scanForTier: {}", tier);
            AbsolutePattern absolutePattern = scanTier(world, tier, origin, facing);
            if (absolutePattern != null)
                return absolutePattern;
        }

        return null;
    }

    public static @Nullable AbsolutePattern compareToWorldQuick(AbsolutePattern absolutePattern, World world) {

        List<String> feedback = new ArrayList<>();
        boolean valid = compareToWorld(absolutePattern, world, feedback);
        return valid ? absolutePattern : null;
    }

    /*

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
    } */

    public static boolean compareToWorld(AbsolutePattern absolutePattern, World world, List<String> feedback) {

        boolean valid = true;
        int controllerCount = 0;
        for (PatternBlock p : absolutePattern.getBlocks()) {

            // Don't load an unloaded chunk
            if (!world.isBlockLoaded(p.getBlockPos())) {
                valid = false;
                continue;
            }

            BlockState currState = world.getBlockState(p.getBlockPos());
            Block currBlock = currState.getBlock();

            String found = StringHelper.translate(currBlock.getTranslationKey());
            String expected = StringHelper.translate(p.getFactoryComponent().getTranslationKey());
            if (!(currBlock instanceof FactoryComponentProvider)) {
                // Controllers are special as they are optional
                if (p.getFactoryComponent() != FactoryComponent.CONTROLLER) {
                    feedback.add(StringHelper.translateFormat(
                            "chat.woot.intern.validate.missing",
                            expected, p.getBlockPos().getX(), p.getBlockPos().getZ(), p.getBlockPos().getZ()));
                    valid = false;
                }
                continue;
            }

            FactoryComponentProvider factoryComponent = (FactoryComponentProvider)currBlock;
            if (!FactoryComponent.isSameComponentFuzzy(factoryComponent.getFactoryComponent(), p.getFactoryComponent())) {
                feedback.add(StringHelper.translateFormat(
                        "chat.woot.intern.validate.incorrect",
                        expected, p.getBlockPos().getX(), p.getBlockPos().getY(), p.getBlockPos().getZ(), found));
                valid = false;
                continue;
            }

            /**
             * Fails if the controller is not valid for the tier
             */
            if (p.getFactoryComponent() == FactoryComponent.CONTROLLER) {
                TileEntity te = world.getTileEntity(p.getBlockPos());
                if (te instanceof ControllerTileEntity) {
                    FakeMob fakeMob = ((ControllerTileEntity) te).getFakeMob();
                    Tier mobTier = Config.getMobTier(fakeMob, world);
                    if (!absolutePattern.getTier().isValidForTier(mobTier)) {
                        feedback.add(StringHelper.translateFormat(
                                "chat.woot.intern.validate.wrongtier",
                                 p.getBlockPos().getX(), p.getBlockPos().getY(), p.getBlockPos().getZ(),
                                absolutePattern.getTier()));
                    } else {
                        controllerCount++;
                    }
                    // TODO blacklisted ???
                }
            }
        }

        if (controllerCount == 0) {
            feedback.add(StringHelper.translate("chat.woot.intern.validate.nocontroller"));
            valid = false;
        }

        return valid;
    }
}
