package ipsis.woot.tileentity;

import cofh.api.energy.IEnergyReceiver;
import ipsis.Woot;
import ipsis.woot.block.BlockMobFactory;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemXpShard;
import ipsis.woot.manager.*;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.plugins.bloodmagic.BloodMagic;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
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
    AxisAlignedBB bb;
    ProxyManager proxyManager;

    int currLearnTicks;
    int currSpawnTicks;
    int consumedRf;
    int storedXp;
    int learnTicksOffset;
    boolean running;
    int structureTicks;

    boolean dirtyStructure;
    boolean dirtyUpgrade;
    boolean dirtyProxy;
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

    public ProxyManager getProxyManager() {
        return proxyManager;
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

        powerManager.writeToNBT(compound);
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

        powerManager.readFromNBT(compound);
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
        this.proxyManager = new ProxyManager(this);

        currLearnTicks = 0;
        currSpawnTicks = 0;
        consumedRf = 0;
        storedXp = 0;
        running = true;
        structureTicks = 0;

        learnTicksOffset = Settings.learnTicks + Woot.RANDOM.nextInt(11);
    }

    public String getMobName() {

        return this.controllerConfig.getMobName();
    }

    public String getMobDisplayName() {

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
            proxyManager.setMaster(false);
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
        onProxyCheck();
    }

    void onUpgradeCheck() {

        updateUpgradeBlocks(false);
        upgradeSetup.clear();
        upgradeBlockList.clear();
        if (factoryTier == EnumMobFactoryTier.TIER_ONE)
            upgradeTier1();
        else if (factoryTier == EnumMobFactoryTier.TIER_TWO)
            upgradeTier2();
        else if (factoryTier == EnumMobFactoryTier.TIER_THREE || factoryTier == EnumMobFactoryTier.TIER_FOUR)
            upgradeTier3();

        spawnReq = Woot.spawnerManager.getSpawnReq(controllerConfig.getMobName(), upgradeSetup, this, factoryTier);

        if (nbtLoaded) {
            /* Preserver on load */
            nbtLoaded = false;
        } else {
            setConsumedRf(0);
            setCurrSpawnTicks(0);
        }
        updateUpgradeBlocks(true);

    }

    void onProxyCheck() {

        proxyManager.setMaster(false);
        proxyManager.scanProxy();
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

    private boolean isMachinePowered() {

        boolean controller = worldObj.isBlockPowered(pos);
        boolean proxy = proxyManager.isBlockPowered();
        return controller || proxy;
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

        if (dirtyProxy) {
            onProxyCheck();
            dirtyProxy = false;
        }

        if (structureTicks >= MULTIBLOCK_BACKOFF_SCAN_TICKS)
            structureTicks = 0;

        boolean powered = isMachinePowered();
        if (running && powered)
            setRunning(false);
        else if (!running && !powered)
            setRunning(true);

        if (!isFormed())
            return;

        tryPickupModItems();

        currLearnTicks++;
        if (currLearnTicks >= learnTicksOffset) {
            if (!Woot.LOOT_TABLE_MANAGER.isFull(controllerConfig.getMobName(), upgradeSetup.getEnchantKey())) {
                /* Not full so fake another spawn */
                BlockPos spawnPos = new BlockPos(getPos().getX(), 0, getPos().getZ());
                Woot.spawnerManager.spawn(controllerConfig.getMobName(), upgradeSetup.getEnchantKey(), this.worldObj, this.getPos());
            }
            currLearnTicks = 0;
        }

        /* Do we have any info on this mob yet - should only happen until the first event fires */
        if (Woot.LOOT_TABLE_MANAGER.isEmpty(controllerConfig.getMobName(), upgradeSetup.getEnchantKey()))
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

    private void tryPickupModItems() {

        EnumEnchantKey key = upgradeSetup.getEnchantKey();
        String mobName = controllerConfig.getMobName();

        /* If still learning check for any dropped items */
        if (!Woot.LOOT_TABLE_MANAGER.isFull(mobName, key)) {

            if (bb == null) {
                BlockPos checkPos = new BlockPos(getPos().getX(), 1, getPos().getZ());
                int range = 2;
                bb = new AxisAlignedBB(checkPos).expand(range, 0, range);
            }

            List<EntityItem> itemList = worldObj.getEntitiesWithinAABB(EntityItem.class, bb, EntitySelectors.IS_ALIVE);
            if (!itemList.isEmpty()) {
                Woot.LOOT_TABLE_MANAGER.update(mobName, key, itemList, false);
                for (EntityItem i : itemList)
                    ((EntityItem) i).setDead();
            }
        }
    }

    public void interruptStructure() {

        dirtyStructure = true;
    }

    public void interruptUpgrade() {

        dirtyUpgrade = true;
    }

    public void interruptProxy() {

        dirtyProxy = true;
    }


    void processPower() {

        int drawnRf = powerManager.extractEnergy(spawnReq.getRfPerTick(), false);

        if (Woot.devMode == true)
            drawnRf = spawnReq.getRfPerTick();

        if (drawnRf == spawnReq.getRfPerTick()) {
            setConsumedRf(consumedRf + drawnRf);
        } else {
            if (Settings.strictPower)
                setConsumedRf(0);
            else
                setConsumedRf(consumedRf + drawnRf);
        }
    }

    private void bmOutput(UpgradeSetup upgradeSetup) {

        if (!upgradeSetup.hasBmUpgrade())
            return;

        if (!bmKeepAlive || BloodMagic.fluidOutput == null)
            return;

        List<IFluidHandler> validHandlers = new ArrayList<>();
        EnumFacing f = worldObj.getBlockState(pos).getValue(BlockMobFactory.FACING);
        if (worldObj.isBlockLoaded(this.getPos().offset(f))) {
            TileEntity te = worldObj.getTileEntity(this.getPos().offset(f));
            if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite()))
                validHandlers.add(te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite()));
        }

        // Proxy
        validHandlers.addAll(proxyManager.getIFluidHandlers());

        int mobCount = Settings.Spawner.DEF_BASE_MOB_COUNT;
        if (upgradeSetup.hasMassUpgrade())
            mobCount = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();

        /**
         * sacrificalDaggerCall(20, true) WOSuffering
         *
         * (1 + sacrificeEfficiencyMultiplier) * 20
         * sacrificeEfficiencyMultiplier = 0.10 * sacrifice rune count
         */


        int upgradeSacrificeCount = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getBmUpgrade()).getSacrificeCount();
        float sacrificeEfficiencyMultiplier = (float)(0.10 * upgradeSacrificeCount);

        int amount = ((int)((1 + sacrificeEfficiencyMultiplier) * 20)) * mobCount;

        FluidStack out = new FluidStack(BloodMagic.fluidOutput, amount);
        for (IFluidHandler hdlr : validHandlers) {

            if (out.amount == 0)
                break;

            int result = hdlr.fill(out, true);
            out.amount = out.amount - result;
            if (out.amount < 0)
                out.amount = 0;
        }

        bmKeepAlive = false;
    }

    private void produceOutput() {

        SpawnerManager.SpawnLoot loot = Woot.spawnerManager.getSpawnerLoot(controllerConfig.getMobName(), upgradeSetup, worldObj.getDifficultyForLocation(getPos()));

        List<IItemHandler> validHandlers = new ArrayList<>();

        // Original position
        EnumFacing f = worldObj.getBlockState(pos).getValue(BlockMobFactory.FACING);
        if (worldObj.isBlockLoaded(this.getPos().offset(f))) {
            TileEntity te = worldObj.getTileEntity(this.getPos().offset(f));
            if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                validHandlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()));
        }

        // Proxy
        validHandlers.addAll(proxyManager.getIItemHandlers());

        bmOutput(upgradeSetup);

        for (IItemHandler hdlr : validHandlers) {

            for (ItemStack itemStack : loot.getDropList()) {

                if (itemStack.stackSize <= 0)
                    continue;

                /**
                 * We try to insert 1 item and decrease itemStack.stackSize if it is successful
                 */
                ItemStack result = ItemHandlerHelper.insertItem(hdlr, ItemHandlerHelper.copyStackWithSize(itemStack, 1), false);
                if (result == null)
                    itemStack.stackSize--;
            }

            storedXp += loot.getXp();
            int c = storedXp / ItemXpShard.XP_VALUE;
            if (c != 0) {
                ItemStack xpShards = new ItemStack(ModItems.itemXpShard);
                ItemHandlerHelper.insertItem(hdlr, ItemHandlerHelper.copyStackWithSize(xpShards, c), false);
                storedXp = storedXp - (c * ItemXpShard.XP_VALUE);
            }
        }
    }

    void onSpawn() {

        if (consumedRf >= spawnReq.getTotalRf()) {

            produceOutput();

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
        proxyManager.setMaster(false);
        super.invalidate();
    }

    private boolean bmKeepAlive = false;
    public void bmKeepAlive() {

        bmKeepAlive = true;
    }

    /**
     * RF interface
     */
    protected PowerManager powerManager = new PowerManager(this);

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {

        if (!isFormed())
            return 0;

        return powerManager.receiveEnergy(from, maxReceive, simulate, true);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {

        if (!isFormed())
            return 0;

        return powerManager.getEnergyStored(from, true);
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {

        if (!isFormed())
            return 0;

        return powerManager.getMaxEnergyStored(from, true);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {

        if (!isFormed())
            return false;

        return powerManager.canConnectEnergy(from, true);
    }
}
