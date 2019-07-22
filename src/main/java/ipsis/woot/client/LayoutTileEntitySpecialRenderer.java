package ipsis.woot.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.common.WootConfig;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.blocks.LayoutTileEntity;
import ipsis.woot.factory.layout.PatternBlock;
import ipsis.woot.util.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class LayoutTileEntitySpecialRenderer extends TileEntityRenderer<LayoutTileEntity> {

    @Override
    public boolean isGlobalRenderer(LayoutTileEntity te) {
        // Force the render even when the chunk is out of view
        return true;
    }

    @Override
    public void render(LayoutTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {

        if (tileEntityIn.getAbsolutePattern() == null)
            tileEntityIn.refresh();

        textureRender(tileEntityIn, x, y, z, partialTicks, destroyStage);
    }

    TextureAtlasSprite getTextureAtlasSprite(FactoryComponent component) {
        return Minecraft.getInstance().getTextureMap().getSprite(
                new ResourceLocation(Woot.MODID, "block/" + component.getName()));
    }

    void textureRender(LayoutTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {

        boolean showAll = tileEntityIn.getLevel() == -1;
        int validY = showAll ? 0 : tileEntityIn.getYForLevel();

        // TODO DONT USE pushLightingAttrib or pushMatrix Forge 1637
        GlStateManager.pushLightingAttributes();
        {
            GlStateManager.pushMatrix();
            setLightmapDisabled(true);
            {
                GlStateManager.translated(x + 0.5F, y + 0.5F, z + 0.5F);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.color4f(1F, 1F, 1F, WootConfig.CLIENT.layoutOpacity.get().floatValue());

                BlockPos origin = tileEntityIn.getPos();
                for (PatternBlock block : tileEntityIn.getAbsolutePattern().getBlocks()) {

                    if (!showAll && block.getBlockPos().getY() != validY)
                        continue;

                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translated(
                                (origin.getX() - block.getBlockPos().getX()) * -1.0F,
                                (origin.getY() - block.getBlockPos().getY()) * -1.0F,
                                (origin.getZ() - block.getBlockPos().getZ()) * -1.0F);

                        TextureAtlasSprite textureAtlasSprite = getTextureAtlasSprite(block.getFactoryComponent());
                        if (textureAtlasSprite != null)
                            RenderHelper.drawTexturedCube(textureAtlasSprite, WootConfig.CLIENT.layoutSize.get().floatValue());
                    }
                    GlStateManager.popMatrix();
                }

                GlStateManager.disableBlend();
            }
            setLightmapDisabled(false);
            GlStateManager.popMatrix();
        }
        GlStateManager.popAttributes();
    }
}
