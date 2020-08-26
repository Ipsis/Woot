package ipsis.woot.crafting;

import ipsis.woot.Woot;
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

    public static final IRecipeType<FactoryRecipe> FACTORY_TYPE = IRecipeType.register(Woot.MODID + ":factory");
    private final ResourceLocation id;
    private final IRecipeType<?> type;
    private final NonNullList<ItemStack> items;
    private final NonNullList<FluidStack> fluids;
    private final FakeMob fakeMob;
    private final NonNullList<Drop> drops;

    @ObjectHolder("woot:factory")
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = null;

    public FactoryRecipe(ResourceLocation id, FakeMob fakeMob, NonNullList<ItemStack> items, NonNullList<FluidStack> fluids, NonNullList<Drop> drops) {
        this.id = id;
        this.type = FACTORY_TYPE;
        this.items = items;
        this.fluids = fluids;
        this.fakeMob = fakeMob;
        this.drops = drops;
    }

    public FakeMob getFakeMob() { return this.fakeMob; }
    public NonNullList<ItemStack> getItems() { return this.items; }
    public NonNullList<FluidStack> getFluids() { return this.fluids; }
    public NonNullList<Drop> getDrops() { return this.drops; }

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

    public static class Drop {
        public ItemStack itemStack;
        public int[] stackSizes = new int[4];
        public float[] dropChance = new float[4];

        public Drop(ItemStack itemStack) {
            this.itemStack = itemStack;

        }

        public Drop(ItemStack itemStack, int s0, int s1, int s2, int s3, float d0, float d1, float d2, float d3) {
            this.itemStack = itemStack;
            this.stackSizes[0] = s0;
            this.stackSizes[1] = s1;
            this.stackSizes[2] = s2;
            this.stackSizes[3] = s3;
            this.dropChance[0] = d0;
            this.dropChance[1] = d1;
            this.dropChance[2] = d2;
            this.dropChance[3] = d3;
        }


    }
}
