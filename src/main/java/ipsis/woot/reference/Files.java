package ipsis.woot.reference;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class Files {

    private static File globalDataDirectory;
    private static File configDirectory;
    public static File lootFile;
    public static File configFile;

    public static final String LOOT_FILENAME = "loot.json";
    public static final String CUSTOM_LOOT_FILENAME = "custom_drops.json";
    public static final String FACTORY_ING_FILENAME = "factory_ingredients.json";
    public static final String FACTORY_CFG_FILENAME = "factory_config.json";
    public static final String CHANGELOG_FILENAME = "changelog.json";
    private static final String CONFIG_FILENAME = "woot.cfg";

    public static void init(FMLPreInitializationEvent event) {

        globalDataDirectory = new File(event.getModConfigurationDirectory().getParentFile(),
                File.separator + Reference.MOD_NAME_LOWER);
        configDirectory = event.getModConfigurationDirectory();

        lootFile = new File(globalDataDirectory, LOOT_FILENAME);
        configFile = new File(configDirectory + File.separator + Reference.MOD_ID, CONFIG_FILENAME);
    }

    public static File getConfigDirectory() {

        return new File(configDirectory, Reference.MOD_ID);
    }
}
