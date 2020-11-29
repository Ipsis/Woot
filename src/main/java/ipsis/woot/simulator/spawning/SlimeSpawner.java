package ipsis.woot.simulator.spawning;

import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.world.World;

public class SlimeSpawner extends AbstractMobSpawner {

    @Override
    public void apply(MobEntity mobEntity, FakeMob fakeMob, World world) {

        if (!(mobEntity instanceof SlimeEntity))
            return;

        SlimeEntity slimeEntity = (SlimeEntity)mobEntity;
        if (fakeMob.isSmallSlime()) {
            slimeEntity.setSlimeSize(1, false);
            //Woot.setup.getLogger().debug("SlimeSpawner: set size to small {}", slimeEntity.getSlimeSize());
        } else {
            slimeEntity.setSlimeSize(2, false);
            //Woot.setup.getLogger().debug("SlimeSpawner: set size to small {}", slimeEntity.getSlimeSize());
        }
    }
}
