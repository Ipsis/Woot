package ipsis.woot.modules.factory;

import ipsis.woot.config.ConfigHelper;
import ipsis.woot.config.WootConfig;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.blocks.UpgradeTileEntity;
import ipsis.woot.modules.factory.layout.Layout;
import ipsis.woot.modules.factory.layout.PatternBlock;
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
    HashMap<PerkType, Integer> perks = new HashMap<>();
    List<FakeMob> mobs = new ArrayList<>();
    BlockPos importPos;
    BlockPos exportPos;
    BlockPos cellPos;
    private int maxMobCount = -1;

    public List<FakeMob> getMobs() { return mobs; }
    public HashMap<PerkType, Integer> getPerks() { return perks; }
    public BlockPos getExportPos() { return exportPos; }
    public BlockPos getImportPos() { return importPos; }
    public BlockPos getCellPos() { return cellPos; }

    public int getMaxMobCount() {
        if (maxMobCount == -1) {
            int mobCount = WootConfig.get().getIntConfig(WootConfig.ConfigKey.MASS_COUNT); // upgrades
            int level = 0;
            if (perks.containsKey(PerkType.MASS)) {
                level = perks.get(PerkType.MASS);
                mobCount = ConfigHelper.getIntValueForPerk(PerkType.MASS, level);
            }

            for (FakeMob mob : mobs) {
                int count = WootConfig.get().getIntConfig(mob, WootConfig.ConfigKey.MASS_COUNT);
                if (perks.containsKey(PerkType.MASS))
                    count = ConfigHelper.getIntValueForPerk(mob, PerkType.MASS, level);

                // Smallest mass allowed
                if (count < mobCount)
                    mobCount = count;
            }
            maxMobCount = mobCount;
        }
        return maxMobCount;
    }

    Setup() {}

    public static Setup createFromLayout(World world, Layout layout) {
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
                        setup.mobs.add(new FakeMob(fakeMob));
                }
            } else if (pb.getFactoryComponent() == FactoryComponent.FACTORY_UPGRADE) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof UpgradeTileEntity) {
                    Perk upgrade = ((UpgradeTileEntity) te).getUpgrade(world.getBlockState(pb.getBlockPos()));
                    if (upgrade != Perk.EMPTY ) {
                        PerkType type = Perk.getType(upgrade);
                        int level = Perk.getLevel(upgrade);

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

                        setup.perks.put(type, level);
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

        for (PerkType upgrade : perks.keySet())
            s += " upgrade: " + upgrade + "/" + perks.get(upgrade);

        return s;
    }
}
