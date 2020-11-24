package ipsis.woot.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;

public class NetworkHelper {

    public static void writeFluidStack(ByteBuf dataOut, FluidStack fluidStack) {
        PacketBuffer buf = new PacketBuffer(dataOut);
        fluidStack.writeToPacket(buf);
    }

    public static FluidStack readFluidStack(ByteBuf dataIn) {
        PacketBuffer buf = new PacketBuffer(dataIn);
        FluidStack fluidStack = FluidStack.readFromPacket(buf);
        return fluidStack;
    }
}
