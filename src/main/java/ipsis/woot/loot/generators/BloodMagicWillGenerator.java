package ipsis.woot.loot.generators;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.farmstructure.IFarmSetup;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class BloodMagicWillGenerator implements ILootGenerator {

    public void generate(World world, @Nonnull List<IFluidHandler> fluidHandlerList, @Nonnull List<IItemHandler> itemHandlerList, @Nonnull IFarmSetup farmSetup, DifficultyInstance difficulty) {

//        LogHelper.info("Generating will into chests");
    }
}
