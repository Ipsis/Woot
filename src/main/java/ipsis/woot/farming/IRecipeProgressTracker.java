package ipsis.woot.farming;

import ipsis.woot.power.storage.IPowerStation;

public interface IRecipeProgressTracker {

    void tick();
    boolean isComplete();
    int getProgress();
    void reset();
    void setPowerRecipe(PowerRecipe powerRecipe);
    void setPowerStation(IPowerStation powerStation);
    long getConsumedPower();

    // For restore on load only
    void setConsumedPower(long power);
}
