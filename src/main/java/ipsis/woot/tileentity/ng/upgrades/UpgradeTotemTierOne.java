package ipsis.woot.tileentity.ng.upgrades;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.tileentity.ng.farmblocks.IFarmBlockUpgrade;
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

        TileEntity te = world.getTileEntity(origin);

        if (te instanceof IFarmBlockUpgrade) {
            EnumSpawnerUpgrade upgrade = ((IFarmBlockUpgrade) te).getUpgrade();
            if (upgrade.getTier() == 1) {
                spawnerUpgrade = upgrade;
                spawnerUpgradeLevel = 1;
                blockPosList.add(new BlockPos(origin));
            }
        }
    }

    public UpgradeTotemTierOne(World world, BlockPos pos) {
        super(world, pos);
    }
}
