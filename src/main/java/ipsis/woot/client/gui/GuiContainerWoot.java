package ipsis.woot.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainerWoot extends GuiContainer {

    public GuiContainerWoot(Container container) {
        super(container);
    }

    public void drawSizedModalRect(int x1, int y1, int x2, int y2, int color) {

        int temp;

        if (x1 < x2) {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2) {
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(r, g, b, a);

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        buffer.pos(x1, y2, this.zLevel).endVertex();
        buffer.pos(x2, y2, this.zLevel).endVertex();
        buffer.pos(x2, y1, this.zLevel).endVertex();
        buffer.pos(x1, y1, this.zLevel).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void drawItemStack(ItemStack stack, int x, int y, String overlayTxt) {

        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;

        FontRenderer font = null;
        if (!stack.isEmpty()) {
            font = stack.getItem().getFontRenderer(stack);
        }
        if (font == null) {
            font = fontRenderer;
        }

        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y - 8, overlayTxt);

        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
        GlStateManager.popMatrix();
        GlStateManager.disableLighting();
    }
}
