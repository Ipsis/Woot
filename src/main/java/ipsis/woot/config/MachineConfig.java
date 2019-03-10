package ipsis.woot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MachineConfig {

    public static ForgeConfigSpec.IntValue SQUEEZER_TANK_CAPACITY;
    public static ForgeConfigSpec.IntValue SQUEEZER_COLOR_CAPACITY;
    public static ForgeConfigSpec.IntValue SQUEEZER_RF_PER_TICK;
    public static ForgeConfigSpec.IntValue SQUEEZER_RF_PER_RECIPE;
    public static ForgeConfigSpec.IntValue SQUEEZER_ENERGY_CAPACITY;
    public static ForgeConfigSpec.IntValue SQUEEZER_ENERGY_RX;
    public static ForgeConfigSpec.IntValue SQUEEZER_RED_MIX;
    public static ForgeConfigSpec.IntValue SQUEEZER_YELLOW_MIX;
    public static ForgeConfigSpec.IntValue SQUEEZER_BLUE_MIX;
    public static ForgeConfigSpec.IntValue SQUEEZER_WHITE_MIX;

    public static ForgeConfigSpec.IntValue STAMPER_TANK_CAPACITY;

    public static void init(ForgeConfigSpec.Builder serverBuilder, ForgeConfigSpec.Builder clientBuilder) {

        serverBuilder.comment("Squeezer");

        SQUEEZER_TANK_CAPACITY = serverBuilder
                .comment("Output tank capacity in mb")
                .defineInRange("squeezer.tankCapacity", 10000, 1000, Integer.MAX_VALUE);
        SQUEEZER_COLOR_CAPACITY = serverBuilder
                .comment("Internal capabity per color in mb")
                .defineInRange("squeezer.colorCapacity", 3000, 1000, Integer.MAX_VALUE);
        SQUEEZER_RF_PER_TICK = serverBuilder
                .comment("RF cost per tick")
                .defineInRange("squeezer.rfPerTick", 30, 1, Integer.MAX_VALUE);
        SQUEEZER_RF_PER_RECIPE = serverBuilder
                .comment("RF cost to process an item")
                .defineInRange("squeezer.rfRecipeCost", 30 * 20 * 5, 1, Integer.MAX_VALUE);
        SQUEEZER_ENERGY_CAPACITY = serverBuilder
                .comment("RF capacity")
                .defineInRange("squeezer.rfCapacity", 10000, 1000, Integer.MAX_VALUE);
        SQUEEZER_ENERGY_RX = serverBuilder
                .comment("RF receive per tick")
                .defineInRange("squeezer.rfRxPerTick", 100, 1, Integer.MAX_VALUE);
        SQUEEZER_RED_MIX = serverBuilder
                .comment("Mb of red needed for 1000mb pure dye")
                .defineInRange("squeezer.redMix", 25, 1, Integer.MAX_VALUE);
        SQUEEZER_YELLOW_MIX= serverBuilder
                .comment("Mb of yellow needed for 1000mb pure dye")
                .defineInRange("squeezer.yellowMix", 25, 1, Integer.MAX_VALUE);
        SQUEEZER_BLUE_MIX = serverBuilder
                .comment("Mb of blue needed for 1000mb pure dye")
                .defineInRange("squeezer.blueMix", 25, 1, Integer.MAX_VALUE);
        SQUEEZER_WHITE_MIX = serverBuilder
                .comment("Mb of white needed for 1000mb pure dye")
                .defineInRange("squeezer.whiteMix", 25, 1, Integer.MAX_VALUE);

        serverBuilder.comment("Stamper");

        STAMPER_TANK_CAPACITY = serverBuilder
                .comment("Input tank capacity in mb")
                .defineInRange("stamper.tankCapacity", 10000, 1000, Integer.MAX_VALUE);
    }
}
