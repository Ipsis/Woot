package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import ipsis.woot.client.gui.element.ElementBase;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ElementTextBox extends ElementBase {

    private List<String> entries = new ArrayList<>();

    public ElementTextBox(GuiContainerWoot guiContainer, FontRenderer fontRenderer, String header, int baseX, int baseY, int sizeX, int sizeY) {

        super(guiContainer, fontRenderer, header, baseX, baseY, sizeX, sizeY);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

        super.drawForeground(mouseX, mouseY);

        for (int n = 0; n < entries.size(); n++) {
            String s = entries.get(n);
            WidgetText.draw(fontRenderer, s, contentX, contentY + (n * WidgetText.getHeight()), Color.WHITE.getRGB());
        }
    }

    public void addString(String s) {

        entries.add(s);
    }
}
