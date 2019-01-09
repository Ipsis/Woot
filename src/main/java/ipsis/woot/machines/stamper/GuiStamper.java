package ipsis.woot.machines.stamper;

import ipsis.Woot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiStamper extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation(Woot.MODID, "textures/gui/stamper.png");

    public static final int WIDTH = 174;
    public static final int HEIGHT = 177;

    private TileEntityStamper te;
    public GuiStamper(TileEntityStamper te, ContainerStamper containerStamper) {
        super(containerStamper);

        xSize = WIDTH;
        ySize = HEIGHT;

        this.te = te;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {

        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
