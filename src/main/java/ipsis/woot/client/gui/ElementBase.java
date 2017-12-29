package ipsis.woot.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

public class ElementBase {

    protected int baseX;
    protected int baseY;
    protected int sizeX;
    protected int sizeY;
    protected GuiContainerWoot guiContainer;

    public ElementBase(GuiContainerWoot guiContainer, int baseX, int baseY, int sizeX, int sizeY) {

        this.baseX = baseX;
        this.baseY = baseY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.guiContainer = guiContainer;
    }

    public void drawBackground() {

        guiContainer.drawSizedModalRect(baseX, baseY, baseX + sizeX, baseY + sizeY, 0x334455);
    }

    public void drawForeground() {

    }

}
