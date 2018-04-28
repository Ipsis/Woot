package ipsis.woot.loot;

import net.minecraft.world.World;

public interface ILootGeneration {

    void initialise();
    void generate(World world, LootGenerationFarmInfo farmInfo);
}
