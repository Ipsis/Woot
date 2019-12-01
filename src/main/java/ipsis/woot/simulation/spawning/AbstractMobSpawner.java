package ipsis.woot.simulation.spawning;

import ipsis.woot.util.FakeMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public abstract class AbstractMobSpawner {

    abstract public void apply(LivingEntity livingEntity, FakeMob fakeMob, World world);
}
