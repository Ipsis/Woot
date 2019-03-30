package ipsis.woot.util.helper;


import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class RandomHelper {

    private static Random RANDOM = new Random();

    public static boolean willDrop(int chance) {
        return willDrop((float)chance);
    }

    public static boolean willDrop(float chance) {
        chance = MathHelper.clamp(chance, 0.0F, 100.0F);
        return chance == 100.0F ? true : RANDOM.nextFloat() < (chance / 100.0F);
    }
}
