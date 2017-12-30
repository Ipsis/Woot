package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import ipsis.woot.oss.LogHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

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
    public void addStack(ItemStack itemStack) {

        int x = guiContainer.getGuiLeft() + baseX + X_MARGIN + (currCol * 18) + 1;
        int y = guiContainer.getGuiTop() + contentY + (currRow * 18) + 1;
        stacks.add(new DisplayStack(x, y, itemStack));

        currCol++;
        if (currCol == maxCol) {
            currRow++;
            currCol = 0;
        }
    }

    @Override
    public void drawBackground() {

        super.drawBackground();

        for (int n = 0; n < stacks.size(); n++) {

           DisplayStack displayStack = stacks.get(n);
           guiContainer.drawItemStack(displayStack.itemStack, displayStack.x, displayStack.y, null);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

        super.drawForeground(mouseX, mouseY);

        for (DisplayStack displayStack : stacks) {
            if (displayStack.isHit(mouseX, mouseY))
                LogHelper.info("Hit " + displayStack);
        }

    }
}
