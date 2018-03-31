package ipsis.woot.farming;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.power.storage.IPowerStation;
import net.minecraft.util.math.MathHelper;

public class SimpleRecipeProgressTracker implements IRecipeProgressTracker {

    private IPowerStation powerStation;
    private PowerRecipe powerRecipe;
    private long consumedPower = 0;

    /**
     * IRecipeProgressTracker
     */
    public void tick() {

        int consumed = powerStation.consume(powerRecipe.getPowerPerTick());
        if (Woot.wootConfiguration.getBoolean(EnumConfigKey.STRICT_POWER)) {
            if (consumed >= powerRecipe.getPowerPerTick())
                consumedPower += consumed;
        } else {
            consumedPower += consumed;
        }
    }

    public boolean isComplete() {

        return consumedPower >= powerRecipe.getTotalPower();
    }

    @Override
    public int getProgress() {

        int p = (int)((100.0F/ (double)powerRecipe.getTotalPower()) * (double) consumedPower);
        return MathHelper.clamp(p, 0, 100);
    }

    public void reset() {

        consumedPower = 0;
    }

    public void setPowerRecipe(PowerRecipe powerRecipe) {

        this.powerRecipe = powerRecipe;
        consumedPower = 0;
    }

    public void setPowerStation(IPowerStation powerStation) {

        this.powerStation = powerStation;
    }

    @Override
    public long getConsumedPower() {

        return consumedPower;
    }

    @Override
    public void setConsumedPower(long power) {

        this.consumedPower = power;
    }


}
