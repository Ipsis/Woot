package ipsis.woot.modules.oracle.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.oracle.blocks.OracleContainer;
import ipsis.woot.common.configuration.Config;
import ipsis.woot.modules.simulation.DropRegistry;
import ipsis.woot.modules.simulation.SimulationConfiguration;
import ipsis.woot.util.oss.NetworkTools;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class DropRegistryStatusReply {

    public List<SimMob> simulatedMobs = new ArrayList<>();
    public int simCount = 0;

    public DropRegistryStatusReply() { }

    public DropRegistryStatusReply(int simCount, List<SimMob> simulatedMobs) {
        this.simCount = simCount;
        this.simulatedMobs = simulatedMobs;
    }

    public static DropRegistryStatusReply fromBytes(ByteBuf buf) {
        int simCount = buf.readInt();
        int numMobs = buf.readInt();
        List<SimMob> simMobs = new ArrayList<>();
        for (int i = 0; i < numMobs; i++) {
            String entityKey = NetworkTools.readString(buf);
            SimMob simulatedMob = new SimMob(new FakeMob(entityKey));
            for (int l = 0; l < 4; l++) {
                simulatedMob.simulationKills[l] = buf.readInt();
                simulatedMob.simulationStatus[l] = buf.readBoolean();
            }
            simMobs.add(simulatedMob);
        }
        return new DropRegistryStatusReply(simCount, simMobs);
    }

    public void toBytes(ByteBuf buf) {

        // How many kills we are simulating
        buf.writeInt(SimulationConfiguration.SIMULATION_MOB_COUNT.get());

        Set<FakeMob> knownMobs = DropRegistry.get().getKnownMobs();
        buf.writeInt(knownMobs.size());
        for (FakeMob fakeMob : knownMobs) {
            List<Integer> simulationCounts = DropRegistry.get().getLearningStatus(fakeMob);
            NetworkTools.writeString(buf, fakeMob.getName());
            buf.writeInt(simulationCounts.get(0)); // no looting
            buf.writeBoolean(DropRegistry.get().isLearningFinished(new FakeMobKey(fakeMob, 0), SimulationConfiguration.SIMULATION_MOB_COUNT.get()));
            buf.writeInt(simulationCounts.get(1)); // looting 1
            buf.writeBoolean(DropRegistry.get().isLearningFinished(new FakeMobKey(fakeMob, 1), SimulationConfiguration.SIMULATION_MOB_COUNT.get()));
            buf.writeInt(simulationCounts.get(2)); // looting 2
            buf.writeBoolean(DropRegistry.get().isLearningFinished(new FakeMobKey(fakeMob, 2), SimulationConfiguration.SIMULATION_MOB_COUNT.get()));
            buf.writeInt(simulationCounts.get(3)); // looting 3
            buf.writeBoolean(DropRegistry.get().isLearningFinished(new FakeMobKey(fakeMob, 3), SimulationConfiguration.SIMULATION_MOB_COUNT.get()));
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null) {
            ctx.get().enqueueWork(() -> {
                if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof OracleContainer) {
                    ((OracleContainer) clientPlayerEntity.openContainer).handleDropRegistryStatus(this);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }

    public static class SimMob {
        public FakeMob fakeMob;
        public int[] simulationKills = new int[4];
        public boolean[] simulationStatus = new boolean[4];

        public SimMob(FakeMob fakeMob) {
            this.fakeMob = fakeMob;
        }
    }
}
