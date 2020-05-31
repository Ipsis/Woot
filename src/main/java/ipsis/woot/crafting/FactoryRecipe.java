package ipsis.woot.crafting;

import ipsis.woot.util.FakeMob;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ObjectHolder;

public class FactoryRecipe implements IRecipe<IInventory> {

    public static final IRecipeType<FactoryRecipe> FACTORY_TYPE = IRecipeType.register("factory");
    private final ResourceLocation id;
    private final IRecipeType<?> type;
    private final NonNullList<ItemStack> items;
    private final NonNullList<FluidStack> fluids;
    private final FakeMob fakeMob;

    @ObjectHolder("woot:factory")
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = null;

    public FactoryRecipe(ResourceLocation id, FakeMob fakeMob, NonNullList<ItemStack> items, NonNullList<FluidStack> fluids) {
        this.id = id;
        this.type = FACTORY_TYPE;
        this.items = items;
        this.fluids = fluids;
        this.fakeMob = fakeMob;
    }

    public FakeMob getFakeMob() { return this.fakeMob; }
    public NonNullList<ItemStack> getItems() { return this.items; }
    public NonNullList<FluidStack> getFluids() { return this.fluids; }

    // IRecipe<IInventory>
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return type;
    }
}
