package ipsis.woot.modules.factory.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.setup.NetworkChannel;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Client -> Server
 */
public class HeartStaticDataRequest {

    public BlockPos pos;
    public HeartStaticDataRequest() { }

    public HeartStaticDataRequest(BlockPos pos) {
        this.pos = new BlockPos(pos);
    }

    public static HeartStaticDataRequest fromBytes(ByteBuf buf) {
        return new HeartStaticDataRequest(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }

    public void toByte(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getSender() != null) {
            ctx.get().enqueueWork(() -> {
                TileEntity te = ctx.get().getSender().world.getTileEntity(pos);
                /*
                if (te instanceof HeartTileEntity) {
                    NetworkChannel.channel.sendTo(new HeartStaticDataReply(((HeartTileEntity) te).createFactoryUIInfo()),
                            ctx.get().getSender().connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                    ctx.get().setPacketHandled(true);
                } */
            });
        }
    }

    @Override
    public String toString() {
        return pos.toString();
    }
}
