package ipsis.woot.modules;

import ipsis.woot.datagen.Languages;
import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

public interface Module {

    void initConfig();
    void runRecipes(Consumer<IFinishedRecipe> consumer);
    void addTranslations(Languages languages);
}
