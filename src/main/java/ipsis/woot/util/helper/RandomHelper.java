package ipsis.woot.util.helper;

import ipsis.woot.Woot;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class RandomHelper {

    public static final Random RANDOM = new Random();

    private static final float PERCENT_MIN = 0.0F;
    private static final float PERCENT_MAX = 100.0F;
    public static boolean rollPercentage(double limit, String context) {
        limit = MathHelper.clamp(limit, PERCENT_MIN, PERCENT_MAX);
        if (limit == PERCENT_MAX) {
            //Woot.setup.getLogger().debug("rollPercentage: {} guaranteed drop", context);
            return true;
        } else if (limit == PERCENT_MIN) {
            return false;
        }

        double x = (Math.random() * ((PERCENT_MAX - PERCENT_MIN) + 1)) + PERCENT_MIN;
        //Woot.setup.getLogger().debug("rollPercentage: {} rolled:{} limit:{}", context, x, limit);
        return x <= limit;
    }
}
