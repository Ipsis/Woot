package ipsis.woot.tileentity.ng;

import ipsis.woot.tileentity.ng.power.storage.IPowerStation;

public interface IRecipeProgressTracker {

    void tick();
    boolean isComplete();
    void reset();
    void setPowerRecipe(PowerRecipe powerRecipe);
    void setPowerStation(IPowerStation powerStation);
}
