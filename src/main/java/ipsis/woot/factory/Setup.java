package ipsis.woot.factory;

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

    public List<FakeMob> getMobs() { return mobs; }
    public HashMap<FactoryUpgradeType, Integer> getUpgrades() { return upgrades; }
    public BlockPos getExportPos() { return exportPos; }
    public BlockPos getImportPos() { return importPos; }

    Setup() {}

    public static Setup creatFromLayout(World world, Layout layout) {
        Setup setup = new Setup();
        setup.tier = layout.getAbsolutePattern().getTier();

        for (PatternBlock pb : layout.getAbsolutePattern().getBlocks()) {
            if (pb.getFactoryComponent() == FactoryComponent.IMPORT) {
                setup.importPos = new BlockPos(pb.getBlockPos());
            } else if (pb.getFactoryComponent() == FactoryComponent.EXPORT) {
                setup.exportPos = new BlockPos(pb.getBlockPos());
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
                    FactoryUpgrade upgrade = ((UpgradeTileEntity) te).getUpgrade();
                    if (upgrade != null ) {
                        FactoryUpgradeType type = FactoryUpgrade.getType(upgrade);
                        int level = FactoryUpgrade.getLevel(upgrade);

                        /**
                         * Tier 1 - level 1 upgrades only
                         * Tier 2 - level 1,2 upgrades only
                         * Tier 3+ - all upgrades
                         */
                        if (setup.tier == Tier.TIER_1 && level > 1)
                            level = 1;
                        else if (setup.tier == Tier.TIER_2 && level > 2)
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
