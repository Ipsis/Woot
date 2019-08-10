package ipsis.woot.factory.blocks.heart;

import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryUIInfo;
import ipsis.woot.mod.ModBlocks;
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
        return ((HeartTileEntity)tileEntity).factoryUIInfo;
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
    public void updateProgressBar(int p_75137_1_, int p_75137_2_) {
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();


        try {
            List<IContainerListener> iContainerListeners =
                    (List<IContainerListener>)ObfuscationReflectionHelper.getPrivateValue(Container.class, this, "listeners");

            for (IContainerListener l : iContainerListeners) {
                ServerPlayerEntity playerEntity = (ServerPlayerEntity) l;
                //Network.channel.sendTo(msg, l.get,NetworkDirection.PLAY_TO_CLIENT);
            }
        } catch (Throwable e) {
            Woot.LOGGER.error("Reflection of container listener failed");
        }
    }

    public void handleUIInfo(FactoryUIInfo factoryUIInfo) {
        ((HeartTileEntity)tileEntity).setFromUIInfo(factoryUIInfo);
    }
}
