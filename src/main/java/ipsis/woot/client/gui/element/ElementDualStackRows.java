package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class ElementDualStackRows extends ElementBase {

    private List<ItemStack> itemStacks = new ArrayList<>();
    private List<FluidStack> fluidStacks = new ArrayList<>();

    public ElementDualStackRows(GuiContainerWoot guiContainer, FontRenderer fontRenderer, String header, int baseX, int baseY, int sizeX, int sizeY) {

        super(guiContainer, fontRenderer, header, baseX, baseY, sizeX, sizeY);
    }

    @Override
    public void drawBackground() {

        super.drawBackground();

        int y = guiContainer.getGuiTop() + contentY;
        for (int n = 0; n < itemStacks.size(); n++) {

            int x = guiContainer.getGuiLeft() + baseX + X_MARGIN + (n * 20) + 1;
            guiContainer.drawItemStack(itemStacks.get(n), x, y, Integer.toString(itemStacks.get(n).getCount()));
        }

    }

    public void addStack(ItemStack itemStack) {

        itemStacks.add(itemStack);
    }

    public void addFluidStack(FluidStack fluidStack) {

        fluidStacks.add(fluidStack);

    }
}
