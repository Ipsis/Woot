package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class ElementStackBox extends ElementBase {

    private List<DisplayStack> stacks = new ArrayList<>();

    public ElementStackBox(GuiContainerWoot guiContainerWoot, FontRenderer fontRenderer, String header, int baseX, int baseY, int sizeX, int sizeY) {

        super(guiContainerWoot, fontRenderer, header, baseX, baseY, sizeX, sizeY);

        maxCol = (sizeX - 4) / 18;
    }

    private int currRow = 0;
    private int currCol = 0;
    private int maxCol = 0;

    public DisplayItemStack addItemStack(ItemStack itemStack) {

        int x = baseX + X_MARGIN + (currCol * 18) + 1;
        int y = contentY + (currRow * 18) + 1;
        DisplayItemStack displayItemStack = new DisplayItemStack(x, y, itemStack);
        stacks.add(displayItemStack);

        currCol++;
        if (currCol == maxCol) {
            currRow++;
            currCol = 0;
        }

        return displayItemStack;
    }

    public DisplayFluidStack addFluidStack(FluidStack fluidStack) {

        int x = baseX + X_MARGIN + (currCol * 18) + 1;
        int y = contentY + (currRow * 18) + 1;
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
                gui.drawItemStack(displayStack.itemStack,
                        gui.getGuiLeft() + displayStack.x,
                        gui.getGuiTop() + displayStack.y, false, null);
            } else if (stack instanceof DisplayFluidStack) {
                DisplayFluidStack displayStack = (DisplayFluidStack)stack;
                gui.drawFluid(gui.getGuiLeft() + displayStack.x,
                        gui.getGuiTop() + displayStack.y,
                        displayStack.fluidStack,
                        16, 16);
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
