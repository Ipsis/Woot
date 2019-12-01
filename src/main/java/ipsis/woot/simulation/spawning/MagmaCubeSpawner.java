package ipsis.woot.simulation.spawning;

import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.world.World;

public class MagmaCubeSpawner extends AbstractMobSpawner {

    @Override
    public void apply(LivingEntity livingEntity, FakeMob fakeMob, World world) {

        if (!(livingEntity instanceof MagmaCubeEntity))
            return;

        MagmaCubeEntity magmaCubeEntity = (MagmaCubeEntity)livingEntity;
        if (fakeMob.isSmallMagmaCube()) {
            magmaCubeEntity.setSlimeSize(1, false);
            Woot.LOGGER.debug("SlimeSpawner: set size to small {}", magmaCubeEntity.getSlimeSize());
        } else {
            magmaCubeEntity.setSlimeSize(2, false);
            Woot.LOGGER.debug("SlimeSpawner: set size to small {}", magmaCubeEntity.getSlimeSize());
        }
    }
}
