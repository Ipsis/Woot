package ipsis.woot.blocks.heart;

import ipsis.Woot;
import ipsis.woot.blocks.TileEntityHeart;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiHeart extends GuiContainer {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 224;
    private static final ResourceLocation BACKGROUND = new ResourceLocation(Woot.MODID, "textures/gui/heart.png");
    private TileEntityHeart tileEntityHeart;

    public GuiHeart(TileEntityHeart tileEntityHeart, ContainerHeart containerHeart) {
        super(containerHeart);

        xSize = WIDTH;
        ySize = HEIGHT;
        this.tileEntityHeart = tileEntityHeart;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(BACKGROUND);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
