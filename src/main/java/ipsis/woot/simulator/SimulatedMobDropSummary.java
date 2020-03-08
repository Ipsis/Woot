package ipsis.woot.simulator;

import io.netty.buffer.ByteBuf;
import ipsis.woot.util.oss.NetworkTools;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class SimulatedMobDropSummary {

    public ItemStack itemStack;
    public float[] chanceToDrop;

    public SimulatedMobDropSummary(ItemStack itemStack, float l0, float l1, float l2, float l3) {
        this.itemStack = itemStack.copy();
        this.itemStack.setCount(1);
        chanceToDrop = new float[]{l0, l1, l2, l3};

    }

    public void writeToPacket(ByteBuf buf) {
        NetworkTools.writeItemStack(buf, itemStack);
        for (int looting = 0; looting < 4; looting++)
            buf.writeFloat(chanceToDrop[looting]);
    }

    public static SimulatedMobDropSummary readFromPacket(ByteBuf buf) {
        ItemStack itemStack = NetworkTools.readItemStack(buf);
        return new SimulatedMobDropSummary(itemStack, buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    @Override
    public String toString() {
        return "SimulatedMobDropSummary{" +
                "itemStack=" + itemStack +
                ", chanceToDrop=" + Arrays.toString(chanceToDrop) +
                '}';
    }
}
