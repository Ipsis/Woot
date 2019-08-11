package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.Woot;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.loot.MobDrop;
import ipsis.woot.oss.NetworkTools;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SimulatedMobDropsReply {

    public List<SimDrop> simulatedDrops = new ArrayList<>();

    public SimulatedMobDropsReply() { }

    public SimulatedMobDropsReply(List<SimDrop> simulatedDrops) {
        this.simulatedDrops = simulatedDrops;
    }

    public static SimulatedMobDropsReply fromBytes(ByteBuf buf) {
        List<SimDrop> simDrops = new ArrayList<>();
        int numDrops = buf.readInt();
        Woot.LOGGER.info("fromBytes: contained {} drops", numDrops);
        for (int i = 0; i < numDrops; i++) {
            SimDrop simDrop = new SimDrop(NetworkTools.readItemStack(buf));
            for (int l = 0; l < 4; l++)
                simDrop.dropChance[l] = buf.readFloat();
            simDrops.add(simDrop);
        }
        return new SimulatedMobDropsReply(simDrops);
    }

    public void toBytes(ByteBuf buf) {
        Woot.LOGGER.info("toBytes: {} drops", simulatedDrops.size());
        buf.writeInt(simulatedDrops.size());
        for (SimDrop simDrop : simulatedDrops) {
            NetworkTools.writeItemStack(buf, simDrop.itemStack);
            for (int l = 0; l < 4; l++)
                buf.writeFloat(simDrop.dropChance[l]);
        }
    }

    public SimulatedMobDropsReply(String entityKey) {
        Woot.LOGGER.info("Asked for drops on " + entityKey);
        FakeMob fakeMob = new FakeMob(entityKey);
        if (fakeMob.isValid()) {
            for (int l = 0; l < 4; l++) {
                List<MobDrop> mobDrops = DropRegistry.get().getMobDrops(new FakeMobKey(fakeMob, l));
                for (MobDrop mobDrop : mobDrops) {
                    ItemStack itemStack = mobDrop.getDroppedItem().copy();
                    SimDrop simDrop = null;
                    for (SimDrop c : simulatedDrops) {
                        if (DropRegistry.get().isEqualForLearning(c.itemStack, itemStack)) {
                            simDrop = c;
                            break;
                        }
                    }
                    if (simDrop == null) {
                        simDrop = new SimDrop(itemStack);
                        simulatedDrops.add(simDrop);
                    }
                    float dropChance = mobDrop.getDropChance();
                    simDrop.dropChance[l] = mobDrop.getDropChance();
                }
            }
        }
        Woot.LOGGER.info("Created drops for " + entityKey);
    }

    public static class SimDrop {
        public ItemStack itemStack;
        public float[] dropChance = new float[4];

        public SimDrop(ItemStack itemStack) {
            this.itemStack = itemStack;
            this.itemStack.setCount(1);
        }
    }
}
