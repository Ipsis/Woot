package ipsis.woot.simulator;

import ipsis.woot.Woot;
import ipsis.woot.simulator.tartarus.TartarusChunkGenerator;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class MobSimulatorSetup {

    public static final RegistryKey<DimensionType> TARTARUS_DIMENSION_TYPE = RegistryKey.create(
            Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(Woot.MODID + ":mobsimulator"));
    public static final RegistryKey<World> TARTARUS = RegistryKey.create(
            Registry.DIMENSION_REGISTRY, new ResourceLocation(Woot.MODID + ":tartarus"));

    public static void init() {
        Registry.register(
                Registry.CHUNK_GENERATOR,
                Woot.MODID + ":simulation_cells",
                TartarusChunkGenerator.codecTartarusChunk);
    }
}
