package ipsis.woot.fluilds.network;

import ipsis.woot.util.TankPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Server -> Client
 */
public class TankPacket {

    public FluidStack fluidStack;
    public int tankId;

    public TankPacket() { }

    public TankPacket(int tankId, FluidStack fluidStack) {
        this.tankId = tankId;
        this.fluidStack = fluidStack.copy();
    }

    public static TankPacket fromBytes(PacketBuffer buf) {
        TankPacket pkt = new TankPacket();
        pkt.tankId = buf.readInt();
        pkt.fluidStack = FluidStack.readFromPacket(buf);
        return pkt;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(tankId);
        fluidStack.writeToPacket(buf);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null) {
            ctx.get().enqueueWork(() -> {
                if (clientPlayerEntity.openContainer instanceof TankPacketHandler) {
                    ((TankPacketHandler) clientPlayerEntity.openContainer).handlePacket(this);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }

}
