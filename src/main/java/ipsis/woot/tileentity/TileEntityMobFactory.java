package ipsis.woot.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import ipsis.Woot;
import ipsis.woot.block.BlockMobFactory;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemXpShard;
import ipsis.woot.manager.*;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMobFactory extends TileEntity implements ITickable, IEnergyReceiver {

    EnumMobFactoryTier factoryTier;
    SpawnerManager.SpawnReq spawnReq;
    boolean nbtLoaded;
    UpgradeSetup upgradeSetup;
    ControllerConfig controllerConfig;

    int currLearnTicks;
    int currSpawnTicks;
    int consumedRf;
    int storedXp;
    int learnTicksOffset;
    boolean running;
    int structureTicks;

    boolean dirtyStructure;
    boolean dirtyUpgrade;
    List<BlockPos> structureBlockList = new ArrayList<BlockPos>();
    List<BlockPos> upgradeBlockList = new ArrayList<BlockPos>();

    static final String NBT_CURR_SPAWN_TICK = "spawnTicks";
    static final String NBT_CONSUMED_RF = "consumedRf";
    static final String NBT_STORED_XP = "storedXp";
    static final String NBT_RUNNING = "running";

    void setCurrSpawnTicks(int ticks) {
        if (currSpawnTicks != ticks) {
            currSpawnTicks = ticks;
            markDirty();
        }
    }

    void incCurrSpawnTicks() {
        currSpawnTicks++;
        markDirty();
    }

    void setConsumedRf(int rf) {
        if (consumedRf != rf) {
            consumedRf = rf;
            markDirty();
        }
    }

    void setStoredXp(int xp) {
        if (storedXp != xp) {
            storedXp = xp;
            markDirty();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (!isFormed())
            return compound;

        compound.setInteger(NBT_CURR_SPAWN_TICK, currSpawnTicks);
        compound.setInteger(NBT_CONSUMED_RF, consumedRf);
        compound.setInteger(NBT_STORED_XP, storedXp);
        compound.setBoolean(NBT_RUNNING, running);

        energyStorage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey(NBT_CURR_SPAWN_TICK)) {
            currSpawnTicks = compound.getInteger(NBT_CURR_SPAWN_TICK);
            consumedRf = compound.getInteger(NBT_CONSUMED_RF);
            storedXp = compound.getInteger(NBT_STORED_XP);
            if (compound.hasKey(NBT_RUNNING))
                running = compound.getBoolean(NBT_RUNNING);
            else
                running = true;
            nbtLoaded = true;
        }

        energyStorage.readFromNBT(compound);
    }

    static final int MULTIBLOCK_BACKOFF_SCAN_TICKS = 20;

    public TileEntityMobFactory() {

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
        storedXp = 0;
        running = true;
        structureTicks = 0;

        learnTicksOffset = Settings.learnTicks + Woot.random.nextInt(11);
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

    void setRunning(boolean running) {

        if (this.running != running) {
            this.running = running;
            markDirty();
        }
    }

    public boolean isRunning() {

        return running;
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

        EnumMobFactoryTier oldFactoryTier = factoryTier;
        MobFactoryMultiblockLogic.FactorySetup factorySetup = MobFactoryMultiblockLogic.validateFactory(this);

        if (factorySetup.getSize() == null) {
            updateStructureBlocks(false);
            updateUpgradeBlocks(false);
            factoryTier = factorySetup.getSize();
            controllerConfig.clearMobName();
            return;
        }

        if (oldFactoryTier != factoryTier) {
            updateStructureBlocks(false);
        }

        factoryTier = factorySetup.getSize();
        controllerConfig.setMobName(factorySetup.getMobName(), factorySetup.getDisplayName());
        structureBlockList = factorySetup.getBlockPosList();
        updateStructureBlocks(true);

        onUpgradeCheck();
    }

    void onUpgradeCheck() {

        updateUpgradeBlocks(false);
        upgradeSetup.clear();
        upgradeBlockList.clear();
        if (factoryTier == EnumMobFactoryTier.TIER_ONE)
            upgradeTier1();
        else if (factoryTier == EnumMobFactoryTier.TIER_TWO)
            upgradeTier2();
        else if (factoryTier == EnumMobFactoryTier.TIER_THREE)
            upgradeTier3();

        spawnReq = Woot.spawnerManager.getSpawnReq(controllerConfig.getMobName(), upgradeSetup,
                Woot.spawnerManager.getSpawnXp(controllerConfig.getMobName(), this), factoryTier);

        if (nbtLoaded) {
            /* Preserver on load */
            nbtLoaded = false;
        } else {
            setConsumedRf(0);
            setCurrSpawnTicks(0);
        }
        updateUpgradeBlocks(true);

    }

    /**
     * Validate the structure and indicate to the user why it cannot be formed
     */
    public void manualValidate(EntityPlayer player) {

        MobFactoryMultiblockLogic.FactorySetup factorySetup = MobFactoryMultiblockLogic.validateFactory(this, true, player);
    }

    void upgradeTierX(BlockPos[] upgradePos, int maxTier) {

        List<SpawnerUpgrade> tmpUpgradeList = new ArrayList<SpawnerUpgrade>();
        EnumFacing f = worldObj.getBlockState(pos).getValue(BlockMobFactory.FACING);
        for (BlockPos p : upgradePos) {

            BlockPos offset = BlockPosHelper.rotateFromSouth(p, f.getOpposite());
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

        structureTicks++;

        if (dirtyStructure && structureTicks >= MULTIBLOCK_BACKOFF_SCAN_TICKS) {
            onStructureCheck();
            dirtyStructure = false;
            dirtyUpgrade = false;
        }

        if (dirtyUpgrade && structureTicks >= MULTIBLOCK_BACKOFF_SCAN_TICKS) {
            onUpgradeCheck();
            dirtyUpgrade = false;
        }

        if (structureTicks >= MULTIBLOCK_BACKOFF_SCAN_TICKS)
            structureTicks = 0;

        boolean powered = worldObj.isBlockPowered(pos);
        if (running && powered)
            setRunning(false);
        else if (!running && !powered)
            setRunning(true);

        if (!isFormed())
            return;

        currLearnTicks++;
        if (currLearnTicks >= learnTicksOffset) {
            if (!Woot.lootManager.isFull(controllerConfig.getMobName(), upgradeSetup.getEnchantKey())) {
                /* Not full so fake another spawn */
                Woot.spawnerManager.spawn(controllerConfig.getMobName(), upgradeSetup.getEnchantKey(), this.worldObj, this.getPos());
            }
            currLearnTicks = 0;
        }

        /* Do we have any info on this mob yet - should only happen until the first event fires */
        if (Woot.lootManager.isEmpty(controllerConfig.getMobName(), upgradeSetup.getEnchantKey()))
            return;

        if (running) {
            incCurrSpawnTicks();
            processPower();
            if (currSpawnTicks >= spawnReq.getSpawnTime()) {
                onSpawn();
                setCurrSpawnTicks(0);
            }
        }
    }

    public void interruptStructure() {

        dirtyStructure = true;
    }

    public void interruptUpgrade() {

        dirtyUpgrade = true;
    }


    void processPower() {

        int drawnRf = energyStorage.extractEnergy(spawnReq.getRfPerTick(), false);
        if (drawnRf == spawnReq.getRfPerTick()) {
            setConsumedRf(consumedRf + drawnRf);
        } else {
            if (Settings.strictPower)
                setConsumedRf(0);
            else
                setConsumedRf(consumedRf + drawnRf);
        }
    }

    void onSpawn() {

        if (consumedRf >= spawnReq.getTotalRf()) {

            EnumFacing f = worldObj.getBlockState(pos).getValue(BlockMobFactory.FACING);
            if (worldObj.isBlockLoaded(this.getPos().offset(f))) {
                TileEntity te = worldObj.getTileEntity(this.getPos().offset(f));
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite())) {

                    IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());

                    SpawnerManager.SpawnLoot spawnLoot = Woot.spawnerManager.getSpawnerLoot(controllerConfig.getMobName(), upgradeSetup, worldObj.getDifficultyForLocation(getPos()));
                    for (ItemStack itemStack : spawnLoot.getDropList())
                        ItemHandlerHelper.insertItem(capability, ItemHandlerHelper.copyStackWithSize(itemStack, 1), false);

                    storedXp += spawnLoot.getXp();
                    int c = storedXp / ItemXpShard.XP_VALUE;
                    if (c != 0) {
                        ItemStack xpShards = new ItemStack(ModItems.itemXpShard);
                        ItemHandlerHelper.insertItem(capability, ItemHandlerHelper.copyStackWithSize(xpShards, c), false);
                        storedXp = storedXp - (c * ItemXpShard.XP_VALUE);
                    }
                }
            }
            /** Everything else is thrown away */

            /**
             * Clear the power used - this uses ALL the consumed power
             * This means that if you don't provide the correct RF/tick then it will eat all the power that you
             * gave it until the spawn ticks was reached.
             */
            setConsumedRf(0);
        } else {
            if (Settings.strictPower)
                setConsumedRf(0);
        }
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
    static final int MAX_RF_TICK = 32000;
    static final int RF_STORED = MAX_RF_TICK * 10;
    protected EnergyStorage energyStorage = new EnergyStorage(RF_STORED, MAX_RF_TICK);

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {

        if (from == EnumFacing.DOWN && isFormed())
            return energyStorage.receiveEnergy(maxReceive, simulate);

        return 0;
    }

    @Override
    public int getEnergyStored(EnumFacing from) {

        if (from == EnumFacing.DOWN)
            return energyStorage.getEnergyStored();

        return 0;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {

        if (from == EnumFacing.DOWN)
            return energyStorage.getMaxEnergyStored();

        return 0;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {

        return from == EnumFacing.DOWN;
    }
}
