package ipsis.woot.loot;

import ipsis.woot.farmstructure.IFarmSetup;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public interface ILootGeneration {

    void initialise();
    void generate(List<IFluidHandler> fluidHandlerList, List<IItemHandler> itemHandlerList, IFarmSetup farmSetup);
}
