package ipsis.woot.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import ipsis.woot.Woot;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

/**
 * Based off McJty's implementation
 */

@Mod.EventBusSubscriber
public class Config {

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SERVER_CONFIG;
    public static final ForgeConfigSpec CLIENT_CONFIG;

    static {

        LayoutConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        MachineConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        PolicyConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        FactoryConfig.init(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static void load(ForgeConfigSpec configSpec, Path path) {
        Woot.LOGGER.debug("Loading config file {}", path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
            .sync()
            .autosave()
            .writingMode(WritingMode.REPLACE)
            .build();

        configData.load();
        configSpec.setConfig(configData);
    }
}
