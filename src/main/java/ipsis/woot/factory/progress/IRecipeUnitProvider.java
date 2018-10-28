package ipsis.woot.factory.progress;

public interface IRecipeUnitProvider {

    /**
     * Consume the specified number of units
     * @return the number of units actually consumed
     */
    int consumeUnits(int units);
}
