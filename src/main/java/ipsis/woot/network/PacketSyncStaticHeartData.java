package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.blocks.heart.ContainerHeart;
import ipsis.woot.blocks.heart.HeartUIFixedInfo;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakeMobKeyFactory;
import ipsis.woot.util.helpers.LogHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This is a fairly busy packet but only in terms on content not frequency.
 * It basically contains all of the fixed information for the gui, such
 * as the mob, upgrades costs etc.
 */
public class PacketSyncStaticHeartData implements IMessage {

    public PacketSyncStaticHeartData() { }

    private  HeartUIFixedInfo info = new HeartUIFixedInfo();

    public PacketSyncStaticHeartData(HeartUIFixedInfo info) {
        if (info.isFormed()) {
            this.info.setFormed();
            this.info.tier = info.tier;
            this.info.fakeMobKey = info.fakeMobKey;
            this.info.recipeTicks = info.recipeTicks;
            this.info.recipeUnits = info.recipeUnits;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (buf.readBoolean()) {
            info.setFormed();
            info.fakeMobKey = FakeMobKeyFactory.createFromString(ByteBufUtils.readUTF8String(buf));
            info.tier = FactoryTier.values()[buf.readInt()];
            info.recipeTicks = buf.readInt();
            info.recipeUnits = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (info.isFormed()) {
            buf.writeBoolean(true);
            ByteBufUtils.writeUTF8String(buf, info.fakeMobKey.getResourceLocation().toString());
            buf.writeInt(info.tier.ordinal());
            buf.writeInt(info.recipeTicks);
            buf.writeInt(info.recipeUnits);
        } else {
            buf.writeBoolean(false);
        }
    }

    public static class Handler implements IMessageHandler<PacketSyncStaticHeartData, IMessage> {

        @Override
        public IMessage onMessage(PacketSyncStaticHeartData message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSyncStaticHeartData message, MessageContext ctx) {

            EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();
            if (player != null && player.openContainer instanceof ContainerHeart)
                ((ContainerHeart)player.openContainer).syncStaticHeartData(message.info);
        }
    }
}
