package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.awt.*;

public class ElementBase {

    protected int baseX;
    protected int baseY;
    protected int sizeX;
    protected int sizeY;
    protected GuiContainerWoot guiContainer;
    protected FontRenderer fontRenderer;
    protected String header;
    protected int contentY;

    protected final int X_MARGIN = 4;
    protected final int Y_MARGIN = 4;
    protected final int TXT_HEIGHT = 12;

    public ElementBase(GuiContainerWoot guiContainer, FontRenderer fontRenderer, String header, int baseX, int baseY, int sizeX, int sizeY) {

        this.baseX = baseX;
        this.baseY = baseY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.guiContainer = guiContainer;
        this.fontRenderer = fontRenderer;
        this.header = header;

        this.contentY = baseY + (Y_MARGIN * 2) + TXT_HEIGHT;
    }

    public void drawBackground() {

        guiContainer.drawSizedModalRect(guiContainer.getGuiLeft() + baseX, guiContainer.getGuiTop() + baseY, baseX + guiContainer.getGuiLeft() + sizeX, baseY + guiContainer.getGuiTop() + sizeY, Color.darkGray.getRGB());
    }

    public void drawForeground(int mouseX, int mouseY) {

        fontRenderer.drawString(header, baseX + X_MARGIN + 1, baseY + Y_MARGIN + 1, Color.yellow.getRGB());
    }

}
