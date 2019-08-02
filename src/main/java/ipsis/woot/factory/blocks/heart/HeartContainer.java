package ipsis.woot.factory.blocks.heart;

import ipsis.woot.mod.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

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

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
                playerIn, ModBlocks.HEART_BLOCK);
    }
}
