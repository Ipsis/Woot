package ipsis.woot.simulation.spawning;

import ipsis.woot.util.FakeMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.world.World;

public class ChargedCreeperSpawner extends AbstractMobSpawner {

    @Override
    public void apply(LivingEntity livingEntity, FakeMob fakeMob, World world) {

        if (!(livingEntity instanceof CreeperEntity))
            return;

        if (fakeMob.isChargedCreeper())
            livingEntity.onStruckByLightning(new LightningBoltEntity(world,
                        livingEntity.getPosition().getX(),
                        livingEntity.getPosition().getY(),
                        livingEntity.getPosition().getZ(),
                        true));
    }
}
