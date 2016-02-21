package ipsis.woot.reference;

public class Settings {

    public static class Spawner {

        public static final int DEF_SAMPLE_SIZE = 100;

        public static final int DEF_RATE_BASE_TICKS = 8 * 20; /* 16 * 20; */
        public static final int DEF_RATE_I_TICKS = 4 * 20; /*8 * 20; */
        public static final int DEF_RATE_II_TICKS = 2 * 20; /*4 * 20; */
        public static final int DEF_RATE_III_TICKS = 1 * 20; /* 2 * 20; */

        public static final int DEF_MASS_MOBS = 4;
        public static final int DEF_MASS_I_MOBS = 8;
        public static final int DEF_MASS_II_MOBS = 12;
        public static final int DEF_MASS_III_MOBS = 16;

        public static final int DEF_DECAPITATE_I_CHANCE = 10;
        public static final int DEF_DECAPITATE_II_CHANCE = 30;
        public static final int DEF_DECAPITATE_III_CHANCE = 70;

        public static final int DEF_LEARN_TICKS = 40;
        public static final int DEF_BASE_RF = 1000;

        public static final boolean DEF_STRICT_FACTORY_SPAWNS = false;
    }

    public static class Power {

        public static final float DEF_RATE_I_MULTI = 2.0F;
        public static final float DEF_RATE_II_MULTI = 10.0F;
        public static final float DEF_RATE_III_MULTI = 50.0F;

        public static final float DEF_LOOTING_I_MULTI = 2.0F;
        public static final float DEF_LOOTING_II_MULTI = 10.0F;
        public static final float DEF_LOOTING_III_MULTI = 50.0F;

        public static final float DEF_DECAPITATE_I_MULTI = 2.0F;
        public static final float DEF_DECAPITATE_II_MULTI = 2.0F;
        public static final float DEF_DECAPITATE_III_MULTI = 2.0F;

        public static final float DEF_XP_I_MULTI = 2.0F;
        public static final float DEF_XP_II_MULTI = 2.0F;
        public static final float DEF_XP_III_MULTI = 2.0F;

        public static final float DEF_MASS_I_MULTI = 2.0F;
        public static final float DEF_MASS_II_MULTI = 2.0F;
        public static final float DEF_MASS_III_MULTI = 2.0F;

        public static final boolean DEF_STRICT_POWER = false;
    }

    public static class Enchant {

        public static final int DEF_LOOTING_I_LEVEL = 1;
        public static final int DEF_LOOTING_II_LEVEL = 2;
        public static final int DEF_LOOTING_III_LEVEL = 3;
    }

    public static int sampleSize;
    public static int learnTicks;
    public static int baseRf;

    public static int rateBaseTicks;
    public static int rateITicks;
    public static int rateIITicks;
    public static int rateIIITicks;
    public static int massBaseMobs;
    public static int massIMobs;
    public static int massIIMobs;
    public static int massIIIMobs;

    public static float rateIMulti;
    public static float rateIIMulti;
    public static float rateIIIMulti;
    public static float lootingIMulti;
    public static float lootingIIMulti;
    public static float lootingIIIMulti;
    public static float xpIMulti;
    public static float xpIIMulti;
    public static float xpIIIMulti;
    public static float massIMulti;
    public static float massIIMulti;
    public static float massIIIMulti;
    public static float decapitateIMulti;
    public static float decapitateIIMulti;
    public static float decapitateIIIMulti;

    public static boolean strictPower;
    public static boolean strictFactorySpawns;

    public static int enchantLootingILevel;
    public static int enchantLootingIILevel;
    public static int enchantLootingIIILevel;

    public static int decapitateIChance;
    public static int decapitateIIChance;
    public static int decapitateIIIChance;

}
