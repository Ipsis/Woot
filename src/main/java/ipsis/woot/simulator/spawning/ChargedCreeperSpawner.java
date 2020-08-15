package ipsis.woot.simulator.spawning;

import ipsis.woot.util.FakeMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.world.World;

public class ChargedCreeperSpawner extends AbstractMobSpawner {

    @Override
    public void apply(MobEntity mobEntity, FakeMob fakeMob, World world) {

        if (!(mobEntity instanceof CreeperEntity))
            return;

        // TODO Creeper onStruckByLightning
        /*
        if (fakeMob.isChargedCreeper())
            mobEntity.onStruckByLightning(
                    new LightningBoltEntity(world,
                        mobEntity.getX(),
                        mobEntity.getY(),
                        mobEntity.getZ(),
                        true)); */
    }
}
