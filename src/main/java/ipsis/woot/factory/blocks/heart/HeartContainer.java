package ipsis.woot.factory.blocks.heart;

import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryUIInfo;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.network.NetworkChannel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

import static ipsis.woot.mod.ModBlocks.HEART_CONTAINER;

public class HeartContainer extends Container {

    private TileEntity tileEntity;

    public HeartContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(HEART_CONTAINER, windowId);
        tileEntity = world.getTileEntity(pos);

        /**
         * There is no player inventory as it is display only
         */
    }

    public BlockPos getPos() {
        return tileEntity.getPos();
    }

    public FactoryUIInfo getFactoryUIInfo() {
        if (tileEntity instanceof HeartTileEntity)
            return ((HeartTileEntity)tileEntity).factoryUIInfo;
        return null;
    }

    public int getProgress() {
        int progress = 0;
        if (tileEntity instanceof HeartTileEntity)
            progress = (int)((100.0F / ((HeartTileEntity) tileEntity).factoryUIInfo.recipeTicks * ((HeartTileEntity) tileEntity).getClientProgress()));
        return progress;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
                playerIn, ModBlocks.HEART_BLOCK);
    }

    /**
     * Sync data - short only
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        if (!(tileEntity instanceof HeartTileEntity))
            return;
        HeartTileEntity heartTileEntity = (HeartTileEntity)tileEntity;

        if (id == 0)
            heartTileEntity.setClientProgress(data);

    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (!(tileEntity instanceof HeartTileEntity))
            return;

        HeartTileEntity heartTileEntity = (HeartTileEntity)tileEntity;

        try {
            List<IContainerListener> iContainerListeners =
                    (List<IContainerListener>)ObfuscationReflectionHelper.getPrivateValue(Container.class, this, "listeners");

            for (IContainerListener l : iContainerListeners) {
                ServerPlayerEntity playerEntity = (ServerPlayerEntity) l;
                if (heartTileEntity.consumedUnits != heartTileEntity.getClientProgress()) {
                    playerEntity.sendWindowProperty(this, 0, heartTileEntity.consumedUnits);
                }
                //NetworkChannel.channel.send(playerEntity, new FixedWindowPropertyPacket(windowId, 1, 65535));
            }

            heartTileEntity.setClientProgress(heartTileEntity.consumedUnits);
        } catch (Throwable e) {
            Woot.LOGGER.error("Reflection of container listener failed");
        }
    }

    public void handleUIInfo(FactoryUIInfo factoryUIInfo) {
        // Static data values
        ((HeartTileEntity)tileEntity).setFromUIInfo(factoryUIInfo);
    }
}
