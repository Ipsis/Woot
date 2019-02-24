package ipsis.woot.layout;

import ipsis.woot.Woot;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.factory.layout.PatternBlock;
import ipsis.woot.util.WootColor;
import ipsis.woot.util.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.EnumMap;

@OnlyIn(Dist.CLIENT)
public class TileEntitySpecialRendererLayout extends TileEntityRenderer<TileEntityLayout> {

    private static EnumMap<FactoryBlock, WootColor> colors = new EnumMap<>(FactoryBlock.class);
    static {
        colors.put(FactoryBlock.BONE, WootColor.WHITE);
        colors.put(FactoryBlock.FLESH, WootColor.BROWN);
        colors.put(FactoryBlock.BLAZE, WootColor.ORANGE);
        colors.put(FactoryBlock.ENDER, WootColor.GREEN);
        colors.put(FactoryBlock.NETHER, WootColor.LIGHTGRAY);
        colors.put(FactoryBlock.REDSTONE, WootColor.RED);
        colors.put(FactoryBlock.UPGRADE, WootColor.PURPLE);
        colors.put(FactoryBlock.CAP_1, WootColor.BLUE);
        colors.put(FactoryBlock.CAP_2, WootColor.BLUE);
        colors.put(FactoryBlock.CAP_3, WootColor.BLUE);
        colors.put(FactoryBlock.CAP_4, WootColor.BLUE);
        colors.put(FactoryBlock.CONTROLLER, WootColor.PINK);
        colors.put(FactoryBlock.HEART, WootColor.BLUE);
        colors.put(FactoryBlock.CELL_1, WootColor.YELLOW);
        colors.put(FactoryBlock.CELL_2, WootColor.YELLOW);
        colors.put(FactoryBlock.CELL_3, WootColor.YELLOW);
        colors.put(FactoryBlock.IMPORT, WootColor.LIME);
        colors.put(FactoryBlock.EXPORT, WootColor.WHITE);
    }

    @Override
    public boolean isGlobalRenderer(TileEntityLayout te) {
        // Force the render even when the chunk is out of view
        return true;
    }

    @Override
    public void render(TileEntityLayout tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {

        if (tileEntityIn.getAbsolutePattern() == null)
            tileEntityIn.refresh();

        //simpleRender(tileEntityIn, x, y, z, partialTicks, destroyStage);
        textureRender(tileEntityIn, x, y, z, partialTicks, destroyStage);
    }

    private TextureAtlasSprite getTextureAtlasSprite(FactoryBlock factoryBlock) {

        return Minecraft.getInstance().getTextureMap().getSprite(new ResourceLocation(Woot.MODID, "blocks/" + factoryBlock.getName()));
    }

    private void textureRender(TileEntityLayout layout, double x, double y, double z, float partialTicks, int destroyStage) {

        // TODO DONT USE pushLightingAttrib or pushMatrix Forge 1637
        GlStateManager.pushLightingAttrib();
        {
            GlStateManager.pushMatrix();
            setLightmapDisabled(true);
            {
                GlStateManager.translated(x + 0.5F, y + 0.5F, z + 0.5F);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.color4f(1F, 1F, 1F, 0.9500F);

                BlockPos origin = layout.getPos();
                for (PatternBlock block : layout.getAbsolutePattern().getBlocks()) {
                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translated(
                                (origin.getX() - block.getBlockPos().getX()) * -1.0F,
                                (origin.getY() - block.getBlockPos().getY()) * -1.0F,
                                (origin.getZ() - block.getBlockPos().getZ()) * -1.0F);

                        TextureAtlasSprite textureAtlasSprite = getTextureAtlasSprite(block.getFactoryBlock());
                        if (textureAtlasSprite != null)
                            RenderHelper.drawTexturedCube(textureAtlasSprite, 0.3F);
                    }
                    GlStateManager.popMatrix();
                }

                GlStateManager.disableBlend();
            }
            setLightmapDisabled(false);
            GlStateManager.popMatrix();
        }
        GlStateManager.popAttrib();
    }

    private void simpleRender(TileEntityLayout layout, double x, double y, double z, float partialTicks, int destroyStage) {

        GlStateManager.pushMatrix();
        {
            GlStateManager.translated(x + 0.5F, y + 0.5F, z + 0.5F);

            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


            float RENDER_ALPHA = 1.0F;
            BlockPos origin = layout.getPos();
            for (PatternBlock block : layout.getAbsolutePattern().getBlocks()) {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translated(
                            (origin.getX() - block.getBlockPos().getX()) * -1.0F,
                            (origin.getY() - block.getBlockPos().getY()) * -1.0F,
                            (origin.getZ() - block.getBlockPos().getZ()) * -1.0F);

                    WootColor color = colors.get(block.getFactoryBlock());
                    GlStateManager.color4f(color.getRed(), color.getGreen(), color.getBlue(), RENDER_ALPHA);
                    RenderHelper.drawShadedCube(0.45F);
                }
                GlStateManager.popMatrix();
            }

            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepthTest();
            GlStateManager.depthMask(true);
        }
        GlStateManager.popMatrix();

    }
}
