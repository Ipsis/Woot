package ipsis.woot.util;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SkullHelper {

    public static ItemStack getSkull(SkullType skullType) {

        if (skullType == SkullType.VANILLA_SKELTON)
            return new ItemStack(Items.SKULL, 1, 0);
        else if (skullType == SkullType.VANILLA_WITHER_SKELETON)
            return new ItemStack(Items.SKULL, 1, 1);
        else if (skullType == SkullType.VANILLA_ZOMBIE)
            return new ItemStack(Items.SKULL, 1, 2);
        else if (skullType == SkullType.VANILLA_CREEPER)
            return new ItemStack(Items.SKULL, 1, 4);

        return new ItemStack(Items.SKULL, 1, 0);
    }

    public static ItemStack getSkullForEntity(EntityLiving entityLiving) {

        if (entityLiving instanceof EntityWitherSkeleton)
            return getSkull(SkullType.VANILLA_WITHER_SKELETON);
        else if (entityLiving instanceof EntityZombie)
            return getSkull(SkullType.VANILLA_ZOMBIE);
        else if (entityLiving instanceof EntitySkeleton)
            return getSkull(SkullType.VANILLA_SKELTON);
        else if (entityLiving instanceof EntityCreeper)
            return getSkull(SkullType.VANILLA_CREEPER);
        else
            return ItemStack.EMPTY;
    }

    public enum SkullType {
        VANILLA_SKELTON,
        VANILLA_CREEPER,
        VANILLA_ZOMBIE,
        VANILLA_WITHER_SKELETON,
    }
}
