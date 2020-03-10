package ipsis.woot.modules.layout.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.layout.LayoutConfiguration;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.modules.factory.layout.PatternBlock;
import ipsis.woot.util.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class LayoutTileEntitySpecialRenderer extends TileEntityRenderer<LayoutTileEntity> {

    public LayoutTileEntitySpecialRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public boolean isGlobalRenderer(LayoutTileEntity te) {
        // Force the render even when the chunk is out of view
        return true;
    }

    @Override
    public void render(LayoutTileEntity layoutTileEntity, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {

        if (layoutTileEntity.getAbsolutePattern() == null)
            layoutTileEntity.refresh();

        textureRender(layoutTileEntity, matrixStack);
    }

    TextureAtlasSprite getTextureAtlasSprite(FactoryComponent component) {
        String path = "block/" + component.getName();
        if (component == FactoryComponent.CELL)
            path += "_1";
        return Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation(Woot.MODID, path));
    }

    void textureRender(LayoutTileEntity tileEntityIn, MatrixStack matrixStack) {

        boolean showAll = tileEntityIn.getLevel() == -1;
        int validY = showAll ? 0 : tileEntityIn.getYForLevel();
        BlockPos origin = tileEntityIn.getPos();

        matrixStack.push();
        {
            matrixStack.translate(0.5F, 0.5F, 0.5F);
            for (PatternBlock block : tileEntityIn.getAbsolutePattern().getBlocks()) {
                if (!showAll && block.getBlockPos().getY() != validY)
                    continue;

                matrixStack.push();
                {
                    matrixStack.translate(
                            (origin.getX() - block.getBlockPos().getX()) * -1.0F,
                            (origin.getY() - block.getBlockPos().getY()) * -1.0F,
                            (origin.getZ() - block.getBlockPos().getZ()) * -1.0F);

                    TextureAtlasSprite textureAtlasSprite = getTextureAtlasSprite(block.getFactoryComponent());
                    if (textureAtlasSprite != null) {
                        RenderHelper.drawTexturedCube(textureAtlasSprite, LayoutConfiguration.RENDER_SIZE.get().floatValue());
                        RenderHelper.drawShadedCube(LayoutConfiguration.RENDER_SIZE.get().floatValue());
                    }
                }
                matrixStack.pop();;
            }
        }
        matrixStack.pop();
        /*
        GlStateManager.pushLightingAttributes();
        {
            GlStateManager.pushMatrix();
            //setLightmapDisabled(true);
            {
                GlStateManager.translated(0.5F, 0.5F, 0.5F);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.color4f(1F, 1F, 1F, LayoutConfiguration.RENDER_OPACITY.get().floatValue());

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
                            RenderHelper.drawTexturedCube(textureAtlasSprite, LayoutConfiguration.RENDER_SIZE.get().floatValue());
                    }
                    GlStateManager.popMatrix();
                }

                GlStateManager.disableBlend();
            }
            //setLightmapDisabled(false);
            GlStateManager.popMatrix();
        }
        GlStateManager.popAttributes();
        matrixStack.pop(); */
    }
}
