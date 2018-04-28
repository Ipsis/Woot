package ipsis.woot.loot.generators;

import ipsis.woot.loot.LootGenerationFarmInfo;
import net.minecraft.world.World;

public interface ILootGenerator {

    void generate(World world, LootGenerationFarmInfo farmInfo);
}
