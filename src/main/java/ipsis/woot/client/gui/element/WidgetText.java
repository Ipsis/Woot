package ipsis.woot.client.gui.element;

import net.minecraft.client.gui.FontRenderer;

public class WidgetText {

    public static final int TEXT_HEIGHT = 10;
    public static final int TEXT_X_MARGIN = 1;
    public static final int TEXT_Y_MARGIN = 1;

    public static int getHeight() {

        return TEXT_HEIGHT + (TEXT_Y_MARGIN * 2);
    }

    public static void draw(FontRenderer fontRenderer, String s, int x, int y, int color) {

        fontRenderer.drawString(s, x + TEXT_X_MARGIN, y + TEXT_Y_MARGIN, color);
    }
}
