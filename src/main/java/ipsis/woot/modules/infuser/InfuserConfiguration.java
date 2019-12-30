package ipsis.woot.modules.infuser;

import net.minecraftforge.common.ForgeConfigSpec;

public class InfuserConfiguration {

    public static final String CATEGORY_BUILDER = "infuser";

    public static ForgeConfigSpec.IntValue TANK_CAPACITY;

    public static void init(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        COMMON_BUILDER.comment("Settings for the infuser").push(CATEGORY_BUILDER);
        CLIENT_BUILDER.comment("Settings for the infuser").push(CATEGORY_BUILDER);
        {
            TANK_CAPACITY = COMMON_BUILDER
                    .comment("Maximum tank capacity (in mB)")
                    .defineInRange("tankMaxCapacity", 10000, 0, Integer.MAX_VALUE);
        }
        COMMON_BUILDER.pop();
        CLIENT_BUILDER.pop();
    }
}
