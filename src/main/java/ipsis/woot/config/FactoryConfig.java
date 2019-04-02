package ipsis.woot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FactoryConfig {

    public static ForgeConfigSpec.IntValue CELL_1_CAPACITY;
    public static ForgeConfigSpec.IntValue CELL_2_CAPACITY;
    public static ForgeConfigSpec.IntValue CELL_3_CAPACITY;
    public static ForgeConfigSpec.IntValue LEARN_MOB_COUNT;
    public static ForgeConfigSpec.IntValue SPAWN_MOB_COUNT;

    public static void init(ForgeConfigSpec.Builder serverBuilder, ForgeConfigSpec.Builder clientBuilder) {

        serverBuilder.comment("Factory");

        CELL_1_CAPACITY = serverBuilder
                .comment("Capacity of the simple cell")
                .defineInRange("factory.cell1Capacity", 10000, 1000, Integer.MAX_VALUE);
        CELL_2_CAPACITY = serverBuilder
                .comment("Capacity of the advanced cell")
                .defineInRange("factory.cell2Capacity", 100000, 1000, Integer.MAX_VALUE);
        CELL_3_CAPACITY = serverBuilder
                .comment("Capacity of the ultimate cell")
                .defineInRange("factory.cell3Capacity", 1000000, 1000, Integer.MAX_VALUE);
        LEARN_MOB_COUNT = serverBuilder
                .comment("Number of mobs to learn from")
                .defineInRange("factory.learnMobCount", 500, 100, 10000);
        SPAWN_MOB_COUNT = serverBuilder
                .comment("Number of mobs to spawn")
                .defineInRange("factory.spawnMobCount", 1, 1, Integer.MAX_VALUE);

    }
}
