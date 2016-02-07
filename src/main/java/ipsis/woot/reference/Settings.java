package ipsis.woot.reference;

public class Settings {

    public static class Spawner {

        public static final int DEF_SAMPLE_SIZE = 100;

        public static final int DEF_RATE_BASE_TICKS = 8 * 20; /* 16 * 20; */
        public static final int DEF_RATE_I_TICKS = 4 * 20; /*8 * 20; */
        public static final int DEF_RATE_II_TICKS = 2 * 20; /*4 * 20; */
        public static final int DEF_RATE_III_TICKS = 1 * 20; /* 2 * 20; */

        public static final int DEF_LEARN_TICKS = 40;
        public static final int DEF_BASE_RF = 1000;
    }

    public static class Power {

        public static final float DEF_RATE_I_MULTI = 2.0F;
        public static final float DEF_RATE_II_MULTI = 10.0F;
        public static final float DEF_RATE_III_MULTI = 50.0F;

        public static final float DEF_LOOTING_I_MULTI = 2.0F;
        public static final float DEF_LOOTING_II_MULTI = 10.0F;
        public static final float DEF_LOOTING_III_MULTI = 50.0F;

        public static final float DEF_XP_I_MULTI = 2.0F;

        public static final boolean DEF_STRICT_POWER = false;
    }

    public static int sampleSize;
    public static int baseRf;
    public static int rateBaseTicks;
    public static int rateITicks;
    public static int rateIITicks;
    public static int rateIIITicks;
    public static int learnTicks;
    public static float rateIMulti;
    public static float rateIIMulti;
    public static float rateIIIMulti;
    public static float lootingIMulti;
    public static float lootingIIMulti;
    public static float lootingIIIMulti;
    public static float xpIMulti;
    public static boolean strictPower;

}
