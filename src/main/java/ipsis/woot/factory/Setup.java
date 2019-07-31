package ipsis.woot.factory;

import ipsis.woot.common.Config;
import ipsis.woot.factory.blocks.ControllerTileEntity;
import ipsis.woot.factory.blocks.UpgradeTileEntity;
import ipsis.woot.factory.layout.Layout;
import ipsis.woot.factory.layout.PatternBlock;
import ipsis.woot.util.FakeMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Defines the valid configuration of the formed factory.
 * It already takes into account any blacklisted upgrades etc.
 */
public class Setup {

    Tier tier;
    HashMap<FactoryUpgradeType, Integer> upgrades = new HashMap<>();
    List<FakeMob> mobs = new ArrayList<>();
    BlockPos importPos;
    BlockPos exportPos;
    BlockPos cellPos;
    private int maxMobCount = -1;

    public List<FakeMob> getMobs() { return mobs; }
    public HashMap<FactoryUpgradeType, Integer> getUpgrades() { return upgrades; }
    public BlockPos getExportPos() { return exportPos; }
    public BlockPos getImportPos() { return importPos; }
    public BlockPos getCellPos() { return cellPos; }

    public int getMaxMobCount() {
        if (maxMobCount == -1) {
            int mobCount = Config.COMMON.MASS_COUNT.get(); // no ugrades
            int level = 0;
            if (upgrades.containsKey(FactoryUpgradeType.MASS)) {
                level = upgrades.get(FactoryUpgradeType.MASS);
                mobCount = Config.getIntValueForUpgrade(FactoryUpgradeType.MASS, level);
            }

            for (FakeMob mob : mobs) {
                int count = Config.getIntValueForUpgrade(mob, FactoryUpgradeType.MASS, 0);
                if (upgrades.containsKey(FactoryUpgradeType.MASS))
                    count = Config.getIntValueForUpgrade(mob, FactoryUpgradeType.MASS, level);

                // Smallest mass allowed
                if (count < mobCount)
                    mobCount = count;
            }
            maxMobCount = mobCount;
        }
        return maxMobCount;
    }

    Setup() {}

    public static Setup creatFromLayout(World world, Layout layout) {
        Setup setup = new Setup();
        setup.tier = layout.getAbsolutePattern().getTier();

        for (PatternBlock pb : layout.getAbsolutePattern().getBlocks()) {
            if (pb.getFactoryComponent() == FactoryComponent.IMPORT) {
                setup.importPos = new BlockPos(pb.getBlockPos());
            } else if (pb.getFactoryComponent() == FactoryComponent.EXPORT) {
                setup.exportPos = new BlockPos(pb.getBlockPos());
            } else if (pb.getFactoryComponent() == FactoryComponent.CELL) {
                setup.cellPos = new BlockPos(pb.getBlockPos());
            } else if (pb.getFactoryComponent() == FactoryComponent.CONTROLLER) {

                // Factory will only be formed if the controller is valid
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof ControllerTileEntity) {
                    FakeMob fakeMob = ((ControllerTileEntity) te).getFakeMob();
                    if (fakeMob.isValid())
                        setup.mobs.add(new FakeMob(fakeMob.getEntityKey(), fakeMob.getTag()));
                }
            } else if (pb.getFactoryComponent() == FactoryComponent.FACTORY_UPGRADE) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof UpgradeTileEntity) {
                    FactoryUpgrade upgrade = ((UpgradeTileEntity) te).getUpgrade(world.getBlockState(pb.getBlockPos()));
                    if (upgrade != FactoryUpgrade.EMPTY ) {
                        FactoryUpgradeType type = FactoryUpgrade.getType(upgrade);
                        int level = FactoryUpgrade.getLevel(upgrade);

                        /**
                         * Tier 1,2 - level 1 upgrades only
                         * Tier 3 - level 1,2 upgrades only
                         * Tier 4+ - all upgrades
                         */
                        if (setup.tier == Tier.TIER_1 && level > 1)
                            level = 1;
                        else if (setup.tier == Tier.TIER_2 && level > 1)
                            level = 1;
                        else if (setup.tier == Tier.TIER_3 && level > 2)
                            level = 2;

                        setup.upgrades.put(type, level);
                    }
                }
            }
        }
        return setup;
    }

    @Override
    public String toString() {
        String s = "tier:" + tier;
        for (FakeMob fakeMob : mobs)
            s += " mob:" + fakeMob;

        for (FactoryUpgradeType upgrade : upgrades.keySet())
            s += " upgrade: " + upgrade + "/" + upgrades.get(upgrade);

        return s;
    }
}
