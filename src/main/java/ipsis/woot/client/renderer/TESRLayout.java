package ipsis.woot.client.renderer;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.event.HandlerTextureStitchEvent;
import ipsis.woot.proxy.ClientProxy;
import ipsis.woot.tileentity.LayoutBlockInfo;
import ipsis.woot.tileentity.TileEntityLayout;
import ipsis.woot.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TESRLayout extends TileEntitySpecialRenderer<TileEntityLayout>{

    @Override
    public boolean isGlobalRenderer(TileEntityLayout te) {

        // Force the render even when the chunk is out of view
        return true;
    }

    @Override
    public void render(TileEntityLayout te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        if ( te.getLayoutBlockInfoList().isEmpty())
            te.refreshLayout();

        if (Woot.wootConfiguration.getBoolean(EnumConfigKey.SIMPLE_LAYOUT))
            coloredLayout(te, x, y, z, partialTicks, destroyStage, alpha);
        else
            texturedLayout(te, x, y, z, partialTicks, destroyStage, alpha);

    }

    private void texturedLayout(TileEntityLayout te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        TileEntityLayout tileEntityLayout = (TileEntityLayout)te;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            /**
             * Render the factory block and the controller
             */
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);

                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.color(1F, 1F, 1F, 0.9500F);

                for (LayoutBlockInfo pos : tileEntityLayout.getLayoutBlockInfoList()) {

                    if (getWorld().getBlockState(pos.blockPos).isOpaqueCube())
                        continue;

                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(
                                (te.getPos().getX() - pos.blockPos.getX()) * -1.0F,
                                (te.getPos().getY() - pos.blockPos.getY()) * -1.0F,
                                (te.getPos().getZ() - pos.blockPos.getZ()) * -1.0F);

                        TextureAtlasSprite texture = null;

                        switch (pos.module) {
                            case BLOCK_1:
                                texture = HandlerTextureStitchEvent.block1;
                                break;
                            case BLOCK_2:
                                texture = HandlerTextureStitchEvent.block2;
                                break;
                            case BLOCK_3:
                                texture = HandlerTextureStitchEvent.block3;
                                break;
                            case BLOCK_4:
                                texture = HandlerTextureStitchEvent.block4;
                                break;
                            case BLOCK_5:
                                texture = HandlerTextureStitchEvent.block5;
                                break;
                            case BLOCK_UPGRADE:
                                texture = HandlerTextureStitchEvent.blockupgrade;
                                break;
                            case CAP_I:
                                texture = HandlerTextureStitchEvent.tiericap;
                                break;
                            case CAP_II:
                                texture = HandlerTextureStitchEvent.tieriicap;
                                break;
                            case CAP_III:
                                texture = HandlerTextureStitchEvent.tieriiicap;
                                break;
                            case CAP_IV:
                                texture = HandlerTextureStitchEvent.tierivcap;
                                break;
                        }

                        RenderUtils.drawTexturedCube(texture, 0.4F);
                    }
                    GlStateManager.popMatrix();
                }

                /**
                 * Factory block
                 */
                GlStateManager.translate(0, 2, 0);
                RenderUtils.drawTexturedCube(HandlerTextureStitchEvent.factory, 0.4F);

                /**
                 * Mob controller
                 */
                // TODO this needs fixed for each orientation
                GlStateManager.translate(1, 1, 0);
                RenderUtils.drawTexturedCube(HandlerTextureStitchEvent.controller, 0.4F);

                GlStateManager.disableBlend();
            }
            GlStateManager.popMatrix();

        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();

    }

    private void coloredLayout(TileEntityLayout te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

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

            /**
             * Render the factory block and the controller
             */
            GlStateManager.pushMatrix();
            {
                /**
                 * Factory block
                 */
                GlStateManager.translate(0, 2, 0);
                GlStateManager.color(0.0F, 1.0F, 1.0F, RENDER_ALPHA);
                RenderUtils.drawShadedCube(0.4F);

                /**
                 * Mob controller
                 */
                GlStateManager.translate(1, 1, 0);
                GlStateManager.color(0.0F, 1.0F, 0.0F, RENDER_ALPHA);
                RenderUtils.drawShadedCube(0.4F);
            }
            GlStateManager.popMatrix();

            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();

    }
}
