package ipsis.woot.modules.generator.blocks;

import ipsis.woot.crafting.ConatusGeneratorRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.fluilds.network.TankPacket;
import ipsis.woot.modules.generator.GeneratorConfiguration;
import ipsis.woot.modules.generator.GeneratorSetup;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootEnergyStorage;
import ipsis.woot.util.WootFluidTank;
import ipsis.woot.util.WootMachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static ipsis.woot.crafting.ConatusGeneratorRecipe.CONATUS_GEN_TYPE;

public class ConatusGeneratorTileEntity extends WootMachineTileEntity implements WootDebug, INamedContainerProvider {

    public ConatusGeneratorTileEntity() {
        super(GeneratorSetup.CONATUS_GENERATOR_BLOCK_TILE.get());
        inputSlots = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                ConatusGeneratorTileEntity.this.onContentsChanged(slot);
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == INPUT_SLOT)
                    return ConatusGeneratorRecipe.isValidCatalyst(stack);
                return false;
            }
        };
    }

    public void configureSides() {
        Direction direction = world.getBlockState(getPos()).get(BlockStateProperties.HORIZONTAL_FACING);
        if (direction == Direction.NORTH) {
            settings.put(Direction.UP, Mode.INPUT);
            settings.put(Direction.DOWN, Mode.OUTPUT);

            settings.put(Direction.WEST, Mode.OUTPUT);
            settings.put(Direction.SOUTH, Mode.OUTPUT);
            settings.put(Direction.EAST, Mode.INPUT);
            settings.put(Direction.NORTH, Mode.INPUT);
        } else if (direction == Direction.SOUTH) {
            settings.put(Direction.UP, Mode.INPUT);
            settings.put(Direction.DOWN, Mode.OUTPUT);

            settings.put(Direction.WEST, Mode.INPUT);
            settings.put(Direction.SOUTH, Mode.INPUT);
            settings.put(Direction.EAST, Mode.OUTPUT);
            settings.put(Direction.NORTH, Mode.OUTPUT);
        } else if (direction == Direction.WEST) {
            settings.put(Direction.UP, Mode.INPUT);
            settings.put(Direction.DOWN, Mode.OUTPUT);

            settings.put(Direction.WEST, Mode.INPUT);
            settings.put(Direction.SOUTH, Mode.OUTPUT);
            settings.put(Direction.EAST, Mode.OUTPUT);
            settings.put(Direction.NORTH, Mode.INPUT);
        } else if (direction == Direction.EAST) {
            settings.put(Direction.UP, Mode.INPUT);
            settings.put(Direction.DOWN, Mode.OUTPUT);

            settings.put(Direction.WEST, Mode.OUTPUT);
            settings.put(Direction.SOUTH, Mode.INPUT);
            settings.put(Direction.EAST, Mode.INPUT);
            settings.put(Direction.NORTH, Mode.OUTPUT);
        }
    }


    private boolean firstTick = true;
    @Override
    public void tick() {

        if (firstTick && world != null) {
            // Configure sides needs to access the block state so cannot do onLoad
            configureSides();
            firstTick = false;
        }

        super.tick();

        if (world.isRemote)
            return;

        if (outputTank.map(WootFluidTank::isEmpty).orElse(true))
            return;

        for (Direction direction : Direction.values()) {

            if (settings.get(direction) != Mode.OUTPUT)
                continue;

            TileEntity te = world.getTileEntity(getPos().offset(direction));
            if (!(te instanceof TileEntity))
                continue;

            LazyOptional<IFluidHandler> lazyOptional = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
            if (lazyOptional.isPresent()) {
                IFluidHandler iFluidHandler = lazyOptional.orElseThrow(NullPointerException::new);
                FluidStack fluidStack = outputTank.map(WootFluidTank::getFluid).orElse(FluidStack.EMPTY);
                if (!fluidStack.isEmpty()) {
                    int filled = iFluidHandler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                    outputTank.ifPresent(f -> f.internalDrain(filled, IFluidHandler.FluidAction.EXECUTE));
                    markDirty();
                }
            }
        }
    }

    //-------------------------------------------------------------------------
    //region Tanks
    private LazyOptional<FluidTank> inputTank = LazyOptional.of(this::createInputTank);
    private LazyOptional<WootFluidTank> outputTank = LazyOptional.of(this::createOutputTank);
    private FluidTank createInputTank() {
        return new FluidTank(GeneratorConfiguration.CONATUS_GEN_INPUT_TANK_CAPACITY.get(),
                h -> ConatusGeneratorRecipe.isValidInput(h));
    }
    private WootFluidTank createOutputTank() {
        return new WootFluidTank(GeneratorConfiguration.CONATUS_GEN_OUTPUT_TANK_CAPACITY.get(),
                h -> h.isFluidEqual(new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1000))).setAccess(false, true);
    }

    public void setInputTankFluid(FluidStack fluid) { inputTank.ifPresent(h -> h.setFluid(fluid));}
    public void setOutputTankFluid(FluidStack fluid) { outputTank.ifPresent(h -> h.setFluid(fluid));}

    public FluidStack getInputTankFluid() { return inputTank.map(h -> h.getFluid()).orElse(FluidStack.EMPTY); }
    public FluidStack getOutputTankFluid() { return outputTank.map(h -> h.getFluid()).orElse(FluidStack.EMPTY); }
    //endregion

    //-------------------------------------------------------------------------
    //region Energy
    private LazyOptional<WootEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private WootEnergyStorage createEnergy() {
        return new WootEnergyStorage(GeneratorConfiguration.CONATUS_GEN_MAX_ENERGY.get(), GeneratorConfiguration.CONATUS_GEN_MAX_ENERGY_RX.get());
    }

    public int getEnergy() {
        return energyStorage.map(h -> h.getEnergyStored()).orElse(0);
    }
    public void setEnergy(int v) { energyStorage.ifPresent(h -> h.setEnergy(v)); }
    //endregion

    //-------------------------------------------------------------------------
    //region Inventory
    public static final int INPUT_SLOT = 0;
    private ItemStackHandler inputSlots;
    private final LazyOptional<IItemHandler> inputSlotHandler = LazyOptional.of(() -> inputSlots);
    //endregion

    //-------------------------------------------------------------------------
    //region NBT
    @Override
    public void read(CompoundNBT compoundNBT) {

        CompoundNBT invInputTag = compoundNBT.getCompound("invInput");
        inputSlots.deserializeNBT(invInputTag);

        CompoundNBT inputTankTag = compoundNBT.getCompound("inputTank");
        inputTank.ifPresent(h -> h.readFromNBT(inputTankTag));

        CompoundNBT outputTankTag = compoundNBT.getCompound("outputTank");
        outputTank.ifPresent(h -> h.readFromNBT(outputTankTag));

        CompoundNBT energyTag = compoundNBT.getCompound("energy");
        energyStorage.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(energyTag));

        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {

        CompoundNBT invInputTag = inputSlots.serializeNBT();
        compoundNBT.put("invInput", invInputTag);

        inputTank.ifPresent(h -> {
            CompoundNBT tankTag = h.writeToNBT(new CompoundNBT());
            compoundNBT.put("inputTank", tankTag);
        });

        outputTank.ifPresent(h -> {
            CompoundNBT tankTag = h.writeToNBT(new CompoundNBT());
            compoundNBT.put("outputTank", tankTag);
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
        debug.add("====> ConatusGeneratorTileEntity");
        debug.add("      Input Tank " + getInputTankFluid().getTranslationKey() + " " + getInputTankFluid().getAmount());
        debug.add("      Output Tank " + getOutputTankFluid().getTranslationKey() + " " + getOutputTankFluid().getAmount());
        debug.add("      Energy " + getEnergy());
        if (currRecipe != null)
            debug.add("      Energy " + currRecipe);
        debug.add("      Settings " + settings);
        return debug;
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Container
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.woot.conatusgenerator.name");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ConatusGeneratorContainer(i, world, pos, playerInventory, playerEntity);
    }
    //endregion

    //-------------------------------------------------------------------------
    //region Client Sync
    private int progress;
    public int getProgress() { return calculateProgress(); }
    public int getClientProgress() { return progress; }
    public void setProgress(int v) { progress = v; }

    private FluidStack clientInTank = FluidStack.EMPTY;
    public FluidStack getClientInTank() { return clientInTank; }
    public void setClientInTank(FluidStack fluidStack) { clientInTank = fluidStack; }
    private FluidStack clientOutTank = FluidStack.EMPTY;
    public FluidStack getClientOutTank() { return clientOutTank; }
    public void setClientOutTank(FluidStack fluidStack) { clientOutTank = fluidStack; }
    //endregion

    //-------------------------------------------------------------------------
    //region Machine Process
    private ConatusGeneratorRecipe currRecipe = null;

    @Override
    protected boolean hasEnergy() {
        return energyStorage.map(e -> e.getEnergyStored() > 0).orElse(false);
    }

    @Override
    protected int useEnergy() {
        return energyStorage.map(e -> e.extractEnergy(GeneratorConfiguration.CONATUS_GEN_ENERGY_PER_TICK.get(), false)).orElse(0);
    }

    @Override
    protected void clearRecipe() { currRecipe = null; }

    @Override
    protected int getRecipeEnergy() { return currRecipe != null ? currRecipe.getEnergy() : 0; }

    @Override
    protected void processFinish() {

        if (currRecipe == null)
            getRecipe();

        if (currRecipe == null) {
            processOff();;
            return;
        }

        ConatusGeneratorRecipe finishedRecipe = currRecipe;

        inputSlots.extractItem(INPUT_SLOT, finishedRecipe.getCatalystCount(), false);
        inputTank.ifPresent(f -> f.drain(finishedRecipe.getInputFluid().getAmount(),
                IFluidHandler.FluidAction.EXECUTE));

        outputTank.ifPresent(f -> f.internalFill(new FluidStack(finishedRecipe.getOutput(),
                finishedRecipe.getOutput().getAmount()), IFluidHandler.FluidAction.EXECUTE));
        markDirty();
    }

    @Override
    protected boolean canStart() {
        if (inputTank.map(f -> f.isEmpty()).orElse(true))
            return false;

        getRecipe();

        if (currRecipe == null)
            return false;

        // Only start if we can hold the output
        if (outputTank.map(h -> {
            int amount = currRecipe.getOutput().getAmount();
            int filled = h.internalFill(new FluidStack(currRecipe.getOutput(), amount), IFluidHandler.FluidAction.SIMULATE);
            return amount != filled;
        }).orElse(false)) {
            return false;
        }

        return true;
    }

    @Override
    protected boolean hasValidInput() {

        if (currRecipe == null)
            getRecipe();

        if (currRecipe == null)
            return false;

        return currRecipe.getCatalystCount() <= inputSlots.getStackInSlot(INPUT_SLOT).getCount();
    }

    @Override
    protected boolean isDisabled() {
        return false;
    }
    //endregion

    private void getRecipe() {
        clearRecipe();

        FluidStack inFluid = inputTank.map(h -> h.getFluid()).orElse(FluidStack.EMPTY);
        if (inFluid.isEmpty()) {
            clearRecipe();
            return;
        }

        List<ConatusGeneratorRecipe> recipes = world.getRecipeManager().getRecipes(
                CONATUS_GEN_TYPE,
                new Inventory(inputSlots.getStackInSlot(INPUT_SLOT)), world);

        for (ConatusGeneratorRecipe recipe : recipes) {
            if (recipe.getInputFluid().isFluidEqual(inFluid)) {
                currRecipe = recipe;
                return;
            }
        }
    }

    public TankPacket getInputTankPacket() {
        return new TankPacket(0, inputTank.map(f -> f.getFluid()).orElse(FluidStack.EMPTY));
    }

    public TankPacket getOutputTankPacket() {
        return new TankPacket(1, outputTank.map(f -> f.getFluid()).orElse(FluidStack.EMPTY));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inputSlotHandler.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (side == null) {
                return inputTank.cast();
            } else if (settings.get(side) == Mode.INPUT) {
                return inputTank.cast();
            } else if (settings.get(side) == Mode.OUTPUT) {
                return outputTank.cast();
            } else {
                return inputTank.cast();
            }
        } else if (cap == CapabilityEnergy.ENERGY) {
            return energyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    public void dropContents(World world, BlockPos pos) {

        List<ItemStack> drops = new ArrayList<>();
        ItemStack itemStack = inputSlots.getStackInSlot(INPUT_SLOT);
        if (!itemStack.isEmpty()) {
            drops.add(itemStack);
            inputSlots.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
        }
        super.dropContents(drops);
    }
}
