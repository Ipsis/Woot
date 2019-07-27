package ipsis.woot.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class Config {

    static final List<String> DEFAULT_MOB_OVERRIDES = Arrays.asList(
            "minecraft:wither_skeleton,spawn_units,40",
            "minecraft:wither_skeleton,tier,3",
            "minecraft:villager,tier,3",
            "minecraft:magma_cube,tier,2",
            "minecraft:enderman,tier,3",
            "minecraft:villager_golem,tier,3",
            "minecraft:guardian,tier,3",
            "minecraft:blaze,tier,2",
            "minecraft:witch,tier,2",
            "minecraft:ghast,tier,2",
            "minecraft:zombie_pigman,tier,2",

            "minecraft:ender_dragon,spawn_units,500",
            "minecraft:ender_dragon,spawn_ticks,12000",
            "minecraft:ender_dragon,tier,4",
            "minecraft:ender_dragon,mass_1,2",
            "minecraft:ender_dragon,mass_2,3",
            "minecraft:ender_dragon,mass_3,4",

            "minecraft:minecraft_wither,spawn_units,500",
            "minecraft:minecraft_wither,spawn_ticks,1200",
            "minecraft:minecraft_wither,tier,4",
            "minecraft:minecraft_wither,mass_1,2",
            "minecraft:minecraft_wither,mass_2,3",
            "minecraft:minecraft_wither,mass_3,4"
            );

    public static class Common {

        public final IntValue SIMULATION_TICKS;
        public final IntValue SIMULATION_MOB_COUNT;
        public final ForgeConfigSpec.ConfigValue<List<String>> MOB_OVERRIDES;

        public final IntValue MASS_COUNT;
        public final IntValue SPAWN_TICKS;
        public final IntValue MASS_COUNT_1;
        public final IntValue MASS_COUNT_2;
        public final IntValue MASS_COUNT_3;
        public final IntValue TIER_1_MAX_UNITS;
        public final IntValue TIER_2_MAX_UNITS;
        public final IntValue TIER_3_MAX_UNITS;
        public final IntValue TIER_4_MAX_UNITS;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configuration settings")
                    .push("common");

            SIMULATION_TICKS = builder
                    .comment("Number of ticks between mob simulations")
                    .translation("woot.configgui.simulationTicks")
                    .defineInRange("simulationTicks", 200, 20, 20 * 60);

            SIMULATION_MOB_COUNT = builder
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
            MASS_COUNT = builder
                    .comment("Number of mobs to spawn")
                    .translation("woot.configgui.mass")
                    .defineInRange("mass", 1, 1, 100);

            SPAWN_TICKS = builder
                    .comment("Number of ticks to spawn a mob")
                    .translation("woot.configgui.spawnTicks")
                    .defineInRange("spawnTicks", 16 * 20, 1, 65535);

            TIER_1_MAX_UNITS = builder
                    .comment("Max units for a tier 1 mob")
                    .translation("woot.configgui.tier1MaxUnits")
                    .defineInRange("tier1MaxUnits", 20, 5, 65535);
            TIER_2_MAX_UNITS = builder
                    .comment("Max units for a tier 2 mob")
                    .translation("woot.configgui.tier2MaxUnits")
                    .defineInRange("tier2MaxUnits", 40, 5, 65535);
            TIER_3_MAX_UNITS = builder
                    .comment("Max units for a tier 3 mob")
                    .translation("woot.configgui.tier3MaxUnits")
                    .defineInRange("tier3MaxUnits", 60, 5, 65535);
            TIER_4_MAX_UNITS = builder
                    .comment("Max units for a tier 4 mob")
                    .translation("woot.configgui.tier4MaxUnits")
                    .defineInRange("tier4MaxUnits", Integer.MAX_VALUE, 5, Integer.MAX_VALUE);

            builder.push("upgrades");
                MASS_COUNT_1 = builder
                        .comment("Number of mobs to spawn for mass 1 upgrade")
                        .translation("woot.configgui.mass1Upgrade")
                        .defineInRange("mass1Upgrade", 3, 1, 100);
                MASS_COUNT_2 = builder
                        .comment("Number of mobs to spawn for mass 2 upgrade")
                        .translation("woot.configgui.mass2Upgrade")
                        .defineInRange("mass2Upgrade", 4, 1, 100);
                MASS_COUNT_3 = builder
                        .comment("Number of mobs to spawn for mass 3 upgrade")
                        .translation("woot.configgui.mass3Upgrade")
                        .defineInRange("mass3Upgrade", 5, 1, 100);
            builder.pop(); // upgrades
            builder.pop(); // factory
            builder.pop(); // server
        }
    }

    public static class Client {

        public final ForgeConfigSpec.DoubleValue LAYOUT_OPACITY;
        public final ForgeConfigSpec.DoubleValue LAYOUT_SIZE;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client configuration settings")
                    .push("client");

            LAYOUT_OPACITY = builder
                    .comment("Opacity of the layout blocks")
                    .translation("woot.configgui.layoutOpacity")
                    .defineInRange("layoutOpacity", 0.95D, 0.10D, 1.00D);

            LAYOUT_SIZE = builder
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
            case MASS: v = COMMON.MASS_COUNT.get(); break;
            case MASS_1: v = COMMON.MASS_COUNT_1.get(); break;
            case MASS_2: v = COMMON.MASS_COUNT_2.get(); break;
            case MASS_3: v = COMMON.MASS_COUNT_3.get(); break;
            case SPAWN_TICKS: v = COMMON.SPAWN_TICKS.get(); break;
        }
        return v;
    }

}
