package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.heart.TileEntityHeart;
import ipsis.woot.util.helpers.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestServerData implements IMessage {

    public static final int CMD_GET_STATIC_HEART_DATA = 0;

    public PacketRequestServerData() { }

    public PacketRequestServerData(int cmd) {
        this.cmd = cmd;
        this.pos = new BlockPos(0, 0, 0);
    }

    public PacketRequestServerData(int cmd, BlockPos pos) {
        this.cmd = cmd;
        this.pos = new BlockPos(pos);
    }

    private int cmd = -1;
    private BlockPos pos;

    @Override
    public void fromBytes(ByteBuf buf) {
        cmd = buf.readInt();
        pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(cmd);
        buf.writeLong(pos.toLong());
    }

    public static class Handler implements IMessageHandler<PacketRequestServerData, IMessage> {

        @Override
        public IMessage onMessage(PacketRequestServerData message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketRequestServerData message, MessageContext ctx) {

            LogHelper.info("PacketRequestServerData: cmd:" + message.cmd);
            if (message.cmd == CMD_GET_STATIC_HEART_DATA) {
                LogHelper.info("PacketRequestServerData: cmd:" + message.cmd + " " + message.pos);

                BlockPos pos = message.pos;
                TileEntity te = ctx.getServerHandler().player.world.getTileEntity(pos);
                if (te instanceof TileEntityHeart) {
                    PacketHandler.INSTANCE.sendTo(new PacketSyncStaticHeartData(((TileEntityHeart)te).getUIFixedInfo()), ctx.getServerHandler().player);
                }
            }
        }
    }
}
