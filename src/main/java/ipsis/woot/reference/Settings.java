package ipsis.woot.reference;

public class Settings {

    public static class Spawner {

        public static final int DEF_SAMPLE_SIZE = 500;
        public static final int DEF_LEARN_TICKS = 20;
        public static final boolean DEF_STRICT_FACTORY_SPAWNS = false;
        public static final boolean DEF_STRICT_POWER = false;
        public static final int DEF_BASE_MOB_COUNT = 1;
        public static final int DEF_BASE_RATE_TICKS = 16 * 20;
        public static final int DEF_TIER_I_MOB_XP_CAP = 5;
        public static final int DEF_TIER_II_MOB_XP_CAP = 20;
        public static final int DEF_TIER_III_MOB_XP_CAP = 49;
        public static final int DEF_TIER_IV_MOB_XP_CAP = 65535;
        public static final boolean DEF_PRISM_USE_WHITELIST = false;
    }

    public static class Progression {

        public static final String[] DEF_PRISM_BLACKLIST = { };
        public static final String[] DEF_PRISM_WHITELIST = { };
        public static final String[] DEF_TIER_I_MOBS = { };
        public static final String[] DEF_TIER_II_MOBS = {
                "Woot:none:Blaze",
                "Woot:none:Witch",
                "Woot:none:Ghast",
                "Woot:none:PigZombie",
                "Woot:none:MagmaCube"
        };
        public static final String[] DEF_TIER_III_MOBS = {
                "Woot:none:Enderman",
                "Woot:none:IronGolem",
                "Woot:none:Guardian",
                "Woot:Wither:Skeleton"
        };
        public static final String[] DEF_TIER_IV_MOBS = {
                "Woot:none:WitherBoss"
        };

        public static final String[] DEF_SPAWN_COST = {
                "Woot:none:IronGolem=10",
                "Woot:Wither:Skeleton=10",
                "Woot:none:MagmaCube=5",
                "Woot:none:Enderman=10"
        };

    }

    public static class Power {

        /**
         * Defaults are based around an Ender IO full operation Stirling Generator
         * running @ 80RF/tick
         */
        public static final int BASE_RF = 80;
        public static final int DEF_TIER_I_RF_TICK = BASE_RF;
        public static final int DEF_TIER_II_RF_TICK = BASE_RF * 2;
        public static final int DEF_TIER_III_RF_TICK = BASE_RF * 3;
        public static final int DEF_TIER_IV_RF_TICK = BASE_RF * 4;

        public static final int DEF_XP_RF_TICK = 16; /* 80 / 5 (xp of zombie) */

        public static final int DEF_TI_UPGRADE = 80;
        public static final int DEF_TII_UPGRADE = 160;
        public static final int DEF_TIII_UPGRADE = 240;

        public static final int DEF_RATE_I_RF_TICK = DEF_TI_UPGRADE;
        public static final int DEF_RATE_II_RF_TICK = DEF_TII_UPGRADE;
        public static final int DEF_RATE_III_RF_TICK = DEF_TIII_UPGRADE;

        public static final int DEF_LOOTING_I_RF_TICK = DEF_TI_UPGRADE;
        public static final int DEF_LOOTING_II_RF_TICK = DEF_TII_UPGRADE;
        public static final int DEF_LOOTING_III_RF_TICK = DEF_TIII_UPGRADE;

        public static final int DEF_XP_I_RF_TICK = DEF_TI_UPGRADE;
        public static final int DEF_XP_II_RF_TICK = DEF_TII_UPGRADE;
        public static final int DEF_XP_III_RF_TICK = DEF_TIII_UPGRADE;

        public static final int DEF_MASS_I_RF_TICK = DEF_TI_UPGRADE;
        public static final int DEF_MASS_II_RF_TICK = DEF_TII_UPGRADE;
        public static final int DEF_MASS_III_RF_TICK = DEF_TIII_UPGRADE;

        public static final int DEF_DECAPITATE_I_RF_TICK = DEF_TI_UPGRADE;
        public static final int DEF_DECAPITATE_II_RF_TICK = DEF_TII_UPGRADE;
        public static final int DEF_DECAPITATE_III_RF_TICK = DEF_TIII_UPGRADE;
    }

    public static class Upgrades {

        public static final int DEF_LOOTING_I_LEVEL = 1;
        public static final int DEF_LOOTING_II_LEVEL = 2;
        public static final int DEF_LOOTING_III_LEVEL = 3;

        public static final int DEF_RATE_I_TICKS = 8 * 20;
        public static final int DEF_RATE_II_TICKS = 4 * 20;
        public static final int DEF_RATE_III_TICKS = 2 * 20;

        public static final int DEF_MASS_I_MOBS = 4;
        public static final int DEF_MASS_II_MOBS = 6;
        public static final int DEF_MASS_III_MOBS = 8;

        public static final int DEF_DECAPITATE_I_CHANCE = 20;
        public static final int DEF_DECAPITATE_II_CHANCE = 40;
        public static final int DEF_DECAPITATE_III_CHANCE = 80;

        public static final int DEF_XP_I_BOOST = 20;
        public static final int DEF_XP_II_BOOST = 40;
        public static final int DEF_XP_III_BOOST = 80;

        public static final int DEF_EFFICIENCY_I_PERCENTAGE = 15;
        public static final int DEF_EFFICIENCY_II_PERCENTAGE = 25;
        public static final int DEF_EFFICIENCY_III_PERCENTAGE = 30;
    }

    /**
     * Global settings
     */
    public static int sampleSize;
    public static int learnTicks;
    public static boolean strictFactorySpawns;
    public static boolean strictPower;
    public static int baseMobCount;
    public static int baseRateTicks;
    public static int tierIMobXpCap;
    public static int tierIIMobXpCap;
    public static int tierIIIMobXpCap;
    public static int tierIVMobXpCap;
    public static String[] prismBlacklist = new String[0];
    public static String[] prismWhitelist = new String[0];
    public static boolean usePrismWhitelist;
    public static String[] tierIMobs = new String[0];
    public static String[] tierIIMobs = new String[0];
    public static String[] tierIIIMobs = new String[0];
    public static String[] tierIVMobs = new String[0];
    public static String[] dropBlacklist = new String[0];
    public static String[] spawnCostList = new String[0];

    public static int tierIRFtick;
    public static int tierIIRFtick;
    public static int tierIIIRFtick;
    public static int tierIVRFtick;
    public static int xpRFtick;

    public static int lootingIRfTick;
    public static int lootingIIRfTick;
    public static int lootingIIIRfTick;

    public static int rateIRfTick;
    public static int rateIIRfTick;
    public static int rateIIIRfTick;

    public static int massIRfTick;
    public static int massIIRfTick;
    public static int massIIIRfTick;

    public static int decapitateIRfTick;
    public static int decapitateIIRfTick;
    public static int decapitateIIIRfTick;

    public static int xpIRfTick;
    public static int xpIIRfTick;
    public static int xpIIIRfTick;

    /**
     * Upgrade effect
     */
    public static int lootingILevel;
    public static int lootingIILevel;
    public static int lootingIIILevel;

    public static int rateITicks;
    public static int rateIITicks;
    public static int rateIIITicks;

    public static int massIMobs;
    public static int massIIMobs;
    public static int massIIIMobs;

    public static int decapitateIChance;
    public static int decapitateIIChance;
    public static int decapitateIIIChance;

    public static int xpIBoost;
    public static int xpIIBoost;
    public static int xpIIIBoost;

    public static int efficiencyI;
    public static int efficiencyII;
    public static int efficiencyIII;
}
