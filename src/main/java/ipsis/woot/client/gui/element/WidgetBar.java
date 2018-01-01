package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class WidgetBar {

    public static final int BAR_X_MARGIN = 1;
    public static final int BAR_Y_MARGIN = 1;
    public static final int BAR_HEIGHT = 10;

    public static int getBarHeight() {

        return BAR_HEIGHT + (BAR_Y_MARGIN * 2);
    }

    public static void draw(GuiContainerWoot gui, int x, int y, int width, int percentage, int color) {

        int length = (int)(((float)width / 100.0F) * percentage);
        length = MathHelper.clamp(length, 0, width);

        gui.drawSizedModalRect(
                x + BAR_X_MARGIN,
                y + BAR_Y_MARGIN,
                x + BAR_X_MARGIN + width,
                y + BAR_Y_MARGIN + BAR_HEIGHT,
                Color.black.getRGB());

        if (percentage > 0) {
            gui.drawSizedModalRect(
                    x + BAR_X_MARGIN + 1,
                    y + BAR_Y_MARGIN + 1,
                    x + BAR_X_MARGIN + length - 1,
                    y + BAR_Y_MARGIN + BAR_HEIGHT - 1,
                    color);
        }
    }
}
