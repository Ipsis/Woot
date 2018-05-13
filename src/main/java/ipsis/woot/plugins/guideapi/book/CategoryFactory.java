package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.page.PageTextImage;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.guideapi.GuideWoot;
import ipsis.woot.reference.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategoryFactory {

    public static void buildCategory(Book book) {

        final String keyBase = "guide." + Reference.MOD_ID + ".entry.factory.";
        final String title = "guide." + Reference.MOD_ID + ".category.factory";

        CategoryItemStack category = new CategoryItemStack(title, new ItemStack(ModBlocks.blockFactoryHeart));

        category.withKeyBase(keyBase);

        String entries[] = {
                "guide",
                "structure",
                "importer",
                "exporter",
                "power",
                "stygianiron",
                "capturing",
                "programming",
                "stygiananvil",
                "headhunter",
                "intern"};

        for (String e : entries) {
            category.addEntry(e, new Entry(keyBase + e, true));
            category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".info"), GuideWoot.MAX_PAGE_LEN));
        }


        String bookImages = "wootguide:";
        ResourceLocation rs = new ResourceLocation(bookImages + "textures/gui/remote_io.png");
        category.getEntry("structure").addPage(new PageTextImage(keyBase + "remote_io_structure", rs, true));
        rs = new ResourceLocation(bookImages + "textures/gui/remote_connect.png");
        category.getEntry("structure").addPage(new PageTextImage(keyBase + "remote_io_connect", rs, true));
        rs = new ResourceLocation(bookImages + "textures/gui/remote_distance.png");
        category.getEntry("structure").addPage(new PageTextImage(keyBase + "remote_io_distance", rs, true));

        String e = "tiers";
        category.addEntry(e, new Entry(keyBase + e, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".info"), GuideWoot.MAX_PAGE_LEN));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".tieri"), GuideWoot.MAX_PAGE_LEN));
        rs = new ResourceLocation(bookImages + "textures/gui/tier_i.png");
        category.getEntry(e).addPage(new PageTextImage("Tier I", rs, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".tierii"), GuideWoot.MAX_PAGE_LEN));
        rs = new ResourceLocation(bookImages + "textures/gui/tier_ii.png");
        category.getEntry(e).addPage(new PageTextImage("Tier II", rs, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".tieriii"), GuideWoot.MAX_PAGE_LEN));
        rs = new ResourceLocation(bookImages + "textures/gui/tier_iii.png");
        category.getEntry(e).addPage(new PageTextImage("Tier III", rs, true));
        category.getEntry(e).addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + e + ".tieriv"), GuideWoot.MAX_PAGE_LEN));
        rs = new ResourceLocation(bookImages + "textures/gui/tier_iv.png");
        category.getEntry(e).addPage(new PageTextImage("Tier IV", rs, true));

        CategoryUtils.toUnicodeAndBeyond(category.entries);

        book.addCategory(category);
    }
}
