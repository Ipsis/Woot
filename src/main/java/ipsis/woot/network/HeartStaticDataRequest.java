package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {

        });
        ctx.setPacketHandled(true);
    }

    @Override
    public String toString() {
        return pos.toString();
    }
}
