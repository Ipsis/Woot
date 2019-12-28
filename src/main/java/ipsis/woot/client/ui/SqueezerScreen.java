package ipsis.woot.client.ui;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.misc.squeezer.SqueezerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SqueezerScreen extends ContainerScreen<SqueezerContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/squeezer.png");

    public SqueezerScreen(SqueezerContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        xSize = 256;
        ySize = 256;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if (mouseX > guiLeft + 82 && mouseX < guiLeft + 132 && mouseY > guiTop + 30 && mouseY < guiTop + 37)
            renderTooltip("Red: " + container.getTileEntity().getRed(), mouseX, mouseY);
        if (mouseX > guiLeft + 82 && mouseX < guiLeft + 132 && mouseY > guiTop + 40 && mouseY < guiTop + 47)
            renderTooltip("Yellow: " + container.getTileEntity().getYellow(), mouseX, mouseY);
        if (mouseX > guiLeft + 82 && mouseX < guiLeft + 132 && mouseY > guiTop + 50 && mouseY < guiTop + 57)
            renderTooltip("Blue: " + container.getTileEntity().getBlue(), mouseX, mouseY);
        if (mouseX > guiLeft + 82 && mouseX < guiLeft + 132 && mouseY > guiTop + 60 && mouseY < guiTop + 67)
            renderTooltip("White: " + container.getTileEntity().getWhite(), mouseX, mouseY);
        if (mouseX > guiLeft + 154 && mouseX < guiLeft + 169 && mouseY > guiTop + 18 && mouseY < guiTop + 77)
            renderTooltip("Pure: " + container.getTileEntity().getPure(), mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize);

        drawHorizontalBar(
                82, 30, 132, 37,
                72 * 100, container.getTileEntity().getRed(), 0xffff1641);
        drawHorizontalBar(
                82, 40, 132, 47,
                72 * 100, container.getTileEntity().getYellow(), 0xffffd800);
        drawHorizontalBar(
                82, 50, 132, 57,
                72 * 100, container.getTileEntity().getBlue(), 0xff0094ff);
        drawHorizontalBar(
                82, 60, 132, 67,
                72 * 100, container.getTileEntity().getWhite(), 0xffffffff);
    }

    private void drawHorizontalBar(int tlx, int tly, int brx, int bry, int max, int v, int color) {
        fill(guiLeft + tlx, guiTop + tly, guiLeft + brx, guiTop + bry, color);
        int p = v * (brx - tlx) / max;
        for (int i = 0; i < p; i++)
            vLine(guiLeft + tlx + 1 + i,
                    guiTop + tly,
                    guiTop + tly + 6,
                    i % 2 == 0 ? color : 0xff000000);
    }
}
