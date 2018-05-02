package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.Woot;
import ipsis.woot.configuration.ChangeLog;
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

        for (ChangeLog.Changes changes : Woot.changeLog.getLibrary()) {

            category.addEntry(changes.version, new Entry(changes.version, true));

            StringBuilder sb = new StringBuilder();

            if (!changes.featureMap.keySet().isEmpty()) {
                sb.append("Features\n========\n\n");
                for (Integer id : changes.featureMap.keySet()) {

                    String desc = changes.featureMap.get(id);
                    if (id < 0)
                        sb.append("[####]\n");
                    else
                        sb.append(String.format("[%04d]\n", id));

                    sb.append(desc).append("\n");
                }
                sb.append("\n");
            }

            if (!changes.fixMap.keySet().isEmpty()) {
                sb.append("Fixes\n=====\n\n");
                for (Integer id : changes.fixMap.keySet()) {

                    String desc = changes.fixMap.get(id);
                    if (id < 0)
                        sb.append("[####]\n");
                    else
                        sb.append(String.format("[%04d]\n", id));

                    sb.append(desc).append("\n");
                }
                sb.append("\n");
            }

            category.getEntry(changes.version).addPageList(PageHelper.pagesForLongText(sb.toString(), GuideWoot.MAX_CHANGELOG_PAGE_LEN));
        }

        CategoryUtils.toUnicodeAndBeyond(category.entries);
        book.addCategory(category);
    }
}
