package ipsis.woot.client.gui.element;

import ipsis.woot.oss.LogHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DisplayStack {

    public int x;
    public int y;
    public ItemStack itemStack;
    private List<String> tooltips;

    public DisplayStack(int x, int y, ItemStack itemStack) {

        this.x = x;
        this.y = y;
        this.itemStack = itemStack;
        tooltips = new ArrayList<>();
    }

    public void addTooltip(String s) {

        tooltips.add(s);
    }

    public boolean isHit(int mouseX, int mouseY) {

        LogHelper.info("isHit " + this + " -- " + mouseX + "," + mouseY);
        if (mouseX >= x && mouseX <= x + 20 && mouseY >= y && mouseY <= y + 20)
            return true;

        return false;
    }

    public List<String> getTooltip() {

        return tooltips;
    }

    @Override
    public String toString() {

        return itemStack + "@" + x + "," + y;
    }
}
