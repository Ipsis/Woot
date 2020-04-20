package ipsis.woot.datagen;

import ipsis.woot.datagen.modules.*;
import ipsis.woot.modules.anvil.AnvilRecipes;
import ipsis.woot.modules.infuser.InfuserRecipes;
import net.minecraft.data.*;

import java.util.function.Consumer;

// NB addCriterion is when the recipe should be unlocked - so cobblestone just means after you get cobblestone
public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        Anvil.registerRecipes(consumer);
        Factory.registerRecipes(consumer);
        Generic.registerRecipes(consumer);
        Infuser.registerRecipes(consumer);
        Layout.registerRecipes(consumer);
        Oracle.registerRecipes(consumer);
        Squeezer.registerRecipes(consumer);
        ConatusGenerator.registerRecipes(consumer);
    }
}
