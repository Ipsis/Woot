package ipsis.woot.modules.generator.blocks;

import ipsis.woot.modules.generator.GeneratorSetup;
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

public class ConatusGeneratorContainer extends WootContainer {

    public ConatusGeneratorTileEntity tileEntity;

    public ConatusGeneratorContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(GeneratorSetup.CONATUS_GENERATOR_BLOCK_CONTAINER.get(), windowId);
        tileEntity = (ConatusGeneratorTileEntity)world.getTileEntity(pos);

        addOwnSlots();
        addPlayerSlots(playerInventory);
        addListeners();
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
                playerIn, GeneratorSetup.CONATUS_GENERATOR_BLOCK.get());
    }

    public ConatusGeneratorTileEntity getTileEntity() { return tileEntity; }

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
}
