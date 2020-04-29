package ipsis.woot.modules.layout.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.blocks.HeartBlock;
import ipsis.woot.modules.layout.LayoutConfiguration;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.modules.factory.layout.PatternBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
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
    public void render(LayoutTileEntity layoutTileEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if (layoutTileEntity.getAbsolutePattern() == null)
            layoutTileEntity.refresh();

        textureRender(layoutTileEntity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    void textureRender(LayoutTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        boolean showAll = tileEntityIn.getLevel() == -1;
        int validY = showAll ? 0 : tileEntityIn.getYForLevel();
        BlockPos origin = tileEntityIn.getPos();

        matrixStack.push();
        {
            matrixStack.translate(0.0F, 0.0F, 0.0F);
            for (PatternBlock block : tileEntityIn.getAbsolutePattern().getBlocks()) {
                if (!showAll && block.getBlockPos().getY() != validY)
                    continue;

                matrixStack.push();
                {
                    matrixStack.translate(
                            (origin.getX() - block.getBlockPos().getX()) * -1.0F,
                            (origin.getY() - block.getBlockPos().getY()) * -1.0F,
                            (origin.getZ() - block.getBlockPos().getZ()) * -1.0F);


                    BlockState blockState = block.getFactoryComponent().getDefaultBlockState();
                    if (block.getFactoryComponent() == FactoryComponent.HEART)
                        blockState = FactorySetup.HEART_BLOCK.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING,  tileEntityIn.getWorld().getBlockState(tileEntityIn.getPos()).get(BlockStateProperties.HORIZONTAL_FACING));

                    Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(blockState,
                            matrixStack, bufferIn, 0x00f000f0, combinedOverlayIn, EmptyModelData.INSTANCE);
                }
                matrixStack.pop();;
            }
        }
        matrixStack.pop();
    }
}
