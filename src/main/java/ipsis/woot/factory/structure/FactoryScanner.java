package ipsis.woot.factory.structure;

import ipsis.Woot;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.factory.structure.pattern.FactoryPattern;
import ipsis.woot.factory.structure.pattern.ScannedPattern;
import ipsis.woot.util.FactoryTier;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FactoryScanner {

    /**
     * Scan for the specific tier and report and errors
     */
    public static ScannedPattern scanTier(World world, FactoryTier tier, BlockPos origin, EnumFacing facing) {

        AbsolutePattern absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(world, tier, FactoryPattern.FactoryPatternType.BASE, origin, facing);
        return absolutePattern.compareToWorld(world);
    }

    /**
     * Scan each possible tier looking for a match
     */
    public static @Nullable ScannedPattern scan(World world, BlockPos origin, EnumFacing facing) {

        ScannedPattern scannedPattern = null;
        for (FactoryTier tier : FactoryTier.values()) {
            AbsolutePattern absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(world, tier, FactoryPattern.FactoryPatternType.BASE, origin, facing);
            ScannedPattern tempPattern = absolutePattern.compareToWorldQuick(world);
            if (tempPattern != null) {
                scannedPattern = tempPattern;
                break;
            }
        }

        return scannedPattern;
    }
}
