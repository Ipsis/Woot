package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.woot.plugins.guideapi.GuideWoot;
import ipsis.woot.reference.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CategoryChangelog {

    public static void buildCategory(Book book) {

        final String keyBase = "guide." + Reference.MOD_ID + ".entry.changelog.";
        final String title = "guide." + Reference.MOD_ID + ".category.changelog";

        CategoryItemStack category = new CategoryItemStack(title, new ItemStack(Items.WRITTEN_BOOK));

        category.withKeyBase(keyBase);

        // New entry per release
        String[] versions = {
                "1_0_0",
                "1_0_1",
                "1_1_0"
        };

        for (String version : versions) {
            category.addEntry(version, new Entry(keyBase + version, true));
            category.getEntry(version).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + version + ".info"), GuideWoot.MAX_PAGE_LEN));
        }

        CategoryUtils.toUnicodeAndBeyond(category.entries);

        book.addCategory(category);
    }
}
