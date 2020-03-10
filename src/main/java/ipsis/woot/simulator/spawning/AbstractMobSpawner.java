package ipsis.woot.simulator.spawning;

import ipsis.woot.util.FakeMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;

public abstract class AbstractMobSpawner {

    abstract public void apply(MobEntity mobEntity, FakeMob fakeMob, World world);
}
