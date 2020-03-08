package ipsis.woot.modules.oracle.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.oracle.blocks.OracleContainer;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.oss.NetworkTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class SimulatedMobsReply {

    public List<FakeMob> simulatedMobs = new ArrayList<>();

    // Required
    public SimulatedMobsReply() {}

    public static SimulatedMobsReply fromBytes(ByteBuf buf) {
        SimulatedMobsReply reply = new SimulatedMobsReply();
        int mobs = buf.readInt();
        for (int i = 0; i < mobs; i++)
            reply.simulatedMobs.add(new FakeMob(NetworkTools.readString(buf)));
        return reply;
    }

    public void toBytes(ByteBuf buf) {
        Set<FakeMob> mobs = MobSimulator.getInstance().getKnownMobs();
        buf.writeInt(mobs.size());
        mobs.forEach(m -> NetworkTools.writeString(buf, m.getName()));
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null) {
            ctx.get().enqueueWork(() -> {
                if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof OracleContainer) {
                    ((OracleContainer) clientPlayerEntity.openContainer).handleSimulatedMobsReply(this);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
