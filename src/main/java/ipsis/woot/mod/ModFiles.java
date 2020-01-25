package ipsis.woot.mod;

import ipsis.woot.Woot;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModFiles {

    public static ModFiles INSTANCE = new ModFiles();

    private Path lootFile;

    public void init() {
        this.lootFile = FMLPaths.GAMEDIR.get().resolve(
                Paths.get(Woot.MODID, "loot.json"));
        Woot.setup.getLogger().info(lootFile);
    }

    public File getLootFile() {
        return this.lootFile.toFile();
    }
}
