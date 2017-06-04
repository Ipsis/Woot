package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.tileentity.ng.*;
import ipsis.woot.tileentity.ng.configuration.EnumConfigKey;
import ipsis.woot.tileentity.ng.farmblocks.IFarmBlockMaster;
import ipsis.woot.tileentity.ng.mock.MockPowerCalculator;
import ipsis.woot.tileentity.ng.mock.MockPowerStation;
import ipsis.woot.tileentity.ng.mock.MockSpawnRecipeConsumer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEntityMobFarm extends TileEntity implements ITickable, IFarmBlockMaster {

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
        tickTracker.setLearnTickCount(Woot.wootConfiguration.getInteger(EnumConfigKey.LEARN_TICKS));
        tickTracker.setStructureTickCount(20);

        powerStation = new MockPowerStation();
        spawnRecipeConsumer = new MockSpawnRecipeConsumer();
        spawnRecipe = new SpawnRecipe();
        powerCalculator = new MockPowerCalculator();
        recipeProgressTracker = new SimpleRecipeProgressTracker();
    }

    @Override
    public void update() {

        if (world.isRemote)
            return;

        // Cannot set this on create as the world may not be set
        if (farmStructure == null)
            farmStructure = new FarmStructure().setWorld(getWorld()).setPosition(getPos());

        tickTracker.tick(world);
        farmStructure.tick(tickTracker);

        if (farmStructure.isFormed()) {
            if (farmStructure.hasChanged()) {
                farmSetup = farmStructure.createSetup();
                powerRecipe = powerCalculator.calculate(farmSetup);
                spawnRecipe = spawnRecipeRepository.get(farmSetup.getWootMob().getWootMobName());
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

    @Nullable
    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return null;
    }
}
