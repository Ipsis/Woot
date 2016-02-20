package ipsis.woot.tileentity;

import ipsis.oss.LogHelper;
import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileEntityMobFarm extends TileEntity implements ITickable {

    EnumFacing facing;
    EnumMobFactoryTier factoryTier;
    String mobName;

    boolean dirtyStructure;
    boolean dirtyUpgrade;
    List<BlockPos> structureBlockList = new ArrayList<BlockPos>();
    List<BlockPos> upgradeBlockList = new ArrayList<BlockPos>();
    List<SpawnerUpgrade> upgradeList = new ArrayList<SpawnerUpgrade>();

    static final int MULTIBLOCK_BACKOFF_SCAN_TICKS = 20;

    public TileEntityMobFarm() {

        this.facing = EnumFacing.SOUTH;
        this.dirtyStructure = true;
        this.dirtyUpgrade = false;
        this.factoryTier = null;
        this.mobName = "Pig";
    }

    public void setFacing(EnumFacing facing) {

        this.facing = facing;
    }

    public EnumFacing getFacing() {

        return this.facing;
    }

    public boolean isFormed() {

        return factoryTier != null && mobName.equals("");
    }

    void updateStructureBlocks(boolean connected) {

        for (BlockPos p : structureBlockList) {
            if (worldObj.isBlockLoaded(p)) {
                TileEntity te = worldObj.getTileEntity(p);
                if (te instanceof TileEntityMobFactoryStructure) {
                    if (connected)
                        ((TileEntityMobFactoryStructure) te).setMaster(this);
                    else
                        ((TileEntityMobFactoryStructure) te).clearMaster();
                }
            }
        }
    }

    void disconnectUpgradeBlocks(boolean connected) {

        for (BlockPos p : upgradeBlockList) {
            if (worldObj.isBlockLoaded(p)) {
                TileEntity te = worldObj.getTileEntity(p);
                if (te instanceof TileEntityMobFactoryUpgrade) {
                    if (connected)
                        ((TileEntityMobFactoryUpgrade) te).setMaster(this);
                    else
                        ((TileEntityMobFactoryUpgrade) te).clearMaster();
                }
            }
        }
    }

    void onStructureCheck() {

        LogHelper.info("onStructureChanged");

        EnumMobFactoryTier oldFactoryTier = factoryTier;
        MobFactoryMultiblockLogic.FactorySetup factorySetup = MobFactoryMultiblockLogic.validateFactory(this);

        if (factorySetup.getSize() == null) {
            LogHelper.info("onStructureChanged: new size is null");
            updateStructureBlocks(false);
            disconnectUpgradeBlocks(false);
            factoryTier = factorySetup.getSize();
            mobName = "";
            return;
        }

        if (oldFactoryTier != factoryTier) {
            LogHelper.info("onStructureChanged: new size != old size");
            updateStructureBlocks(false);
        }

        factoryTier = factorySetup.getSize();
        mobName = factorySetup.getMobName();
        structureBlockList = factorySetup.getBlockPosList();
        updateStructureBlocks(true);

        onUpgradeCheck();
    }

    void onUpgradeCheck() {

        LogHelper.info("onUpgradeCheck: " + factoryTier);

        upgradeList.clear();
        if (factoryTier == EnumMobFactoryTier.TIER_ONE)
            upgradeTier1();
        else if (factoryTier == EnumMobFactoryTier.TIER_TWO)
            upgradeTier2();
        else if (factoryTier == EnumMobFactoryTier.TIER_THREE)
            upgradeTier3();

        for (SpawnerUpgrade u : upgradeList)
            LogHelper.info("onUpgradeCheck: " + u.getUpgradeType() + "/" + u.getUpgradeTier());
    }

    void upgradeTierX(BlockPos[] upgradePos, int maxTier) {

        for (BlockPos p : upgradePos) {

            BlockPos offset = BlockPosHelper.rotateFromSouth(p, getFacing().getOpposite());
            BlockPos p2 = getPos().add(offset.getX(), offset.getY(), offset.getZ());
            SpawnerUpgrade upgrade = UpgradeManager.scanUpgradeTotem(worldObj, p2, maxTier);
            if (upgrade != null)
                upgradeList.add(upgrade);
        }
    }

    void upgradeTier1() {

        BlockPos[] upgradePos = new BlockPos[] {
                new BlockPos(1, 0, 0),
                new BlockPos(-1, 0, 0)
        };

        upgradeTierX(upgradePos, 1);
    }

    void upgradeTier2() {

        BlockPos[] upgradePos = new BlockPos[] {
                new BlockPos(1, 0, 0),
                new BlockPos(-1, 0, 0),
                new BlockPos(2, 0, 0),
                new BlockPos(-2, 0, 0)
        };

        upgradeTierX(upgradePos, 2);
    }

    void upgradeTier3() {

        BlockPos[] upgradePos = new BlockPos[] {
                new BlockPos(1, 0, 0),
                new BlockPos(-1, 0, 0),
                new BlockPos(2, 0, 0),
                new BlockPos(-2, 0, 0)
        };

        upgradeTierX(upgradePos, 3);
    }

    @Override
    public void update() {

        if (worldObj.isRemote)
            return;

        if (dirtyStructure && worldObj.getWorldTime() % MULTIBLOCK_BACKOFF_SCAN_TICKS == 0) {
            onStructureCheck();
            dirtyStructure = false;
            dirtyUpgrade = false;
            LogHelper.info("update: structure->recalc spawner requirements");
        }

        if (dirtyUpgrade && worldObj.getWorldTime() % MULTIBLOCK_BACKOFF_SCAN_TICKS == 0) {
            onUpgradeCheck();
            dirtyUpgrade = false;
            LogHelper.info("update: upgrade->recalc spawner requirements");
        }

        if (!isFormed())
            return;
    }

    public void interruptStructure() {

        LogHelper.info("interruptStructure");
        dirtyStructure = true;
    }

    public void interruptUpgrade() {

        LogHelper.info("interruptUpgrade");
        dirtyUpgrade = true;
    }
}
