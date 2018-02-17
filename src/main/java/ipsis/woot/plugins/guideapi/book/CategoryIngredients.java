package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.Woot;
import ipsis.woot.init.ModItems;
import ipsis.woot.plugins.guideapi.GuideWoot;
import ipsis.woot.plugins.guideapi.page.PageFarmRecipe;
import ipsis.woot.reference.Reference;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;

public class CategoryIngredients {

    public static void buildCategory(Book book) {

        final String keyBase = "guide." + Reference.MOD_ID + ".entry.ingredients.";
        final String title = "guide." + Reference.MOD_ID + ".category.ingredients";

        CategoryItemStack category = new CategoryItemStack(title, new ItemStack(ModItems.itemEnderShard));

        category.withKeyBase(keyBase);

        category.addEntry("intro", new Entry(keyBase + "intro", true));
        category.getEntry("intro").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "intro.info"), GuideWoot.MAX_PAGE_LEN));

        /**
         * Although there can be default recipes, this will not add them to the book as only
         * those with unique recipes are retrieved
         */
        for (WootMobName wootMobName : Woot.spawnRecipeRepository.getAllMobs()) {
            String name = EntityList.getTranslationName(wootMobName.getResourceLocation());
            category.addEntry(name, new Entry(name, true));
            category.getEntry(name).addPage(new PageFarmRecipe(wootMobName, Woot.spawnRecipeRepository.get(wootMobName)));
        }

        CategoryUtils.toUnicodeAndBeyond(category.entries);

        book.addCategory(category);
    }

}
