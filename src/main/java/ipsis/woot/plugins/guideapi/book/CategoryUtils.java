package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class CategoryUtils {

    public static void toUnicodeAndBeyond(Map<ResourceLocation, EntryAbstract> entries) {

        for (EntryAbstract entry : entries.values()) {
            for (IPage p : entry.pageList) {
                if (p instanceof Page)
                    ((Page) p).setUnicodeFlag(true);

            }
        }
    }
}
