package ipsis.woot.modules.factory.layout;

import ipsis.woot.Woot;
import ipsis.woot.config.Config;
import ipsis.woot.modules.factory.*;
import ipsis.woot.modules.factory.blocks.ExoticBlock;
import ipsis.woot.modules.factory.blocks.UpgradeBlock;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
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
            //LOGGER.debug("scanForTier: {} {}", tier, absolutePattern == null ? "bad" : "good");
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

        Woot.setup.getLogger().debug("compareToWorld: {}", absolutePattern.getTier());

        BlockPos heartPos = null;
        for (PatternBlock p : absolutePattern.getBlocks()) {
            if (p.getFactoryComponent() == FactoryComponent.HEART)
                heartPos = new BlockPos(p.getBlockPos());
        }

        if (heartPos == null)
            return false;

        BlockPos primaryControllerPos;
        primaryControllerPos = new BlockPos(heartPos.offset(absolutePattern.facing, 1).add(0,-2,0));

        if (absolutePattern.getTier() == Tier.TIER_5) {
            BlockState exoticBlockState = world.getBlockState(heartPos.up());
            if (exoticBlockState.getBlock() instanceof ExoticBlock) {
                Block b = exoticBlockState.getBlock();
                Exotic exotic = ((ExoticBlock) b).getExotic();
                absolutePattern.setExotic(((ExoticBlock) b).getExotic());
            }
        } else {
            absolutePattern.setExotic(Exotic.NONE);
        }

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

            if (p.getFactoryComponent() == FactoryComponent.CELL)
                expected = StringHelper.translate("info.woot.intern.cell");

            if (!(currBlock instanceof FactoryComponentProvider)) {
                // Controllers are special as they are optional
                if (p.getFactoryComponent() != FactoryComponent.CONTROLLER) {
                    feedback.add(StringHelper.translateFormat(
                            "chat.woot.intern.validate.missing",
                            expected, p.getBlockPos().getX(), p.getBlockPos().getY(), p.getBlockPos().getZ()));
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

            if (p.getFactoryComponent() == FactoryComponent.FACTORY_UPGRADE) {
                BlockState blockState = world.getBlockState(p.getBlockPos());
                if (blockState.get(UpgradeBlock.UPGRADE) != Perk.EMPTY)
                    absolutePattern.addPerk(blockState.get(UpgradeBlock.UPGRADE));
            }

            /**
             * Fails if the controller is not valid for the tier
             */
            if (p.getFactoryComponent() == FactoryComponent.CONTROLLER) {
                TileEntity te = world.getTileEntity(p.getBlockPos());
                if (te instanceof ControllerTileEntity) {
                    FakeMob fakeMob = ((ControllerTileEntity) te).getFakeMob();
                    if (fakeMob.isValid()) {
                        Tier mobTier = Config.OVERRIDE.getMobTier(fakeMob, world);

                        /**
                         * Ignore if blacklisted
                         * Ignore if wrong tier
                         */
                        if (!PolicyRegistry.get().canCaptureEntity(fakeMob.getResourceLocation())) {
                            Woot.setup.getLogger().debug("compareToWorld:    {} is invalid policy", fakeMob);
                            feedback.add(StringHelper.translateFormat(
                                    "chat.woot.intern.validate.blacklisted",
                                    p.getBlockPos().getX(), p.getBlockPos().getY(), p.getBlockPos().getZ()));
                        } else if (!absolutePattern.getTier().isValidForTier(mobTier)) {
                            Woot.setup.getLogger().debug("compareToWorld: {} is invalid tier", fakeMob);
                            feedback.add(StringHelper.translateFormat(
                                    "chat.woot.intern.validate.wrongtier",
                                    p.getBlockPos().getX(), p.getBlockPos().getY(), p.getBlockPos().getZ(),
                                    absolutePattern.getTier()));
                        } else {
                            // This is a valid controller
                            absolutePattern.addControllerPos(p.getBlockPos());
                            if (primaryControllerPos.equals(p.getBlockPos())) {
                                Woot.setup.getLogger().debug("compareToWorld: Found primary controller {}", fakeMob);
                                foundPrimaryController = true;
                                absolutePattern.addMob(fakeMob);
                            } else {
                                Woot.setup.getLogger().debug("compareToWorld: Found valid secondary controller {}", fakeMob);
                                absolutePattern.addMob(fakeMob);
                            }
                        }
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
        if (valid == false) {
            absolutePattern.clearMobs();
            absolutePattern.clearControllerPos();
            absolutePattern.clearPerks();
        }
        LOGGER.debug("compareToWorld: {}", absolutePattern);
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

        //Woot.setup.getLogger().debug("isPatternEqual: pattern1:{} pattern2:{}", pattern1, pattern2);
        if (pattern1.tier != pattern2.tier)
            return false;

        if (pattern1.mobs.size() != pattern2.mobs.size())
            return false;

        for (FakeMob p : pattern1.mobs)
            if (!pattern2.mobs.contains(p))
                return false;

        if (pattern1.perks.size() != pattern2.perks.size())
            return false;

        if (pattern1.exotic != pattern2.exotic)
            return false;

        // Does the new perk setup equal the old setup
        for (Perk p : pattern2.perks)
            if (!pattern1.perks.contains(p))
                return false;

        //Woot.setup.getLogger().debug("isPatternEqual: old and new patterns are equal");
        return true;
    }
}
