package ipsis.woot.client.manual;

import net.minecraft.client.gui.GuiScreen;

public interface IBookPage {

    Section getSection();
    void setSection(Section s);
    void renderPage(GuiScreen gui, int x, int y, int page_width);
}
