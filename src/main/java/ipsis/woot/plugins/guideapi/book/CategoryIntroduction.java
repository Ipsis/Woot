package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import ipsis.woot.reference.Reference;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ipsis.woot.plugins.guideapi.book.EntryHelper.addEntry;

public class CategoryIntroduction {


    public static Map<ResourceLocation, EntryAbstract> buildCategory() {

        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();
        String base = "guide." + Reference.MOD_ID + ":entry.introduction.";

        List<IPage> introPages = new ArrayList<>();
        addEntry(introPages, base, "intro", entries);

        List<IPage> creditsPages = new ArrayList<>();
        addEntry(creditsPages, base, "credits", entries);

        return entries;
    }
}
