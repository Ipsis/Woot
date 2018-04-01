package ipsis.woot.farming;

/**
 * The PowerRecipe supports
 *
 * number of ticks 1->Integer.MAX_VALUE
 * power per tick 1->Integer.MAX_VALUE
 * total power 1->Long.MAX_VALUE
 */
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

        if (perTick < 1)
            perTick = 1;

        return (int)perTick;
    }

    public PowerRecipe(int ticks, long totalPower) {

        this.ticks = ticks;
        this.totalPower = totalPower;

        /**
         * If negative, assume there was an overflow
         */
        if (this.totalPower < 0)
            this.totalPower = Long.MAX_VALUE;
        if (this.ticks < 0)
            this.ticks = Integer.MAX_VALUE;

        if (this.totalPower < 1)
            this.totalPower = 1;
        if (this.ticks < 1)
            this.ticks = 1;
    }

    private PowerRecipe() { }

    @Override
    public String toString() {
        return "total:" + totalPower + " ticks:" + ticks + " perTick:" + getPowerPerTick();
    }
}
