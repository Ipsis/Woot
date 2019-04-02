package ipsis.woot.config;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import java.util.*;

public class PolicyConfig {

    private static ForgeConfigSpec.ConfigValue<List<String>> MOB_BLACKLIST;
    private static ForgeConfigSpec.ConfigValue<List<String>> MOBS_FROM_MOD_BLACKLIST;

    public static void init(ForgeConfigSpec.Builder serverBuilder, ForgeConfigSpec.Builder clientBuilder) {

        serverBuilder.comment("Policy");

        MOB_BLACKLIST = serverBuilder
                .comment("A list of mobs which cannot be captured or used in the factory")
                .define("policy.mobBlacklist", Collections.emptyList());
        MOBS_FROM_MOD_BLACKLIST = serverBuilder
                .comment("A list of mods whose mobs cannot be captured or used in the factory")
                .define("policy.mobsFromMobBlacklist", Collections.emptyList());
    }

    private static Set<ResourceLocation> mobsBlacklist = null;
    public static Set<ResourceLocation> getMobBlacklist() {
        if (mobsBlacklist == null)
            mobsBlacklist = new HashSet<>();
        for (String s : MOB_BLACKLIST.get())
            mobsBlacklist.add(new ResourceLocation(s));

        return mobsBlacklist;
    }

    private static Set<String> mobsFromModBlacklist = null;
    public static Set<String> getMobsFromModBlacklist() {
        if (mobsFromModBlacklist == null)
            mobsFromModBlacklist = new HashSet<>();
        for (String s : MOBS_FROM_MOD_BLACKLIST.get())
            mobsFromModBlacklist.add(s);

        return mobsFromModBlacklist;
    }

    /**
     * Policy
     */
    private static Set<ResourceLocation> mobsInternalBlacklist = new HashSet<>(Arrays.asList(
        ));
    private static Set<String> mobsFromModInternalBlacklist = new HashSet<>(Arrays.asList(
            "botania"));
    public static boolean canCapture(@Nonnull ResourceLocation resourceLocation) {

        String mod = resourceLocation.getNamespace();
        if (mobsFromModInternalBlacklist.contains(mod))
            return false;

        if (mobsInternalBlacklist.contains(resourceLocation))
            return false;

        if (getMobsFromModBlacklist().contains(mod))
            return false;

        if (getMobBlacklist().contains(resourceLocation))
            return false;

        return true;
    }

    public static boolean canLearn(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty())
            return false;
        return true;
    }

    public static boolean canDrop(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty())
            return false;
        return true;
    }
}
