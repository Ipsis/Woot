package ipsis.woot.factory.multiblock;

import ipsis.woot.factory.FactoryTier;
import ipsis.woot.factory.layout.AbsolutePattern;
import ipsis.woot.factory.layout.AbsolutePatternBuilder;
import ipsis.woot.tools.FactoryHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FactoryScanner {

    /**
     * Scan for the specific tier. The will always return a scanned pattern, however it may or may not be complete.
     */
    public static @Nullable
    AbsolutePattern scanTier(World world, FactoryTier tier, BlockPos origin, EnumFacing facing) {
        AbsolutePattern absolutePattern = AbsolutePatternBuilder.createAbsolutePattern(world, tier, origin,  facing);
        return FactoryHelper.compareToWorldQuick(absolutePattern, world);
    }

    public static @Nullable
    AbsolutePattern scan(World world, BlockPos origin, EnumFacing facing) {
        for (FactoryTier factoryTier : FactoryTier.values()) {
            AbsolutePattern absolutePattern = scanTier(world, factoryTier, origin, facing);
            if (absolutePattern != null)
                return absolutePattern;
        }
        return null;
    }
}
