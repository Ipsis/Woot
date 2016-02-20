package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.oss.LogHelper;
import ipsis.woot.manager.SpawnerManager;
import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileEntityMobFarm extends TileEntity implements ITickable {

    EnumFacing facing;
    EnumMobFactoryTier factoryTier;
    String mobName;
    SpawnerManager.EnchantKey enchantKey = SpawnerManager.EnchantKey.NO_ENCHANT;
    SpawnerManager.SpawnReq spawnReq;

    int currLearnTicks;
    int currSpawnTicks;
    int consumedRf;

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
        this.spawnReq = null;

        currLearnTicks = 0;
        currSpawnTicks = 0;
        consumedRf = 0;
    }

    public void setFacing(EnumFacing facing) {

        this.facing = facing;
    }

    public EnumFacing getFacing() {

        return this.facing;
    }

    public boolean isFormed() {

        return factoryTier != null && !mobName.equals("") && spawnReq != null;
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

        spawnReq = Woot.spawnerManager.getSpawnReq(mobName, upgradeList, Woot.spawnerManager.getXp(mobName, this));
        enchantKey = UpgradeManager.getEnchantKey(upgradeList);
        consumedRf = 0;
        currSpawnTicks = 0;
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
                new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0)
        };

        upgradeTierX(upgradePos, 1);
    }

    void upgradeTier2() {

        BlockPos[] upgradePos = new BlockPos[] {
                new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0)
        };

        upgradeTierX(upgradePos, 2);
    }

    void upgradeTier3() {

        BlockPos[] upgradePos = new BlockPos[] {
                new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0)
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

        // Tick forwards
        currLearnTicks++;
        currSpawnTicks++;

        if (currLearnTicks >= Settings.learnTicks) {
            if (!Woot.spawnerManager.isFull(mobName, enchantKey)) {
                /* Not full so fake another spawn */
                LogHelper.info("update: Fake spawn");
                Woot.spawnerManager.spawn(mobName, enchantKey, this.worldObj, this.getPos());
            }
            currLearnTicks = 0;
        }

        /* Do we have any info on this mob yet - should only happen once per mob */
        if (Woot.spawnerManager.isEmpty(mobName, enchantKey)) {
            LogHelper.info("update: No spawn info for " + mobName + ":" + enchantKey);
            return;
        }

        processPower();
        if (currSpawnTicks == spawnReq.getSpawnTime()) {
            onSpawn();
            currSpawnTicks = 0;
        }

    }

    public void interruptStructure() {

        LogHelper.info("interruptStructure");
        dirtyStructure = true;
    }

    public void interruptUpgrade() {

        LogHelper.info("interruptUpgrade");
        dirtyUpgrade = true;
    }


    void processPower() {

        // TODO actually get the drawn rf rather than fake it
        int drawnRf = spawnReq.getRfPerTick() * 1;
        if (drawnRf == spawnReq.getRfPerTick()) {
            consumedRf += drawnRf;
        } else {
            if (Settings.strictPower)
                consumedRf = 0;
            else
                consumedRf += drawnRf;
        }
    }

    void onSpawn() {

        LogHelper.info("Check spawn: " + consumedRf + "/" + spawnReq.getTotalRf());
        if (consumedRf >= spawnReq.getTotalRf()) {
            List<ItemStack> dropList = Woot.spawnerManager.getDrops(mobName, enchantKey);

            LogHelper.info(dropList);

            for (EnumFacing f : EnumFacing.values()) {
                if (worldObj.isBlockLoaded(this.getPos().offset(f))) {
                    TileEntity te = worldObj.getTileEntity(this.getPos().offset(f));
                    if (te == null)
                        continue;

                    if (!te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                        continue;

                    IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());
                    for (int i = 0; i < dropList.size(); i++) {
                        ItemStack result = ItemHandlerHelper.insertItem(capability, ItemHandlerHelper.copyStackWithSize(dropList.get(i), 1), false);
                        if (result != null)
                            dropList.get(i).stackSize = result.stackSize;
                        else
                            dropList.get(i).stackSize = 0;
                    }
                }
            }

            /** Everything else is thrown away */
        } else {
            if (Settings.strictPower)
                consumedRf = 0;
        }
        consumedRf = 0;
    }
}
