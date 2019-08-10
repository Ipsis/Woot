package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.oss.NetworkTools;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/**
 * Client request for information
 */
public class ServerDataRequest {

    public String s;
    public BlockPos pos;
    public Type requestType;
    public ServerDataRequest(Type requestType, BlockPos pos, String s) {
        this.requestType = requestType;
        this.pos = new BlockPos(pos);
        this.s = s;
    }

    public static ServerDataRequest fromBytes(ByteBuf buf) {
        return new ServerDataRequest(Type.fromIndex(buf.readInt()),
                new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()), NetworkTools.readString(buf));
    }

    public void toByte(ByteBuf buf) {
        buf.writeInt(requestType.ordinal());
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        NetworkTools.writeString(buf, s);
    }

    @Override
    public String toString() {
        return requestType + " " + pos.toString() + "(" + s + ")";
    }

    public enum Type {
        HEART_STATIC_DATA,
        DROP_REGISTRY_STATUS,
        SIMULATED_MOB_DROPS;

        static Type[] VALUES = values();
        public static Type fromIndex(int index) {
            index = MathHelper.clamp(index, 0, VALUES.length -1);
            return VALUES[index];
        }
    }
}
