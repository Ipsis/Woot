package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ElementStackBox extends ElementBase {

    private List<DisplayStack> stacks = new ArrayList<>();
    private boolean showFlag = false;

    public ElementStackBox(GuiContainerWoot guiContainerWoot, FontRenderer fontRenderer, String header, int baseX, int baseY, int sizeX, int sizeY) {

        super(guiContainerWoot, fontRenderer, header, baseX, baseY, sizeX, sizeY);

        maxCol = (sizeX - (2 * PANEL_X_MARGIN)) / WidgetItemStack.getWidth();
    }

    public void setShowFlag(boolean showFlag) {
        this.showFlag = showFlag;
    }

    private int currRow = 0;
    private int currCol = 0;
    private int maxCol = 0;

    public DisplayItemStack addItemStack(ItemStack itemStack) {

        int x = contentX + (currCol * WidgetItemStack.getWidth());
        int y = contentY + (currRow * WidgetItemStack.getHeight());
        DisplayItemStack displayItemStack = new DisplayItemStack(x, y, itemStack);
        stacks.add(displayItemStack);

        currCol++;
        if (currCol == maxCol) {
            currRow++;
            currCol = 0;
        }

        return displayItemStack;
    }

    public void setHeader(String header) {

        this.header = header;
    }

    public DisplayFluidStack addFluidStack(FluidStack fluidStack) {

        int x = contentX + (currCol * WidgetFluidStack.getWidth());
        int y = contentY + (currRow * WidgetFluidStack.getHeight());
        DisplayFluidStack displayFluidStack = new DisplayFluidStack(x, y, fluidStack);
        stacks.add(displayFluidStack);

        currCol++;
        if (currCol == maxCol) {
            currRow++;
            currCol = 0;
        }

        return displayFluidStack;
    }


    @Override
    public void drawBackground(int mouseX, int mouseY) {

        super.drawBackground(mouseX, mouseY);

        for (int n = 0; n < stacks.size(); n++) {

            DisplayStack stack = stacks.get(n);
            if (stack instanceof DisplayItemStack) {
                DisplayItemStack displayStack = (DisplayItemStack)stack;

                int x = gui.getGuiLeft() + displayStack.x;
                int y = gui.getGuiTop() + displayStack.y;
                WidgetItemStack.draw(gui,
                        displayStack.itemStack, x, y, null);

                if (showFlag) {
                    String s = "?";
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();
                    GlStateManager.disableBlend();
                    fontRenderer.drawStringWithShadow(s, (float) (x + 19 - 2 - fontRenderer.getStringWidth(s)), (float) (y + 6 + 3 + 8), Color.RED.getRGB());
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                    // Fixes opaque cooldown overlay a bit lower
                    // TODO: check if enabled blending still screws things up down the line.
                    GlStateManager.enableBlend();
                }

            } else if (stack instanceof DisplayFluidStack) {
                DisplayFluidStack displayStack = (DisplayFluidStack)stack;

                int x = gui.getGuiLeft() + displayStack.x;
                int y = gui.getGuiTop() + displayStack.y;
                WidgetFluidStack.draw(gui,
                        displayStack.fluidStack, x, y);

                if (showFlag) {
                    String s = "?";
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();
                    GlStateManager.disableBlend();
                    fontRenderer.drawStringWithShadow(s, (float) (x + 19 - 2 - fontRenderer.getStringWidth(s)), (float) (y + 6 + 3 + 8), Color.RED.getRGB());
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                    // Fixes opaque cooldown overlay a bit lower
                    // TODO: check if enabled blending still screws things up down the line.
                    GlStateManager.enableBlend();
                }
            }

        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

        super.drawForeground(mouseX, mouseY);

        for (DisplayStack stack : stacks) {

            if (stack.isHit(mouseX, mouseY)) {
                List<String> tooltip = stack.getTooltip(gui);
                gui.drawHoveringText(tooltip, mouseX, mouseY);

                // Can only hover over one icon at a time!
                break;
            }
        }

    }
}
