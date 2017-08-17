package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.farming.*;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farmblocks.IFarmBlockMaster;
import ipsis.woot.farmstructure.FarmBuilder;
import ipsis.woot.farmstructure.IFarmStructure;
import ipsis.woot.loot.repository.ILootRepositoryLookup;
import ipsis.woot.mock.MockPowerStation;
import ipsis.woot.mock.MockSpawnRecipeConsumer;
import ipsis.woot.mock.MockSpawnRecipeRepository;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.power.calculation.Calculator;
import ipsis.woot.power.calculation.IPowerCalculator;
import ipsis.woot.power.storage.IPowerStation;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityMobFactoryHeart extends TileEntity implements ITickable, IFarmBlockMaster, IMobFarm {

    private ITickTracker tickTracker;
    private IFarmStructure farmStructure;
    private IFarmSetup farmSetup;
    private PowerRecipe powerRecipe;
    private ISpawnRecipe spawnRecipe;
    private IPowerCalculator powerCalculator;
    private IRecipeProgressTracker recipeProgressTracker;
    private ISpawnRecipeConsumer spawnRecipeConsumer;

    ISpawnRecipeRepository spawnRecipeRepository;

    public TileEntityMobFactoryHeart() {

        // NOTE: this is called without a world on startup
        tickTracker = new SimpleTickTracker();
        tickTracker.setLearnTickCount(
                Woot.wootConfiguration.getInteger(EnumConfigKey.LEARN_TICKS) + Woot.RANDOM.nextInt(11));
        tickTracker.setStructureTickCount(20);

        spawnRecipeConsumer = new MockSpawnRecipeConsumer();
        spawnRecipe = new SpawnRecipe();
        powerCalculator = new Calculator();
        recipeProgressTracker = new SimpleRecipeProgressTracker();
        spawnRecipeRepository = new MockSpawnRecipeRepository();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
       super.writeToNBT(compound);

       if (farmStructure != null && farmStructure.isFormed())
           compound.setInteger("wootConsumedPower", recipeProgressTracker.getConsumedPower());

       return compound;
    }

    private int nbtConsumedPower = 0;
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("wootConsumedPower"))
            nbtConsumedPower = compound.getInteger("wootConsumedPower");
    }

    @Override
    public void invalidate() {

        // TODO disconnect all the farm blocks!
        super.invalidate();
    }

    @Override
    public void update() {

        if (world.isRemote)
            return;

        // Cannot set this on create as the world may not be set
        if (farmStructure == null) {
            farmStructure = new FarmBuilder().setWorld(getWorld()).setPosition(getPos());
            farmStructure.setStructureDirty();
        }

        tickTracker.tick(world);
        farmStructure.tick(tickTracker);

        if (farmStructure.isFormed()) {
            if (farmStructure.hasChanged()) {
                farmSetup = farmStructure.createSetup();
                powerRecipe = powerCalculator.calculate(farmSetup);
                spawnRecipe = spawnRecipeRepository.get(farmSetup.getWootMobName());
                recipeProgressTracker.setPowerStation(farmSetup.getPowerStation());
                recipeProgressTracker.setPowerRecipe(powerRecipe);
                farmStructure.clearChanged();

                if (nbtConsumedPower != 0) {
                    recipeProgressTracker.setConsumedPower(nbtConsumedPower);
                    nbtConsumedPower = 0;
                }
            }

            Woot.lootLearner.tick(tickTracker, getWorld(), getPos(), farmSetup);
            recipeProgressTracker.tick();
            if (recipeProgressTracker.isComplete() && spawnRecipeConsumer.consume(getWorld(), getPos(), spawnRecipe, farmSetup.getNumMobs())) {
                Woot.lootGeneration.generate(farmSetup.getConnectedExportTanks(), farmSetup.getConnectedExportChests(), farmSetup);
                recipeProgressTracker.reset();
            }
        }
    }

    /**
     * IFarmBlockMaster
     */
    @Override
    public void interruptFarmStructure() {

        farmStructure.setStructureDirty();
    }

    @Override
    public void interruptFarmUpgrade() {

        farmStructure.setUpgradeDirty();
    }

    @Override
    public void interruptFarmProxy() {

        farmStructure.setProxyDirty();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        if (farmStructure != null && farmStructure.isFormed())
            return capability == CapabilityEnergy.ENERGY;

        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY && farmStructure != null && farmStructure.isFormed())
            return (T)farmSetup.getPowerStation().getEnergyStorage();

        return super.getCapability(capability, facing);
    }

    @Override
    public void getUIInfo(FarmUIInfo info) {

        if (!farmStructure.isFormed())
            return;

        info.wootMob = farmSetup.getWootMob();
        info.isRunning = true;
        info.recipeTotalPower = powerRecipe.getTotalPower();
        info.recipeTotalTime = powerRecipe.getTicks();
        info.recipePowerPerTick = powerRecipe.getPowerPerTick();
        info.consumedPower = recipeProgressTracker.getConsumedPower();
        info.tier = farmSetup.getFarmTier();
        info.powerCapacity = farmSetup.getPowerStation().getEnergyStorage().getMaxEnergyStored();
        info.powerCapacity = farmSetup.getPowerStation().getEnergyStorage().getEnergyStored();

        List<ILootRepositoryLookup.LootItemStack> loot = Woot.lootRepository.getDrops(farmSetup.getWootMobName(), farmSetup.getEnchantKey());
        for (ILootRepositoryLookup.LootItemStack lootItemStack : loot) {
            ItemStack itemStack = lootItemStack.itemStack.copy();
            itemStack.setCount(lootItemStack.dropChance);
            info.drops.add(itemStack);
        }

        // Say everything is okay
        info.setValid();
    }
}
