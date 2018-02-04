package ipsis.woot.network.packets;

import io.netty.buffer.ByteBuf;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetFarmInfo implements IMessage {

    public PacketGetFarmInfo() { }

    BlockPos pos;

    public PacketGetFarmInfo(BlockPos pos) {

        this.pos = new BlockPos(pos);
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<PacketGetFarmInfo, IMessage> {

        @Override
        public IMessage onMessage(PacketGetFarmInfo message, MessageContext ctx) {

            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketGetFarmInfo pkt, MessageContext ctx) {

            TileEntity te = ctx.getServerHandler().player.getEntityWorld().getTileEntity(pkt.pos);
            if (!(te instanceof TileEntityMobFactoryHeart))
                return;

            TileEntityMobFactoryHeart heart = (TileEntityMobFactoryHeart)te;
            FarmUIInfo info = new FarmUIInfo();
            heart.getUIInfo(info);
            if (info.isValid)
                PacketHandler.INSTANCE.sendTo(new PacketFarmInfo(info), ctx.getServerHandler().player);
        }
    }
}
