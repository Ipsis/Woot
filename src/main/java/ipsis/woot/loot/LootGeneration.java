package ipsis.woot.loot;

import ipsis.woot.loot.generators.*;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.plugins.bloodmagic.BloodMagic;
import ipsis.woot.plugins.evilcraft.EvilCraft;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Loader;
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


        if (Loader.isModLoaded(BloodMagic.BM_MODID)) {
            generatorList.add(new BloodMagicLifeEssenceGenerator());
            generatorList.add(new BloodMagicWillGenerator());
        }

        if (Loader.isModLoaded(EvilCraft.EC_MODID))
        generatorList.add(new EvilCraftBloodGenerator());
    }

    @Override
    public void generate(World world, LootGenerationFarmInfo farmInfo) {

        for (ILootGenerator lootGenerator : generatorList)
            lootGenerator.generate(world, farmInfo);
    }
}
