package ipsis.woot.modules.factory.recipe;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StorageHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Currently running recipe
 */
public class HeartRecipe {

    private int numTicks;
    private int numUnits;
    public HashMap<FakeMob, List<ItemStack>> items = new HashMap<>();
    public HashMap<FakeMob, List<FluidStack>> fluids = new HashMap<>();

    private boolean hasItemIngredients;
    private boolean hasFluidIngredients;
    private List<Ingredient> ingredients;
    public boolean hasItemIngredients() {
        return hasItemIngredients;
    }
    public boolean hasFluidIngredients() {
        return hasFluidIngredients;
    }

    public int getNumTicks() {
        return numTicks;
    }

    public int getNumUnits() {
        return numUnits;
    }

    private HeartRecipe() {
        numTicks = 1;
        numUnits = 1;
        hasFluidIngredients = false;
        hasItemIngredients = false;
        ingredients = new ArrayList<>();
    }

    public HeartRecipe(int numTicks, int numUnits) {
        this();
        this.numTicks = MathHelper.clamp(numTicks, 1, Integer.MAX_VALUE);
        this.numUnits = MathHelper.clamp(numUnits, 1, Integer.MAX_VALUE);
    }

    public void addIngredient(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return;

        AtomicBoolean added = new AtomicBoolean(false);
        ingredients.forEach(i -> {
            if (i instanceof ItemIngredient) {
                if (((ItemIngredient) i).itemStack.isItemEqual(itemStack)) {
                    i.addAmount(itemStack.getCount());
                    added.set(true);
                }
            }
        });

        if (added.get() == false)
            ingredients.add(new ItemIngredient(itemStack));

        hasItemIngredients = true;
    }

    public void addIngredient(FluidStack fluidStack) {
        if (fluidStack.isEmpty())
            return;

        AtomicBoolean added = new AtomicBoolean(false);
        ingredients.forEach(i -> {
            if (i instanceof FluidIngredient) {
                if (((FluidIngredient) i).fluidStack.isFluidEqual(fluidStack)) {
                    i.addAmount(fluidStack.getAmount());
                    added.set(true);
                }
            }
        });

        if (added.get() == false)
            ingredients.add(new FluidIngredient(fluidStack));

        ingredients.add(new FluidIngredient(fluidStack));
        hasFluidIngredients = true;
    }

    public void addItem(FakeMob fakeMob, ItemStack itemStack) {
        if (!items.containsKey(fakeMob))
            items.put(fakeMob, new ArrayList<>());

        if (!itemStack.isEmpty())
            items.get(fakeMob).add(itemStack.copy());
    }

    public void addFluid(FakeMob fakeMob, FluidStack fluidStack) {
        if (!fluids.containsKey(fakeMob))
            fluids.put(fakeMob, new ArrayList<>());

        if (!fluidStack.isEmpty())
            fluids.get(fakeMob).add(fluidStack.copy());
    }

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "numTicks=" + numTicks +
                ", numUnits=" + numUnits +
                ", ingredients=" + ingredients.size() +
                ", items=" + items.size() +
                ", fluids=" + fluids.size() +
                '}';
    }

    public static abstract class Ingredient {
        int amount;
        public Ingredient() {
            amount = 0;
        }
        public void addAmount(int amount) {
            if (amount > 0)
                this.amount += amount;
        }

        public int getAmount() {
            return amount;
        }

        public abstract Ingredient copy();
    }
    public static class ItemIngredient extends Ingredient {
        private ItemStack itemStack;
        public ItemIngredient(ItemStack itemStack) {
            super();
            this.itemStack = itemStack.copy();
        }

        public ItemIngredient(ItemIngredient itemIngredient) {
            super();
            this.itemStack = itemIngredient.itemStack.copy();
            this.amount = itemIngredient.amount;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public ItemIngredient copy() {
            ItemIngredient itemIngredient = new ItemIngredient(this.itemStack);
            itemIngredient.amount = this.amount;
            return itemIngredient;
        }
    }
    public static class FluidIngredient extends Ingredient {
        private FluidStack fluidStack;
        public FluidIngredient(FluidStack fluidStack) {
            super();
            this.fluidStack = fluidStack.copy();
        }

        public FluidIngredient(FluidIngredient fluidIngredient) {
            super();
            this.fluidStack = fluidIngredient.fluidStack.copy();
            this.amount = fluidIngredient.amount;
        }

        public FluidStack getFluidStack() {
            return fluidStack;
        }

        public FluidIngredient copy() {
            FluidIngredient fluidIngredient = new FluidIngredient(this.fluidStack);
            fluidIngredient.amount = this.amount;
            return fluidIngredient;
        }
    }

    public boolean canFindIngredients(FormedSetup formedSetup) {

        if (ingredients.isEmpty())
            return true;

        List<LazyOptional<IFluidHandler>> fluidHandlers = formedSetup.getImportFluidHandlers();
        List<LazyOptional<IItemHandler>> itemHandlers = formedSetup.getImportItemHandlers();

        if (hasFluidIngredients && fluidHandlers.isEmpty())
            return false;

        if (hasItemIngredients && itemHandlers.isEmpty())
            return false;

        for (Ingredient i : ingredients) {
            if (i instanceof ItemIngredient) {
                int count = StorageHelper.getCount(((ItemIngredient) i).itemStack, itemHandlers);
                if (count == 0 || count < i.amount)
                    return false;
            } else if (i instanceof  FluidIngredient) {
                int amount = StorageHelper.getAmount(((FluidIngredient) i).fluidStack, fluidHandlers);
                if (amount == 0 || amount < i.amount)
                    return false;
            }
        }

        return true;
    }
}
