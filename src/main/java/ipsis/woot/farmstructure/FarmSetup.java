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
import net.minecraftforge.fluids.capability.IFluidHandler;
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
    private BlockPos powerCellBlockPos;
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
        this.powerCellBlockPos = blockPos;

        TileEntityMobFactoryCell cell = (TileEntityMobFactoryCell)world.getTileEntity(this.powerCellBlockPos);
        powerStation = cell.getPowerStation();
    }

    @Override
    public void setExportBlockPos(BlockPos blockPos) {
        this.exportBlockPos = blockPos;
    }

    @Override
    public void setImportBlockPos(BlockPos blockPos) {
        this.importBlockPos = blockPos;
    }

    @Nonnull
    @Override
    public List<IFluidHandler> getConnectedImportTanks() {
        List<IFluidHandler> tanks = new ArrayList<>();
        return tanks;
    }

    @Nonnull
    @Override
    public List<IFluidHandler> getConnectedExportTanks() {
        List<IFluidHandler> tanks = new ArrayList<>();
        return tanks;
    }

    @Nonnull
    @Override
    public List<IItemHandler> getConnectedExportChests() {

        List<IItemHandler> chests = new ArrayList<>();

        for (EnumFacing f : EnumFacing.HORIZONTALS) {
            if (world.isBlockLoaded(exportBlockPos.offset(f))) {
                TileEntity te = world.getTileEntity(exportBlockPos.offset(f));
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                    chests.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()));
            }

        }

        return chests;
    }

    @Nonnull
    @Override
    public List<IItemHandler> getConnectedImportChests() {

        List<IItemHandler> chests = new ArrayList<>();

        for (EnumFacing f : EnumFacing.HORIZONTALS) {
            if (world.isBlockLoaded(importBlockPos.offset(f))) {
                TileEntity te = world.getTileEntity(importBlockPos.offset(f));
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                    chests.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()));
            }

        }

        return chests;
    }
}
