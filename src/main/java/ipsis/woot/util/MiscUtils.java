package ipsis.woot.util;

import net.minecraft.util.math.MathHelper;

public class MiscUtils {

    public static int clampLooting(int looting) {
        return MathHelper.clamp(looting, 0, 3);
    }
}
