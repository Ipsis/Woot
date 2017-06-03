package ipsis.woot.tileentity.ng;

public interface IRecipeProgressTracker {

    void tick();
    boolean isComplete();
    void reset();
    void setPowerRecipe(PowerRecipe powerRecipe);
    void setPowerStation(IPowerStation powerStation);
}
