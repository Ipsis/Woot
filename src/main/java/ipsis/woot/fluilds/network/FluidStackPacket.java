package ipsis.woot.fluilds.network;

import ipsis.woot.modules.oracle.blocks.OracleContainer;
import ipsis.woot.util.FluidStackPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Server -> Client
 */
public class FluidStackPacket {

    public List<FluidStack> fluidStackList = new ArrayList<>();

    public FluidStackPacket() { }

    public FluidStackPacket(FluidStack... stacks) {
        for (FluidStack fluidStack : stacks)
            fluidStackList.add(fluidStack.copy());
    }

    public static FluidStackPacket fromBytes(PacketBuffer buf) {

        FluidStackPacket pkt = new FluidStackPacket();
        int stacks = buf.readInt();
        if (stacks > 0) {
            for (int i = 0; i < stacks; i++) {
                if (buf.readBoolean() == true)
                    pkt.fluidStackList.add(FluidStack.readFromPacket(buf));
                else
                    pkt.fluidStackList.add(FluidStack.EMPTY);
            }
        }
        return pkt;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(fluidStackList.size());
        for (int i = 0; i < fluidStackList.size(); i++) {
            FluidStack fluidStack = fluidStackList.get(i);
            if (fluidStack.isEmpty()) {
                buf.writeBoolean(false);
            } else {
                buf.writeBoolean(true);
                fluidStack.writeToPacket(buf);
            }
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            final ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player.openContainer instanceof FluidStackPacketHandler)
                ((FluidStackPacketHandler) player.openContainer).handlePacket(this);
            ctx.get().setPacketHandled(true);
        })) ;
    }
}
