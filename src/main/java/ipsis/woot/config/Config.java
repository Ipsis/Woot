package ipsis.woot.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.modules.layout.LayoutConfiguration;
import ipsis.woot.modules.simulation.SimulationConfiguration;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.policy.PolicyConfiguration;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    static {
        setupGeneralConfig();
        FactoryConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);
        InfuserConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);
        LayoutConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);
        SimulationConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);
        SqueezerConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);
        PolicyConfiguration.init(COMMON_BUILDER, CLIENT_BUILDER);

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupGeneralConfig() {
        COMMON_BUILDER.comment("General settings").push("general");
        CLIENT_BUILDER.comment("General settings").push("general");
        {
        }
        CLIENT_BUILDER.pop();
        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();;
        spec.setConfig(configData);
    }
}
