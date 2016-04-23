package ipsis.woot.client.renderer;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.LayoutBlockInfo;
import ipsis.woot.tileentity.TileEntityLayout;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
import ipsis.woot.util.RenderUtils;
import ipsis.woot.util.WootColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TESRLayout extends TileEntitySpecialRenderer{

    @Override
    public boolean isGlobalRenderer(TileEntity te) {

        // Force the render even when the chunk is out of view
        return true;
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {

        if (!(te instanceof TileEntityLayout))
            return;

        if (((TileEntityLayout) te).getLayoutBlockInfoList().isEmpty())
            ((TileEntityLayout) te).refreshLayout();

        TileEntityLayout tileEntityLayout = (TileEntityLayout)te;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();

            if (Minecraft.isAmbientOcclusionEnabled())
                GlStateManager.shadeModel(GL11.GL_SMOOTH);
            else
                GlStateManager.shadeModel(GL11.GL_FLAT);

            float RENDER_ALPHA = 0.7F;

            for (LayoutBlockInfo pos : tileEntityLayout.getLayoutBlockInfoList()) {

                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(
                            (te.getPos().getX() - pos.blockPos.getX()) * -1.0F,
                            (te.getPos().getY() - pos.blockPos.getY()) * -1.0F,
                            (te.getPos().getZ() - pos.blockPos.getZ()) * -1.0F);

                    GlStateManager.color(pos.module.getColor().getRed(), pos.module.getColor().getGreen(), pos.module.getColor().getBlue(), RENDER_ALPHA);
                    RenderUtils.drawShadedCube(0.4F);
                }
                GlStateManager.popMatrix();
            }

            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
