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

public class CategoryGeneral {

    public static Map<ResourceLocation, EntryAbstract> buildCategory() {

        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String tag = GuideWoot.BOOK_TAG + ".entry.general.";
        String tag1;
        List<IPage> pages;

        tag1 = "intro";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "why";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "started";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.3")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.4")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "capturing";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.1")));
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info.2")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "factory";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 +".info")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "power";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 + ".info")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "proxy";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 + ".info")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "tier";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 + ".info")));
        entries.put(new ResourceLocation(tag + tag1), new Entry(pages, TextHelper.localize(tag + tag1), true));

        tag1 = "extra";
        pages = new ArrayList<>();
        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(tag + tag1 + ".info")));
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
