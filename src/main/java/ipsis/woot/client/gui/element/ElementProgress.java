package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class ElementProgress extends ElementBase {

    private int tagX;
    private int tagY;
    private int barX;
    private int barY;
    private int barWidth;
    private int curr;
    private int max;
    private int color;
    private String tag;
    private int type; // 0 - %, 1 = curr/max

    private final int PAD = 1;

    public ElementProgress(GuiContainerWoot guiContainerWoot, FontRenderer fontRenderer, String header, String tag, int type, int color, int baseX, int baseY, int sizeX, int sizeY) {

        super(guiContainerWoot, fontRenderer, header, baseX, baseY, sizeX, sizeY);

        this.tag = tag;
        this.type = type;
        this.color = color;
        this.curr = 0;

        int tagLen = fontRenderer.getStringWidth(tag);

        tagX = contentX;
        tagY = contentY;
        barX = contentX + tagLen + (2 * WidgetText.TEXT_X_MARGIN) + PAD;
        barY = contentY;
        barWidth = contentSizeX - contentX - tagLen - (2 * WidgetText.TEXT_X_MARGIN) - (2 * PAD);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {

        super.drawBackground(mouseX, mouseY);

        int percentage = (int)((100.0F / max) * curr);
        WidgetBar.draw(gui,
                gui.getGuiLeft() + barX, gui.getGuiTop() + barY, barWidth, percentage, color);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

        super.drawForeground(mouseX, mouseY);
        WidgetText.draw(fontRenderer, tag, tagX, tagY, Color.white.getRGB());
        String s = curr + "%";
        if (type == 1)
            s = curr + "/" + max;

        int textX = barX + (barWidth / 2) - (fontRenderer.getStringWidth(s) / 2);
        int textY = barY + 1;

        WidgetText.draw(fontRenderer, s, textX, textY, Color.white.getRGB());
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void updateProgress(int curr) {

        this.curr = curr;
    }
}
