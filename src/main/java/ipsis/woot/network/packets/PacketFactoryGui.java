package ipsis.woot.network.packets;

import io.netty.buffer.ByteBuf;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.oss.NetworkTools;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFactoryGui implements IMessage {

    private FarmUIInfo farmUIInfo;
    private String mobName;

    @Override
    public void fromBytes(ByteBuf buf) {

        farmUIInfo = new FarmUIInfo();
        farmUIInfo.isValid = true;
        farmUIInfo.recipeTotalPower = buf.readInt();
        farmUIInfo.recipePowerPerTick = buf.readInt();
        farmUIInfo.recipeTotalTime = buf.readInt();
        farmUIInfo.mobCount = buf.readInt();
        farmUIInfo.tier = EnumMobFactoryTier.getTier(buf.readByte());
        mobName = NetworkTools.readString(buf);

        int drops = buf.readInt();
        for (int i = 0; i < drops; i++)
            farmUIInfo.drops.add(NetworkTools.readItemStack(buf));

        int itemIngredients = buf.readInt();
        for (int i = 0; i < itemIngredients; i++)
            farmUIInfo.ingredients.add(NetworkTools.readItemStack(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(farmUIInfo.recipeTotalPower);
        buf.writeInt(farmUIInfo.recipePowerPerTick);
        buf.writeInt(farmUIInfo.recipeTotalTime);
        buf.writeInt(farmUIInfo.mobCount);
        buf.writeByte(farmUIInfo.tier.ordinal());
        NetworkTools.writeString(buf, mobName);

        LogHelper.info("toBytes: drops: " + farmUIInfo.drops.size());
        buf.writeInt(farmUIInfo.drops.size());
        for (ItemStack itemStack : farmUIInfo.drops)
            NetworkTools.writeItemStack(buf, itemStack);

        LogHelper.info("toBytes: ingredients: " + farmUIInfo.ingredients.size());
        buf.writeInt(farmUIInfo.ingredients.size());
        for (ItemStack itemStack : farmUIInfo.ingredients)
            NetworkTools.writeItemStack(buf, itemStack);
    }

    public PacketFactoryGui() {
    }

    public PacketFactoryGui(FarmUIInfo farmUIInfo, String mobName) {

        this.farmUIInfo = farmUIInfo;
        this.mobName = mobName;
    }

    public static class Handler implements IMessageHandler<PacketFactoryGui, IMessage> {

        @Override
        public IMessage onMessage(PacketFactoryGui message, MessageContext ctx) {

            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketFactoryGui pkt, MessageContext ctx) {

            LogHelper.info("handle message: " + pkt.mobName);
        }
    }
}
