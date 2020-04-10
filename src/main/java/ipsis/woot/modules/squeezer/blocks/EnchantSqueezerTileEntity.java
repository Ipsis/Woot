package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootEnergyStorage;
import ipsis.woot.util.WootMachineTileEntity;
import ipsis.woot.util.helper.EnchantmentHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EnchantSqueezerTileEntity extends WootMachineTileEntity implements WootDebug, INamedContainerProvider {

    public EnchantSqueezerTileEntity() {
        super(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK_TILE.get());
        inputSlots = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
                EnchantSqueezerTileEntity.this.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return EnchantmentHelper.isEnchanted(stack);
            }
        };
    }

    //-------------------------------------------------------------------------
    //region Tanks
    private LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createTank);
    private FluidTank createTank() {
        return new FluidTank(SqueezerConfiguration.ENCH_SQUEEZER_TANK_CAPACITY.get(), h -> h.isFluidEqual(new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 1)));
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Energy
    private LazyOptional<WootEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private WootEnergyStorage createEnergy() {
        return new WootEnergyStorage(SqueezerConfiguration.ENCH_SQUEEZER_MAX_ENERGY.get(), SqueezerConfiguration.ENCH_SQUEEZER_MAX_ENERGY_RX.get());
    }

    public int getEnergy() { return energyStorage.map(h -> h.getEnergyStored()).orElse(0); }
    public void setEnergy(int v) { energyStorage.ifPresent(h -> h.setEnergy(v)); }
    //endregion

    //-------------------------------------------------------------------------
    //region Inventory
    public static int INPUT_SLOT = 0;
    private ItemStackHandler inputSlots;
    private final LazyOptional<IItemHandler> inputSlotHandler = LazyOptional.of(() -> inputSlots);
    //endregion

    //-------------------------------------------------------------------------
    //region NBT
    @Override
    public void read(CompoundNBT compoundNBT) {
        CompoundNBT invTag = compoundNBT.getCompound("inv");
        inputSlots.deserializeNBT(invTag);

        CompoundNBT tankTag = compoundNBT.getCompound("tank");
        fluidTank.ifPresent(h -> h.readFromNBT(tankTag));

        CompoundNBT energyTag = compoundNBT.getCompound("energy");
        energyStorage.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(energyTag));

        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        CompoundNBT invTag = inputSlots.serializeNBT();
        compoundNBT.put("inv", invTag);

        fluidTank.ifPresent(h -> {
            CompoundNBT tankTag = h.writeToNBT(new CompoundNBT());
            compoundNBT.put("tank", tankTag);
        });

        energyStorage.ifPresent(h -> {
            CompoundNBT energyTag = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
            compoundNBT.put("energy", energyTag);
        });

        return super.write(compoundNBT);
    }
    //endregion

    //-------------------------------------------------------------------------
    //region WootDebug
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> EnchantSqueezerTileEntity");
        fluidTank.ifPresent(h -> {
            debug.add("     p:" + h.getFluidAmount());
        });
        return debug;
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Container
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.woot.enchsqueezer.name");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new EnchantSqueezerContainer(i, world, pos, playerInventory, playerEntity);
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Client sync
    public int getEnchant() {
        return fluidTank.map(h -> h.getFluidAmount()).orElse(0);
    }

    public void setEnchant(int v) {
        fluidTank.ifPresent(h -> h.setFluid(new FluidStack(FluidSetup.ENCHANT_FLUID.get(), v)));
    }

    private int progress;
    public int getProgress() {
        return calculateProgress();
    }
    public int getClientProgress() { return progress; }
    public void setProgress(int v) { progress = v; }
    //endregion

    //-------------------------------------------------------------------------
    //region Machine Process

    @Override
    protected boolean hasEnergy() {
        return energyStorage.map(e -> e.getEnergyStored() > 0).orElse(false);
    }

    @Override
    protected int useEnergy() {
        return energyStorage.map(e -> e.extractEnergy(SqueezerConfiguration.ENCH_SQUEEZER_ENERGY_PER_TICK.get(), false)).orElse(0);
    }

    @Override
    protected int getRecipeEnergy() {
        return SqueezerConfiguration.ENCH_SQUEEZER_RECIPE_ENERGY.get();
    }

    @Override
    protected void clearRecipe() { }

    @Override
    protected void processFinish() {
        ItemStack itemStack = inputSlots.getStackInSlot(INPUT_SLOT);
        if (itemStack.isEmpty())
            return;

        inputSlots.extractItem(INPUT_SLOT, 1, false);

        int amount = getEnchantAmount(itemStack);
        fluidTank.ifPresent(h -> {
            h.fill(new FluidStack(FluidSetup.ENCHANT_FLUID.get(), amount), IFluidHandler.FluidAction.EXECUTE);
        });

        markDirty();
    }

    @Override
    protected boolean canStart() {
        ItemStack itemStack = inputSlots.getStackInSlot(INPUT_SLOT);
        if (itemStack.isEmpty())
            return false;

        if (fluidTank.map(h -> {
            int amount = getEnchantAmount(itemStack);
            int filled = h.fill(new FluidStack(FluidSetup.ENCHANT_FLUID.get(), amount), IFluidHandler.FluidAction.SIMULATE);
            return amount != filled;
        }).orElse(false)) {
            return false;
        }

        return true;
    }

    @Override
    protected boolean hasValidInput() {
        ItemStack itemStack = inputSlots.getStackInSlot(INPUT_SLOT);
        if (itemStack.isEmpty() || !EnchantmentHelper.isEnchanted(itemStack))
                return false;

        return true;
    }

    @Override
    protected boolean isDisabled() {
        return false;
    }
    //endregion

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null || side == Direction.UP || side == Direction.EAST)
                return inputSlotHandler.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTank.cast();
        } else if (cap == CapabilityEnergy.ENERGY) {
            return energyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    private int getEnchantAmount(ItemStack itemStack) {
        int amount = 0;
        if (!itemStack.isEmpty() && EnchantmentHelper.isEnchanted(itemStack)) {
            ListNBT listNBT;
            if (itemStack.getItem() == Items.ENCHANTED_BOOK)
                listNBT = EnchantedBookItem.getEnchantments(itemStack);
            else
                listNBT = itemStack.getEnchantmentTagList();

            for (int i = 0; i < listNBT.size(); i++) {
                CompoundNBT compoundNBT = listNBT.getCompound(i);
                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryCreate(compoundNBT.getString("id")));
                if (enchantment != null && compoundNBT.contains("lvl"))
                    amount += SqueezerConfiguration.getEnchantFluidAmount(compoundNBT.getInt("lvl"));
            }
        }
        return amount;
    }

    public void dropContents(World world, BlockPos pos) {
        ItemStack itemStack = inputSlots.getStackInSlot(INPUT_SLOT);
        if (!itemStack.isEmpty()) {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            inputSlots.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
        }

        markDirty();
        if (world != null)
            WorldHelper.updateClient(world, pos);
    }

}
