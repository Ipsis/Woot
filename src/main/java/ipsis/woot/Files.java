package ipsis.woot;

import ipsis.Woot;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class Files {

    public static File lootFile;
    private static File globalDataDir;

    public static void init(FMLPreInitializationEvent event) {

        globalDataDir = new File(event.getModConfigurationDirectory().getParentFile(),
                File.separator + Woot.MODID);

        lootFile = new File(globalDataDir, "loot.json");

    }
}
