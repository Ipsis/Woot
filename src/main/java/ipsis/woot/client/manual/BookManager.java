package ipsis.woot.client.manual;

import ipsis.woot.plugins.bloodmagic.BloodMagic;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.HashMap;

public class BookManager {

    public static BookManager INSTANCE = new BookManager();
    private ArrayList<String> sectionList;
    private HashMap<String, Section> sectionMap;

    public BookManager() {

        sectionList = new ArrayList<>();
        sectionMap = new HashMap<>();
    }

    public void load() {

        addEntry("cover", new BookPageCover("cover.0"));
        addEntry("introduction", new BookPageText("introduction.0"), new BookPageText("introduction.1"));
        addEntry("crafting", new BookPageText("crafting.0"), new BookPageText("crafting.1"));
        addEntry("building", new BookPageText("building.0"), new BookPageText("building.1"), new BookPageText("building.2"));
        addEntry("capturing", new BookPageText("capturing.0"), new BookPageText("capturing.1"));
        addEntry("operation", new BookPageText("operation.0"), new BookPageText("operation.1"));
        addEntry("power", new BookPageText("power.0"));
        addEntry("tier1", new BookPageText("tier1.0"), new BookPageText("tier1.1"));
        addEntry("tier2", new BookPageText("tier2.0"), new BookPageText("tier2.1"));
        addEntry("tier3", new BookPageText("tier3.0"), new BookPageText("tier3.1"));
        addEntry("tier4", new BookPageText("tier4.0"), new BookPageText("tier4.1"));
        addEntry("proxy", new BookPageText("proxy.0"), new BookPageText("proxy.1"));
        addEntry("upgrades", new BookPageText("upgrades.0"), new BookPageText("upgrades.1"));
        addEntry("upgrade_looting", new BookPageText("upgrade_looting.0"));
        addEntry("upgrade_rate", new BookPageText("upgrade_rate.0"));
        addEntry("upgrade_xp", new BookPageText("upgrade_xp.0"));
        addEntry("upgrade_mass", new BookPageText("upgrade_mass.0"));
        addEntry("upgrade_decapitate", new BookPageText("upgrade_decapitate.0"));
        addEntry("upgrade_efficiency", new BookPageText("upgrade_efficiency.0"));
        addEntry("enchant_decapitate", new BookPageText("enchant_decapitate.0"));

        if (Loader.isModLoaded(BloodMagic.BM_MODID))
            addEntry("upgrade_bloodmagic", new BookPageText("upgrade_bloodmagic.0"), new BookPageText("upgrade_bloodmagic.1"));
    }

    private void addEntry(String tag, IBookPage... pages) {

        sectionList.add(tag);
        sectionMap.put(tag, new Section(tag, pages));
    }

    public IBookPage getFirstPage() {

        IBookPage firstPage = null;
        if (!sectionList.isEmpty()) {
            Section section =  sectionMap.get(sectionList.get(0));
            firstPage = section.getFirstPage();
        }

        return firstPage;
    }

    public IBookPage getNextPage(IBookPage currPage) {

        IBookPage nextPage = currPage.getSection().getNextPage(currPage);
        if (nextPage == null) {
            Section section = currPage.getSection();
            for (int i = 0; i < sectionList.size() && nextPage == null; i++) {
                if (sectionList.get(i).equals(section.tag)) {
                    if (i < sectionList.size() - 1)
                        nextPage = sectionMap.get(sectionList.get(i + 1)).getFirstPage();
                }
            }
        }

        return nextPage;
    }

    public IBookPage getPrevPage(IBookPage currPage) {

        IBookPage prevPage = currPage.getSection().getPrevPage(currPage);
        if (prevPage == null) {
            Section section = currPage.getSection();
            for (int i = 0; i < sectionList.size() && prevPage == null; i++) {
                if (sectionList.get(i).equals(section.tag)) {
                    if (i != 0)
                        prevPage = sectionMap.get(sectionList.get(i - 1)).getLastPage();
                }
            }
        }

        return prevPage;
    }

    public boolean hasNextPage(IBookPage currPage) {

        return getNextPage(currPage) != null;
    }

    public boolean hasPrevPage(IBookPage currPage) {

        return getPrevPage(currPage) != null;
    }
}
