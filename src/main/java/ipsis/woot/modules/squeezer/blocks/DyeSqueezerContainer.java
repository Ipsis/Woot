package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.Woot;
import ipsis.woot.crafting.DyeSqueezerRecipe;
import ipsis.woot.fluilds.network.TankPacket;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.setup.NetworkChannel;
import ipsis.woot.util.TankPacketHandler;
import ipsis.woot.util.WootContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class DyeSqueezerContainer extends WootContainer implements TankPacketHandler {

    public DyeSqueezerTileEntity tileEntity;

    public DyeSqueezerContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(SqueezerSetup.SQUEEZER_BLOCK_CONTAINER.get(), windowId);
        tileEntity = (DyeSqueezerTileEntity)world.getTileEntity(pos);
        addOwnSlots(tileEntity.getInventory());
        addPlayerSlots(playerInventory);
        addListeners();
    }

    private void addOwnSlots(IItemHandler inv) {
        this.addSlot(new SlotItemHandler(inv, 0, 39, 40));
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
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {

        // Based off Gigaherz Elements Of Power code
        Slot slot = this.inventorySlots.get(index);
        if (slot == null || !slot.getHasStack())
            return ItemStack.EMPTY;

        ItemStack stack = slot.getStack();
        ItemStack stackCopy = stack.copy();

        int startIndex;
        int endIndex;

        final int MACHINE_INV_SIZE = 1;
        final int PLAYER_INV_SIZE = 27;
        final int TOOLBAR_INV_SIZE = 9;

        if (index >= MACHINE_INV_SIZE) {
            // player slot
            if (DyeSqueezerRecipe.isValidInput(stack)) {
                // -> machine
                startIndex = 0;
                endIndex = MACHINE_INV_SIZE;
            } else if (index < PLAYER_INV_SIZE + MACHINE_INV_SIZE) {
                // -> toolbar
                startIndex = PLAYER_INV_SIZE + MACHINE_INV_SIZE;
                endIndex = startIndex + TOOLBAR_INV_SIZE;
            } else if (index >= PLAYER_INV_SIZE + MACHINE_INV_SIZE) {
                // -> player
                startIndex = MACHINE_INV_SIZE;
                endIndex = startIndex + PLAYER_INV_SIZE;
            } else {
                return ItemStack.EMPTY;
            }
        } else {
            // machine slot
            startIndex = MACHINE_INV_SIZE;
            endIndex = startIndex + PLAYER_INV_SIZE + TOOLBAR_INV_SIZE;
        }

        if (!this.mergeItemStack(stack, startIndex, endIndex, false))
            return ItemStack.EMPTY;

        if (stack.getCount() == 0)
            slot.putStack(ItemStack.EMPTY);
        else
            slot.onSlotChanged();

        if (stack.getCount() == stackCopy.getCount())
            return ItemStack.EMPTY;

        slot.onTake(playerIn, stack);
        return stackCopy;

    }

    private int red = 0;
    private int yellow = 0;
    private int blue = 0;
    private int white = 0;
    private int progress = 0;
    private FluidStack pureDye = FluidStack.EMPTY;
    private int energy = 0;
    private boolean dumpExcess = false;

    @OnlyIn(Dist.CLIENT)
    public int getRedDyeAmount() { return this.red; }
    @OnlyIn(Dist.CLIENT)
    public int getYellowDyeAmount() { return this.yellow; }
    @OnlyIn(Dist.CLIENT)
    public int getBlueDyeAmount() { return this.blue; }
    @OnlyIn(Dist.CLIENT)
    public int getWhiteDyeAmount() { return this.white; }
    @OnlyIn(Dist.CLIENT)
    public int getProgress() { return this.progress; }
    @OnlyIn(Dist.CLIENT)
    public FluidStack getPureDye() { return this.pureDye; }
    @OnlyIn(Dist.CLIENT)
    public int getEnergy() { return this.energy; }
    @OnlyIn(Dist.CLIENT)
    public boolean getDumpExcess() { return this.dumpExcess; }

    public void addListeners() {
        addShortListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getRed(); }

            @Override
            public void set(int i) { red = i; }
        });
        addShortListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getBlue(); }

            @Override
            public void set(int i) { blue = i; }
        });
        addShortListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getWhite(); }

            @Override
            public void set(int i) { white = i; }
        });
        addShortListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getYellow(); }

            @Override
            public void set(int i) { yellow = i; }
        });
        addIntegerListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getEnergy(); }

            @Override
            public void set(int i) { energy = i; }
        });
        addShortListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getProgress(); }

            @Override
            public void set(int i) { progress = i; }
        });
        addShortListener(new IntReferenceHolder() {
            @Override
            public int get() { return tileEntity.getDumpExcess() ? 1 : 0; }

            @Override
            public void set(int i) { dumpExcess = i == 1; }
        });
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (!pureDye.isFluidStackIdentical(tileEntity.getOutputTankFluid())) {
            pureDye = tileEntity.getOutputTankFluid().copy();
            for (IContainerListener l : listeners) {
                if (l instanceof ServerPlayerEntity) {
                    NetworkChannel.channel.sendTo(tileEntity.getOutputTankPacket(), ((ServerPlayerEntity) l).connection.netManager,
                            NetworkDirection.PLAY_TO_CLIENT);
                }
            }
        }
    }

    @Override
    public void handlePacket(TankPacket packet) {
        if (packet.tankId == 0)
            pureDye = packet.fluidStack;
    }
}
