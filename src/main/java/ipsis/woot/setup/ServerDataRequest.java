package ipsis.woot.setup;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.oracle.blocks.OracleTileEntity;
import ipsis.woot.modules.oracle.network.SimulatedMobDropsSummaryReply;
import ipsis.woot.modules.oracle.network.SimulatedMobsReply;
import ipsis.woot.util.oss.NetworkTools;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity serverPlayerEntity = ctx.get().getSender();
        if (serverPlayerEntity != null) {
            ctx.get().enqueueWork(() -> {
                TileEntity te = serverPlayerEntity.world.getTileEntity(pos);
                if (requestType == Type.DROP_REGISTRY_STATUS) {
                    if (te instanceof OracleTileEntity) {
                        NetworkChannel.channel.sendTo(new SimulatedMobsReply(),
                                serverPlayerEntity.connection.netManager,
                                NetworkDirection.PLAY_TO_CLIENT);
                        ctx.get().setPacketHandled(true);
                    }
                } else if (requestType == Type.SIMULATED_MOB_DROPS) {
                    if (te instanceof OracleTileEntity) {
                        NetworkChannel.channel.sendTo(SimulatedMobDropsSummaryReply.fromMob(s),
                                serverPlayerEntity.connection.netManager,
                                NetworkDirection.PLAY_TO_CLIENT);
                        ctx.get().setPacketHandled(true);
                    }
                } else if (requestType == Type.HEART_STATIC_DATA) {
                    if (te instanceof HeartTileEntity) {
                        NetworkChannel.channel.sendTo((((HeartTileEntity) te).createStaticDataReply2()),
                                serverPlayerEntity.connection.netManager,
                                NetworkDirection.PLAY_TO_CLIENT);
                        ctx.get().setPacketHandled(true);
                    }
                }
            });
        }
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
