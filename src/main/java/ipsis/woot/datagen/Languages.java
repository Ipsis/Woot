package ipsis.woot.datagen;

import ipsis.woot.Woot;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class Languages extends LanguageProvider {

    public Languages(DataGenerator generator, String locale) {
        super(generator, Woot.MODID, locale);
    }

    @Override
    protected void addTranslations() {

        add("itemGroup.woot", "Woot");
        add("info.woot.sneakforinfo", "Hold shift for more detail");

        Woot.instance.getModules().addTranslations(this);


    }
}
