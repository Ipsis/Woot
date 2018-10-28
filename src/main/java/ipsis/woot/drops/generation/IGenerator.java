package ipsis.woot.drops.generation;

import net.minecraft.world.World;

public interface IGenerator {

    void generate(World world, LootGenerator.Setup setup);
}
