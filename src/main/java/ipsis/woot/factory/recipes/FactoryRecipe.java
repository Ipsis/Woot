package ipsis.woot.factory.recipes;

import net.minecraft.util.math.MathHelper;

/**
 * This is base recipe for progress in the factory.
 */
public class FactoryRecipe {

    private int numTicks;
    private int numUnits;

    public int getNumTicks() {
        return numTicks;
    }

    public int getNumUnits() {
        return numUnits;
    }

    /**
     * This will ALWAYS be at least 1
     */
    public int getUnitsPerTick() {
        return MathHelper.clamp(this.numUnits / this.numTicks, 1, Integer.MAX_VALUE);
    }

    public FactoryRecipe(int numTicks, int numUnits) {
        if (numTicks >= 1 && numUnits >= 1) {
            this.numTicks = numTicks;
            this.numUnits = numUnits;
        } else {
            this.numTicks = this.numUnits = 1;
        }
    }
}
