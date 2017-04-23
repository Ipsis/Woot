package ipsis.woot.reference;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class Files {

    public static File globalDataDirectory;
    public static File configDirectory;
    public static File lootFile;
    public static File spawnReqFile;
    public static File configFile;

    private static final String LOOT_FILENAME = "loot.json";
    private static final String CONFIG_FILENAME = "woot.cfg";
    private static final String SPAWN_REQ_FILENAME = "woot_spawnreq.json";

    public static void init(FMLPreInitializationEvent event) {

        globalDataDirectory = new File(event.getModConfigurationDirectory().getParentFile(),
                File.separator + Reference.MOD_NAME_LOWER);
        configDirectory = event.getModConfigurationDirectory();

        lootFile = new File(globalDataDirectory, LOOT_FILENAME);
        spawnReqFile = new File(configDirectory + File.separator + Reference.MOD_ID, SPAWN_REQ_FILENAME);
        configFile = new File(configDirectory + File.separator + Reference.MOD_ID, CONFIG_FILENAME);
    }

    public static String getWootFileForDisplay() {

        /**
         * Strip out anything that could be interpreted as an escape in a String format
         * As that is what FMLLog is using to display this
         */
        String raw = lootFile.toString();
        return raw.replace("%", "%%");
    }

    public static String getWootSpawnReqFileForDisplay() {

        String raw = spawnReqFile.toString();
        return raw.replace("%", "%%");
    }
}
