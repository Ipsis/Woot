package ipsis.woot.modules.factory.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.factory.blocks.HeartContainer;
import ipsis.woot.modules.factory.recipe.HeartSummary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HeartStaticDataReply2 {

    public HeartSummary heartSummary;

    public HeartStaticDataReply2() {}
    public HeartStaticDataReply2(HeartSummary heartSummary) {
        this.heartSummary = heartSummary;
    }

    public static HeartStaticDataReply2 fromBytes(ByteBuf byteBuf) {
        HeartStaticDataReply2 pkt = new HeartStaticDataReply2();
        pkt.heartSummary = HeartSummary.fromBytes(byteBuf);
        return pkt;
    }

    public void toBytes(ByteBuf byteBuf) {
        heartSummary.toBytes(byteBuf);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            final ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player.openContainer instanceof HeartContainer)
                ((HeartContainer) player.openContainer).handleStaticDataReply(heartSummary);
            ctx.get().setPacketHandled(true);
        })) ;
    }
}
