package ipsis.woot.util.helper;

import ipsis.woot.Woot;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class RandomHelper {

    private static final Random RANDOM = new Random();

    private static final float PERCENT_MIN = 0.0F;
    private static final float PERCENT_MAX = 100.0F;
    public static boolean rollPercentage(double limit) {
        limit = MathHelper.clamp(limit, PERCENT_MIN, PERCENT_MAX);
        if (limit == PERCENT_MAX)
            return true;

        double x = (Math.random() * ((PERCENT_MAX - PERCENT_MIN) + 1)) + PERCENT_MIN;
        Woot.setup.getLogger().debug("rollPercentage: limit:{} x:{}", limit, x);
        return x <= limit;
    }
}
