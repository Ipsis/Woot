package ipsis.woot.configuration;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Lang;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import ipsis.woot.util.StringHelper;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationLoader {

    public static void load(Configuration config, IWootConfiguration wootConfiguration) {

        loadDefaults(wootConfiguration);

        // Boolean keys
        for (EnumConfigKey key : EnumConfigKey.getBooleanKeys()) {

            wootConfiguration.setBoolean(
                    key,
                    config.get(Configuration.CATEGORY_GENERAL,
                            key.getText(),
                            key.getDefaultBoolean(),
                            key.getTranslated()).getBoolean(key.getDefaultBoolean()));
        }

        // Integer keys
        for (EnumConfigKey key : EnumConfigKey.getIntegerKeys()) {

            wootConfiguration.setInteger(
                    key,
                    config.get(Configuration.CATEGORY_GENERAL,
                            key.getText(),
                            key.getDefaultInteger(),
                            key.getTranslated()).getInt(key.getDefaultInteger()));
        }

        // Mob configuration
        String[] configStringList = config.getStringList("mobConfigList",
                Configuration.CATEGORY_GENERAL,
                ConfigurationLoaderDefaults.defMobConfigs,
                StringHelper.localize(Lang.TAG_CONFIG + "mobConfigList"));

        for (String entry : configStringList) {

            String[] parts = entry.split(",");
            if (parts.length != 3) {
                LogHelper.error("mobConfigList: incorrect format " + entry);
            } else {
                int v;
                try {
                    v = Integer.parseInt(parts[2]);
                    WootMobName wootMobName = WootMobNameBuilder.createFromConfigString(parts[0]);
                    EnumConfigKey configKey = EnumConfigKey.get(parts[1]);
                    if (!wootMobName.isValid())
                        LogHelper.error("mobConfigList: invalid mob " + entry);
                    else if (configKey == null)
                        LogHelper.error("mobConfigList: invalid config key " + entry);
                    else
                        wootConfiguration.setInteger(wootMobName, configKey, v);
                } catch (NumberFormatException e) {
                    LogHelper.error("mobConfigList: incorrect format " + entry);
                }
            }
        }

        // Mob list
       configStringList = config.getStringList("mobList",
                Configuration.CATEGORY_GENERAL,
                new String[]{},
                StringHelper.localize(Lang.TAG_CONFIG + "mobList"));

        for (String mobName : configStringList) {
            WootMobName wootMobName = WootMobNameBuilder.createFromConfigString(mobName);
            if (wootMobName.isValid())
                wootConfiguration.addToMobList(wootMobName);
        }

        // Mod Item list
        configStringList = config.getStringList("modItemBlacklist",
                Configuration.CATEGORY_GENERAL,
                new String[]{},
                StringHelper.localize(Lang.TAG_CONFIG + "modItemBlacklist"));

        for (String modName : configStringList)
            wootConfiguration.addToModItemList(modName);

        // Item list
        configStringList = config.getStringList("itemBlacklist",
                Configuration.CATEGORY_GENERAL,
                new String[]{},
                StringHelper.localize(Lang.TAG_CONFIG + "itemBlacklist"));

        for (String entry : configStringList)
            wootConfiguration.addToItemList(entry);
    }

    private static void loadDefaults(IWootConfiguration wootConfiguration) {

        for (String modName : ConfigurationLoaderDefaults.internalModBlacklist)
            wootConfiguration.addToInternalModBlacklist(modName);

        for (String mobName : ConfigurationLoaderDefaults.internalMobBlacklist)
            wootConfiguration.addToInternalMobBlacklist(mobName);

        for (String modName : ConfigurationLoaderDefaults.internalModItemBlacklist)
            wootConfiguration.addToInternalModItemBlacklist(modName);

        for (String itemName : ConfigurationLoaderDefaults.internalItemBlacklist)
            wootConfiguration.addToInternalItemBlacklist(itemName);

    }
}
