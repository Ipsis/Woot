package ipsis.woot.factory.structure;

import ipsis.Woot;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.factory.structure.pattern.ScannedPattern;
import ipsis.woot.util.FactoryTier;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FactoryScanner {

    /**
     * Scan for the specific tier. The will always return a scanned pattern, however it may or may not be complete.
     */
    public static @Nonnull ScannedPattern scanTier(World world, FactoryTier tier, BlockPos origin, EnumFacing facing) {

        AbsolutePattern absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(world, tier, origin, facing);
        return absolutePattern.compareToWorld(world);
    }

    /**
     * Scans each possible tier looking for an exact match.
     * @return the scanned blocks or null if no match was found
     */
    public static @Nullable ScannedPattern scan(World world, BlockPos origin, EnumFacing facing) {

        ScannedPattern scannedPattern = null;
        for (FactoryTier tier : FactoryTier.values()) {
            AbsolutePattern absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(world, tier, origin, facing);
            ScannedPattern tempPattern = absolutePattern.compareToWorldQuick(world);
            if (tempPattern != null) {
                scannedPattern = tempPattern;
                break;
            }
        }

        return scannedPattern;
    }
}
