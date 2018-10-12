package ipsis.woot.client;

import ipsis.woot.blocks.TileEntityLayout;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.RenderUtils;
import ipsis.woot.util.WootColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class TESRLayout extends TileEntitySpecialRenderer<TileEntityLayout> {

    @Override
    public boolean isGlobalRenderer(TileEntityLayout te) {
        // Force the render even when the chunk is out of view;
        return true;
    }

    @Override
    public void render(TileEntityLayout te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        if (te.getAbsolutePattern() == null)
            te.refreshLayout();

        //simpleRender(te, x, y, z, partialTicks, destroyStage, alpha);
        textureRender(te, x, y, z, partialTicks, destroyStage, alpha);
    }

    private TextureAtlasSprite getTexture(FactoryBlock factoryBlock) {

        TextureAtlasSprite sprite = null;
        switch (factoryBlock) {
            case BONE: sprite = WootTextures.BASE_BONE; break;
            case FLESH: sprite = WootTextures.BASE_FLESH; break;
            case BLAZE: sprite = WootTextures.BASE_BLAZE; break;
            case ENDER: sprite = WootTextures.BASE_ENDER; break;
            case NETHER: sprite = WootTextures.BASE_NETHER; break;
            case REDSTONE: sprite = WootTextures.BASE_REDSTONE; break;
            case UPGRADE: sprite = WootTextures.BASE_UPGRADE; break;
            case CAP_1: sprite = WootTextures.CAP_1; break;
            case CAP_2: sprite = WootTextures.CAP_2; break;
            case CAP_3: sprite = WootTextures.CAP_3; break;
            case CAP_4: sprite = WootTextures.CAP_4; break;
            case HEART: sprite = WootTextures.HEART; break;
            case CONTROLLER: sprite = WootTextures.CONTROLLER; break;
            case POWER_1: sprite = WootTextures.POWER_1; break;
            case POWER_2: sprite = WootTextures.POWER_2; break;
            case POWER_3: sprite = WootTextures.POWER_3; break;
            case IMPORT: sprite = WootTextures.IMPORT; break;
            case EXPORT: sprite = WootTextures.EXPORT; break;
            default: break;
        }

        return sprite;
    }

    private void textureRender(TileEntityLayout layout, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1F, 1F, 1F, 0.9500F);

            for (AbsolutePattern.AbsoluteBlock block : layout.getAbsolutePattern().getBlocks()) {

                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(
                            (layout.getPos().getX() - block.getPos().getX()) * -1.0F,
                            (layout.getPos().getY() - block.getPos().getY()) * -1.0F,
                            (layout.getPos().getZ() - block.getPos().getZ()) * -1.0F);

                    TextureAtlasSprite texture = getTexture(block.getFactoryBlock());
                    if (texture != null)
                        RenderUtils.drawTexturedCube(texture, 0.3F);
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

    private void simpleRender(TileEntityLayout layout, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

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

            for (AbsolutePattern.AbsoluteBlock block : layout.getAbsolutePattern().getBlocks()) {

                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(
                            (layout.getPos().getX() - block.getPos().getX()) * -1.0F,
                            (layout.getPos().getY() - block.getPos().getY()) * -1.0F,
                            (layout.getPos().getZ() - block.getPos().getZ()) * -1.0F);

                    WootColor color = block.getFactoryBlock().getColor();
                    GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), RENDER_ALPHA);
                    RenderUtils.drawShadedCube(0.3F);
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
