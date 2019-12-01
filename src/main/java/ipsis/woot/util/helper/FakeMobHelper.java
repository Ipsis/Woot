package ipsis.woot.util.helper;

import ipsis.woot.util.FakeMob;
import net.minecraft.util.ResourceLocation;

public class FakeMobHelper {

    public static final String CHARGED_TAG = "charged";
    public static final String SMALL_TAG = "small";
    public static final String LARGE_TAG = "large";

    private static final String CREEPER = "minecraft:creeper";
    private static final String SLIME = "minecraft:slime";
    private static final String MAGMA_CUBE = "minecraft:magma_cube";

    private static boolean isEntity(FakeMob fakeMob, String s) {
        ResourceLocation rl = fakeMob.getResourceLocation();
        if (rl == null || !rl.getNamespace().equalsIgnoreCase(s))
            return false;

        return true;
    }

    public static boolean isChargedCreeper(FakeMob fakeMob) {
        return isEntity(fakeMob, CREEPER) && fakeMob.getTag().equalsIgnoreCase(CHARGED_TAG);
    }

    public static boolean isSmallSlime(FakeMob fakeMob) {
        return isEntity(fakeMob, SLIME) && fakeMob.getTag().equalsIgnoreCase(SMALL_TAG);
    }

    public static boolean isLargeSlime(FakeMob fakeMob) {
        return isEntity(fakeMob, SLIME) && fakeMob.getTag().equalsIgnoreCase(LARGE_TAG);
    }

    public static boolean isSmallMagmaCube(FakeMob fakeMob) {
        return isEntity(fakeMob, MAGMA_CUBE) && fakeMob.getTag().equalsIgnoreCase(SMALL_TAG);
    }

    public static boolean isLargeMagmaCube(FakeMob fakeMob) {
        return isEntity(fakeMob, MAGMA_CUBE) && fakeMob.getTag().equalsIgnoreCase(LARGE_TAG);
    }

    public static FakeMob createChargedCreeper() {
        return new FakeMob(CREEPER, CHARGED_TAG);
    }

    public static FakeMob createSmallSlime() {
        return new FakeMob(SLIME, SMALL_TAG);
    }

    public static FakeMob createLargeSlime() {
        return new FakeMob(SLIME, LARGE_TAG);
    }

    public static FakeMob createSmallMagmaCube() {
        return new FakeMob(MAGMA_CUBE, SMALL_TAG);
    }

    public static FakeMob createLargeMagmaCube() {
        return new FakeMob(MAGMA_CUBE, LARGE_TAG);
    }

}
