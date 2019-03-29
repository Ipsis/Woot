package ipsis.woot.factory.multiblock;

import ipsis.woot.config.PolicyConfig;
import ipsis.woot.factory.FactoryTier;
import ipsis.woot.factory.TileEntityController;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.factory.layout.PatternBlock;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.MobHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class FactoryConfig {

    private BlockPos cellPos;
    private BlockPos exportPos;
    private BlockPos importPos;
    private BlockPos controllerPos;
    private List<BlockPos> upgradePos = new ArrayList<>();
    private FactoryTier factoryTier = FactoryTier.TIER_1;
    private List<FactoryConfigMob> mobs = new ArrayList<>();

    public BlockPos getCellPos() { return cellPos; }
    public BlockPos getExportPos() { return exportPos; }
    public BlockPos getImportPos() { return importPos; }
    public BlockPos getControllerPos() { return controllerPos; }
    public FactoryTier getFactoryTier() { return factoryTier; }
    public List<FakeMobKey> getValidMobs() {
        List<FakeMobKey> keys = new ArrayList<>();
        for (FactoryConfigMob mob : mobs)
            if (mob.state == FactoryConfigMob.MobState.VALID)
                keys.add(mob.fakeMobKey);
        return keys;
    }

    public static class FactoryConfigMob {
        enum MobState {
            VALID, BLACKLISTED, WRONG_TIER
        };

        public FakeMobKey fakeMobKey;
        public MobState state;
        public FactoryConfigMob(FakeMobKey fakeMobKey, MobState state) {
            this.fakeMobKey = fakeMobKey;
            this.state = state;
        }

        @Override
        public String toString() {
            return fakeMobKey + "(" + state + ")";
        }
    }

    @Override
    public String toString() {
        String tmp = factoryTier + " ";
        for (FactoryConfigMob mob : mobs)
            tmp += mob + " ";
        return tmp;
    }

    public static FactoryConfig createFromLayout(World world, @Nonnull FactoryLayout layout) {

        if (world == null || !layout.isFormed() || layout.getAbsolutePattern() == null)
            return null;

        FactoryConfig factoryConfig = new FactoryConfig();
        for (PatternBlock patternBlock : layout.getAbsolutePattern().getBlocks()) {
            if (patternBlock.getFactoryBlock() == FactoryBlock.IMPORT)
                factoryConfig.importPos = patternBlock.getBlockPos();
            else if (patternBlock.getFactoryBlock() == FactoryBlock.EXPORT)
                factoryConfig.exportPos = patternBlock.getBlockPos();
            else if (patternBlock.getFactoryBlock() == FactoryBlock.CONTROLLER)
                factoryConfig.controllerPos = patternBlock.getBlockPos();
            else if (patternBlock.getFactoryBlock().isCell())
                factoryConfig.cellPos = patternBlock.getBlockPos();
            else if (patternBlock.getFactoryBlock() == FactoryBlock.UPGRADE)
                factoryConfig.upgradePos.add(patternBlock.getBlockPos());
        }

        factoryConfig.factoryTier = layout.getAbsolutePattern().getFactoryTier();

        TileEntity te = world.getTileEntity(factoryConfig.controllerPos);
        if (te instanceof TileEntityController) {
            TileEntityController controller = (TileEntityController)te;
            for (FakeMobKey key : controller.getFakeMobKeyList())
                if (PolicyConfig.canCapture(key.getResourceLocation())) {
                    if (MobHelper.canGenerateFromTier(world, key, factoryConfig.getFactoryTier()))
                        factoryConfig.mobs.add(new FactoryConfigMob(key, FactoryConfigMob.MobState.VALID));
                    else
                        factoryConfig.mobs.add(new FactoryConfigMob(key, FactoryConfigMob.MobState.WRONG_TIER));
                } else {
                    factoryConfig.mobs.add(new FactoryConfigMob(key, FactoryConfigMob.MobState.BLACKLISTED));
                }
        }

        return factoryConfig;
    }
}
