package ipsis.woot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FactoryConfig {

    public static ForgeConfigSpec.IntValue CELL_1_CAPACITY;
    public static ForgeConfigSpec.IntValue CELL_2_CAPACITY;
    public static ForgeConfigSpec.IntValue CELL_3_CAPACITY;

    public static void init(ForgeConfigSpec.Builder serverBuilder, ForgeConfigSpec.Builder clientBuilder) {

        serverBuilder.comment("Factory");

        CELL_1_CAPACITY = serverBuilder
                .comment("Capacity of the simple cell")
                .defineInRange("cell1.capacity", 10000, 1000, Integer.MAX_VALUE);
        CELL_2_CAPACITY = serverBuilder
                .comment("Capacity of the advanced cell")
                .defineInRange("cell2.capacity", 100000, 1000, Integer.MAX_VALUE);
        CELL_3_CAPACITY = serverBuilder
                .comment("Capacity of the ultimate cell")
                .defineInRange("cell3.capacity", 1000000, 1000, Integer.MAX_VALUE);

    }
}
