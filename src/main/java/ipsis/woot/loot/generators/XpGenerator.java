package ipsis.woot.loot.generators;

import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.farmstructure.IFarmSetup;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class XpGenerator implements ILootGenerator {

    public void generate(@Nonnull List<IFluidHandler> fluidHandlerList, @Nonnull List<IItemHandler> itemHandlerList, @Nonnull IFarmSetup farmSetup) {

        if (itemHandlerList.size() == 0)
            return;

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.XP))
            return;

        // TODO farm setup might need to contain the stored XP

        for (IItemHandler hdlr : itemHandlerList) {

        }
    }
}
