package ipsis.woot.handler;

import ipsis.oss.LogHelper;
import ipsis.woot.reference.Config;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.util.StringHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigHandler {

    public static Configuration configuration;

    public static void init(File configFile) {

        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    static int getConfigInt(String key, int def) {

        return configuration.get(Configuration.CATEGORY_GENERAL, key, def, StringHelper.localize(Lang.getLangConfigValue(key))).getInt(def);
    }
    static boolean getConfigBool(String key, boolean def) {

        return configuration.get(Configuration.CATEGORY_GENERAL, key, def, StringHelper.localize(Lang.getLangConfigValue(key))).getBoolean(def);
    }

    static void loadConfiguration() {

        /**
         * Global settings
         */
        Settings.sampleSize = getConfigInt(Config.General.SAMPLE_SIZE, Settings.Spawner.DEF_SAMPLE_SIZE);
        Settings.learnTicks = getConfigInt(Config.General.LEARN_TICKS, Settings.Spawner.DEF_LEARN_TICKS);
        Settings.strictFactorySpawns = getConfigBool(Config.General.STRICT_SPAWNS, Settings.Spawner.DEF_STRICT_FACTORY_SPAWNS);
        Settings.strictPower = getConfigBool(Config.General.STRICT_POWER, Settings.Spawner.DEF_STRICT_POWER);
        Settings.tierIRF = getConfigInt(Config.General.TIER_I_RF, Settings.Spawner.DEF_TIER_I_RF);
        Settings.tierIIRF = getConfigInt(Config.General.TIER_II_RF, Settings.Spawner.DEF_TIER_II_RF);
        Settings.tierIIIRF = getConfigInt(Config.General.TIER_III_RF, Settings.Spawner.DEF_TIER_III_RF);
        Settings.baseMobCount = getConfigInt(Config.General.BASE_MOB_COUNT, Settings.Spawner.DEF_BASE_MOB_COUNT);
        Settings.baseRateTicks = getConfigInt(Config.General.BASE_RATE_TICKS, Settings.Spawner.DEF_BASE_RATE_TICKS);

        Settings.prismBlacklist = configuration.getStringList(Config.General.PRISM_BLACKLIST, Configuration.CATEGORY_GENERAL,
                Settings.prismBlacklist, StringHelper.localize(Lang.getLangConfigValue(Config.General.PRISM_BLACKLIST)));

        for (int i = 0; i < Settings.prismBlacklist.length; i++)
            LogHelper.info("Prism Blacklist: " + Settings.prismBlacklist[i]);

        /**
         * Power
         */
        Settings.rateIRfTick = getConfigInt(Config.Power.RATE_I_COST, Settings.Power.DEF_RATE_I_RF_TICK);
        Settings.rateIIRfTick = getConfigInt(Config.Power.RATE_II_COST, Settings.Power.DEF_RATE_II_RF_TICK);
        Settings.rateIIIRfTick = getConfigInt(Config.Power.RATE_III_COST, Settings.Power.DEF_RATE_III_RF_TICK);

        Settings.lootingIRfTick = getConfigInt(Config.Power.LOOTING_I_COST, Settings.Power.DEF_LOOTING_I_RF_TICK);
        Settings.lootingIIRfTick = getConfigInt(Config.Power.LOOTING_II_COST, Settings.Power.DEF_LOOTING_II_RF_TICK);
        Settings.lootingIIIRfTick = getConfigInt(Config.Power.LOOTING_III_COST, Settings.Power.DEF_LOOTING_III_RF_TICK);

        Settings.xpIRfTick = getConfigInt(Config.Power.XP_I_COST, Settings.Power.DEF_XP_I_RF_TICK);
        Settings.xpIIRfTick = getConfigInt(Config.Power.XP_II_COST, Settings.Power.DEF_XP_II_RF_TICK);
        Settings.xpIIIRfTick = getConfigInt(Config.Power.XP_III_COST, Settings.Power.DEF_XP_III_RF_TICK);

        Settings.massIRfTick = getConfigInt(Config.Power.MASS_I_COST, Settings.Power.DEF_MASS_I_RF_TICK);
        Settings.massIIRfTick = getConfigInt(Config.Power.MASS_II_COST, Settings.Power.DEF_MASS_II_RF_TICK);
        Settings.massIIIRfTick = getConfigInt(Config.Power.MASS_III_COST, Settings.Power.DEF_MASS_III_RF_TICK);

        Settings.decapitateIRfTick = getConfigInt(Config.Power.DECAP_I_COST, Settings.Power.DEF_DECAPITATE_I_RF_TICK);
        Settings.decapitateIIRfTick = getConfigInt(Config.Power.DECAP_II_COST, Settings.Power.DEF_DECAPITATE_II_RF_TICK);
        Settings.decapitateIIIRfTick = getConfigInt(Config.Power.DECAP_III_COST, Settings.Power.DEF_DECAPITATE_III_RF_TICK);

        /**
         * Upgrades
         */
        Settings.lootingILevel = getConfigInt(Config.Upgrades.LOOTING_I_LEVEL, Settings.Upgrades.DEF_LOOTING_I_LEVEL);
        Settings.lootingIILevel = getConfigInt(Config.Upgrades.LOOTING_II_LEVEL, Settings.Upgrades.DEF_LOOTING_II_LEVEL);
        Settings.lootingIIILevel = getConfigInt(Config.Upgrades.LOOTING_III_LEVEL, Settings.Upgrades.DEF_LOOTING_III_LEVEL);

        Settings.rateITicks = getConfigInt(Config.Upgrades.RATE_I_TICKS, Settings.Upgrades.DEF_RATE_I_TICKS);
        Settings.rateIITicks = getConfigInt(Config.Upgrades.RATE_II_TICKS, Settings.Upgrades.DEF_RATE_II_TICKS);
        Settings.rateIIITicks = getConfigInt(Config.Upgrades.RATE_III_TICKS, Settings.Upgrades.DEF_RATE_III_TICKS);

        Settings.massIMobs = getConfigInt(Config.Upgrades.MASS_I_MOB_COUNT, Settings.Upgrades.DEF_MASS_I_MOBS);
        Settings.massIIMobs = getConfigInt(Config.Upgrades.MASS_II_MOB_COUNT, Settings.Upgrades.DEF_MASS_II_MOBS);
        Settings.massIIIMobs = getConfigInt(Config.Upgrades.MASS_III_MOB_COUNT, Settings.Upgrades.DEF_MASS_III_MOBS);

        Settings.decapitateIChance = getConfigInt(Config.Upgrades.DECAP_I_CHANCE, Settings.Upgrades.DEF_DECAPITATE_I_CHANCE);
        Settings.decapitateIIChance = getConfigInt(Config.Upgrades.DECAP_II_CHANCE, Settings.Upgrades.DEF_DECAPITATE_II_CHANCE);
        Settings.decapitateIIIChance = getConfigInt(Config.Upgrades.DECAP_III_CHANCE, Settings.Upgrades.DEF_DECAPITATE_III_CHANCE);

        Settings.xpIBoost = getConfigInt(Config.Upgrades.XP_I_BOOST, Settings.Upgrades.DEF_XP_I_BOOST);
        Settings.xpIIBoost = getConfigInt(Config.Upgrades.XP_II_BOOST, Settings.Upgrades.DEF_XP_II_BOOST);
        Settings.xpIIIBoost = getConfigInt(Config.Upgrades.XP_III_BOOST, Settings.Upgrades.DEF_XP_III_BOOST);

        if (configuration.hasChanged())
            configuration.save();
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
            loadConfiguration();
    }
}
