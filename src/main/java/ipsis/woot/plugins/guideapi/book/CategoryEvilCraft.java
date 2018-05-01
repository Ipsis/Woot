package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.guideapi.GuideWoot;
import ipsis.woot.reference.Reference;
import net.minecraft.item.ItemStack;

public class CategoryEvilCraft {


    public static void buildCategory(Book book) {

        final String keyBase = "guide." + Reference.MOD_ID + ".entry.evilcraft.";
        final String title = "guide." + Reference.MOD_ID + ".category.evilcraft";

        CategoryItemStack category = new CategoryItemStack(title, new ItemStack(ModBlocks.blockUpgradeB, 1, 9));

        category.withKeyBase(keyBase);

        String entries[] = {
                "intro",
                "tank" };

        for (String e : entries) {
            category.addEntry(e, new Entry(keyBase + e, true));
            category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".info"), GuideWoot.MAX_PAGE_LEN));
        }

        CategoryUtils.toUnicodeAndBeyond(category.entries);

        book.addCategory(category);
    }
}
