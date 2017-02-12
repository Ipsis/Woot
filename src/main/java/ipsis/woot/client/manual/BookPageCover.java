package ipsis.woot.client.manual;

import ipsis.woot.reference.Lang;
import ipsis.woot.util.StringHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.util.List;

public class BookPageCover implements IBookPage {

    private String tag;
    private Section section;

    public BookPageCover(String tag) {

        this.tag = tag;
    }

    @Override
    public Section getSection() {

        return section;
    }

    String getTranslatedText() {

        return StringHelper.localize(Lang.TAG_BOOK + tag);
    }

    @Override
    public void setSection(Section s) {

        this.section = s;
    }

    @Override
    public void renderPage(GuiScreen gui, int x, int y, int page_width) {

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        String text = getTranslatedText();
        List<String> l = fontRenderer.listFormattedStringToWidth(text, page_width);
        for (int i = 0; i < l.size(); i++)
            fontRenderer.drawString(l.get(i), x, y + 60 + (i * fontRenderer.FONT_HEIGHT), Color.YELLOW.getRGB(), true);
    }
}
