package ipsis.woot.client.manual;

import ipsis.woot.reference.Lang;
import ipsis.woot.util.StringHelper;

public class Section {

    String tag;
    private IBookPage pages[];

    public Section(String tag, IBookPage... pages) {

        this.tag = tag;
        this.pages = pages;

        for (IBookPage page : pages)
            page.setSection(this);
    }

    IBookPage getFirstPage() {

        if (pages.length == 0)
            return null;

        return pages[0];
    }

    IBookPage getLastPage() {

        if (pages.length == 0)
            return null;

        return pages[(pages.length - 1)];
    }

    IBookPage getNextPage(IBookPage currPage) {

        IBookPage nextPage = null;
        for (int i = 0; i < pages.length && nextPage == null; i++) {
            if (pages[i] == currPage) {
                if (i < pages.length - 1)
                    nextPage = pages[i + 1];
            }
        }

        return nextPage;
    }

    IBookPage getPrevPage(IBookPage currPage) {

        IBookPage prevPage = null;
        for (int i = 0; i < pages.length && prevPage == null; i++) {
            if (pages[i] == currPage) {
                if (i > 0)
                    prevPage = pages[i - 1];
            }

        }

        return prevPage;
    }

    public String getTitle() {

        return StringHelper.localize(Lang.TAG_BOOK + tag + ".title");
    }

}
