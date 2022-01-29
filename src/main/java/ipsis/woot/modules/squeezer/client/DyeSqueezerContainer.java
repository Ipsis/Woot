package ipsis.woot.modules.squeezer.client;

import ipsis.woot.modules.squeezer.SqueezerModule;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class DyeSqueezerContainer extends Container {

    private DyeSqueezerTileEntity te;
    private final Slot ingredientSlot;

    public DyeSqueezerContainer(int windowId, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(SqueezerModule.DYE_SQUEEZER_CONTAINER.get(), windowId);
        BlockPos pos = packetBuffer.readBlockPos();
        this.te = (DyeSqueezerTileEntity) playerInventory.player.level.getBlockEntity(pos);

        IItemHandler itemHandler = this.getTe().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(IllegalStateException::new);
        this.ingredientSlot = addSlot(new IngredientSlot(itemHandler, 0, 52, 34));
        addPlayerSlots(playerInventory);
    }

    public DyeSqueezerContainer(int windowId, PlayerInventory playerInventory, DyeSqueezerTileEntity te) {
        super(SqueezerModule.DYE_SQUEEZER_CONTAINER.get(), windowId);
        this.te = te;

        IItemHandler itemHandler = this.getTe().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(IllegalStateException::new);
        this.ingredientSlot = addSlot(new IngredientSlot(itemHandler, 0, 52, 34));
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++)
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return this.te.stillValid(playerEntity);
    }

    public DyeSqueezerTileEntity getTe() { return this.te; }

    static class IngredientSlot extends SlotItemHandler {
        public IngredientSlot(IItemHandler itemHandler, int id, int x, int y) {
            super(itemHandler, id, x, y);
        }

        public boolean mayPlace(ItemStack itemStack) {
            return true;
        }
    }
}
