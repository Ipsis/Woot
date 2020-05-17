package ipsis.woot.modules.oracle.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.oracle.blocks.OracleContainer;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SimulatedMobDropsSummaryReply {

    public List<SimulatedMobDropSummary> drops = new ArrayList<>();

    // Required
    public SimulatedMobDropsSummaryReply() {}

    public static SimulatedMobDropsSummaryReply fromBytes(ByteBuf buf) {
       SimulatedMobDropsSummaryReply reply = new SimulatedMobDropsSummaryReply();
       int numDrops = buf.readInt();
       for (int i = 0; i < numDrops; i++)
           reply.drops.add(SimulatedMobDropSummary.readFromPacket(buf));

       return reply;
    }

    public static SimulatedMobDropsSummaryReply fromMob(String entityKey) {
        SimulatedMobDropsSummaryReply reply = new SimulatedMobDropsSummaryReply();
        FakeMob fakeMob = new FakeMob(entityKey);
        if (fakeMob.isValid())
            reply.drops.addAll(MobSimulator.getInstance().getDropSummary(fakeMob));
        return reply;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(drops.size());
        drops.forEach(d -> d.writeToPacket(buf));
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            final ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player.openContainer instanceof OracleContainer)
                ((OracleContainer) player.openContainer).handleSimulatedMobDropsSummaryReply(this);
            ctx.get().setPacketHandled(true);
        })) ;
    }
}
