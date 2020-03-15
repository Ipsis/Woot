package ipsis.woot.modules.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.FactoryUIInfo;
import ipsis.woot.util.WootContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

public class HeartContainer extends WootContainer {

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

    public FactoryUIInfo getFactoryUIInfo() {
        return tileEntity.factoryUIInfo;
    }

    public int getProgress() {
        return (int)((100.0F / tileEntity.factoryUIInfo.recipeTicks * tileEntity.getClientProgress()));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
                playerIn, FactorySetup.HEART_BLOCK.get());
    }

    /**
     * Sync data - short only
     */
    /*
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
            Woot.setup.getLogger().error("Reflection of container listener failed");
        }
    } */

    public void handleUIInfo(FactoryUIInfo factoryUIInfo) {
        // Static data values
        tileEntity.setFromUIInfo(factoryUIInfo);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    private void addListeners() {
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() {
                return tileEntity.getClientProgress();
            }

            @Override
            public void set(int i) {
                tileEntity.setClientProgress(i);
            }
        });
    }
}
