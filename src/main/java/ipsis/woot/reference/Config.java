package ipsis.woot.reference;

public class Config {

    public static class General {

        public static final String SAMPLE_SIZE = "globalSampleSize";
        public static final String LEARN_TICKS = "globalLearnTicks";
        public static final String STRICT_SPAWNS = "globalStrictSpawns";
        public static final String STRICT_POWER = "globalStrictPower";
        public static final String BASE_RF = "globalBaseRF";
        public static final String BASE_MOB_COUNT = "globalBaseMobCount";
        public static final String BASE_RATE_TICKS = "globalBaseRateTicks";
    }

    public static class Power {

        public static final String RATE_I_COST = "rateI_RFTick";
        public static final String RATE_II_COST = "rateII_RFTick";
        public static final String RATE_III_COST = "rateIII_RFTick";

        public static final String LOOTING_I_COST = "lootingI_RFTick";
        public static final String LOOTING_II_COST = "lootingII_RFTick";
        public static final String LOOTING_III_COST = "lootingIII_RFTick";

        public static final String XP_I_COST = "xpI_RFTick";
        public static final String XP_II_COST = "xpII_RFTick";
        public static final String XP_III_COST = "xpIII_RFTick";

        public static final String MASS_I_COST = "massI_RFTick";
        public static final String MASS_II_COST = "massII_RFTick";
        public static final String MASS_III_COST = "massIII_RFTick";

        public static final String DECAP_I_COST = "decapI_RFTick";
        public static final String DECAP_II_COST = "decapII_RFTick";
        public static final String DECAP_III_COST = "decapIII_RFTick";
    }

    public static class Upgrades {

        public static final String LOOTING_I_LEVEL = "lootingI_enchantLevel";
        public static final String LOOTING_II_LEVEL = "lootingII_enchantLevel";
        public static final String LOOTING_III_LEVEL = "lootingIII_enchantLevel";

        public static final String RATE_I_TICKS = "rateI_ticks";
        public static final String RATE_II_TICKS = "rateII_ticks";
        public static final String RATE_III_TICKS = "rateIII_ticks";

        public static final String MASS_I_MOB_COUNT = "massI_mobCount";
        public static final String MASS_II_MOB_COUNT = "massII_mobCount";
        public static final String MASS_III_MOB_COUNT = "massIII_mobCount";

        public static final String DECAP_I_CHANCE = "decapI_percentage";
        public static final String DECAP_II_CHANCE = "decapII_percentage";
        public static final String DECAP_III_CHANCE = "decapIII_percentage";

        public static final String XP_I_BOOST = "xpI_boost";
        public static final String XP_II_BOOST = "xpII_boost";
        public static final String XP_III_BOOST = "xpIII_boost";
    }
}
