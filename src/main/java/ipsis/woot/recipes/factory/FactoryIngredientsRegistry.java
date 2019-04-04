package ipsis.woot.recipes.factory;

import ipsis.woot.factory.multiblock.FactoryConfig;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactoryIngredientsRegistry {

    public static final FactoryIngredientsRegistry REGISTRY = new FactoryIngredientsRegistry();
    private HashMap<FakeMobKey, FactoryIngredients> RECIPES = new HashMap<>();

    public void load() {

        FactoryIngredients test = new FactoryIngredients();
        test.items.add(new ItemStack(Blocks.DIRT));
        RECIPES.put(new FakeMobKey("minecraft:cow"), test);

    }

    public FactoryIngredients get(FactoryConfig factoryConfig) {

        FactoryIngredients ingredients = new FactoryIngredients();
        for (FakeMobKey fakeMobKey : factoryConfig.getValidMobs()) {
            if (RECIPES.containsKey(fakeMobKey)) {
                for (ItemStack itemStack : RECIPES.get(fakeMobKey).items)
                    ingredients.items.add(itemStack.copy());

                for (FluidStack fluidStack : RECIPES.get(fakeMobKey).fluids)
                    ingredients.fluids.add(fluidStack.copy());
            }
        }

        return ingredients;
    }

    public class FactoryIngredients {
        private List<FluidStack> fluids = new ArrayList<>();
        private List<ItemStack> items = new ArrayList<>();

        public List<FluidStack> getFluids() {
            return fluids;
        }

        public List<ItemStack> getItems() {
            return items;
        }

    }
}
