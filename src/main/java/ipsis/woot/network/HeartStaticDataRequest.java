package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

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

    @Override
    public String toString() {
        return pos.toString();
    }
}
