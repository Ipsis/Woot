package ipsis.woot.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.items.MobShardItem;
import mezz.jei.api.constants.VanillaTypes;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class AnvilRecipe implements IRecipe<IInventory> {

    private final NonNullList<Ingredient> ingredients;
    private final Ingredient baseIngredient;
    private final Item result;
    private final int count;
    private final ResourceLocation id;
    private final IRecipeType<?> type;

    public AnvilRecipe(ResourceLocation id, Ingredient baseIngredient, IItemProvider result, int count, NonNullList<Ingredient> ingredients) {
        this.id = id;
        this.baseIngredient = baseIngredient;
        this.result = result.asItem();
        this.count = count;
        this.ingredients = ingredients;
        this.type = ANVIL_TYPE;


        if (baseIngredient.getMatchingStacks().length == 1 && baseIngredient.getMatchingStacks()[0].getItem() == FactorySetup.MOB_SHARD_ITEM.get()) {
            ItemStack itemStack = new ItemStack(FactorySetup.MOB_SHARD_ITEM.get());
            MobShardItem.setJEIEnderShard(itemStack);
            inputs.add(Arrays.asList(itemStack));
        } else {
            inputs.add(Arrays.asList(baseIngredient.getMatchingStacks()));
        }

        for (Ingredient i : ingredients)
            inputs.add(Arrays.asList(i.getMatchingStacks()));
    }

    public Ingredient getBaseIngredient() { return this.baseIngredient; }
    public NonNullList<Ingredient> getIngredients() { return this.ingredients; }
    public ItemStack getOutput() { return new ItemStack(result, count); }

    public static final IRecipeType<AnvilRecipe> ANVIL_TYPE = IRecipeType.register("anvil");

    @ObjectHolder("woot:anvil")
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = null;

    public static AnvilRecipe anvilRecipe(ResourceLocation id, Ingredient baseIngredient, IItemProvider result, NonNullList<Ingredient> ingredients) {
        return new AnvilRecipe(id, baseIngredient, result, 1, ingredients);
    }
    public static AnvilRecipe anvilRecipe(ResourceLocation id, Ingredient baseIngredient, IItemProvider result, int count, NonNullList<Ingredient> ingredients) {
        return new AnvilRecipe(id, baseIngredient, result, count, ingredients);
    }

    /**
     * Valid inputs
     */
    private static List<ItemStack> validInputs = new ArrayList<>();
    public static void clearValidInputs() { validInputs.clear(); }
    public static void addValidInput(ItemStack itemStack) { validInputs.add(itemStack); }
    public static boolean isValidInput(ItemStack itemStack) {
        for (ItemStack i : validInputs) {
            if (i.isItemEqual(itemStack))
                return true;
        }
        return false;
    }

    /**
     * Jei
     */
    private List<List<ItemStack>> inputs = new ArrayList<>();
    public List<List<ItemStack>> getInputs() { return inputs; }

    /**
     * IRecipe
     * Matches base item and all ingredients
     */
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if (!baseIngredient.test(inv.getStackInSlot(0)))
            return false;

        int count = 0;
        for (int i = 1; i < 4; i++) {
            if (!inv.getStackInSlot(i).isEmpty())
                count++;
        }

        if (ingredients.size() != count)
            return false;

        List<Integer> matchedSlots = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            for (int i = 1; i < 4; i++) {
                if (!matchedSlots.contains(i) && ingredient.test(inv.getStackInSlot(i))) {
                    // found ingredient in one of the slots
                    matchedSlots.add(i);
                    break;
                }
            }
        }

        if (matchedSlots.size() == ingredients.size())
            return true;

        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
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

    /**
     * IFinishedRecipe
     */
    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        if (this.baseIngredient == null)
            throw new IllegalStateException("No valid base ingredient for recipe " + id);
        consumer.accept(new Finished(id, this.baseIngredient, this.result, this.count, this.ingredients));
    }
    public static class Finished implements IFinishedRecipe {

        private final NonNullList<Ingredient> ingredients;
        private final Ingredient baseIngredient;
        private final Item result;
        private final int count;
        private final ResourceLocation id;

        public Finished(ResourceLocation id, Ingredient baseIngredient, Item result, int count, NonNullList<Ingredient> ingredients) {
            this.id = id;
            this.baseIngredient = baseIngredient;
            this.result = result;
            this.count = count;
            this.ingredients = ingredients;
        }

        @Override
        public void serialize(JsonObject json) {
            json.add("base", this.baseIngredient.serialize());
            JsonArray array = new JsonArray();
            for (Ingredient i : this.ingredients)
                array.add(i.serialize());
            json.add("ingredients", array);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1)
                jsonObject.addProperty("count", this.count);
            json.add("result", jsonObject);
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }


}
