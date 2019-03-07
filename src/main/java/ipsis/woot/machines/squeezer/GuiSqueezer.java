package ipsis.woot.machines.squeezer;

import ipsis.woot.Woot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiSqueezer extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 177;

    private static final ResourceLocation background = new ResourceLocation(Woot.MODID, "textures/gui/squeezer.png");
    private TileEntitySqueezer squeezer;


    public GuiSqueezer(TileEntitySqueezer tileEntitySqueezer, ContainerSqueezer containerSqueezer) {
        super(containerSqueezer);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.squeezer = tileEntitySqueezer;
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        // @todo is Color from awt safe [forge 5591]
        drawRect(guiLeft + 82, guiTop + 30, guiLeft + 132, guiTop + 37, Color.BLUE.getRGB());
        drawRect(guiLeft + 82, guiTop + 40, guiLeft + 132, guiTop + 47, Color.RED.getRGB());
        drawRect(guiLeft + 82, guiTop + 50, guiLeft + 132, guiTop + 57, Color.YELLOW.getRGB());
        drawRect(guiLeft + 82, guiTop + 60, guiLeft + 132, guiTop + 67, Color.WHITE.getRGB());
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.squeezer.getName().getString();
        this.fontRenderer.drawString(s, (float)(this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6.0F, 4210752);
    }
}
