package ipsis.woot.tileentity.ng.spawning;

import ipsis.woot.tileentity.ng.WootMobName;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class AbstractMobEntity {

    abstract public void runSetup(Entity entity, WootMobName wootMobName, World world);
}
