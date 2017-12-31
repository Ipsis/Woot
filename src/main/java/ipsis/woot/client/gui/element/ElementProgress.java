package ipsis.woot.client.gui.element;

import ipsis.woot.client.gui.GuiContainerWoot;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ElementProgress extends ElementBase {

    public ElementProgress(GuiContainerWoot guiContainerWoot, FontRenderer fontRenderer, String header, int baseX, int baseY, int sizeX, int sizeY) {

        super(guiContainerWoot, fontRenderer, header, baseX, baseY, sizeX, sizeY);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {

        super.drawBackground(mouseX, mouseY);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

        super.drawForeground(mouseX, mouseY);

        int n = 0;
        for (Progress p : widgets.values()) {
            fontRenderer.drawString(p.name + ": " + p.curr + "/" + p.max, baseX + X_MARGIN, contentY + (n * TXT_HEIGHT) + 1, Color.white.getRGB());
            n++;
        }
    }

    private Map<Integer, Progress> widgets = new HashMap<>();

    public void addProgress(int id, String name, int max) {

        widgets.put(id, new Progress(name, max));
    }

    public void updateProgress(int id, int curr) {

        if (widgets.keySet().contains(id))
            widgets.get(id).curr = curr;
    }

    private class Progress {

        String name;
        int max;
        int curr;

        public Progress(String name, int max) {

            this.name = name;
            this.max = max;
            this.curr = 0;
        }
    }
}
