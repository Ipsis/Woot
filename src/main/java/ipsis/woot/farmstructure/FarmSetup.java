package ipsis.woot.farmstructure;

import ipsis.Woot;
import ipsis.woot.power.storage.IPowerStation;
import ipsis.woot.tileentity.TileEntityMobFactoryCell;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobName;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FarmSetup implements IFarmSetup {

    private Map<EnumFarmUpgrade, Integer> upgradeMap = new HashMap<>();
    private WootMob wootMob;
    private EnumEnchantKey enchantKey = EnumEnchantKey.NO_ENCHANT;
    private EnumMobFactoryTier tier = EnumMobFactoryTier.TIER_ONE;
    private EnumFacing facing = EnumFacing.SOUTH;
    private BlockPos exportBlockPos;
    private BlockPos importBlockPos;
    private IPowerStation powerStation;
    private World world;
    private int storedXp = 0;

    public FarmSetup(World world, WootMob wootMob) {

        this.wootMob = wootMob;
        this.world = world;
    }

    @Override
    public void setStoredXp(int storedXp) {
        this.storedXp = storedXp;
    }

    @Override
    public int getStoredXp() {
        return storedXp;
    }

    @Override
    public @Nonnull WootMob getWootMob() {

        return wootMob;
    }

    @Override
    public @Nonnull
    WootMobName getWootMobName() {

        return wootMob.getWootMobName();
    }

    @Override
    public int getNumMobs() {

        int numMobs;
        int massLevel = getUpgradeLevel(EnumFarmUpgrade.MASS);
        switch (massLevel) {
            case 1:
                numMobs = Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.MASS_1_PARAM);
                break;
            case 2:
                numMobs =  Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.MASS_2_PARAM);
                break;
            case 3:
                numMobs = Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.MASS_3_PARAM);
                break;
            default:
                numMobs = Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.NUM_MOBS);
                break;
        }

        return numMobs;
    }

    @Override
    public int getUpgradeLevel(EnumFarmUpgrade upgrade) {

        if (upgradeMap.containsKey(upgrade))
            return upgradeMap.get(upgrade);

        return 0;
    }

    @Override
    public void setUpgradeLevel(EnumFarmUpgrade upgrade, int level) {

        upgradeMap.put(upgrade, level);
    }

    @Override
    public void setFarmTier(EnumMobFactoryTier tier) {

        this.tier = tier;
    }

    @Override
    public void setEnchantKey(EnumEnchantKey key) {

        this.enchantKey = key;
    }

    @Override
    public boolean hasUpgrade(EnumFarmUpgrade upgrade) {

        return getUpgradeLevel(upgrade) != 0;
    }


    @Override
    public void setFacing(EnumFacing facing) {

        this.facing = facing;
    }

    @Override
    public EnumFacing getFacing() {
        return facing;
    }

    @Nonnull
    @Override
    public EnumEnchantKey getEnchantKey() {
        return enchantKey;
    }

    @Nonnull
    @Override
    public EnumMobFactoryTier getFarmTier() {
        return tier;
    }

    @Override
    public IPowerStation getPowerStation() {

        return powerStation;
    }

    @Override
    public void setPowerCellBlockPos(BlockPos blockPos) {

        if (blockPos == null) {
            powerStation = null;
            return;
        }

        TileEntity te = world.getTileEntity(blockPos);
        if (te instanceof TileEntityMobFactoryCell) {
            powerStation = ((TileEntityMobFactoryCell) te).getPowerStation();
        } else {
            powerStation = null;
        }
    }

    @Override
    public void setExportBlockPos(BlockPos blockPos) {
        this.exportBlockPos = blockPos;
    }

    @Override
    public void setImportBlockPos(BlockPos blockPos) {
        this.importBlockPos = blockPos;
    }

    /**
     * Import/export utils
     */
    private class InventoryInfo {

        TileEntity te;
        IItemHandler iItemHandler;

        public InventoryInfo(TileEntity te, IItemHandler iItemHandler) {
            this.te = te;
            this.iItemHandler = iItemHandler;
        }
    }

    @Nonnull
    private List<InventoryInfo> getConnectedChests(BlockPos origin) {

        List<InventoryInfo> chests = new ArrayList<>();

        for (EnumFacing f : EnumFacing.HORIZONTALS) {
            if (world.isBlockLoaded(origin.offset(f))) {
                TileEntity te = world.getTileEntity(origin.offset(f));
                if (te == null)
                    continue;

                if (!te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                    continue;

                IItemHandler iItemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());
                if (iItemHandler == null)
                    continue;

                chests.add(new InventoryInfo(te, iItemHandler));
            }
        }

        return chests;
    }

    private class TankInfo {

        TileEntity te;
        IFluidHandler iFluidHandler;

        public TankInfo(TileEntity te, IFluidHandler iFluidHandler) {
            this.te = te;
            this.iFluidHandler = iFluidHandler;
        }
    }

    @Nonnull
    private List<TankInfo> getConnectedTanks(BlockPos origin, boolean drain) {

        List<TankInfo> tanks = new ArrayList<>();

        for (EnumFacing f : EnumFacing.HORIZONTALS) {
            if (world.isBlockLoaded(origin.offset(f))) {
                TileEntity te = world.getTileEntity(origin.offset(f));
                if (te == null)
                    continue;

                if (!te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite()))
                    continue;

                IFluidHandler iFluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, f.getOpposite());
                if (iFluidHandler == null)
                    continue;

                IFluidTankProperties[] properties = iFluidHandler.getTankProperties();
                if (properties == null)
                    continue;

                for (IFluidTankProperties p : properties) {
                    if ((drain && p.canDrain()) || (!drain && p.canFill())) {
                        tanks.add(new TankInfo(te, iFluidHandler));
                        break;
                    }
                }
            }
        }

        return tanks;
    }

    @Nonnull
    @Override
    public List<TileEntity> getConnectedImportTanksTiles() {

        List<TileEntity> tanks = new ArrayList<>();

        if (importBlockPos == null)
            return tanks;

        List<TankInfo> connected = getConnectedTanks(importBlockPos, true);
        for (TankInfo i : connected)
            tanks.add(i.te);

        return tanks;
    }

    @Nonnull
    @Override
    public List<TileEntity> getConnectedExportTanksTiles() {

        List<TileEntity> tanks = new ArrayList<>();

        if (exportBlockPos == null)
            return tanks;

        List<TankInfo> connected = getConnectedTanks(exportBlockPos, false);
        for (TankInfo i : connected)
            tanks.add(i.te);

        return tanks;
    }

    @Nonnull
    @Override
    public List<IFluidHandler> getConnectedImportTanks() {

        List<IFluidHandler> tanks = new ArrayList<>();

        if (importBlockPos == null)
            return tanks;

        List<TankInfo> connected = getConnectedTanks(importBlockPos, true);
        for (TankInfo i : connected)
            tanks.add(i.iFluidHandler);

        return tanks;
    }

    @Nonnull
    @Override
    public List<IFluidHandler> getConnectedExportTanks() {

        List<IFluidHandler> tanks = new ArrayList<>();

        if (exportBlockPos == null)
            return tanks;

        List<TankInfo> connected = getConnectedTanks(exportBlockPos, false);
        for (TankInfo i : connected)
            tanks.add(i.iFluidHandler);

        return tanks;
    }

    @Nonnull
    @Override
    public List<IItemHandler> getConnectedImportChests() {

        List<IItemHandler> chests = new ArrayList<>();

        if (importBlockPos == null)
            return chests;

        List<InventoryInfo> connected = getConnectedChests(importBlockPos);
        for (InventoryInfo i : connected)
            chests.add(i.iItemHandler);

        return chests;
    }


    @Nonnull
    @Override
    public List<IItemHandler> getConnectedExportChests() {

        List<IItemHandler> chests = new ArrayList<>();

        if (exportBlockPos == null)
            return chests;

        List<InventoryInfo> connected = getConnectedChests(exportBlockPos);
        for (InventoryInfo i : connected)
            chests.add(i.iItemHandler);

        return chests;
    }

    @Nonnull
    @Override
    public List<TileEntity> getConnectedImportChestsTiles() {

        List<TileEntity> chests = new ArrayList<>();

        if (importBlockPos == null)
            return chests;

        List<InventoryInfo> connected = getConnectedChests(importBlockPos);
        for (InventoryInfo i : connected)
            chests.add(i.te);

        return chests;
    }

    @Nonnull
    @Override
    public List<TileEntity> getConnectedExportChestsTiles() {

        List<TileEntity> chests = new ArrayList<>();

        if (exportBlockPos == null)
            return chests;

        List<InventoryInfo> connected = getConnectedChests(exportBlockPos);
        for (InventoryInfo i : connected)
            chests.add(i.te);

        return chests;
    }
}
