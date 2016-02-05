package ipsis.woot.util;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;

public class MobUtil {

    public static String getMobName(EntityLiving entityLiving) {

        String name = EntityList.getEntityString(entityLiving);
        if (entityLiving instanceof EntitySkeleton) {
            if (((EntitySkeleton) entityLiving).getSkeletonType() == 1)
                name += ".wither";
        }

        return name;
    }

    public static boolean isWitherSkeleton(String name) {
        return name.equals("Skeleton.wither");
    }

    public static String getBaseMobName(String name) {
        if (isWitherSkeleton(name))
            return "Skeleton";
        return name;
    }
}
