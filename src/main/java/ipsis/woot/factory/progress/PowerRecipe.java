package ipsis.woot.factory.progress;

public class PowerRecipe implements IProgessRecipe {

    private int totalTicks;
    private long totalPower;

    @Override
    public int getTotalTicks() {
        return totalTicks;
    }

    @Override
    public long getTotalRecipeUnits() {
        return totalPower;
    }

    @Override
    public int getRecipeUnitsPerTick() {

        long perTick = totalPower / totalTicks;
        if (totalPower % totalTicks > 0)
            perTick++;

        if (perTick > Integer.MAX_VALUE)
            perTick = Integer.MAX_VALUE;

        if (perTick < 0)
            perTick = 0;

        return (int)perTick;
    }

    @Override
    public void setRecipe(int totalTicks, long totalUnits) {

        this.totalPower = totalUnits;
        this.totalTicks = totalTicks;

        if (this.totalPower < 0)
            this.totalPower = Long.MAX_VALUE;
        if (this.totalTicks < 0)
            this.totalTicks = Integer.MAX_VALUE;

        if (this.totalTicks < 1)
            this.totalTicks = 1;
    }

    public PowerRecipe() {
        setRecipe(1, 0);
    }

    @Override
    public String toString() {
        return "total:" + totalPower + " ticks:" + totalTicks + " perTick: " + getRecipeUnitsPerTick();
    }

    public static IProgessRecipe createRecipe(int totalTicks, long totalUnits) {

        PowerRecipe powerRecipe = new PowerRecipe();
        powerRecipe.setRecipe(totalTicks, totalUnits);
        return powerRecipe;
    }
}
