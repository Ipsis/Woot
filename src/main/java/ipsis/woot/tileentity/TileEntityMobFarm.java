package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.farming.*;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farmblocks.IFarmBlockMaster;
import ipsis.woot.farmstructure.FarmBuilder;
import ipsis.woot.farmstructure.IFarmStructure;
import ipsis.woot.mock.MockPowerStation;
import ipsis.woot.mock.MockSpawnRecipeConsumer;
import ipsis.woot.mock.MockSpawnRecipeRepository;
import ipsis.woot.power.calculation.Calculator;
import ipsis.woot.power.calculation.IPowerCalculator;
import ipsis.woot.power.storage.IPowerStation;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileEntityMobFarm extends TileEntity implements ITickable, IFarmBlockMaster, IMobFarm {

    private ITickTracker tickTracker;
    private IFarmStructure farmStructure;
    private IFarmSetup farmSetup;
    private PowerRecipe powerRecipe;
    private ISpawnRecipe spawnRecipe;
    private IPowerCalculator powerCalculator;
    private IRecipeProgressTracker recipeProgressTracker;
    private ISpawnRecipeConsumer spawnRecipeConsumer;
    private IPowerStation powerStation;

    ISpawnRecipeRepository spawnRecipeRepository;

    public TileEntityMobFarm() {

        // NOTE: this is called without a world on startup
        tickTracker = new SimpleTickTracker();
        tickTracker.setLearnTickCount(
                Woot.wootConfiguration.getInteger(EnumConfigKey.LEARN_TICKS) + Woot.RANDOM.nextInt(11));
        tickTracker.setStructureTickCount(20);

        powerStation = new MockPowerStation();
        spawnRecipeConsumer = new MockSpawnRecipeConsumer();
        spawnRecipe = new SpawnRecipe();
        powerCalculator = new Calculator();
        recipeProgressTracker = new SimpleRecipeProgressTracker();
        spawnRecipeRepository = new MockSpawnRecipeRepository();
    }

    @Override
    public void invalidate() {

        // TODO invalidate the complete farm
        super.invalidate();
    }

    @Override
    public void update() {

        if (world.isRemote)
            return;

        // Cannot set this on create as the world may not be set
        if (farmStructure == null)
            farmStructure = new FarmBuilder().setWorld(getWorld()).setPosition(getPos());

        tickTracker.tick(world);
        farmStructure.tick(tickTracker);

        if (farmStructure.isFormed()) {
            if (farmStructure.hasChanged()) {
                farmSetup = farmStructure.createSetup();
                powerRecipe = powerCalculator.calculate(farmSetup);
                spawnRecipe = spawnRecipeRepository.get(farmSetup.getWootMobName());
                powerStation.setTier(farmSetup.getFarmTier());
                recipeProgressTracker.setPowerStation(powerStation);
                recipeProgressTracker.setPowerRecipe(powerRecipe);
                farmStructure.clearChanged();
            }

            Woot.lootLearner.tick(tickTracker, getWorld(), getPos(), farmSetup);
            recipeProgressTracker.tick();
            if (recipeProgressTracker.isComplete() && spawnRecipeConsumer.consume(getWorld(), getPos(), spawnRecipe, farmSetup.getNumMobs())) {
                Woot.lootGeneration.generate(farmStructure.getConnectedTanks(), farmStructure.getConnectedChests(), farmSetup);
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
            return (T)powerStation.getEnergyStorage();

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
        info.setValid();
    }
}
