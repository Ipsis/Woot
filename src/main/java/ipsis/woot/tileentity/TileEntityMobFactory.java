package ipsis.woot.tileentity;

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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMobFactory extends TileEntity implements ITickable {

    EnumFacing facing;
    EnumMobFactoryTier factoryTier;
    String mobName;
    EnumEnchantKey enchantKey = EnumEnchantKey.NO_ENCHANT;
    SpawnerManager.SpawnReq spawnReq;
    boolean nbtLoaded;

    int currLearnTicks;
    int currSpawnTicks;
    int consumedRf;

    boolean dirtyStructure;
    boolean dirtyUpgrade;
    List<BlockPos> structureBlockList = new ArrayList<BlockPos>();
    List<BlockPos> upgradeBlockList = new ArrayList<BlockPos>();
    List<SpawnerUpgrade> upgradeList = new ArrayList<SpawnerUpgrade>();

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
    }

    static final int MULTIBLOCK_BACKOFF_SCAN_TICKS = 20;

    public TileEntityMobFactory() {

        this.facing = EnumFacing.SOUTH;
        this.dirtyStructure = true;
        this.dirtyUpgrade = false;
        this.factoryTier = null;
        this.mobName = MobManager.INVALID_MOB_NAME;
        this.spawnReq = null;
        this.nbtLoaded = false;

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

        return this.mobName;
    }

    public SpawnerManager.SpawnReq getSpawnReq() {

        return this.spawnReq;
    }

    public EnumMobFactoryTier getFactoryTier() {

        return this.factoryTier;
    }

    public List<SpawnerUpgrade> getUpgradeList() {

        return this.upgradeList;
    }

    public boolean isFormed() {

        return factoryTier != null && MobManager.isValidMobName(mobName) && spawnReq != null;
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
            mobName = MobManager.INVALID_MOB_NAME;
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

        LogHelper.info("onStructureChanged: tier=" + factoryTier + " mob=" + mobName);
        onUpgradeCheck();
    }

    void onUpgradeCheck() {

        LogHelper.info("onUpgradeCheck: " + factoryTier);

        updateUpgradeBlocks(false);
        upgradeList.clear();
        upgradeBlockList.clear();
        if (factoryTier == EnumMobFactoryTier.TIER_ONE)
            upgradeTier1();
        else if (factoryTier == EnumMobFactoryTier.TIER_TWO)
            upgradeTier2();
        else if (factoryTier == EnumMobFactoryTier.TIER_THREE)
            upgradeTier3();

        for (SpawnerUpgrade u : upgradeList)
            LogHelper.info("onUpgradeCheck: " + u.getUpgradeType() + "/" + u.getUpgradeTier());

        spawnReq = Woot.spawnerManager.getSpawnReq(mobName, upgradeList, Woot.spawnerManager.getXp(mobName, this));
        enchantKey = UpgradeManager.getLootingEnchant(upgradeList);
        LogHelper.info("onUpgradeCheck: " + enchantKey + " " + spawnReq);

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

        for (BlockPos p : upgradePos) {

            BlockPos offset = BlockPosHelper.rotateFromSouth(p, getFacing().getOpposite());
            BlockPos p2 = getPos().add(offset.getX(), offset.getY(), offset.getZ());
            UpgradeManager.scanUpgradeTotem(worldObj, p2, maxTier, upgradeList, upgradeBlockList);
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
        }

        if (dirtyUpgrade && worldObj.getWorldTime() % MULTIBLOCK_BACKOFF_SCAN_TICKS == 0) {
            onUpgradeCheck();
            dirtyUpgrade = false;
        }

        if (!isFormed())
            return;

        currLearnTicks++;
        if (currLearnTicks >= Settings.learnTicks) {
            if (!Woot.spawnerManager.isFull(mobName, enchantKey)) {
                /* Not full so fake another spawn */
                LogHelper.info("update: Fake spawn " + mobName + " " + enchantKey);
                Woot.spawnerManager.spawn(mobName, enchantKey, this.worldObj, this.getPos());
            }
            currLearnTicks = 0;
        }

        /* Do we have any info on this mob yet - should only happen until the first event fires */
        if (Woot.spawnerManager.isEmpty(mobName, enchantKey))
            return;

        currSpawnTicks++;
        processPower();
        if (currSpawnTicks == spawnReq.getSpawnTime()) {
            LogHelper.info("update: Factory generate " + mobName);
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

            EnumFacing f = getFacing();
            if (worldObj.isBlockLoaded(this.getPos().offset(f))) {
                TileEntity te = worldObj.getTileEntity(this.getPos().offset(f));
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite())) {

                    IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());

                    SpawnerUpgrade upgradeMass = UpgradeManager.getMassUpgrade(upgradeList);
                    int maxMass = Settings.massBaseMobs;
                    if (upgradeMass != null)
                        maxMass = upgradeMass.getMass();

                    for (int mass = 0; mass < maxMass; mass++) {

                        SpawnerManager.SpawnLoot spawnLoot = Woot.spawnerManager.getSpawnLoot(mobName, upgradeList);
                        LogHelper.info("Loot: " + spawnLoot.getDropList());
                        LogHelper.info("XP: " + spawnLoot.getXp());
                        for (int i = 0; i < spawnLoot.getDropList().size(); i++) {
                            ItemStack result = ItemHandlerHelper.insertItem(capability, ItemHandlerHelper.copyStackWithSize(spawnLoot.getDropList().get(i), 1), false);
                            if (result != null)
                                spawnLoot.getDropList().get(i).stackSize = result.stackSize;
                            else
                                spawnLoot.getDropList().get(i).stackSize = 0;
                        }

                        /* XP as xp bottles for now */
                        ItemStack bottleXp = new ItemStack(Items.experience_bottle);
                        ItemHandlerHelper.insertItem(capability, ItemHandlerHelper.copyStackWithSize(bottleXp, spawnLoot.getXp()), false);
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
