package ipsis.woot.modules;

import ipsis.woot.datagen.Languages;
import net.minecraft.data.IFinishedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Modules {

    private final List<Module> modules = new ArrayList<>();

    public void register(Module module) {
        modules.add(module);
    }

    public void runRecipes(Consumer<IFinishedRecipe> consumer) {
        modules.forEach(x -> x.runRecipes(consumer));
    }

    public void addTranslations(Languages languages) {
        modules.forEach(x -> x.addTranslations(languages));
    }

    public void initConfig() {
        modules.forEach(x -> x.initConfig());
    }
}
