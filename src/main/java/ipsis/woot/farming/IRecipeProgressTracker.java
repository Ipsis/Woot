package ipsis.woot.farming;

import ipsis.woot.power.storage.IPowerStation;

public interface IRecipeProgressTracker {

    void tick();
    boolean isComplete();
    void reset();
    void setPowerRecipe(PowerRecipe powerRecipe);
    void setPowerStation(IPowerStation powerStation);
    int getConsumedPower();
}
