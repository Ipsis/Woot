package ipsis.woot.client.gui.element;

import net.minecraft.item.ItemStack;

public class DisplayStackSize extends DisplayStack {

    public DisplayStackSize(int x, int y, ItemStack itemStack) {

        super(x, y, itemStack);
        super.addTooltip(itemStack.getDisplayName());
        super.addTooltip(Integer.toString(itemStack.getCount()));
    }
}
