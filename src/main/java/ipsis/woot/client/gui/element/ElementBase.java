package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class ElementBase {

    protected int baseX;
    protected int baseY;
    protected int sizeX;
    protected int sizeY;
    protected GuiContainerWoot gui;
    protected FontRenderer fontRenderer;
    protected String header;
    protected int contentX;
    protected int contentY;
    protected int contentSizeX;
    protected int contentSizeY;

    protected final int PANEL_X_MARGIN = 2;
    protected final int PANEL_Y_MARGIN = 2;

    public ElementBase(GuiContainerWoot guiContainer, FontRenderer fontRenderer, String header, int baseX, int baseY, int sizeX, int sizeY) {

        this.baseX = baseX;
        this.baseY = baseY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.gui = guiContainer;
        this.fontRenderer = fontRenderer;
        this.header = header;
        this.contentX = baseX + PANEL_X_MARGIN;
        this.contentY = baseY + PANEL_Y_MARGIN;
        this.contentSizeX = sizeX - (2 * PANEL_X_MARGIN);
        this.contentSizeY = sizeY - (2 * PANEL_Y_MARGIN);

        if (header != null) {
            this.contentY = baseY + PANEL_Y_MARGIN + WidgetText.getHeight();
            this.contentSizeY = sizeY - (2 * PANEL_Y_MARGIN) - WidgetText.getHeight();
        }
    }

    public void drawBackground(int mouseX, int mouseY) {

        gui.drawSizedModalRect(gui.getGuiLeft() + baseX, gui.getGuiTop() + baseY, baseX + gui.getGuiLeft() + sizeX, baseY + gui.getGuiTop() + sizeY, Color.darkGray.getRGB());
    }

    public void drawForeground(int mouseX, int mouseY) {

        if (header != null)
            WidgetText.draw(fontRenderer, header, baseX, baseY, Color.yellow.getRGB());
    }

}
