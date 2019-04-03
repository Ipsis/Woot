package ipsis.woot.recipes.factory;

import net.minecraft.util.math.MathHelper;

public class FactoryRecipe {

    private int numTicks;
    private int numUnits;

    public int getNumTicks() { return numTicks; }
    public int getNumUnits() { return numUnits; }

    public FactoryRecipe() {
        numTicks = 1;
        numUnits = 1;
    }

    public FactoryRecipe(int numTicks, int numUnits) {
        numTicks = MathHelper.clamp(numTicks, 1, Integer.MAX_VALUE);
        numUnits = MathHelper.clamp(numTicks, 1, Integer.MAX_VALUE);
        this.numTicks = numTicks;
        this.numUnits = numUnits;
    }

    public int getUnitsPerTick() {
        return MathHelper.clamp(numUnits / numTicks, 1, Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        return "units:" + numUnits + "/ticks:" + numTicks;
    }
}
