package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootEnergyStorage;
import ipsis.woot.util.helper.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EnchantSqueezerTileEntity extends TileEntity implements ITickableTileEntity, WootDebug, INamedContainerProvider {

    public EnchantSqueezerTileEntity() {
        super(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK_TILE.get());
    }

    @Override
    public void tick() {
       if (world.isRemote)
           return;

       if (world.getGameTime() % 20 != 0)
           return;

       itemHandler.ifPresent(h -> {
           ItemStack itemStack = h.getStackInSlot(0);
           if (!itemStack.isEmpty() && EnchantmentHelper.isEnchanted(itemStack)) {
               int amount = 0;

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
               FluidStack fluidStack = new FluidStack(FluidSetup.ENCHANT_FLUID.get(), amount);
               fluidTank.ifPresent(t -> {
                   t.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                   h.extractItem(0, 1, false);
                   markDirty();
               });
           }
       });
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();
        else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return fluidTank.cast();
        else if (cap == CapabilityEnergy.ENERGY)
            return energyStorage.cast();
        return super.getCapability(cap, side);
    }

    public int getEnchant() {
        AtomicInteger v = new AtomicInteger(0);
        fluidTank.ifPresent(h -> {
            v.set(h.getFluidAmount());
        });
        return v.get();
    }

    public void setEnchant(int v) {
        fluidTank.ifPresent(h -> {
            h.setFluid(new FluidStack(FluidSetup.ENCHANT_FLUID.get(), v));
        });
    }
    public int getEnergy() {
        return energyStorage.map(h -> h.getEnergyStored()).orElse(0);
    }
    public void setEnergy(int v) { energyStorage.ifPresent(h -> h.setEnergy(v)); }

    /**
     * Tank
     */
    private LazyOptional<FluidTank> fluidTank = LazyOptional.of(this::createTank);
    private FluidTank createTank() {
        return new FluidTank(SqueezerConfiguration.ENCH_SQUEEZER_TANK_CAPACITY.get(), h -> h.isFluidEqual(new FluidStack(FluidSetup.ENCHANT_FLUID.get(), 1)));
    }

    /**
     * Inventory
     */
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private IItemHandler createItemHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return EnchantmentHelper.isEnchanted(stack);
            }
        };
    }

    /**
     * Energy
     */
    private LazyOptional<WootEnergyStorage> energyStorage = LazyOptional.of(this::createEnergy);
    private WootEnergyStorage createEnergy() {
        return new WootEnergyStorage(SqueezerConfiguration.ENCH_SQUEEZER_MAX_ENERGY.get(), SqueezerConfiguration.ENCH_SQUEEZER_MAX_ENERGY_RX.get());
    }

    /**
     * NBT
     */

    public void read(CompoundNBT compoundNBT) {
        CompoundNBT invTag = compoundNBT.getCompound("inv");
        itemHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(invTag));

        CompoundNBT tankTag = compoundNBT.getCompound("tank");
        fluidTank.ifPresent(h -> h.readFromNBT(tankTag));

        CompoundNBT energyTag = compoundNBT.getCompound("energy");
        energyStorage.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(energyTag));

        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        itemHandler.ifPresent(h -> {
            CompoundNBT invTag = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            compoundNBT.put("inv", invTag);
        });

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

    /**
     * INamedContainerProvider
     */
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.woot.enchsqueezer.name");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new EnchantSqueezerContainer(i, world, pos, playerInventory, playerEntity);
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> EnchantSqueezerTileEntity");
        fluidTank.ifPresent(h -> {
            debug.add("     p:" + h.getFluidAmount());
        });
        return debug;
    }
}
