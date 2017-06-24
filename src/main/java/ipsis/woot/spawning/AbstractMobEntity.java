package ipsis.woot.spawning;

import ipsis.woot.util.WootMobName;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class AbstractMobEntity {

    abstract public void runSetup(Entity entity, WootMobName wootMobName, World world);
}
