package ipsis.woot.tileentity.ng;

public class PowerRecipe {

    public int getTicks() {
        return ticks;
    }

    private int ticks;

    public int getTotalPower() {
        return totalPower;
    }

    private int totalPower;

    public int getPowerPerTick() {

        int perTick = totalPower / ticks;
        if (totalPower % ticks > 0)
            perTick++;

        return perTick;
    }

    public PowerRecipe(int ticks, int totalPower) {

        this.ticks = ticks;
        this.totalPower = totalPower;
    }

    private PowerRecipe() { }

    @Override
    public String toString() {
        return "total:" + totalPower + " ticks:" + ticks + " perTick:" + getPowerPerTick();
    }
}
