package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryModule;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class Languages extends LanguageProvider {

    public Languages(DataGenerator generator, String locale) {
        super(generator, Woot.MODID, locale);
    }

    @Override
    protected void addTranslations() {

        add("itemGroup.woot", "Woot");

        add(FactoryModule.LAYOUT.get(), "Layout Guide");
        add(FactoryModule.HEART.get(), "Heart");
        add(FactoryModule.IMPORTER.get(), "Importer");
        add(FactoryModule.EXPORTER.get(), "Exporter");
        add(FactoryModule.BASE_1.get(), "Casing 1");
        add(FactoryModule.BASE_2.get(), "Casing 2");
        add(FactoryModule.BASE_GLASS.get(), "Factory Glass");
        add(FactoryModule.CORE_1A.get(), "Core 1A");
        add(FactoryModule.CORE_1B.get(), "Core 1B");
    }
}
