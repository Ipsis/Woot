package ipsis.woot.factory.progress;

public class MockRecipeUnitProvider implements IRecipeUnitProvider {

    @Override
    public int consumeUnits(int units) {
        return units;
    }
}
