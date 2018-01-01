package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.item.ItemStack;

public class WidgetItemStack {

    public static final int STACK_HEIGHT = 16;
    public static final int STACK_WIDTH = 16;
    public static final int STACK_X_MARGIN = 1;
    public static final int STACK_Y_MARGIN = 1;

    public static int getHeight() {

        return STACK_HEIGHT + (STACK_Y_MARGIN * 2);
    }

    public static int getWidth() {

        return STACK_WIDTH + (STACK_X_MARGIN * 2);
    }

    public static void draw(GuiContainerWoot gui, ItemStack stack, int x, int y, String overlay) {

        gui.drawItemStack(stack,
                x + STACK_X_MARGIN,
                y + STACK_Y_MARGIN,
                overlay != null, overlay);

    }
}
