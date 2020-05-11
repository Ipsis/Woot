package ipsis.woot.modules.factory;

import com.google.common.collect.Lists;
import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FactoryRecipes {

    public static FactoryRecipes getInstance() { return INSTANCE; }
    static FactoryRecipes INSTANCE;
    static { INSTANCE = new FactoryRecipes(); }

    private FactoryRecipes() { }
    private HashMap<FakeMob, Recipe> recipes = new HashMap<>();

    public boolean hasRecipe(FakeMob fakeMob) {
        return recipes.containsKey(fakeMob);
    }

    public void addRecipe(FakeMob fakeMob, List<ItemStack> itemIngredients, List<FluidStack> fluidIngredients) {
        recipes.put(fakeMob, new Recipe(itemIngredients, fluidIngredients));
    }

    public List<ItemStack> getItems(FakeMob fakeMob) {
        if (recipes.containsKey(fakeMob))
            return Collections.unmodifiableList(recipes.get(fakeMob).items);
        return new ArrayList<>();
    }

    public List<FluidStack> getFluids(FakeMob fakeMob) {
        if (recipes.containsKey(fakeMob))
            return Collections.unmodifiableList(recipes.get(fakeMob).fluids);
        return new ArrayList<>();
    }

    private class Recipe {
        List<ItemStack> items = new ArrayList<>();
        List<FluidStack> fluids = new ArrayList<>();

        public Recipe(List<ItemStack> items, List<FluidStack> fluids) {
            items.forEach(h -> this.items.add(h.copy()));
            fluids.forEach(h -> this.fluids.add(h.copy()));
        }
    }

    public void load() {

        List<FakeMob> mobs = new ArrayList<>();
        mobs.add(FakeMob.getWither());

        List<List<ItemStack>> items = new ArrayList<>();
        items.add(
                Lists.newArrayList( // Ender Dragon
                    new ItemStack(Items.SOUL_SAND, 4),
                    new ItemStack(Items.WITHER_SKELETON_SKULL, 3)));

        List<List<FluidStack>> fluids = new ArrayList<>();
        fluids.add(Lists.newArrayList());

        if (mobs.size() != items.size() || mobs.size() != fluids.size()) {
            Woot.setup.getLogger().error("FactoryRecipe:load mobs, items, fluids must be the same size");
            return;
        }

        for (int i = 0; i < mobs.size(); i++) {
            if (mobs.get(i).isValid()) {
                addRecipe(mobs.get(i), items.get(i), fluids.get(i));
            }
        }
    }
}
