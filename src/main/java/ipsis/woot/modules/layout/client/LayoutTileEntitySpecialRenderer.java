package ipsis.woot.modules.layout.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.modules.factory.layout.PatternBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;

@OnlyIn(Dist.CLIENT)
public class LayoutTileEntitySpecialRenderer extends TileEntityRenderer<LayoutTileEntity> {

    public LayoutTileEntitySpecialRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public boolean shouldRenderOffScreen(LayoutTileEntity te) {
        // Force the render even when the chunk is out of view
        return true;
    }

    @Override
    public void render(LayoutTileEntity layoutTileEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        World world = layoutTileEntity.getLevel();
        if (world != null) {
            if (layoutTileEntity.getAbsolutePattern() == null)
                layoutTileEntity.refresh();

            textureRender(layoutTileEntity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }

    void textureRender(LayoutTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        boolean showAll = tileEntityIn.getDisplayLevel() == -1;
        int validY = showAll ? 0 : tileEntityIn.getYForLevel();
        BlockPos origin = tileEntityIn.getBlockPos();
        Direction facing = Direction.SOUTH;

        // Watch for this being called after the block is broken
        // Ensure that we still have a Layout Block at the position to extract the facing from
        Block layoutBlock = tileEntityIn.getLevel().getBlockState(origin).getBlock();
        if (layoutBlock == LayoutSetup.LAYOUT_BLOCK.get())
            facing = tileEntityIn.getLevel().getBlockState(origin).getValue(BlockStateProperties.HORIZONTAL_FACING);

        float minX = 0.0F, minY = 0.0F, minZ = 0.0F;
        float maxX = 0.0F, maxY = 0.0F, maxZ = 0.0F;
        matrixStack.pushPose();
        {
            matrixStack.translate(0.0F, 0.0F, 0.0F);
            for (PatternBlock block : tileEntityIn.getAbsolutePattern().getBlocks()) {
                if (!showAll && block.getBlockPos().getY() != validY)
                    continue;

                matrixStack.pushPose();
                {
                    float x = (origin.getX() - block.getBlockPos().getX()) * -1.0F;
                    float y = (origin.getY() - block.getBlockPos().getY()) * -1.0F;
                    float z = (origin.getZ() - block.getBlockPos().getZ()) * -1.0F;
                    matrixStack.translate(x, y, z);
                    minX = x < minX ? x : minX;
                    minY = y < minY ? y : minY;
                    minZ = z < minZ ? z : minZ;
                    maxX = x > maxX ? x : maxX;
                    maxY = y > maxY ? y : maxY;
                    maxZ = z > maxZ ? z : maxZ;

                    BlockState blockState = block.getFactoryComponent().getDefaultBlockState();
                    if (block.getFactoryComponent() == FactoryComponent.HEART)
                        blockState = FactorySetup.HEART_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);

                    Minecraft.getInstance().getBlockRenderer().renderBlock(blockState,
                            matrixStack, bufferIn, 0x00f000f0, combinedOverlayIn, EmptyModelData.INSTANCE);
                }
                matrixStack.popPose();;
            }
        }
        matrixStack.popPose();
        matrixStack.pushPose();
        {
            maxX += 1.0F;
            maxY += 1.0F;
            maxZ += 1.0F;
            matrixStack.translate(0.0F, 0.0F, 0.0F);
            IVertexBuilder iVertexBuilder = bufferIn.getBuffer(RenderType.LINES);
            WorldRenderer.renderLineBox(matrixStack, iVertexBuilder,
                minX, minY, minZ, maxX, maxY, maxZ,
               0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
        }
        matrixStack.popPose();
    }
}
