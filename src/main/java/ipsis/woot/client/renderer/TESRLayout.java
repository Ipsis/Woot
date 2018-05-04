package ipsis.woot.client.renderer;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.event.HandlerTextureStitchEvent;
import ipsis.woot.tileentity.*;
import ipsis.woot.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class TESRLayout extends TileEntitySpecialRenderer<TileEntityLayout>{

    private static float SIZE = 0.3F;

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

                boolean showAll = te.getCurrLevel() == -1;
                int validY = showAll ? 0 : te.getPos().getY() + 1 + te.getCurrLevel();

                for (ILayoutBlockInfo pos : te.getLayoutBlockInfoList()) {

                    if (getWorld().getBlockState(pos.getPos()).isOpaqueCube())
                        continue;

                    if (!showAll && pos.getPos().getY() != validY)
                        continue;

                    if (pos instanceof StructureLayoutBlockInfo) {
                        GlStateManager.pushMatrix();
                        {
                            GlStateManager.translate(
                                    (te.getPos().getX() - pos.getPos().getX()) * -1.0F,
                                    (te.getPos().getY() - pos.getPos().getY()) * -1.0F,
                                    (te.getPos().getZ() - pos.getPos().getZ()) * -1.0F);

                            TextureAtlasSprite texture = null;

                            switch (((StructureLayoutBlockInfo) pos).module) {
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

                            RenderUtils.drawTexturedCube(texture, SIZE);
                        }
                        GlStateManager.popMatrix();
                    } else if (pos instanceof HeartLayoutBlockInfo) {
                        GlStateManager.pushMatrix();
                        {
                            GlStateManager.translate(
                                    (te.getPos().getX() - pos.getPos().getX()) * -1.0F,
                                    (te.getPos().getY() - pos.getPos().getY()) * -1.0F,
                                    (te.getPos().getZ() - pos.getPos().getZ()) * -1.0F);
                            RenderUtils.drawTexturedCube(HandlerTextureStitchEvent.factory, 0.4F);
                        }
                        GlStateManager.popMatrix();
                    } else if (pos instanceof ControllerLayoutBlockInfo) {
                        GlStateManager.pushMatrix();
                        {
                            GlStateManager.translate(
                                    (te.getPos().getX() - pos.getPos().getX()) * -1.0F,
                                    (te.getPos().getY() - pos.getPos().getY()) * -1.0F,
                                    (te.getPos().getZ() - pos.getPos().getZ()) * -1.0F);
                            RenderUtils.drawTexturedCube(HandlerTextureStitchEvent.controller, 0.4F);
                        }
                        GlStateManager.popMatrix();
                    }
                }
                GlStateManager.disableBlend();
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();

    }

    private void coloredLayout(TileEntityLayout te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

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

            for (ILayoutBlockInfo pos : te.getLayoutBlockInfoList()) {

                if (pos instanceof StructureLayoutBlockInfo) {

                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(
                                (te.getPos().getX() - pos.getPos().getX()) * -1.0F,
                                (te.getPos().getY() - pos.getPos().getY()) * -1.0F,
                                (te.getPos().getZ() - pos.getPos().getZ()) * -1.0F);

                        GlStateManager.color(
                                ((StructureLayoutBlockInfo) pos).module.getColor().getRed(),
                                ((StructureLayoutBlockInfo) pos).module.getColor().getGreen(),
                                ((StructureLayoutBlockInfo) pos).module.getColor().getBlue(), RENDER_ALPHA);
                        RenderUtils.drawShadedCube(SIZE);
                    }
                    GlStateManager.popMatrix();
                } else if (pos instanceof HeartLayoutBlockInfo) {
                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(
                                (te.getPos().getX() - pos.getPos().getX()) * -1.0F,
                                (te.getPos().getY() - pos.getPos().getY()) * -1.0F,
                                (te.getPos().getZ() - pos.getPos().getZ()) * -1.0F);
                        GlStateManager.color(0.0F, 1.0F, 1.0F, RENDER_ALPHA);
                        RenderUtils.drawShadedCube(SIZE);
                    }
                    GlStateManager.popMatrix();
                } else if (pos instanceof ControllerLayoutBlockInfo) {
                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(
                                (te.getPos().getX() - pos.getPos().getX()) * -1.0F,
                                (te.getPos().getY() - pos.getPos().getY()) * -1.0F,
                                (te.getPos().getZ() - pos.getPos().getZ()) * -1.0F);
                        GlStateManager.color(0.0F, 1.0F, 0.0F, RENDER_ALPHA);
                        RenderUtils.drawShadedCube(SIZE);
                    }
                    GlStateManager.popMatrix();
                }
            }

            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();

    }
}
