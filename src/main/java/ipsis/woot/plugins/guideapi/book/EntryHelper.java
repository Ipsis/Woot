package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.page.PageText;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public class EntryHelper {

    public static void addEntry(List<IPage> pages, String base, String key, Map<ResourceLocation, EntryAbstract> entryAbstractMap) {

        pages.addAll(PageHelper.pagesForLongText(TextHelper.localize(base + key + ".info")));
        for (IPage page : pages) {
            if (page instanceof PageText)
                ((PageText) page).setUnicodeFlag(true);
        }
        entryAbstractMap.put(new ResourceLocation(base + key), new Entry(pages, TextHelper.localize(base + key), true));
    }
}
