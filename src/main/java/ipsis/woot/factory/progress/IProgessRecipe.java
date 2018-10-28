package ipsis.woot.factory.progress;

public interface IProgessRecipe {

    int getTotalTicks();
    long getTotalRecipeUnits();
    int getRecipeUnitsPerTick();
    void setRecipe(int totalTicks, long totalUnits);
}
