package ipsis.woot.machines.squeezer;

import ipsis.woot.Woot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiSqueezer extends GuiContainer {

    public static final int WIDTH = 174;
    public static final int HEIGHT = 177;

    private static final ResourceLocation background = new ResourceLocation(Woot.MODID, "textures/gui/squeezer.png");
    private TileEntitySqueezer squeezer;


    public GuiSqueezer(TileEntitySqueezer tileEntitySqueezer, ContainerSqueezer containerSqueezer) {
        super(containerSqueezer);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.squeezer = tileEntitySqueezer;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
