package ipsis.woot.network.packets;

import io.netty.buffer.ByteBuf;
import ipsis.woot.client.gui.inventory.FactoryHeartContainer;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.oss.NetworkTools;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFarmInfo implements IMessage {

    private FarmUIInfo farmUIInfo;

    @Override
    public void fromBytes(ByteBuf buf) {

        farmUIInfo = new FarmUIInfo();
        farmUIInfo.isValid = true;
        farmUIInfo.recipeTotalPower = buf.readLong();
        farmUIInfo.recipePowerPerTick = buf.readInt();
        farmUIInfo.recipeTotalTime = buf.readInt();
        farmUIInfo.mobCount = buf.readInt();
        farmUIInfo.isRunning = buf.readBoolean();
        farmUIInfo.tier = EnumMobFactoryTier.getTier(buf.readByte());
        farmUIInfo.powerStored = buf.readInt();
        farmUIInfo.powerCapacity = buf.readInt();
        farmUIInfo.missingIngredients = buf.readBoolean();
        farmUIInfo.mobName = NetworkTools.readString(buf);

        int drops = buf.readInt();
        for (int i = 0; i < drops; i++)
            farmUIInfo.drops.add(NetworkTools.readItemStack(buf));

        int itemIngredients = buf.readInt();
        for (int i = 0; i < itemIngredients; i++) {
            ItemStack itemStack = NetworkTools.readItemStack(buf);
            if (!itemStack.isEmpty())
                farmUIInfo.ingredientsItems.add(itemStack);
        }

        int fluidIngredients = buf.readInt();
        for (int i = 0; i < fluidIngredients; i++) {
            FluidStack fluidStack = ipsis.woot.util.NetworkTools.readFluidStack(buf);
            if (fluidStack != null)
                farmUIInfo.ingredientsFluids.add(fluidStack);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeLong(farmUIInfo.recipeTotalPower);
        buf.writeInt(farmUIInfo.recipePowerPerTick);
        buf.writeInt(farmUIInfo.recipeTotalTime);
        buf.writeInt(farmUIInfo.mobCount);
        buf.writeBoolean(farmUIInfo.isRunning);
        buf.writeByte(farmUIInfo.tier.ordinal());
        buf.writeInt(farmUIInfo.powerStored);
        buf.writeInt(farmUIInfo.powerCapacity);
        buf.writeBoolean(farmUIInfo.missingIngredients);
        NetworkTools.writeString(buf, farmUIInfo.mobName);

        buf.writeInt(farmUIInfo.drops.size());
        for (ItemStack itemStack : farmUIInfo.drops)
            NetworkTools.writeItemStack(buf, itemStack);

        buf.writeInt(farmUIInfo.ingredientsItems.size());
        for (ItemStack itemStack : farmUIInfo.ingredientsItems)
            NetworkTools.writeItemStack(buf, itemStack);

        buf.writeInt(farmUIInfo.ingredientsFluids.size());
        for (FluidStack fluidStack : farmUIInfo.ingredientsFluids)
            ipsis.woot.util.NetworkTools.writeFluidStack(buf, fluidStack);
    }

    public PacketFarmInfo() {
    }

    public PacketFarmInfo(FarmUIInfo farmUIInfo) {

        this.farmUIInfo = farmUIInfo;
    }

    public static class Handler implements IMessageHandler<PacketFarmInfo, IMessage> {

        @Override
        public IMessage onMessage(PacketFarmInfo message, MessageContext ctx) {

            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketFarmInfo pkt, MessageContext ctx) {

            EntityPlayerSP player = FMLClientHandler.instance().getClient().player;
            if (player != null && player.openContainer != null && player.openContainer instanceof FactoryHeartContainer)
                ((FactoryHeartContainer)player.openContainer).handleFarmInfo(pkt.farmUIInfo);
        }
    }
}
