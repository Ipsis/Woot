package ipsis.woot.farming;

public class PowerRecipe {

    public int getTicks() {
        return ticks;
    }

    private int ticks;

    public long getTotalPower() {
        return totalPower;
    }

    private long totalPower;

    public int getPowerPerTick() {

        long perTick = totalPower / ticks;
        if (totalPower % ticks > 0)
            perTick++;

        if (perTick > Integer.MAX_VALUE)
            perTick = Integer.MAX_VALUE;

        return (int)perTick;
    }

    public PowerRecipe(int ticks, long totalPower) {

        this.ticks = ticks;
        this.totalPower = totalPower;
    }

    private PowerRecipe() { }

    @Override
    public String toString() {
        return "total:" + totalPower + " ticks:" + ticks + " perTick:" + getPowerPerTick();
    }
}
