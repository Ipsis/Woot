package ipsis.woot.configuration.loaders;

import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.configuration.IWootConfiguration;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationLoader {

    public static void load(Configuration config, IWootConfiguration wootConfiguration) {

        // Boolean keys
        for (EnumConfigKey key : EnumConfigKey.getBooleanKeys()) {

            wootConfiguration.setBoolean(
                    key,
                    config.get(key.getCategory(),
                            key.getText(),
                            key.getDefaultBoolean(),
                            key.getComment()).getBoolean(key.getDefaultBoolean()));
        }

        // Integer keys
        for (EnumConfigKey key : EnumConfigKey.getIntegerKeys()) {

            wootConfiguration.setInteger(
                    key,
                    config.get(key.getCategory(),
                            key.getText(),
                            key.getDefaultInteger(),
                            key.getComment()).getInt(key.getDefaultInteger()));
        }
    }
}
