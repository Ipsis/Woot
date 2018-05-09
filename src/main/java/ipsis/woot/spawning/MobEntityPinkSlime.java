package ipsis.woot.spawning;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;

public class MobEntityPinkSlime extends AbstractMobEntity {

    private static String IND_FORG_MOD_ID = "industrialforegoing";
    private static String PINK_SLIME_NAME = "pink_slime";

    /**
     * Slime must spawn as baby to generate drops
     */

    public static boolean isIFPinkSlime(Entity entity) {

        ResourceLocation res = EntityList.getKey(entity.getClass());
        if (res == null)
            return false;

        return res.getResourceDomain().equalsIgnoreCase(IND_FORG_MOD_ID) && res.getResourcePath().equalsIgnoreCase(PINK_SLIME_NAME);
    }

    @Override
    public void runSetup(Entity entity, WootMobName wootMobName, World world) {

        if (!(entity instanceof EntitySlime))
            return;

        if (isIFPinkSlime(entity)) {

            try {
                Method m = ReflectionHelper.findMethod(EntitySlime.class, "setSlimeSize", "func_70799_a", int.class, boolean.class);
                m.invoke(entity, 1, false);
            } catch (Throwable e) {
                LogHelper.warn("Reflection EntitySlime.setSlimeSize failed");
            }
        }
    }
}
