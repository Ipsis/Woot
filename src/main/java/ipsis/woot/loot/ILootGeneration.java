package ipsis.woot.loot;

import ipsis.woot.farmstructure.IFarmSetup;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public interface ILootGeneration {

    void initialise();
    void generate(World world, List<IFluidHandler> fluidHandlerList, List<IItemHandler> itemHandlerList, IFarmSetup farmSetup, DifficultyInstance difficulty);
}
