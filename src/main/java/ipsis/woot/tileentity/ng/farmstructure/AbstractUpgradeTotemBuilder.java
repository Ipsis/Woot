package ipsis.woot.tileentity.ng.farmstructure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AbstractUpgradeTotemBuilder {

    public static @Nullable
    AbstractUpgradeTotem build(Class clazz, World world, BlockPos pos) {

        if (clazz == UpgradeTotemTierOne.class)
            return new UpgradeTotemTierOne(world, pos);
        else if (clazz == UpgradeTotemTierOne.class)
            return new UpgradeTotemTierTwo(world, pos);
        else if (clazz == UpgradeTotemTierThree.class)
            return new UpgradeTotemTierTwo(world, pos);

        return null;
    }
}
