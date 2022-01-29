package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.modules.squeezer.client.DyeSqueezerContainer;
import ipsis.woot.modules.squeezer.SqueezerModule;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DyeSqueezerTileEntity extends TileEntity implements INamedContainerProvider {

    public DyeSqueezerTileEntity() {
        super(SqueezerModule.DYE_SQUEEZER_TE.get());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.woot." + SqueezerModule.DYE_SQUEEZER_ID);
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new DyeSqueezerContainer(windowId, playerInventory, this);
    }

    public boolean stillValid(PlayerEntity playerEntity) {
        return !(playerEntity.distanceToSqr(
                (double)this.worldPosition.getX() + 0.5D,
                (double)this.worldPosition.getY() + 0.5D,
                (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
    }

    /**
     * Inventory
     */
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1);
    private LazyOptional<IItemHandler> iItemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && iItemHandlerLazyOptional != null)
            iItemHandlerLazyOptional.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        super.save(compoundNBT);
        // save inventory, tanks, energy, progress
        return compoundNBT;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compoundNBT) {
        super.load(blockState, compoundNBT);
        // load inventory, tanks, energy, progress
    }
}
