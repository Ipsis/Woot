package ipsis.woot.client.gui.element;

import net.minecraft.client.gui.inventory.GuiContainer;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class DisplayStack {

    int x;
    int y;

    public abstract boolean isHit(int mouseX, int mouseY);
    public abstract @Nonnull List<String> getTooltip(GuiContainer gui);
    public abstract void appendTooltip(String s);
}
