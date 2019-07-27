package ipsis.woot.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class Config {

    static final List<String> DEFAULT_MOB_OVERRIDES = Arrays.asList(
            "minecraft:cow,mass_1,10",
            "minecraft:bat,mass_2,20"
            );

    public static class Common {

        public final IntValue simulationTicks;
        public final IntValue simulationMobCount;
        public final ForgeConfigSpec.ConfigValue<List<String>> MOB_OVERRIDES;

        public final IntValue massCount0;
        public final IntValue massCount1;
        public final IntValue massCount2;
        public final IntValue massCount3;
        public final IntValue tier1MaxUnits;
        public final IntValue tier2MaxUnits;
        public final IntValue tier3MaxUnits;
        public final IntValue tier4MaxUnits;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configuration settings")
                    .push("common");

            simulationTicks = builder
                    .comment("Number of ticks between mob simulations")
                    .translation("woot.configgui.simulationTicks")
                    .defineInRange("simulationTicks", 200, 20, 20 * 60);

            simulationMobCount = builder
                    .comment("Number of simulated mobs to learn from")
                    .translation("woot.configgui.simulationMobCount")
                    .defineInRange("simulationMobCount", 500,100, 5000);

            builder.push("mob");
            MOB_OVERRIDES = builder
                    .comment("A list of mob specific factory configuration values")
                    .translation("woot.configgui.mobOverrides")
                    .define("mobOverrides", DEFAULT_MOB_OVERRIDES);
            builder.pop(); // mob

            builder.push("factory");
            massCount0 = builder
                    .comment("Number of mobs to spawn")
                    .translation("woot.configgui.mass")
                    .defineInRange("mass", 1, 1, 100);

            tier1MaxUnits = builder
                    .comment("Max units for a tier 1 mob")
                    .translation("woot.configgui.tier1MaxUnits")
                    .defineInRange("tier1MaxUnits", 20, 5, 65535);
            tier2MaxUnits = builder
                    .comment("Max units for a tier 2 mob")
                    .translation("woot.configgui.tier2MaxUnits")
                    .defineInRange("tier2MaxUnits", 40, 5, 65535);
            tier3MaxUnits = builder
                    .comment("Max units for a tier 3 mob")
                    .translation("woot.configgui.tier3MaxUnits")
                    .defineInRange("tier3MaxUnits", 60, 5, 65535);
            tier4MaxUnits = builder
                    .comment("Max units for a tier 4 mob")
                    .translation("woot.configgui.tier4MaxUnits")
                    .defineInRange("tier4MaxUnits", Integer.MAX_VALUE, 5, Integer.MAX_VALUE);

            builder.push("upgrades");
                massCount1 = builder
                        .comment("Number of mobs to spawn for mass 1 upgrade")
                        .translation("woot.configgui.mass1Upgrade")
                        .defineInRange("mass1Upgrade", 3, 1, 100);
                massCount2 = builder
                        .comment("Number of mobs to spawn for mass 2 upgrade")
                        .translation("woot.configgui.mass2Upgrade")
                        .defineInRange("mass2Upgrade", 4, 1, 100);
                massCount3 = builder
                        .comment("Number of mobs to spawn for mass 3 upgrade")
                        .translation("woot.configgui.mass3Upgrade")
                        .defineInRange("mass3Upgrade", 5, 1, 100);
            builder.pop(); // upgrades
            builder.pop(); // factory
            builder.pop(); // server
        }
    }

    public static class Client {

        public final ForgeConfigSpec.DoubleValue layoutOpacity;
        public final ForgeConfigSpec.DoubleValue layoutSize;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client configuration settings")
                    .push("client");

            layoutOpacity = builder
                    .comment("Opacity of the layout blocks")
                    .translation("woot.configgui.layoutOpacity")
                    .defineInRange("layoutOpacity", 0.95D, 0.10D, 1.00D);

            layoutSize = builder
                    .comment("Size of the layout blocks")
                    .translation("woot.configgui.layoutSize")
                    .defineInRange("layoutSize", 0.35D, 0.10D, 1.0D);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    /**
     * Access
     */
    public static int getIntValue(WootConfig.Key key) {
        int v = 0;
        switch (key) {
            case MASS: v = COMMON.massCount0.get(); break;
            case MASS_1: v = COMMON.massCount1.get(); break;
            case MASS_2: v = COMMON.massCount2.get(); break;
            case MASS_3: v = COMMON.massCount3.get(); break;
        }
        return v;
    }

}
