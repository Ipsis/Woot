package ipsis.woot.reference;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class Files {

    public static File globalDataDirectory;
    public static File lootFile;
    public static File spawnReqFile;

    private static final String LOOT_FILENAME = "loot.json";
    private static final String SPAWN_REQ_FILENAME = "spawnreq.json";

    public static void init(FMLPreInitializationEvent event) {

        globalDataDirectory = new File(event.getModConfigurationDirectory().getParentFile(),
                File.separator + Reference.MOD_NAME_LOWER);

        lootFile = new File(globalDataDirectory, LOOT_FILENAME);
        spawnReqFile = new File(globalDataDirectory, SPAWN_REQ_FILENAME);
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
