package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactory;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemXpShard;
import ipsis.woot.manager.*;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.*;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMobFactory extends TileEntity implements ITickable {

    EnumMobFactoryTier factoryTier;
    SpawnerManager.SpawnReq spawnReq;
    boolean nbtLoaded;
    UpgradeSetup upgradeSetup;
    ControllerConfig controllerConfig;
    AxisAlignedBB bb;
    ProxyManager proxyManager;

    public static final int LOOTBOX_Y = 253;

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
    boolean hasLootBox;
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

        energyManager.writeToNBT(compound);
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

        energyManager.readFromNBT(compound);
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
        this.hasLootBox = true; /* Will force one fake check */

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

    public int getConsumedRf() {

        return this.consumedRf;
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

        return factoryTier != null && Woot.mobRegistry.isValidMobName(controllerConfig.getMobName()) && spawnReq != null && Woot.mobRegistry.isPrismValid(controllerConfig.getMobName());
    }

    void updateStructureBlocks(boolean connected) {

        for (BlockPos p : structureBlockList) {
            if (world.isBlockLoaded(p)) {
                TileEntity te = world.getTileEntity(p);
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
            if (world.isBlockLoaded(p)) {
                TileEntity te = world.getTileEntity(p);
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
        EnumFacing f = world.getBlockState(pos).getValue(BlockMobFactory.FACING);
        for (BlockPos p : upgradePos) {

            BlockPos offset = BlockPosHelper.rotateFromSouth(p, f.getOpposite());
            BlockPos p2 = getPos().add(offset.getX(), offset.getY(), offset.getZ());
            UpgradeManager.scanUpgradeTotem(world, p2, maxTier, tmpUpgradeList, upgradeBlockList);
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

        boolean controller = world.isBlockPowered(pos);
        boolean proxy = proxyManager.isBlockPowered();
        return controller || proxy;
    }

    private long lastWorldTime = 0;

    @Override
    public void update() {

        if (world.isRemote)
            return;

        long currWorldTime = getWorld().getTotalWorldTime();
        if (currWorldTime == lastWorldTime) {
            /* Bypass any acceleration methods */
            return;
        } else {
            lastWorldTime = currWorldTime;
        }

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
                BlockPos spawnPos = new BlockPos(getPos());
                Woot.spawnerManager.spawn(controllerConfig.getMobName(), upgradeSetup.getEnchantKey(), this.getWorld(), this.getPos());
                hasLootBox = true;
            } else {
                if (hasLootBox) {
                    BlockPos spawnPos = new BlockPos(getPos());
                    Woot.spawnerManager.destroyLootBox(this.getWorld(), spawnPos);
                    hasLootBox = false;
                }
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
                BlockPos checkPos = new BlockPos(getPos().getX(), LOOTBOX_Y, getPos().getZ());
                int range = 2;
                bb = new AxisAlignedBB(checkPos).expand(range, 0, range);
            }

            List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, bb, EntitySelectors.IS_ALIVE);
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

        int drawnRf = energyManager.extractEnergyInternal(spawnReq.getRfPerTick());

        if (Woot.devMode)
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

    /**
     * If there is a tank then it takes priority
     * Else we store the number of mobs for the ritual to pickup
     * /
     */
    private boolean bmUseTanks(int mobCount) {

        if (!bmKeepAlive || BloodMagic.fluidOutput == null)
            return false;

        List<IFluidHandler> validHandlers = new ArrayList<>();
        EnumFacing f = world.getBlockState(pos).getValue(BlockMobFactory.FACING);
        if (world.isBlockLoaded(this.getPos().offset(f))) {
            TileEntity te = world.getTileEntity(this.getPos().offset(f));
            if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite()))
                validHandlers.add(te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite()));
        }

        // Proxy
        validHandlers.addAll(proxyManager.getIFluidHandlers());

        if (validHandlers.isEmpty())
            return false;

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

        bmMobCount = 0;
        bmSacrificeAmount = 0;
        return true;
    }

    private void bmUseRitual(int mobCount, int sacrificeAmount) {

        bmKeepAlive = false;
        bmMobCount = mobCount;
        bmSacrificeAmount = sacrificeAmount;
    }

    private void bmOutput(UpgradeSetup upgradeSetup) {

        if (!upgradeSetup.hasBmUpgrade()) {
            bmClear();
            return;
        }

        int mobCount = Settings.Spawner.DEF_BASE_MOB_COUNT;
        if (upgradeSetup.hasMassUpgrade())
            mobCount = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();

        // Scale with the upgrades
        int sacrificeAmount = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getBmUpgrade()).getAltarLifeEssence();

        if (!bmUseTanks(mobCount))
            bmUseRitual(mobCount, sacrificeAmount);

        bmKeepAlive = false;
    }

    /**
     * Check that there are at least mount of type itemStack in the inventory
     */
    private boolean canRemoveItemStack(IItemHandler itemHandler, ItemStack itemStack, int amount) {

        if (amount <= 0)
            return true;

        if (itemHandler.getSlots() == 0)
            return false;

        int found = 0;
        for (int i = 0; i < itemHandler.getSlots(); i++) {

            ItemStack s = itemHandler.getStackInSlot(i);
            if (s.isEmpty())
                continue;

            if (s.isItemEqual(itemStack))
                found += s.getCount();

            if (found >= amount)
                break;
        }

        return found >= amount;
    }

    private void removeItemStack(IItemHandler itemHandler, ItemStack itemStack, int amount) {

        int left = amount;

        for (int i = 0; i < itemHandler.getSlots(); i++) {

            ItemStack removeStack = itemHandler.getStackInSlot(i);
            if (removeStack.isEmpty())
                continue;

            if (removeStack.isItemEqual(itemStack)) {
                ItemStack t = itemHandler.extractItem(i, left, false);
                left -= t.getCount();
            }

            if (left == 0)
                break;
        }
    }

    private boolean processExtraSpawnReq(String wootName, UpgradeSetup upgradeSetup) {

        if (!Woot.SPAWN_REQ_MANAGER.hasExtraSpawnReq(wootName))
            return true;

        List<ItemStack> requiredItems = Woot.SPAWN_REQ_MANAGER.getItems(wootName, upgradeSetup);
        FluidStack fluidStack = Woot.SPAWN_REQ_MANAGER.getFluid(wootName, upgradeSetup);

        if (!requiredItems.isEmpty()) {

            TileEntity te = getWorld().getTileEntity(this.getPos().offset(EnumFacing.UP, 2));
            if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
                IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

                boolean allItemsPresent = true;
                for (ItemStack itemStack : requiredItems) {
                    if (!canRemoveItemStack(itemHandler, itemStack, itemStack.getCount())) {
                        allItemsPresent = false;
                        break;
                    }

                }

                if (allItemsPresent) {
                    for (ItemStack itemStack : requiredItems)
                        removeItemStack(itemHandler, itemStack, itemStack.getCount());
                    return true;
                }

                return false;

            } else {
                return false;
            }

        } else if (fluidStack != null) {

            TileEntity te = getWorld().getTileEntity(this.getPos().offset(EnumFacing.UP, 2));
            if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
                IFluidHandler iFluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN);

                FluidStack removedFluid = iFluidHandler.drain(fluidStack, false);
                if (removedFluid != null && removedFluid.amount == fluidStack.amount) {
                    iFluidHandler.drain(fluidStack, true);
                    return  true;
                }
                return false;
            } else {
                return false;
            }
        }

        return true;
    }

    private void produceOutput() {

        if (!processExtraSpawnReq(controllerConfig.getMobName(), upgradeSetup)) {
            return;
        }

        SpawnerManager.SpawnLoot loot = Woot.spawnerManager.getSpawnerLoot(controllerConfig.getMobName(), upgradeSetup, getWorld().getDifficultyForLocation(getPos()));

        List<IItemHandler> validHandlers = new ArrayList<>();

        // Original position
        EnumFacing f = world.getBlockState(pos).getValue(BlockMobFactory.FACING);
        if (world.isBlockLoaded(this.getPos().offset(f))) {
            TileEntity te = world.getTileEntity(this.getPos().offset(f));
            if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                validHandlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()));
        }

        // Proxy
        validHandlers.addAll(proxyManager.getIItemHandlers());

        bmOutput(upgradeSetup);

        for (IItemHandler hdlr : validHandlers) {

            for (ItemStack itemStack : loot.getDropList()) {

                if (itemStack.getCount() <= 0)
                    continue;

                boolean success = true;
                while (success && !itemStack.isEmpty()) {

                    /**
                     * We try to insert 1 item and decrease itemStack.stackSize if it is successful
                     */
                    ItemStack result = ItemHandlerHelper.insertItem(hdlr, ItemHandlerHelper.copyStackWithSize(itemStack, 1), false);
                    if (result.isEmpty())
                        itemStack.shrink(1);
                    else
                        success = false;
                }
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
    private int bmMobCount = 0;
    private int bmSacrificeAmount = 0;
    public void bmKeepAlive() {

        bmKeepAlive = true;
    }

    public int bmGetMobCount() {

        return bmMobCount;
    }

    public int bmGetSacrificeAmount() {

        return bmSacrificeAmount;
    }

    public void bmClear() {

        bmMobCount = 0;
        bmKeepAlive = false;
        bmSacrificeAmount = 0;
    }

    /**
     * Forge Power Interface
     */
    protected EnergyManager energyManager = new EnergyManager(Settings.maxPower, EnergyManager.MAX_RF_TICK, this);

    public EnergyManager getEnergyManager() {

        return this.energyManager;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

        return capability == CapabilityEnergy.ENERGY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY) {
            IEnergyStorage energyStorage = energyManager;
            return (T)energyStorage;
        }

        return super.getCapability(capability, facing);
    }
}
