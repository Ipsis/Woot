package ipsis.woot.crafting.ingredient;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.IngredientNBT;

import javax.annotation.Nullable;
import java.util.Map;

public class IngredientEnchantedBook extends IngredientNBT {

    private final ItemStack stack;
    private final Enchantment enchantment;
    private final int enchantment_lvl;
    protected IngredientEnchantedBook(ItemStack stack, Enchantment enchantment, int lvl) {

        super(stack);
        this.stack = stack;
        this.enchantment_lvl = lvl;
        this.enchantment = enchantment;
    }

    @Override
    public boolean apply(@Nullable ItemStack input) {

        if (input == null || this.stack.getItem() != input.getItem()  || this.stack.getItemDamage() != input.getItemDamage())
            return false;

        Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(input);
        // Don't allow books with multiple enchantments
        if (enchantmentMap.size() != 1)
            return false;

        for (Enchantment enchantment2 : enchantmentMap.keySet()) {

            int lvl = enchantmentMap.get(enchantment2);
            if (enchantment2.equals(enchantment) && lvl == enchantment_lvl)
                return true;
        }

        return false;
    }
}
