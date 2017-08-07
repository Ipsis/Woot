package ipsis.woot.configuration.loaders;

import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.configuration.IWootConfiguration;
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
