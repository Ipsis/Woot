package ipsis.woot.factory.progress;

import ipsis.woot.factory.SimpleTickTracker;

public class RFRecipeProgressTracker {

    private IRecipeUnitProvider iRecipeUnitProvider;
    private IProgessRecipe iProgessRecipe;
    private long consumedPower;

    private RFRecipeProgressTracker() { }
    public RFRecipeProgressTracker(IProgessRecipe iProgessRecipe, IRecipeUnitProvider iRecipeUnitProvider) {
        this.iProgessRecipe = iProgessRecipe;
        this.iRecipeUnitProvider = iRecipeUnitProvider;
        this.consumedPower = 0;
    }

    public void tick(SimpleTickTracker tickTracker) {

        int consumed = iRecipeUnitProvider.consumeUnits(iProgessRecipe.getRecipeUnitsPerTick());
        consumedPower += consumed;
    }

    public boolean isComplete() {

        return consumedPower >= iProgessRecipe.getTotalRecipeUnits();
    }

    public void reset() {
        this.consumedPower = 0;
    }
}
