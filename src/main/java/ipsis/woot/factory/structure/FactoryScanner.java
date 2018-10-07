package ipsis.woot.factory.structure;

import ipsis.Woot;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.factory.structure.pattern.ScannedPattern;
import ipsis.woot.util.FactoryTier;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FactoryScanner {

    /**
     * Scan for the specific tier and report and errors
     */
    public static void scanTier(World world, FactoryTier tier, BlockPos origin, EnumFacing facing) {

        /*
        AbsolutePattern absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(world, tier, origin, facing);
        ScannedPattern scannedTierPattern = absolutePattern.compareToWorld();
        */

        // TODO controller, remote and upgrades
    }

    /**
     * Scan each possible tier looking for a match
     */
    public static void scan(World world, BlockPos origin, EnumFacing facing) {

        // Find the first tier that matches
        /*
        ScannedPattern scannedTierPattern = null;
        for (FactoryTier factoryTier : FactoryTier.values()) {
            AbsolutePattern absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(world, factoryTier, origin, facing);
            ScannedPattern tempPattern = absolutePattern.compareToWorldQuick();
            if (!tempPattern.hasBadBlocks()) {
                scannedTierPattern = tempPattern;
                break;
            }
        }

        if (scannedTierPattern != null) {
            // TODO controller, remote and upgrades
        } */
    }
}
