package ipsis.woot.handler;

import ipsis.Woot;
import ipsis.woot.manager.MobRegistry;
import ipsis.woot.manager.loot.LootEnderDragon;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Config;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
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
        Settings.tierIRFtick = getConfigInt(Config.General.TIER_I_RF, Settings.Power.DEF_TIER_I_RF_TICK);
        Settings.tierIIRFtick = getConfigInt(Config.General.TIER_II_RF, Settings.Power.DEF_TIER_II_RF_TICK);
        Settings.tierIIIRFtick = getConfigInt(Config.General.TIER_III_RF, Settings.Power.DEF_TIER_III_RF_TICK);
        Settings.tierIVRFtick = getConfigInt(Config.General.TIER_IV_RF, Settings.Power.DEF_TIER_IV_RF_TICK);
        Settings.xpRFtick = getConfigInt(Config.General.XP_RF, Settings.Power.DEF_XP_RF_TICK);
        Settings.baseMobCount = getConfigInt(Config.General.BASE_MOB_COUNT, Settings.Spawner.DEF_BASE_MOB_COUNT);
        Settings.baseRateTicks = getConfigInt(Config.General.BASE_RATE_TICKS, Settings.Spawner.DEF_BASE_RATE_TICKS);
        Settings.tierIMobXpCap = getConfigInt(Config.General.TIER_I_MOB_XP_CAP, Settings.Spawner.DEF_TIER_I_MOB_XP_CAP);
        Settings.tierIIMobXpCap = getConfigInt(Config.General.TIER_II_MOB_XP_CAP, Settings.Spawner.DEF_TIER_II_MOB_XP_CAP);
        Settings.tierIIIMobXpCap = getConfigInt(Config.General.TIER_III_MOB_XP_CAP, Settings.Spawner.DEF_TIER_III_MOB_XP_CAP);
        Settings.tierIVMobXpCap = getConfigInt(Config.General.TIER_IV_MOB_XP_CAP, Settings.Spawner.DEF_TIER_IV_MOB_XP_CAP);
        Settings.maxPower = getConfigInt(Config.Power.MAX_POWER, Settings.Power.DEF_MAX_POWER);
        Settings.allowXPGen = getConfigBool(Config.General.ALLOW_XP_GEN, Settings.Spawner.DEF_ALLOW_XP_GEN);

        Settings.prismBlacklist = configuration.getStringList(Config.General.PRISM_BLACKLIST, Configuration.CATEGORY_GENERAL,
                Settings.Progression.DEF_PRISM_BLACKLIST, StringHelper.localize(Lang.getLangConfigValue(Config.General.PRISM_BLACKLIST)));
        Settings.prismWhitelist = configuration.getStringList(Config.General.PRISM_WHITELIST, Configuration.CATEGORY_GENERAL,
                Settings.Progression.DEF_PRISM_WHITELIST, StringHelper.localize(Lang.getLangConfigValue(Config.General.PRISM_WHITELIST)));
        Settings.usePrismWhitelist = getConfigBool(Config.General.PRISM_USE_WHITELIST, Settings.Spawner.DEF_PRISM_USE_WHITELIST);

        if (Settings.usePrismWhitelist) {
            for (int i = 0; i < Settings.prismWhitelist.length; i++)
                LogHelper.info("Using Prism Whitelist: " + Settings.prismWhitelist[i]);
        } else {
            for (int i = 0; i < Settings.prismBlacklist.length; i++)
                LogHelper.info("Using Prism Blacklist: " + Settings.prismBlacklist[i]);
        }

        Settings.tierIMobs = configuration.getStringList(Config.General.TIER_I_MOB_LIST, Configuration.CATEGORY_GENERAL,
                Settings.Progression.DEF_TIER_I_MOBS, StringHelper.localize(Lang.getLangConfigValue(Config.General.TIER_I_MOB_LIST)));
        Settings.tierIIMobs = configuration.getStringList(Config.General.TIER_II_MOB_LIST, Configuration.CATEGORY_GENERAL,
                Settings.Progression.DEF_TIER_II_MOBS, StringHelper.localize(Lang.getLangConfigValue(Config.General.TIER_II_MOB_LIST)));
        Settings.tierIIIMobs = configuration.getStringList(Config.General.TIER_III_MOB_LIST, Configuration.CATEGORY_GENERAL,
                Settings.Progression.DEF_TIER_III_MOBS, StringHelper.localize(Lang.getLangConfigValue(Config.General.TIER_III_MOB_LIST)));
        Settings.tierIVMobs = configuration.getStringList(Config.General.TIER_IV_MOB_LIST, Configuration.CATEGORY_GENERAL,
                Settings.Progression.DEF_TIER_IV_MOBS, StringHelper.localize(Lang.getLangConfigValue(Config.General.TIER_IV_MOB_LIST)));

        for (int i = 0; i < Settings.tierIMobs.length; i++)
            Woot.tierMapper.addMapping(Settings.tierIMobs[i], EnumMobFactoryTier.TIER_ONE);
        for (int i = 0; i < Settings.tierIIMobs.length; i++)
            Woot.tierMapper.addMapping(Settings.tierIIMobs[i], EnumMobFactoryTier.TIER_TWO);
        for (int i = 0; i < Settings.tierIIIMobs.length; i++)
            Woot.tierMapper.addMapping(Settings.tierIIIMobs[i], EnumMobFactoryTier.TIER_THREE);
        for (int i = 0; i < Settings.tierIVMobs.length; i++)
            Woot.tierMapper.addMapping(Settings.tierIVMobs[i], EnumMobFactoryTier.TIER_FOUR);

        Settings.dropBlacklist = configuration.getStringList(Config.General.DROP_BLACKLIST, Configuration.CATEGORY_GENERAL,
                Settings.dropBlacklist, StringHelper.localize(Lang.getLangConfigValue(Config.General.DROP_BLACKLIST)));
        for (int i = 0; i < Settings.dropBlacklist.length; i++)
            Woot.LOOT_TABLE_MANAGER.addToBlacklist(Settings.dropBlacklist[i]);

        Settings.spawnCostList = configuration.getStringList(Config.General.SPAWN_COST_LIST, Configuration.CATEGORY_GENERAL,
                Settings.Progression.DEF_SPAWN_COST, StringHelper.localize(Lang.getLangConfigValue(Config.General.SPAWN_COST_LIST)));
        for (int i = 0; i < Settings.spawnCostList.length; i++) {
            String mob;
            int cost;
            String[] parts = Settings.spawnCostList[i].split("=");
            if (parts.length == 2) {
                try {
                    cost = Integer.parseInt(parts[1]);
                    Woot.mobRegistry.addCosting(parts[0], cost);
                    LogHelper.info("Adding mob cost: " + parts[0] + "=" + cost);
                } catch (NumberFormatException e) {
                    LogHelper.error("Invalid mob cost: " + Settings.spawnCostList[i]);
                }
            } else {
                LogHelper.error("Invalid mob cost: " + Settings.spawnCostList[i]);
            }
        }

        /**
         * EnderDragon
         */
        Settings.allowEnderDragon = getConfigBool(Config.General.ALLOW_ENDER_DRAGON, Settings.Spawner.DEF_ALLOW_ENDER_DRAGON);
        Settings.dragonDeathCost = getConfigInt(Config.General.ENDER_DRAGON_DEATH_COST, Settings.EnderDragon.DEF_DEATH_COST);
        Settings.dragonSpawnCost = getConfigInt(Config.General.ENDER_DRAGON_SPAWN_COST, Settings.EnderDragon.DEF_SPAWN_COST);

        Woot.mobRegistry.addCosting(MobRegistry.ENDER_DRAGON, Settings.dragonSpawnCost);
        Woot.mobRegistry.addMapping(MobRegistry.ENDER_DRAGON, Settings.dragonDeathCost);
        Woot.tierMapper.addMapping(MobRegistry.ENDER_DRAGON, EnumMobFactoryTier.TIER_FOUR);
        Settings.dragonDropList = configuration.getStringList(Config.General.ENDER_DRAGON_DROP_LIST, Configuration.CATEGORY_GENERAL,
                Settings.EnderDragon.DEF_DRAGON_DROPS, StringHelper.localize(Lang.getLangConfigValue(Config.General.ENDER_DRAGON_DROP_LIST)));


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

        Settings.bmIRfTick = getConfigInt(Config.Power.BM_I_COST, Settings.Power.DEF_BM_I_RF_TICK);
        Settings.bmIIRfTick = getConfigInt(Config.Power.BM_II_COST, Settings.Power.DEF_BM_II_RF_TICK);
        Settings.bmIIIRfTick = getConfigInt(Config.Power.BM_III_COST, Settings.Power.DEF_BM_III_RF_TICK);

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

        Settings.efficiencyI = getConfigInt(Config.Upgrades.EFFICIENCY_I_PERCENTAGE, Settings.Upgrades.DEF_EFFICIENCY_I_PERCENTAGE);
        Settings.efficiencyII = getConfigInt(Config.Upgrades.EFFICIENCY_II_PERCENTAGE, Settings.Upgrades.DEF_EFFICIENCY_II_PERCENTAGE);
        Settings.efficiencyIII = getConfigInt(Config.Upgrades.EFFICIENCY_III_PERCENTAGE, Settings.Upgrades.DEF_EFFICIENCY_III_PERCENTAGE);

        Settings.bmICount = getConfigInt(Config.Upgrades.BM_I_COUNT, Settings.Upgrades.DEF_BM_I_SACRIFICE_COUNT);
        Settings.bmIICount = getConfigInt(Config.Upgrades.BM_II_COUNT, Settings.Upgrades.DEF_BM_II_SACRIFICE_COUNT);
        Settings.bmIIICount = getConfigInt(Config.Upgrades.BM_III_COUNT, Settings.Upgrades.DEF_BM_III_SACRIFICE_COUNT);

        Settings.bmIAltarLifeEssence = getConfigInt(Config.Upgrades.BM_I_ALTAR_LIFE_ESSENCE, Settings.Upgrades.DEF_BM_I_ALTAR_LIFE_ESSENCE);
        Settings.bmIIAltarLifeEssence = getConfigInt(Config.Upgrades.BM_II_ALTAR_LIFE_ESSENCE, Settings.Upgrades.DEF_BM_II_ALTAR_LIFE_ESSENCE);
        Settings.bmIIIAltarLifeEssence = getConfigInt(Config.Upgrades.BM_III_ALTAR_LIFE_ESSENCE, Settings.Upgrades.DEF_BM_III_ALTAR_LIFE_ESSENCE);

        if (configuration.hasChanged())
            configuration.save();
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
            loadConfiguration();
    }
}
