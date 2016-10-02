package ipsis.woot.init;

import ipsis.woot.enchantment.EnchantmentDecapitate;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModEnchantments {

    public static void preInit() {

    }

    public static void init() {
    }

    public static Enchantment DECAPITATE;
    public static void postInit() {

        DECAPITATE = new EnchantmentDecapitate();
        GameRegistry.register(DECAPITATE);
    }
}
