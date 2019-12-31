package ipsis.woot.util;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public abstract class WootContainerScreen<T extends Container> extends ContainerScreen<T> {

    public WootContainerScreen(T container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
    }

    public void renderEnergyBar(int x1, int y1, int x2, int y2, int curr, int max) {
        int filled = curr * max / 100;
        filled = MathHelper.clamp(filled, 0, 100);
        int h = filled * (y2 - y1) / 100;
        fill(guiLeft + x1, guiTop + y2 - h,
                guiLeft + x2, guiTop + y2, 0xffff0000);
    }

    public void renderFluidTank(int x1, int y1, int x2, int y2, int curr, int max, FluidStack fluidStack)  {
        int filled = curr * 100 / max;
        filled = MathHelper.clamp(filled, 0, 100);
        int h = filled * (y2 - y1) / 100;
        fill(guiLeft + x1, guiTop + y2 - h,
                guiLeft + x2, guiTop + y2, fluidStack.getFluid().getAttributes().getColor());
    }

    public void renderHorizontalBar(int x1, int y1, int x2, int y2, int curr, int max, int color) {
        int filled = curr * max / 100;
        filled = MathHelper.clamp(filled, 0, 100);
        int l = filled * (x2 - x1) / 100;
        fill(guiLeft + x1, guiTop + y2,
                guiLeft + x2 + l, guiTop + y2, color);
    }

    public void renderHorizontalGauge(int x1, int y1, int x2, int y2, int curr, int max, int color) {
        fill(guiLeft + x1, guiTop + y1, guiLeft + x2, guiTop + y2, color);
        int p = curr * (x2 - x1) / max;
        for (int i = 0; i < p; i++)
            vLine(guiLeft + x1 + 1 + i,
                    guiTop + y1,
                    guiTop + y2 - 1,
                    i % 2 == 0 ? color : 0xff000000);
    }

    public void renderFluidTankTooltip(int mouseX, int mouseY, FluidStack fluidStack, int capacity) {
        List<String> tooltip = new ArrayList<>();
        tooltip.add(fluidStack.getDisplayName().getFormattedText());
        tooltip.add(String.format("%d/%d mb", fluidStack.getAmount(), capacity));
        renderTooltip(tooltip, mouseX, mouseY);
    }

    public void renderEnergyTooltip(int mouseX, int mouseY, int curr, int capacity) {
        renderTooltip(String.format("%d/%d RF", curr, capacity), mouseX, mouseY);
    }
}
