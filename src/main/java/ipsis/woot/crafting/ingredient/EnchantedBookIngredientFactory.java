package ipsis.woot.crafting.ingredient;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class EnchantedBookIngredientFactory implements IIngredientFactory{

    @Nonnull
    @Override
    public Ingredient parse(JsonContext context, JsonObject json) {

        final ItemStack itemStack = CraftingHelper.getItemStack(json, context);

        int enchantId = JsonUtils.getInt(json, "enchant_id");
        int enchantLvl = JsonUtils.getInt(json, "enchant_lvl");

        if (Enchantment.getEnchantmentByID(enchantId) == null)
            throw new JsonSyntaxException("Unknown enchantment id '" + enchantId + "'");

        Enchantment enchantment = Enchantment.getEnchantmentByID(enchantId);
        Map<Enchantment, Integer> enchMap = Collections.singletonMap(enchantment, enchantLvl);
        EnchantmentHelper.setEnchantments(enchMap, itemStack);

        return new IngredientEnchantedBook(itemStack, enchantId, enchantLvl);
    }
}
