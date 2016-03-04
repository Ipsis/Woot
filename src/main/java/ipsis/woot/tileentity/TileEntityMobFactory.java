package ipsis.woot.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import ipsis.Woot;
import ipsis.oss.LogHelper;
import ipsis.woot.manager.*;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMobFactory extends TileEntity implements ITickable, IEnergyReceiver {

    EnumFacing facing;
    EnumMobFactoryTier factoryTier;
    SpawnerManager.SpawnReq spawnReq;
    boolean nbtLoaded;
    UpgradeSetup upgradeSetup;
    ControllerConfig controllerConfig;

    int currLearnTicks;
    int currSpawnTicks;
    int consumedRf;

    boolean dirtyStructure;
    boolean dirtyUpgrade;
    List<BlockPos> structureBlockList = new ArrayList<BlockPos>();
    List<BlockPos> upgradeBlockList = new ArrayList<BlockPos>();

    static final String NBT_FACING = "facing";
    static final String NBT_CURR_SPAWN_TICK = "spawnTicks";
    static final String NBT_CONSUMED_RF = "consumedRf";

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setByte(NBT_FACING, (byte)facing.ordinal());

        if (!isFormed())
            return;

        compound.setInteger(NBT_CURR_SPAWN_TICK, currSpawnTicks);
        compound.setInteger(NBT_CONSUMED_RF, consumedRf);

        energyStorage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        facing = EnumFacing.getFront(compound.getByte(NBT_FACING));

        if (compound.hasKey(NBT_CURR_SPAWN_TICK)) {
            currSpawnTicks = compound.getInteger(NBT_CURR_SPAWN_TICK);
            consumedRf = compound.getInteger(NBT_CONSUMED_RF);
            nbtLoaded = true;
        }

        energyStorage.readFromNBT(compound);
    }

    static final int MULTIBLOCK_BACKOFF_SCAN_TICKS = 20;

    public TileEntityMobFactory() {

        this.facing = EnumFacing.SOUTH;
        this.dirtyStructure = true;
        this.dirtyUpgrade = false;
        this.factoryTier = null;
        this.spawnReq = null;
        this.nbtLoaded = false;
        this.upgradeSetup = new UpgradeSetup();
        this.controllerConfig = new ControllerConfig();

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

    public String getMobName() {

        return this.controllerConfig.getMobName();
    }

    public String getDisplayName() {

        return this.controllerConfig.getDisplayName();
    }

    public SpawnerManager.SpawnReq getSpawnReq() {

        return this.spawnReq;
    }

    public EnumMobFactoryTier getFactoryTier() {

        return this.factoryTier;
    }

    public UpgradeSetup getUpgradeSetup() {

        return this.upgradeSetup;
    }

    public boolean isFormed() {

        return factoryTier != null && Woot.mobRegistry.isValidMobName(controllerConfig.getMobName()) && spawnReq != null;
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

    void updateUpgradeBlocks(boolean connected) {

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
            updateUpgradeBlocks(false);
            factoryTier = factorySetup.getSize();
            controllerConfig.clearMobName();
            return;
        }

        if (oldFactoryTier != factoryTier) {
            LogHelper.info("onStructureChanged: new size != old size");
            updateStructureBlocks(false);
        }

        factoryTier = factorySetup.getSize();
        controllerConfig.setMobName(factorySetup.getMobName(), factorySetup.getDisplayName());
        structureBlockList = factorySetup.getBlockPosList();
        updateStructureBlocks(true);

        LogHelper.info("onStructureChanged: tier=" + factoryTier + " mob=" + controllerConfig.getMobName());
        onUpgradeCheck();
    }

    void onUpgradeCheck() {

        LogHelper.info("onUpgradeCheck: " + factoryTier);

        updateUpgradeBlocks(false);
        upgradeSetup.clear();
        upgradeBlockList.clear();
        if (factoryTier == EnumMobFactoryTier.TIER_ONE)
            upgradeTier1();
        else if (factoryTier == EnumMobFactoryTier.TIER_TWO)
            upgradeTier2();
        else if (factoryTier == EnumMobFactoryTier.TIER_THREE)
            upgradeTier3();

        for (EnumSpawnerUpgrade u : upgradeSetup.getUpgradeList())
            LogHelper.info("onUpgradeCheck: " + u);

        spawnReq = Woot.spawnerManager.getSpawnReq(controllerConfig.getMobName(), upgradeSetup,
                Woot.spawnerManager.getSpawnXp(controllerConfig.getMobName(), this), factoryTier);
        LogHelper.info("onUpgradeCheck: " + upgradeSetup.getEnchantKey() + " " + spawnReq);

        if (nbtLoaded) {
            /* Preserver on load */
            nbtLoaded = false;
        } else {
            consumedRf = 0;
            currSpawnTicks = 0;
        }
        updateUpgradeBlocks(true);

    }

    void upgradeTierX(BlockPos[] upgradePos, int maxTier) {

        List<SpawnerUpgrade> tmpUpgradeList = new ArrayList<SpawnerUpgrade>();
        for (BlockPos p : upgradePos) {

            BlockPos offset = BlockPosHelper.rotateFromSouth(p, getFacing().getOpposite());
            BlockPos p2 = getPos().add(offset.getX(), offset.getY(), offset.getZ());
            UpgradeManager.scanUpgradeTotem(worldObj, p2, maxTier, tmpUpgradeList, upgradeBlockList);
        }

        upgradeSetup.processUpgrades(tmpUpgradeList);
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
        }

        if (dirtyUpgrade && worldObj.getWorldTime() % MULTIBLOCK_BACKOFF_SCAN_TICKS == 0) {
            onUpgradeCheck();
            dirtyUpgrade = false;
        }

        if (!isFormed())
            return;

        currLearnTicks++;
        if (currLearnTicks >= Settings.learnTicks) {
            if (!Woot.spawnerManager.isFull(controllerConfig.getMobName(), upgradeSetup.getEnchantKey())) {
                /* Not full so fake another spawn */
                LogHelper.info("update: Fake spawn " + controllerConfig.getMobName() + " " + upgradeSetup.getEnchantKey());
                Woot.spawnerManager.spawn(controllerConfig.getMobName(), upgradeSetup.getEnchantKey(), this.worldObj, this.getPos());
            }
            currLearnTicks = 0;
        }

        /* Do we have any info on this mob yet - should only happen until the first event fires */
        if (Woot.spawnerManager.isEmpty(controllerConfig.getMobName(), upgradeSetup.getEnchantKey()))
            return;

        currSpawnTicks++;
        processPower();
        if (currSpawnTicks == spawnReq.getSpawnTime()) {
            LogHelper.info("update: Factory generate " + controllerConfig.getMobName());
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
        int drawnRf = energyStorage.extractEnergy(spawnReq.getRfPerTick(), false);
        drawnRf = spawnReq.getRfPerTick();
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

            EnumFacing f = getFacing();
            if (worldObj.isBlockLoaded(this.getPos().offset(f))) {
                TileEntity te = worldObj.getTileEntity(this.getPos().offset(f));
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite())) {

                    IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());

                    SpawnerManager.SpawnLoot spawnLoot = Woot.spawnerManager.getSpawnerLoot(controllerConfig.getMobName(), upgradeSetup);
                    LogHelper.info("Loot: " + spawnLoot.getDropList());
                    LogHelper.info("XP: " + spawnLoot.getXp());
                    for (ItemStack itemStack : spawnLoot.getDropList())
                        ItemHandlerHelper.insertItem(capability, ItemHandlerHelper.copyStackWithSize(itemStack, 1), false);

                    /* XP as xp bottles for now */
                    ItemStack bottleXp = new ItemStack(Items.experience_bottle);
                    ItemHandlerHelper.insertItem(capability, ItemHandlerHelper.copyStackWithSize(bottleXp, spawnLoot.getXp()), false);
                }
            }
            /** Everything else is thrown away */
        } else {
            if (Settings.strictPower)
                consumedRf = 0;
        }
        consumedRf = 0;
    }

    @Override
    public void invalidate() {

        updateStructureBlocks(false);
        updateUpgradeBlocks(false);
        super.invalidate();
    }

    /**
     * RF interface
     */
    static final int RF_STORED = 50000;
    static final int MAX_RF_TICK = 10000;
    protected EnergyStorage energyStorage = new EnergyStorage(RF_STORED, MAX_RF_TICK);

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {

        // TODO down only
        if (isFormed())
            return energyStorage.receiveEnergy(maxReceive, simulate);

        return 0;
    }

    @Override
    public int getEnergyStored(EnumFacing from) {

        if (isFormed())
            return energyStorage.getEnergyStored();
        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {

        if (isFormed())
            return energyStorage.getMaxEnergyStored();
        return 0;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {

        // TODO down only
        return true;
    }
}
