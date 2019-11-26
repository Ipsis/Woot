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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FactoryScanner {

    private static final Logger LOGGER = LogManager.getLogger();

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

            AbsolutePattern absolutePattern = scanTier(world, tier, origin, facing);
            LOGGER.debug("scanForTier: {} {}", tier, absolutePattern == null ? "bad" : "good");
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

    public static boolean compareToWorld(AbsolutePattern absolutePattern, World world, List<String> feedback) {

        boolean valid = true;
        boolean foundPrimaryController = false;

        BlockPos heartPos = null;
        for (PatternBlock p : absolutePattern.getBlocks()) {
            if (p.getFactoryComponent() == FactoryComponent.HEART)
                heartPos = new BlockPos(p.getBlockPos());
        }

        if (heartPos == null)
            return false;

        BlockPos primaryControllerPos;
        primaryControllerPos = new BlockPos(heartPos.offset(absolutePattern.facing, 1).add(0,-2,0));

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
                    }
                    if (primaryControllerPos.equals(p.getBlockPos())) {
                        Woot.LOGGER.debug("compareToWorld: Found primary controller");
                        foundPrimaryController = true;
                    }
                }
            }
        }

        if (foundPrimaryController == false) {
            feedback.add(StringHelper.translateFormat("chat.woot.intern.validate.noprimary",
                    primaryControllerPos.getX(), primaryControllerPos.getY(), primaryControllerPos.getZ()
                    ));
            valid = false;
        }

        //feedback.forEach(s -> LOGGER.debug("compareToWorld: {}", s));
        return valid;
    }

    /**
     * This identifies any change in the two patterns, including things like
     * the upgrade mods or controllers changing.
     * This will be used to trigger a new factory recipe calculation.
     */
    public static boolean isPatternEqual(@Nonnull World world, AbsolutePattern pattern1, AbsolutePattern pattern2) {

        if (pattern1 == null || pattern2 == null)
            return false;

        if (pattern1.tier != pattern2.tier)
            return false;

        List<FakeMob> pattern1Mobs = new ArrayList<>();
        List<FakeMob> pattern2Mobs = new ArrayList<>();

        for (PatternBlock pb : pattern1.getBlocks()) {
            if (pb.getFactoryComponent() == FactoryComponent.CONTROLLER) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof ControllerTileEntity)
                    pattern1Mobs.add(((ControllerTileEntity) te).getFakeMob());
            }
        }

        for (PatternBlock pb : pattern2.getBlocks()) {
            if (pb.getFactoryComponent() == FactoryComponent.CONTROLLER) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof ControllerTileEntity)
                    pattern2Mobs.add(((ControllerTileEntity) te).getFakeMob());
            }
        }

        if (pattern1Mobs.size() != pattern2Mobs.size())
            return false;

        if (!pattern1Mobs.equals(pattern2Mobs))
            return false;

        Woot.LOGGER.debug("isPatternEqual: old and new patterns are equal");
        return true;
    }
}
