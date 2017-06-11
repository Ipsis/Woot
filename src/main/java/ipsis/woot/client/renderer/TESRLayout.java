package ipsis.woot.client.renderer;

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
}
