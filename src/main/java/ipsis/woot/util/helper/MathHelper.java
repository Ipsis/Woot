package ipsis.woot.util.helper;

public class MathHelper {

    public static int clampLooting(int looting) {
        return net.minecraft.util.math.MathHelper.clamp(looting, 0, 3);
    }
}
