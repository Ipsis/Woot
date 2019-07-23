package ipsis.woot.factory.layout;

import ipsis.woot.Woot;
import ipsis.woot.factory.Tier;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FactoryScanner {

    /**
     * Scan for the specified tier.
     */
    public static @Nullable AbsolutePattern scanTier(World world, Tier tier, BlockPos origin, Direction facing) {
        AbsolutePattern absolutePattern = AbsolutePattern.create(world, tier, origin, facing);
        return FactoryHelper.compareToWorldQuick(absolutePattern, world);
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
}
