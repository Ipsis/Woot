package ipsis.woot.factory.generators;

import ipsis.woot.factory.multiblock.FactoryConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LootGenerator {

    public static final LootGenerator LOOT_GENERATOR = new LootGenerator();

    private List<ILootGenerator> generators = new LinkedList<>(Arrays.asList(
            new ItemGenerator()
    ));

    public void generate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull FactoryConfig factoryConfig) {

        for (ILootGenerator generator : generators)
            generator.generate(world, pos, factoryConfig);
    }
}
