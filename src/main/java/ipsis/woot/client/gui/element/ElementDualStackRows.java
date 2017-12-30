package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class ElementDualStackRows extends ElementBase {

    private List<DisplayStack> stacks = new ArrayList<>();
    private List<DisplayItemStack> items = new ArrayList<>();
    private List<DisplayFluidStack> fluids = new ArrayList<>();

    public ElementDualStackRows(GuiContainerWoot guiContainer, FontRenderer fontRenderer, String header, int baseX, int baseY, int sizeX, int sizeY) {

        super(guiContainer, fontRenderer, header, baseX, baseY, sizeX, sizeY);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {

        super.drawBackground(mouseX, mouseY);

        int y = gui.getGuiTop() + contentY;
        for (int n = 0; n < items.size(); n++) {

            DisplayItemStack displayItemStack = items.get(n);
            gui.drawItemStack(displayItemStack.itemStack,
                    gui.getGuiLeft() + displayItemStack.x,
                    gui.getGuiTop() + displayItemStack.y, false, null);
        }

        y += 22;
        for (int n = 0; n < fluids.size(); n++) {

            DisplayFluidStack displayFluidStack = fluids.get(n);
            gui.drawFluid( gui.getGuiLeft() + displayFluidStack.x,
                    gui.getGuiTop() + displayFluidStack.y,
                    displayFluidStack.fluidStack,
                    20, 20);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);

        for (DisplayItemStack displayItemStack : items) {
            if (displayItemStack.isHit(mouseX, mouseY)) {
                List<String> tooltip = gui.getItemToolTip(displayItemStack.itemStack);
                tooltip.add(displayItemStack.itemStack.getCount() + " per mob");
                gui.drawHoveringText(tooltip, mouseX, mouseY);
                break;
            }
        }

        for (DisplayFluidStack displayFluidStack : fluids) {
            if (displayFluidStack.isHit(mouseX, mouseY)) {

                List<String> tooltip = new ArrayList<>();
                Fluid fluid = displayFluidStack.fluidStack.getFluid();
                tooltip.add(fluid.getLocalizedName(displayFluidStack.fluidStack));
                tooltip.add(displayFluidStack.fluidStack.amount + " mb per mob");
                gui.drawHoveringText(tooltip, mouseX, mouseY);
                break;
            }
        }
    }

    private int currItemCol = 0;
    private int currFluidCol = 0;
    public void addStack(ItemStack itemStack) {

        int x = baseX + X_MARGIN + (currItemCol * 18) + 1;
        int y = contentY + 1;
        stacks.add(new DisplayItemStack(x, y, itemStack));
        currItemCol++;
    }

    public void addFluidStack(FluidStack fluidStack) {

        int x = baseX + X_MARGIN + (currFluidCol * 18) + 1;
        int y = contentY + 20 + 4 + 1;
        stacks.add(new DisplayFluidStack(x, y, fluidStack));
        currFluidCol++;

    }
}
