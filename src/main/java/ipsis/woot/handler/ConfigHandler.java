package ipsis.woot.handler;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.reference.Reference;
import ipsis.woot.configuration.loaders.ConfigurationLoader;
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

    public static void saveDimensionId(int id) {

        EnumConfigKey key = EnumConfigKey.TARTARUS_ID;
        Woot.wootConfiguration.setInteger(key, id);
        configuration.get(Configuration.CATEGORY_GENERAL, key.getText(), key.getDefaultBoolean(), key.getComment()).set(id);
        configuration.save();

    }

    static void loadConfiguration() {

        ConfigurationLoader.load(configuration, Woot.wootConfiguration);
        if (configuration.hasChanged())
            configuration.save();
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}
