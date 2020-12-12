package ipsis.woot.simulator.spawning;

import ipsis.woot.util.FakeMob;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ChargedCreeperSpawner extends AbstractMobSpawner {

    @Override
    public void apply(MobEntity mobEntity, FakeMob fakeMob, World world) {

        if (!(mobEntity instanceof CreeperEntity))
            return;

        if (!(world instanceof ServerWorld))
            return;

        if (fakeMob.isChargedCreeper())
            ((CreeperEntity)mobEntity).func_241841_a((ServerWorld) world,
                        new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world));
    }
}
