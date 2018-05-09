package ipsis.woot.spawning;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;

public class MobEntitySlime extends AbstractMobEntity {

    /**
     * Slime must spawn as baby to generate drops
     */

    @Override
    public void runSetup(Entity entity, WootMobName wootMobName, World world) {

        if (!(entity instanceof EntitySlime))
            return;

        if (MobEntityPinkSlime.isIFPinkSlime(entity))
            return;

        try {
            Method m = ReflectionHelper.findMethod(EntitySlime.class, "setSlimeSize", "func_70799_a", int.class, boolean.class);
            m.invoke(entity, 1, false);
        } catch (Throwable e){
            LogHelper.warn("Reflection EntitySlime.setSlimeSize failed");
        }
    }
}
