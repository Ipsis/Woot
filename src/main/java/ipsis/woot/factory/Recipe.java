package ipsis.woot.factory;


import net.minecraft.util.math.MathHelper;

public class Recipe {

    private int numTicks;
    private int numUnits;

    public int getNumTicks() {
        return numTicks;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public Recipe() {
        numTicks = 1;
        numUnits = 1;
    }

    public Recipe(int numTicks, int numUnits) {
        this.numTicks = MathHelper.clamp(numTicks, 1, Integer.MAX_VALUE);
        this.numUnits = MathHelper.clamp(numUnits, 1, Integer.MAX_VALUE);
    }

    public int getUnitsPerTick() {
        return MathHelper.clamp(numUnits / numTicks, 1, Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        return "u:" + numUnits + "/t:" + numTicks;
    }
}
