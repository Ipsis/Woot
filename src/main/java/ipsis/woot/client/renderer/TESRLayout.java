package ipsis.woot.client.renderer;

import com.sun.org.apache.regexp.internal.RE;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.LayoutBlockInfo;
import ipsis.woot.tileentity.TileEntityLayout;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
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
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {

        if (!(te instanceof TileEntityLayout))
            return;

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

            float RENDER_ALPHA = 1.0F;

            for (LayoutBlockInfo pos : tileEntityLayout.getLayoutBlockInfoList()) {

                GlStateManager.pushMatrix();
                {

                    GlStateManager.translate(
                            (te.getPos().getX() - pos.blockPos.getX()) * -1.0F,
                            (te.getPos().getY() - pos.blockPos.getY()) * -1.0F,
                            (te.getPos().getZ() - pos.blockPos.getZ()) * -1.0F);

                    WootColor color = WootColor.WHITE;

                    if (pos.module == EnumMobFactoryModule.BLOCK_1)
                      color = WootColor.GRAY;
                    else if (pos.module == EnumMobFactoryModule.BLOCK_2)
                      color = WootColor.RED;
                    else if (pos.module == EnumMobFactoryModule.BLOCK_3)
                      color = WootColor.GREEN;
                    else if (pos.module == EnumMobFactoryModule.BLOCK_4)
                      color = WootColor.PURPLE;
                    else if (pos.module == EnumMobFactoryModule.CAP_I)
                      color = WootColor.LIGHTGRAY;
                    else if (pos.module == EnumMobFactoryModule.CAP_II)
                      color = WootColor.YELLOW;
                    else if (pos.module == EnumMobFactoryModule.CAP_III)
                      color = WootColor.CYAN;

                    GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), RENDER_ALPHA);


                    float size = 0.3F;
                    Tessellator tessellator = Tessellator.getInstance();
                    VertexBuffer worldRenderer = tessellator.getBuffer();

                    // Front - anticlockwise vertices
                    // Back - clockwise vertices
                    worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

                    // xy anti-clockwise - front
                    worldRenderer.pos(-size, -size, size).endVertex();
                    worldRenderer.pos(size, -size, size).endVertex();
                    worldRenderer.pos(size, size, size).endVertex();
                    worldRenderer.pos(-size, size, size).endVertex();

                    // xy clockwise - back
                    worldRenderer.pos(-size, -size, -size).endVertex();
                    worldRenderer.pos(-size, size, -size).endVertex();
                    worldRenderer.pos(size, size, -size).endVertex();
                    worldRenderer.pos(size, -size, -size).endVertex();

                    // anti-clockwise - left
                    worldRenderer.pos(-size, -size, -size).endVertex();
                    worldRenderer.pos(-size, -size, size).endVertex();
                    worldRenderer.pos(-size, size, size).endVertex();
                    worldRenderer.pos(-size, size, -size).endVertex();

                    // clockwise - right
                    worldRenderer.pos(size, -size, -size).endVertex();
                    worldRenderer.pos(size, size, -size).endVertex();
                    worldRenderer.pos(size, size, size).endVertex();
                    worldRenderer.pos(size, -size, size).endVertex();

                    // anticlockwise - top
                    worldRenderer.pos(-size, size, -size).endVertex();
                    worldRenderer.pos(-size, size, size).endVertex();
                    worldRenderer.pos(size, size, size).endVertex();
                    worldRenderer.pos(size, size, -size).endVertex();

                    // clockwise - bottom
                    worldRenderer.pos(-size, -size, -size).endVertex();
                    worldRenderer.pos(size, -size, -size).endVertex();
                    worldRenderer.pos(size, -size, size).endVertex();
                    worldRenderer.pos(-size, -size, size).endVertex();

                    tessellator.draw();

                }
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
