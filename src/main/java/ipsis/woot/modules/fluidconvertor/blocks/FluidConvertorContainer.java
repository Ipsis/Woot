package ipsis.woot.modules.fluidconvertor.blocks;

import ipsis.woot.Woot;
import ipsis.woot.crafting.FluidConvertorRecipe;
import ipsis.woot.fluilds.network.TankPacket;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.setup.NetworkChannel;
import ipsis.woot.util.TankPacketHandler;
import ipsis.woot.util.WootContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.List;

public class FluidConvertorContainer extends WootContainer implements TankPacketHandler {

    public FluidConvertorTileEntity tileEntity;
    private boolean first = true;

    public FluidConvertorContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK_CONTATAINER.get(), windowId);
        tileEntity = (FluidConvertorTileEntity)world.getTileEntity(pos);

        addOwnSlots();
        addPlayerSlots(playerInventory);
        addListeners();
        tileEntity.setClientOutTank(FluidStack.EMPTY);
        tileEntity.setClientInTank(FluidStack.EMPTY);
    }

    private void addOwnSlots() {
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((
                iItemHandler -> {
                    addSlot(new SlotItemHandler(iItemHandler, 0, 100, 22));
                }
        ));
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 95;
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            this.addSlot(new Slot(playerInventory, row, x, 153));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
                playerIn, FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get());
    }

    public FluidConvertorTileEntity getTileEntity() { return tileEntity; }

    public void addListeners() {
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getEnergy(); }

            @Override
            public void set(int i) { tileEntity.setEnergy(i); }
        });

        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getProgress(); }

            @Override
            public void set(int i) { tileEntity.setProgress(i); }
        });
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        try {
            List<IContainerListener> iContainerListeners =
                    (List<IContainerListener>) ObfuscationReflectionHelper.getPrivateValue(Container.class, this, "listeners");

            if (first || !tileEntity.getClientInTank().isFluidStackIdentical(tileEntity.getInputTankFluid())) {
                tileEntity.setClientInTank(tileEntity.getInputTankFluid().copy());
                for (IContainerListener l : iContainerListeners) {
                    NetworkChannel.channel.sendTo(tileEntity.getInputTankPacket(), ((ServerPlayerEntity) l).connection.netManager,
                            NetworkDirection.PLAY_TO_CLIENT);
                }
            }

            if (first || !tileEntity.getClientOutTank().isFluidStackIdentical(tileEntity.getOutputTankFluid())) {
                tileEntity.setClientOutTank(tileEntity.getOutputTankFluid().copy());
                for (IContainerListener l : iContainerListeners) {
                    NetworkChannel.channel.sendTo(tileEntity.getOutputTankPacket(), ((ServerPlayerEntity) l).connection.netManager,
                            NetworkDirection.PLAY_TO_CLIENT);
                }
            }

            first = false;
        } catch (Throwable e) {
            Woot.setup.getLogger().error("Reflection of container listener failed");
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int index) {

        // 0 input slot
        // 1-> 27 player
        // 28 -> 36 hotbar
        // NB: mergeItemStack minIndex(inclusive), maxIndex(exclusive)
        final int LAST_MACHINE_SLOT = 0;
        final int FIRST_PLAYER_SLOT = 1;
        final int LAST_PLAYER_SLOT = FIRST_PLAYER_SLOT + 27 - 1;
        final int FIRST_HOTBAR_SLOT = LAST_PLAYER_SLOT + 1;
        final int LAST_HOTBAR_SLOT = FIRST_HOTBAR_SLOT + 9 - 1;

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();
            if (index <= LAST_MACHINE_SLOT) {
                // Machine -> Player/Hotbar
                if (!this.mergeItemStack(stack, FIRST_PLAYER_SLOT, LAST_HOTBAR_SLOT + 1, true))
                    return ItemStack.EMPTY;
                slot.onSlotChange(stack, itemStack);
            } else {
                if (FluidConvertorRecipe.isValidCatalyst(itemStack)) {
                    // Player -> input
                    if (!this.mergeItemStack(stack, 0, 1, false))
                        return ItemStack.EMPTY;
                }

                // No player to machine supported
                if (index >= FIRST_PLAYER_SLOT && index <= LAST_PLAYER_SLOT) {
                    // Player -> Hotbar
                    if (!this.mergeItemStack(stack, FIRST_HOTBAR_SLOT, LAST_HOTBAR_SLOT + 1, false))
                        return ItemStack.EMPTY;
                } else if (index <= LAST_HOTBAR_SLOT && !this.mergeItemStack(stack, FIRST_PLAYER_SLOT, LAST_PLAYER_SLOT + 1, false)) {
                    // Hotbar -> Player
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();

            if (stack.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(playerEntity, stack);
        }

        return itemStack;
    }

    @Override
    public void handlePacket(TankPacket packet) {
        if (packet.tankId == 0)
            tileEntity.setInputTankFluid(packet.fluidStack);
        else if (packet.tankId == 1)
            tileEntity.setOutputTankFluid(packet.fluidStack);
    }
}
