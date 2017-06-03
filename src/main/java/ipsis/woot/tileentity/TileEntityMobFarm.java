package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.manager.ConfigManager;
import ipsis.woot.tileentity.ng.*;
import ipsis.woot.tileentity.ng.loot.ILootGeneration;
import ipsis.woot.tileentity.ng.loot.ILootLearner;
import ipsis.woot.tileentity.ng.loot.LootGeneration;
import ipsis.woot.tileentity.ng.mock.MockPowerStation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityMobFarm extends TileEntity implements ITickable {

    private ITickTracker tickTracker;
    private IFarmStructure farmStructure;
    private IFarmSetup farmSetup;
    private PowerRecipe powerRecipe;
    private SpawnRecipe spawnRecipe;
    private IPowerCalculator powerCalculator;
    private IRecipeProgressTracker recipeProgressTracker;
    private ISpawnRecipeConsumer spawnRecipeConsumer;
    private IPowerStation powerStation;

    ISpawnRecipeRepository spawnRecipeRepository;

    public TileEntityMobFarm() {

        tickTracker = new SimpleTickTracker();
        tickTracker.setWorld(getWorld());
        tickTracker.setLearnTickCount(Woot.wootConfiguration.getInteger(ConfigManager.EnumConfigKey.LEARN_TICKS));
        tickTracker.setStructureTickCount(20);

        farmStructure = new FarmStructure().setWorld(getWorld()).setPosition(getPos());
        farmStructure.setWorld(getWorld());
        powerStation = new MockPowerStation();
        spawnRecipeConsumer = new SpawnRecipeConsumer();

        recipeProgressTracker.setPowerStation(powerStation);
    }

    @Override
    public void update() {

        tickTracker.tick();
        farmStructure.tick(tickTracker);

        if (farmStructure.isFormed()) {
            if (farmStructure.hasChanged()) {
                farmSetup = farmStructure.createSetup();
                powerRecipe = powerCalculator.calculate(farmSetup);
                spawnRecipe = spawnRecipeRepository.get(farmSetup.getWootMob().getWootMobName());
                recipeProgressTracker.setPowerRecipe(powerRecipe);
                farmStructure.clearChanged();
            }

            Woot.lootLearner.tick(tickTracker, getWorld(), getPos(), farmSetup);
            recipeProgressTracker.tick();
            if (recipeProgressTracker.isComplete() && spawnRecipeConsumer.consume(spawnRecipe, farmSetup.getNumMobs())) {
                Woot.lootGeneration.generate(farmStructure.getConnectedTanks(), farmStructure.getConnectedChests(), farmSetup);
                recipeProgressTracker.reset();
            }
        }

    }
}
