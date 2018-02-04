package ipsis.woot.client.gui.element;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class DisplayItemStack extends DisplayStack {

    public ItemStack itemStack;

    public DisplayItemStack(int x, int y, ItemStack itemStack) {

        this.x = x;
        this.y = y;
        this.itemStack = itemStack;
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

        List<String> tooltip = gui.getItemToolTip(itemStack);

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

        return itemStack + "@" + x + "," + y;
    }
}
