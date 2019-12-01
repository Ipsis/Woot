package ipsis.woot.simulation.spawning;

import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.world.World;

public class SlimeSpawner extends AbstractMobSpawner {

    @Override
    public void apply(LivingEntity livingEntity, FakeMob fakeMob, World world) {

        if (!(livingEntity instanceof SlimeEntity))
            return;

        SlimeEntity slimeEntity = (SlimeEntity)livingEntity;
        if (fakeMob.isSmallSlime()) {
            slimeEntity.setSlimeSize(1, false);
            Woot.LOGGER.debug("SlimeSpawner: set size to small {}", slimeEntity.getSlimeSize());
        } else {
            slimeEntity.setSlimeSize(2, false);
            Woot.LOGGER.debug("SlimeSpawner: set size to small {}", slimeEntity.getSlimeSize());
        }
    }
}
