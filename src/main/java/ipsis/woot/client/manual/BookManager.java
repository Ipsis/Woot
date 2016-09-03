package ipsis.woot.client.manual;

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

        addEntry("introduction", new BookPageText("introduction.0"), new BookPageText("introduction.1"));
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
