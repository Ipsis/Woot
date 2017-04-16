package ipsis.woot.plugins.guideapi;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.page.PageText;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryUpgrade {

    public static Map<ResourceLocation, EntryAbstract> buildCategory() {

        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String tag = GuideWoot.BOOK_TAG + ".entry.upgrades.";
        String tag1;
        List<IPage> pages;

        tag1 = "totems";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "rate";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.3")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "mass";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.3")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "looting";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.3")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "xp";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.3")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "efficiency";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.3")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "decapitate";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.3")));

        tag1 = "bloodmagic";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.3")));

        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));
        for (Map.Entry<ResourceLocation, EntryAbstract> entry : entries.entrySet()) {
            for (IPage page : entry.getValue().pageList) {
                if (page instanceof PageText)
                    ((PageText) page).setUnicodeFlag(true);
            }
        }

        return entries;
    }
}
