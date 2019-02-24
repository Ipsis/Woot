package ipsis.woot.enchantment;

import ipsis.woot.Woot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentHeadhunter extends Enchantment {

    private static final String BASENAME = "headhunter";

    public EnchantmentHeadhunter() {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        setRegistryName(Woot.MODID, BASENAME);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 16;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 60;
    }
}
