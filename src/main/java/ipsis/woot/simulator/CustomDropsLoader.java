package ipsis.woot.simulator;


import ipsis.woot.crafting.FactoryRecipe;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;

import java.util.HashMap;


public class CustomDropsLoader {

    public static void load(RecipeManager recipeManager) {

        for (IRecipe recipe : recipeManager.getRecipes()) {
            if (recipe instanceof FactoryRecipe) {
                FactoryRecipe factoryRecipe = (FactoryRecipe) recipe;
                if (factoryRecipe.getFakeMob().isValid()) {
                    HashMap<Integer, Integer> stackSizes = new HashMap<>();
                    for (FactoryRecipe.Drop drop : factoryRecipe.getDrops()) {
                        ItemStack itemStack = drop.itemStack.copy();
                        for (int i = 0; i < 4; i++) {
                            itemStack.setCount(drop.stackSizes[i]);
                            MobSimulator.getInstance().learnCustomDrop(
                                    new FakeMobKey(factoryRecipe.getFakeMob(), i),
                                    itemStack,
                                    drop.dropChance[i], stackSizes);
                        }
                    }
                }
            }
        }
    }
}
