package ipsis.woot.loot;

import ipsis.woot.farmstructure.IFarmSetup;
import net.minecraft.world.DifficultyInstance;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class LootGenerationFarmInfo {

    public List<IFluidHandler> fluidHandlerList;
    public List<IItemHandler> itemHandlerList;
    public IFarmSetup farmSetup;
    public DifficultyInstance difficultyInstance;
    public boolean keepAliveTankRitual;
}
