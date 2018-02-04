package ipsis.woot.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;

import java.io.IOException;

public class NetworkTools {

    public static void writeFluidStack(ByteBuf dataOut, FluidStack fluidStack) {

        PacketBuffer buf = new PacketBuffer(dataOut);
        NBTTagCompound nbt = new NBTTagCompound();
        fluidStack.writeToNBT(nbt);
        try {
            buf.writeCompoundTag(nbt);
            buf.writeInt(fluidStack.amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FluidStack readFluidStack(ByteBuf dataIn) {
        PacketBuffer buf = new PacketBuffer(dataIn);
        try {
            NBTTagCompound nbt = buf.readCompoundTag();
            FluidStack stack = FluidStack.loadFluidStackFromNBT(nbt);
            if (stack != null) {
                stack.amount = buf.readInt();
                return stack;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
