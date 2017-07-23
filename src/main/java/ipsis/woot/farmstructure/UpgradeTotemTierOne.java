package ipsis.woot.farmstructure;

import ipsis.woot.util.EnumSpawnerUpgrade;
import ipsis.woot.farmblocks.IFarmBlockUpgrade;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Totem can only be one tall and must be a tier 1 upgrade
 */
public class UpgradeTotemTierOne extends AbstractUpgradeTotem {

    @Override
    public void scan() {

        if (!world.isBlockLoaded(origin))
            return;

        EnumSpawnerUpgrade baseUpgrade = TotemHelper.getUpgrade(world, origin, 1);
        if (baseUpgrade == null)
            return;

        if (TotemHelper.getTier(world, origin, 1) != 1)
            return;

        spawnerUpgrade = baseUpgrade;
        spawnerUpgradeLevel = 1;
        blockPosList.add(new BlockPos(origin));
    }

    public UpgradeTotemTierOne(World world, BlockPos pos) {
        super(world, pos);
    }
}
