package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.crafting.SqueezerRecipe;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.WootContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SqueezerContainer extends WootContainer {

    public SqueezerTileEntity tileEntity;

    public SqueezerContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(SqueezerSetup.SQUEEZER_BLOCK_CONTAINER.get(), windowId);
        tileEntity = (SqueezerTileEntity)world.getTileEntity(pos);
        addOwnSlots();
        addPlayerSlots(playerInventory);
        addListeners();
    }

    private void addOwnSlots() {
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((
                iItemHandler -> {
                    addSlot(new SlotItemHandler(iItemHandler, 0, 39, 40));
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
                playerIn, SqueezerSetup.SQUEEZER_BLOCK.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int index) {

        // 0 input slot
        // 1 -> 27 player
        // 28 -> 36 hotbar

        // Straight from McJty's YouTubeModdingTutorial
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();
            if (index == 0) {
                // Machine -> Player/Hotbar
                if (!this.mergeItemStack(stack, 1, 37, true))
                    return ItemStack.EMPTY;
                slot.onSlotChange(stack, itemStack);
            } else {
                if (SqueezerRecipe.findRecipe(stack) != null) {
                    // Player -> Machine
                    if (!this.mergeItemStack(stack, 0, 1, false))
                        return ItemStack.EMPTY;
                } else if (index < 28) {
                        // Player -> Hotbar
                        if (!this.mergeItemStack(stack, 28, 37, false))
                            return ItemStack.EMPTY;
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
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


    public void addListeners() {
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getRed(); }

            @Override
            public void set(int i) { tileEntity.setRed(i); }
        });
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getBlue(); }

            @Override
            public void set(int i) { tileEntity.setBlue(i); }
        });
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getWhite(); }

            @Override
            public void set(int i) { tileEntity.setWhite(i); }
        });
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getYellow(); }

            @Override
            public void set(int i) { tileEntity.setYellow(i); }
        });
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getPure(); }

            @Override
            public void set(int i) { tileEntity.setPure(i); }
        });
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getEnergy(); }

            @Override
            public void set(int i) { tileEntity.setEnergy(i); }
        });
    }

    public SqueezerTileEntity getTileEntity() { return tileEntity; }
}
