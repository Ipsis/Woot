package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.FactoryUIInfo;
import ipsis.woot.util.WootContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() {
                return tileEntity.getFluidAmount();
            }

            @Override
            public void set(int i) {
                tileEntity.setClientFluidAmount(i);
            }
        });
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() {
                return tileEntity.getFluidCapacity();
            }

            @Override
            public void set(int i) {
                tileEntity.setClientFluidCapacity(i);
            }
        });
    }

    public  HeartTileEntity getTileEntity() { return tileEntity; }
}
