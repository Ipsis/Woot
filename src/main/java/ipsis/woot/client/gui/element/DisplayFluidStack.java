package ipsis.woot.client.gui.element;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DisplayFluidStack extends DisplayStack {

    public FluidStack fluidStack;

    public DisplayFluidStack(int x, int y, FluidStack fluidStack) {

        this.x = x;
        this.y = y;
        this.fluidStack = fluidStack;
    }

    @Override
    public boolean isHit(int mouseX, int mouseY) {

        if (mouseX >= x && mouseX <= x + 20 && mouseY >= y && mouseY <= y + 20)
            return true;

        return false;
    }

    @Nonnull
    @Override
    public List<String> getTooltip(GuiContainer gui) {

        List<String> tooltip = new ArrayList<>();
        Fluid fluid = fluidStack.getFluid();
        tooltip.add(fluid.getLocalizedName(fluidStack));

        if (customTooltip != null)
            tooltip.add(customTooltip);

        return tooltip;
    }

    private String customTooltip;
    @Override
    public void appendTooltip(String s) {

        customTooltip = s;
    }

    @Override
    public String toString() {

        return fluidStack + "@" + x + "," + y;
    }
}
