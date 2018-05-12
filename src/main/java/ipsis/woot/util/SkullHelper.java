package ipsis.woot.util;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.plugins.enderio.EnderIO;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class SkullHelper {

    // for reference private static final String[] SKULL_TYPES = new String[] {"skeleton", "wither", "zombie", "char", "creeper", "dragon"};
    private static HashMap<SkullType, ItemStack> skulls = new HashMap<>();
    private static HashMap<WootMobName, ItemStack> wootSkulls = new HashMap<>();

    public static void postInit() {

        skulls.put(SkullType.VANILLA_SKELTON, new ItemStack(Items.SKULL, 1, 0));
        skulls.put(SkullType.VANILLA_WITHER_SKELETON, new ItemStack(Items.SKULL, 1, 1));
        skulls.put(SkullType.VANILLA_ZOMBIE, new ItemStack(Items.SKULL, 1, 2));
        skulls.put(SkullType.VANILLA_CREEPER, new ItemStack(Items.SKULL, 1, 4));

        wootSkulls.put(WootMobNameBuilder.create("minecraft:skeleton"), new ItemStack(Items.SKULL, 1, 0));
        wootSkulls.put(WootMobNameBuilder.create("minecraft:wither_skeleton"), new ItemStack(Items.SKULL, 1, 1));
        wootSkulls.put(WootMobNameBuilder.create("minecraft:zombie"), new ItemStack(Items.SKULL, 1, 2));
        wootSkulls.put(WootMobNameBuilder.create("minecraft:creeper"), new ItemStack(Items.SKULL, 1, 4));

        ItemStack enderioSkull = EnderIO.getEndermanSkull();
        if (!enderioSkull.isEmpty()) {
            skulls.put(SkullType.ENDERIO_ENDERMAN, enderioSkull.copy());
            wootSkulls.put(WootMobNameBuilder.create("minecraft:enderman"), enderioSkull.copy());
            LogHelper.info("Adding EnderIO enderman skull");
        }
    }

    public static ItemStack getSkull(SkullType skullType) {

        if (skulls.containsKey(skullType))
            return skulls.get(skullType);

        return ItemStack.EMPTY;
    }

    public static ItemStack getSkull(WootMobName wootMobName) {

        if (wootSkulls.containsKey(wootMobName))
            return wootSkulls.get(wootMobName);

        return ItemStack.EMPTY;
    }

    public static ItemStack getSkull(EntityLiving entityLiving) {

        if (entityLiving instanceof EntityWitherSkeleton)
            return getSkull(SkullType.VANILLA_WITHER_SKELETON);
        else if (entityLiving instanceof EntityZombie)
            return getSkull(SkullType.VANILLA_ZOMBIE);
        else if (entityLiving instanceof EntitySkeleton)
            return getSkull(SkullType.VANILLA_SKELTON);
        else if (entityLiving instanceof EntityCreeper)
            return getSkull(SkullType.VANILLA_CREEPER);
        else if (entityLiving instanceof EntityEnderman)
            return getSkull(SkullType.ENDERIO_ENDERMAN);
        else
            return ItemStack.EMPTY;
    }

    public enum SkullType {
        VANILLA_SKELTON,
        VANILLA_CREEPER,
        VANILLA_ZOMBIE,
        VANILLA_WITHER_SKELETON,
        ENDERIO_ENDERMAN
    }


}
