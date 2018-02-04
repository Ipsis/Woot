package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.woot.init.ModItems;
import ipsis.woot.plugins.guideapi.GuideWoot;
import ipsis.woot.reference.Reference;
import net.minecraft.item.ItemStack;

public class CategoryForeword {

    public static void buildCategory(Book book) {

        final String keyBase = "guide." + Reference.MOD_ID + ".entry.foreword.";
        final String title = "guide." + Reference.MOD_ID + ".category.foreword";

        CategoryItemStack category = new CategoryItemStack(title, new ItemStack(ModItems.itemYahHammer));

        category.withKeyBase(keyBase);

        String e = "intro";
        category.addEntry(e, new Entry(keyBase + e, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".info"), GuideWoot.MAX_PAGE_LEN));

        e = "purpose";
        category.addEntry(e, new Entry(keyBase + e, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".info"), GuideWoot.MAX_PAGE_LEN));

        e = "naming";
        category.addEntry(e, new Entry(keyBase + e, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".info"), GuideWoot.MAX_PAGE_LEN));

        e = "sponge";
        category.addEntry(e, new Entry(keyBase + e, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".info"), GuideWoot.MAX_PAGE_LEN));

        e = "credits";
        category.addEntry(e, new Entry(keyBase + e, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".info"), GuideWoot.MAX_PAGE_LEN));

        CategoryUtils.toUnicodeAndBeyond(category.entries);

        book.addCategory(category);
    }
}
