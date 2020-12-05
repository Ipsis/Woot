package ipsis.woot.simulator.spawning;

import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.world.World;

public class MagmaCubeSpawner extends AbstractMobSpawner {

    @Override
    public void apply(MobEntity mobEntity, FakeMob fakeMob, World world) {

        if (!(mobEntity instanceof MagmaCubeEntity))
            return;

        MagmaCubeEntity magmaCubeEntity = (MagmaCubeEntity)mobEntity;
        if (fakeMob.isSmallMagmaCube()) {
            magmaCubeEntity.setSlimeSize(1, false);
            //Woot.setup.getLogger().debug("SlimeSpawner: set size to small {}", magmaCubeEntity.getSlimeSize());
        } else {
            magmaCubeEntity.setSlimeSize(2, false);
            //Woot.setup.getLogger().debug("SlimeSpawner: set size to small {}", magmaCubeEntity.getSlimeSize());
        }
    }
}
