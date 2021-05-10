package ipsis.woot.modules.factory.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import ipsis.woot.Woot;
import ipsis.woot.modules.factory.ComponentType;
import ipsis.woot.modules.factory.FactoryModule;
import ipsis.woot.modules.factory.FactoryTier;
import ipsis.woot.modules.factory.blocks.LayoutTileEntity;
import ipsis.woot.modules.factory.layout.PatternBlockInfo;
import ipsis.woot.modules.factory.layout.PatternLibrary;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;

@OnlyIn(Dist.CLIENT)
public class LayoutSpecialRenderer extends TileEntityRenderer<LayoutTileEntity> {

    public LayoutSpecialRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public boolean shouldRenderOffScreen(LayoutTileEntity te) {
        return true;
    }

    @Override
    public void render(LayoutTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int combinedLight, int combinedOverlay) {

        // Render something
        if (te.getLevel() == null)
            return;

        BlockState blockState = te.getLevel().getBlockState(te.getBlockPos());
        if (blockState.getBlock() != FactoryModule.LAYOUT.get())
            return;

        /**
         * The layout block sits directly in front of the heart
         */
        Direction facing = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockPos origin = te.getBlockPos();

        PatternLibrary.Pattern pattern = PatternLibrary.get().get(te.getFactoryTier());
        if (pattern == null)
            return;

        int displayY = te.getDisplayLevel();
        if (displayY != LayoutTileEntity.DISPLAY_ALL)
            displayY = origin.getY() + displayY;

        float minX = 0.0F, minY = 0.0F, minZ = 0.0F;
        float maxX = 0.0F, maxY = 0.0F, maxZ = 0.0F;

        matrixStack.pushPose();
        {
            matrixStack.translate(0.0F, 0.0F, 0.0F);
            for (PatternBlockInfo block : pattern.getBlocks())  {
                matrixStack.pushPose();
                {
                    BlockPos pos = origin.offset(BlockPosHelper.rotateFromSouth(block.offset, facing)).relative(facing.getOpposite(), 2);
                    float x = (origin.getX() - pos.getX()) * -1.0F;
                    float y = (origin.getY() - pos.getY()) * -1.0F;
                    float z = (origin.getZ() - pos.getZ()) * -1.0F;

                    if (displayY == LayoutTileEntity.DISPLAY_ALL || displayY == pos.getY()) {
                        matrixStack.translate(x, y, z);
                        BlockState state = ComponentType.getDefaultBlockState(block.componentType);
                        if (block.componentType == ComponentType.HEART)
                            state = FactoryModule.HEART.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
                        Minecraft.getInstance().getBlockRenderer().renderBlock(state, matrixStack, renderTypeBuffer, 0x00f000f0, combinedOverlay, EmptyModelData.INSTANCE);
                    }

                    minX = x < minX ? x : minX;
                    minY = y < minY ? y : minY;
                    minZ = z < minZ ? z : minZ;
                    maxX = x > maxX ? x : maxX;
                    maxY = y > maxY ? y : maxY;
                    maxZ = z > maxZ ? z : maxZ;
                }
                matrixStack.popPose();
            }
        }
        matrixStack.popPose();

        matrixStack.pushPose();
        {
            maxX += 1.0F;
            maxY += 1.0F;
            maxZ += 1.0F;
            matrixStack.translate(0.0F, 0.0F, 0.0F);
            IVertexBuilder iVertexBuilder = renderTypeBuffer.getBuffer(RenderType.LINES);
            WorldRenderer.renderLineBox(matrixStack, iVertexBuilder,
                    minX, minY, minZ, maxX, maxY, maxZ,
                    0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
        }
        matrixStack.popPose();
    }
}
