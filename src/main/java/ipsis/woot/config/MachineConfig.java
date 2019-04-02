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

        serverBuilder.push("squeezer");

        SQUEEZER_TANK_CAPACITY = serverBuilder
                .comment("Output tank capacity in mb")
                .defineInRange("tankCapacity", 10000, 1000, Integer.MAX_VALUE);
        SQUEEZER_COLOR_CAPACITY = serverBuilder
                .comment("Internal capabity per color in mb")
                .defineInRange("colorCapacity", 3000, 1000, Integer.MAX_VALUE);
        SQUEEZER_RF_PER_TICK = serverBuilder
                .comment("RF cost per tick")
                .defineInRange("rfPerTick", 30, 1, Integer.MAX_VALUE);
        SQUEEZER_RF_PER_RECIPE = serverBuilder
                .comment("RF cost to process an item")
                .defineInRange("rfRecipeCost", 30 * 20 * 5, 1, Integer.MAX_VALUE);
        SQUEEZER_ENERGY_CAPACITY = serverBuilder
                .comment("RF capacity")
                .defineInRange("rfCapacity", 10000, 1000, Integer.MAX_VALUE);
        SQUEEZER_ENERGY_RX = serverBuilder
                .comment("RF receive per tick")
                .defineInRange("rfRxPerTick", 100, 1, Integer.MAX_VALUE);
        SQUEEZER_RED_MIX = serverBuilder
                .comment("Mb of red needed for 1000mb pure dye")
                .defineInRange("redMix", 25, 1, Integer.MAX_VALUE);
        SQUEEZER_YELLOW_MIX= serverBuilder
                .comment("Mb of yellow needed for 1000mb pure dye")
                .defineInRange("yellowMix", 25, 1, Integer.MAX_VALUE);
        SQUEEZER_BLUE_MIX = serverBuilder
                .comment("Mb of blue needed for 1000mb pure dye")
                .defineInRange("blueMix", 25, 1, Integer.MAX_VALUE);
        SQUEEZER_WHITE_MIX = serverBuilder
                .comment("Mb of white needed for 1000mb pure dye")
                .defineInRange("whiteMix", 25, 1, Integer.MAX_VALUE);

        serverBuilder.pop();
        serverBuilder.push("stamper");

        STAMPER_TANK_CAPACITY = serverBuilder
                .comment("Input tank capacity in mb")
                .defineInRange("tankCapacity", 10000, 1000, Integer.MAX_VALUE);

        serverBuilder.pop();
    }
}
