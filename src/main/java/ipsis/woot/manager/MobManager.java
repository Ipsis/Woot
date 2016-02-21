package ipsis.woot.manager;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public class MobManager {

    public static final String INVALID_MOB_NAME = "InvalidMob";

    List<String> mobBlacklist = new ArrayList<String>();

    public void addToBlacklist(String entityString) {

        mobBlacklist.add(entityString);
    }

    public boolean isBlacklisted(EntityLivingBase entityLivingBase) {

        if (entityLivingBase instanceof EntityPlayer)
            return true;

        return false;
    }

    public static boolean isValidMobName(String mobName) {

        return mobName != null && !mobName.equals(INVALID_MOB_NAME) && !mobName.equals("");
    }

    public String getMobName(EntityLivingBase entityLiving) {

        // This is the class to string value
        String name = EntityList.getEntityString(entityLiving);

        if (entityLiving instanceof EntitySkeleton) {
            if (((EntitySkeleton) entityLiving).getSkeletonType() == 1)
                name += ".wither";
        }

        return name;
    }

    public String getDisplayName(String mobName) {

        String baseName = getBaseMobName(mobName);
        if (isValidMobName(mobName))
            return StatCollector.translateToLocal("entity." + baseName + ".name");

        return "Unknown";
    }

    public static boolean isWitherSkeleton(String mobName) {
        return mobName.equals("Skeleton.wither");
    }

    public static String getBaseMobName(String mobName) {
        if (isWitherSkeleton(mobName))
            return "Skeleton";
        return mobName;
    }

}
