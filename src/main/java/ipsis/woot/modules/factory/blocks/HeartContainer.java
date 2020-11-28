package ipsis.woot.modules.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.fluilds.network.TankPacket;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.client.ClientFactorySetup;
import ipsis.woot.setup.NetworkChannel;
import ipsis.woot.util.TankPacketHandler;
import ipsis.woot.util.WootContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkDirection;

public class HeartContainer extends WootContainer implements TankPacketHandler  {

    private HeartTileEntity tileEntity;

    public HeartContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(FactorySetup.HEART_BLOCK_CONTAINER.get(), windowId);
        tileEntity = (HeartTileEntity)world.getTileEntity(pos);
        addListeners();

        /**
         * There is no player inventory as it is display only
         */
    }

    public BlockPos getPos() {
        return tileEntity.getPos();
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
                playerIn, FactorySetup.HEART_BLOCK.get());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (!inputFluid.isFluidStackIdentical(tileEntity.getTankFluid())) {
            inputFluid = tileEntity.getTankFluid().copy();
            TankPacket tankPacket = new TankPacket(0, inputFluid);
            for (IContainerListener l : listeners) {
                if (l instanceof ServerPlayerEntity) {
                    NetworkChannel.channel.sendTo(tankPacket, ((ServerPlayerEntity) l).connection.netManager,
                            NetworkDirection.PLAY_TO_CLIENT);
                }
            }
        }
    }

    private int progress = 0;
    private FluidStack inputFluid = FluidStack.EMPTY;
    private int cellType = 0;

    @OnlyIn(Dist.CLIENT)
    public FluidStack getInputFluid() { return inputFluid; }
    @OnlyIn(Dist.CLIENT)
    public int getProgress() { return this.progress; }
    @OnlyIn(Dist.CLIENT)
    public int getCellType() { return this.cellType; }


    private void addListeners() {
        addShortListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getProgress(); }

            @Override
            public void set(int i) { progress = i; }
        });
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() {
                return tileEntity.getCellType();
            }

            @Override
            public void set(int i) { cellType = i; }
        });
    }

    public  HeartTileEntity getTileEntity() { return tileEntity; }

    /**
     * Client sync
     */
    public void handleStaticDataReply(ClientFactorySetup clientFactorySetup) {
        tileEntity.setClientFactorySetup(clientFactorySetup);
    }

    @Override
    public void handlePacket(TankPacket packet) {
        if (packet.tankId == 0)
            inputFluid = packet.fluidStack;
    }
}
