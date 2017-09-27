package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.Woot;
import ipsis.woot.plugins.guideapi.page.PageFarmRecipe;
import ipsis.woot.reference.Reference;
import ipsis.woot.util.WootMobName;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CategoryIngredients {

    public static void buildCategory(Book book) {

        final String keyBase = "guide." + Reference.MOD_ID + ".entry.ingredients.";

        CategoryItemStack category = new CategoryItemStack(keyBase + "ingredients", new ItemStack(Items.BREWING_STAND));
        category.withKeyBase(keyBase);

        category.addEntry("intro", new Entry(keyBase + "intro", true));
        category.getEntry("intro").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "intro.info")));

        category.addEntry("recipes", new Entry(keyBase + "recipes", true));
        category.getEntry("recipes").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "recipes.info")));

        for (WootMobName wootMobName : Woot.spawnRecipeRepository.getAllMobs()) {
            category.getEntry("recipes").addPage(new PageFarmRecipe(wootMobName, Woot.spawnRecipeRepository.get(wootMobName)));
        }

        for (EntryAbstract entry : category.entries.values()) {
            for (IPage p : entry.pageList) {
                if (p instanceof Page)
                    ((Page) p).setUnicodeFlag(true);

            }
        }

        book.addCategory(category);
    }

}
