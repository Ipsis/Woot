package ipsis.woot.util;

import com.mojang.authlib.GameProfile;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.reference.Settings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.UUID;

public class FakePlayerUtil {

    static GameProfile WOOT_GAME_PROFILE = new GameProfile(UUID.randomUUID(), "[Woot]");
    static FakePlayer fakePlayer;

    public static FakePlayer getFakePlayer(WorldServer world) {

        if (fakePlayer == null)
            fakePlayer = FakePlayerFactory.get(world, WOOT_GAME_PROFILE);
        return fakePlayer;
    }

    static ItemStack swordLootingI;
    static ItemStack swordLootingII;
    static ItemStack swordLootingIII;
    public static void setLooting(FakePlayer f, EnumEnchantKey enchantKey) {

        ItemStack itemStack = null;
        createSword(enchantKey);
        if (enchantKey == EnumEnchantKey.LOOTING_I)
            itemStack = swordLootingI;
        else if (enchantKey == EnumEnchantKey.LOOTING_II)
            itemStack = swordLootingII;
        else if (enchantKey == EnumEnchantKey.LOOTING_III)
            itemStack = swordLootingIII;

        if (f != null && itemStack != null)
            f.setCurrentItemOrArmor(0, itemStack);
    }

    static void createSword(EnumEnchantKey enchantKey) {

        ItemStack itemStack = new ItemStack(Items.iron_sword);
        if (enchantKey == EnumEnchantKey.LOOTING_I && swordLootingI == null) {
            itemStack.addEnchantment(Enchantment.looting, Settings.lootingILevel);
            swordLootingI = itemStack;
        } else if (enchantKey == EnumEnchantKey.LOOTING_II && swordLootingII == null) {
            itemStack.addEnchantment(Enchantment.looting, Settings.lootingIILevel);
            swordLootingII = itemStack;
        } else if (enchantKey == EnumEnchantKey.LOOTING_III && swordLootingIII == null) {
            itemStack.addEnchantment(Enchantment.looting, Settings.lootingIIILevel);
            swordLootingIII = itemStack;
        }
    }
}
