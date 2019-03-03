package ipsis.woot.util.helper;

import net.minecraft.util.math.MathHelper;

public class MiscHelper {

    public static int clampLooting(int looting) {
        return MathHelper.clamp(looting, 0, 3);
    }
}
