package ipsis.woot.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import ipsis.woot.Woot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderLastWorldEventHandler {

    @SubscribeEvent
    public static void handleRenderLastWorldEvent(RenderWorldLastEvent e) {

        List<BlockPos> blocks = Woot.instance.getClientInfo().getHighlightedBlocks();
        if (blocks.isEmpty())
            return;

        Minecraft minecraft = Minecraft.getInstance();
        long time = System.currentTimeMillis();

        if (time > Woot.instance.getClientInfo().getExpireTicks()) {
            Woot.instance.getClientInfo().clearHighlightedBlocks();
            return;
        }

        if (((time / 500) & 1) == 0)
            return;

        for (BlockPos pos : blocks) {
            MatrixStack matrixStack = e.getMatrixStack();
            IRenderTypeBuffer.Impl buffer = minecraft.renderBuffers().bufferSource();
            IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);

            matrixStack.pushPose();
            {
                Vector3d projectedView = minecraft.gameRenderer.getMainCamera().getPosition();
                matrixStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);

                WorldRenderer.renderLineBox(matrixStack, builder,
                        pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                                1.0F, 0.0F, 0.0F, 0.5F);
            }
            matrixStack.popPose();
            RenderSystem.disableDepthTest();
            buffer.endBatch(RenderType.LINES);
        }

    }
}