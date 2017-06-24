package ipsis.woot.farmstructure;

import ipsis.woot.util.EnumSpawnerUpgrade;
import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.farmblocks.IFarmBlockUpgrade;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UpgradeTotemTierTwo extends AbstractUpgradeTotem {

    @Override
    public void scan() {

        if (!world.isBlockLoaded(origin))
            return;

        // Tier 1 upgrade
        TileEntity te = world.getTileEntity(origin);
        if (!(te instanceof IFarmBlockUpgrade))
            return;

        EnumSpawnerUpgrade firstUpgrade = ((IFarmBlockUpgrade) te).getUpgrade();
        if (firstUpgrade.getTier() != 1)
            return;

        spawnerUpgrade = firstUpgrade;
        spawnerUpgradeLevel = 1;
        blockPosList.add(new BlockPos(origin));

        // Tier 2 upgrade
        te = world.getTileEntity(new BlockPos(origin.getX(), origin.getY() + 1, origin.getZ()));
        if (!(te instanceof IFarmBlockUpgrade))
            return;

        EnumSpawnerUpgrade upgrade = ((IFarmBlockUpgrade) te).getUpgrade();
        if (upgrade.getTier() != 2)
            return;

        if (EnumFarmUpgrade.getFromEnumSpawnerUpgrade(firstUpgrade) != EnumFarmUpgrade.getFromEnumSpawnerUpgrade(upgrade))
            return;

        spawnerUpgradeLevel = 2;
        blockPosList.add(new BlockPos(origin.getX(), origin.getY() + 1, origin.getZ()));
    }

    public UpgradeTotemTierTwo(World world, BlockPos pos) {
        super(world, pos);
    }
}
