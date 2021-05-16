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

        addGuideTranslations();
    }

    private void addGuideTranslations() {

        add("item.woot.guide.name", "Woot Guide");
        add("item.woot.guide.landing",
                "A guide to the big, bad, magic multiblock Mob Factory. " +
                "Like Marmite, you will either love it or hate it - you choose");

        /**********************************************************************
         * Categories
         */
        {
            final String CATEGORY_TAG = "guide.woot.category.";
            String[][] text = {
                    { CATEGORY_TAG + "basics", "Basics"},
                    { CATEGORY_TAG + "basics.desc", "How to build and run your mob factory."},
                    { CATEGORY_TAG + "exotics", "Factory Exotics"},
                    { CATEGORY_TAG + "exotics.desc", "Exotic extensions to the factory."},
                    { CATEGORY_TAG + "factoryBlocks", "Factory Components"},
                    { CATEGORY_TAG + "factoryBlocks.desc", "The blocks that make up the factor."},
                    { CATEGORY_TAG + "machines", "Machines"},
                    { CATEGORY_TAG + "machines.desc", "Important machines for creating and running your Woot factory."},
                    { CATEGORY_TAG + "perks", "Perks"},
                    { CATEGORY_TAG + "perks.desc", "How to make your factory better."},
                    { CATEGORY_TAG + "tools", "Tools"},
                    { CATEGORY_TAG + "tools.desc", "The tools of Woot. (Yes, another mod with a hammer.)"}
            };

            for (String[] strings : text) add(strings[0], strings[1]);
        }
    }
}
