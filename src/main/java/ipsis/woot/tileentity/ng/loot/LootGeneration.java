package ipsis.woot.tileentity.ng.loot;

import ipsis.woot.tileentity.ng.IFarmSetup;
import ipsis.woot.tileentity.ng.loot.generators.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.LinkedList;
import java.util.List;

public class LootGeneration implements ILootGeneration {

    List<ILootGenerator> generatorList = new LinkedList<>();

    /**
     * ILootGeneration
     */
    public void initialise() {

        generatorList.add(new ItemGenerator());
        generatorList.add(new XpGenerator());
        generatorList.add(new DecapitationGenerator());
        generatorList.add(new BloodMagicLifeEssenceGenerator());
        generatorList.add(new BloodMagicWillGenerator());
    }

    public void generate(List<IFluidHandler> fluidHandlerList, List<IItemHandler> itemHandlerList, IFarmSetup farmSetup) {

        for (ILootGenerator lootGenerator : generatorList)
            lootGenerator.generate(fluidHandlerList, itemHandlerList, farmSetup);
    }
}
