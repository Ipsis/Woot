package ipsis.woot.crafting.ingredient;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.IngredientNBT;

import javax.annotation.Nullable;
import java.util.Map;

public class IngredientEnchantedBook extends IngredientNBT {

    private final ItemStack stack;
    private final int enchantment_id;
    private final int enchantment_lvl;
    protected IngredientEnchantedBook(ItemStack stack, int id, int lvl) {

        super(stack);
        this.stack = stack;
        this.enchantment_id = id;
        this.enchantment_lvl = lvl;
    }

    @Override
    public boolean apply(@Nullable ItemStack input) {

        if (input == null || this.stack.getItem() != input.getItem()  || this.stack.getItemDamage() != input.getItemDamage())
            return false;

        Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(input);
        // Don't allow books with multiple enchantments
        if (enchantmentMap.size() != 1)
            return false;

        for (Enchantment enchantment : enchantmentMap.keySet()) {

            int id = Enchantment.getEnchantmentID(enchantment);
            int lvl = enchantmentMap.get(enchantment);
            if (id == enchantment_id && lvl == enchantment_lvl)
                return true;
        }

        return false;
    }
}
