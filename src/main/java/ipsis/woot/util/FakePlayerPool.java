package ipsis.woot.util;

import com.mojang.authlib.GameProfile;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.reference.Settings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.HashMap;
import java.util.UUID;

public class FakePlayerPool {

    static GameProfile WOOT_GAME_PROFILE = new GameProfile(UUID.nameUUIDFromBytes("[Woot]".getBytes()), "[Woot]");
    static GameProfile WOOT_GAME_PROFILE_I = new GameProfile(UUID.nameUUIDFromBytes("[Woot_I]".getBytes()), "[Woot I]");
    static GameProfile WOOT_GAME_PROFILE_II = new GameProfile(UUID.nameUUIDFromBytes("[Woot_II]".getBytes()), "[Woot II]");
    static GameProfile WOOT_GAME_PROFILE_III = new GameProfile(UUID.nameUUIDFromBytes("[Woot_III]".getBytes()), "[Woot III]");

    static HashMap<EnumEnchantKey, FakePlayer> fakePlayerHashMap;

    static void init(WorldServer world) {

        fakePlayerHashMap  = new HashMap<EnumEnchantKey, FakePlayer>();

        FakePlayer fakePlayer = FakePlayerFactory.get(world, WOOT_GAME_PROFILE);
        FakePlayer fakePlayerI = FakePlayerFactory.get(world, WOOT_GAME_PROFILE_I);
        FakePlayer fakePlayerII = FakePlayerFactory.get(world, WOOT_GAME_PROFILE_II);
        FakePlayer fakePlayerIII = FakePlayerFactory.get(world, WOOT_GAME_PROFILE_III);
        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        ItemStack swordI = new ItemStack(Items.DIAMOND_SWORD);
        ItemStack swordII = new ItemStack(Items.DIAMOND_SWORD);
        ItemStack swordIII = new ItemStack(Items.DIAMOND_SWORD);

        swordI.addEnchantment(Enchantment.getEnchantmentByLocation("looting"), Settings.lootingILevel);
        swordII.addEnchantment(Enchantment.getEnchantmentByLocation("looting"), Settings.lootingIIILevel);
        swordIII.addEnchantment(Enchantment.getEnchantmentByLocation("looting"), Settings.lootingIIILevel);

        fakePlayer.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, sword);
        fakePlayerI.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, swordI);
        fakePlayerII.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, swordII);
        fakePlayerIII.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, swordIII);

        fakePlayerHashMap.put(EnumEnchantKey.NO_ENCHANT, fakePlayer);
        fakePlayerHashMap.put(EnumEnchantKey.LOOTING_I, fakePlayerI);
        fakePlayerHashMap.put(EnumEnchantKey.LOOTING_II, fakePlayerII);
        fakePlayerHashMap.put(EnumEnchantKey.LOOTING_III, fakePlayerIII);
    }

    public static FakePlayer getFakePlayer(WorldServer world, EnumEnchantKey enumEnchantKey) {

        if (fakePlayerHashMap == null)
            init(world);

        return fakePlayerHashMap.get(enumEnchantKey);
    }
}
