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

        if (fakeMob.isChargedCreeper())
            mobEntity.onStruckByLightning(new LightningBoltEntity(world,
                        mobEntity.getPosition().getX(),
                        mobEntity.getPosition().getY(),
                        mobEntity.getPosition().getZ(),
                        true));
    }
}
