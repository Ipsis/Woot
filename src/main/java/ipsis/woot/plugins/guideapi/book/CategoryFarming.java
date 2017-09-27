package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.guideapi.GuideWoot;
import ipsis.woot.reference.Reference;
import net.minecraft.item.ItemStack;

public class CategoryFarming {

    public static void buildCategory(Book book) {

        final String keyBase = "guide." + Reference.MOD_ID + ".entry.farming.";

        CategoryItemStack category = new CategoryItemStack(keyBase + "farming", new ItemStack(ModBlocks.blockFactoryHeart));

        category.withKeyBase(keyBase);

        category.addEntry("intro", new Entry(keyBase + "intro", true));
        category.getEntry("intro").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "intro.info"), GuideWoot.MAX_PAGE_LEN));

        category.addEntry("farm", new Entry(keyBase + "farm", true));
        category.getEntry("farm").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "farm.info.1"), GuideWoot.MAX_PAGE_LEN));
        category.getEntry("farm").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "farm.info.2"), GuideWoot.MAX_PAGE_LEN));

        for (EntryAbstract entry : category.entries.values()) {
            for (IPage p : entry.pageList) {
                if (p instanceof Page)
                    ((Page) p).setUnicodeFlag(true);

            }
        }

        book.addCategory(category);

    }
}
