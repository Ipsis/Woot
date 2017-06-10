package ipsis.woot.tileentity.ng.spawning;

import ipsis.woot.tileentity.ng.WootMobName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;

public class MobEntityChargedCreeper extends AbstractMobEntity{

    @Override
    public void runSetup(Entity entity, WootMobName wootMobName, World world) {

        if (!(entity instanceof EntityCreeper))
            return;

        // TODO check for charger creeper

        entity.onStruckByLightning(new EntityLightningBolt(
                world,
                entity.getPosition().getX(),
                entity.getPosition().getY(),
                entity.getPosition().getZ(), true));
    }
}
