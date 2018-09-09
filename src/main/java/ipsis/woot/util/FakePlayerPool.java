package ipsis.woot.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FakePlayerPool {

    private static final String NO_LOOTING_NAME = "[woot]";
    private static final String LOOTING_1_NAME = "[woot_i]";
    private static final String LOOTING_2_NAME = "[woot_ii]";
    private static final String LOOTING_3_NAME = "[woot_iii]";

    private static GameProfile WOOT_GP_NO_LOOTING = new GameProfile(UUID.nameUUIDFromBytes(NO_LOOTING_NAME.getBytes()), NO_LOOTING_NAME);
    private static GameProfile WOOT_GP_LOOTING_1 = new GameProfile(UUID.nameUUIDFromBytes(LOOTING_1_NAME.getBytes()), LOOTING_1_NAME);
    private static GameProfile WOOT_GP_LOOTING_2 = new GameProfile(UUID.nameUUIDFromBytes(LOOTING_2_NAME.getBytes()), LOOTING_2_NAME);
    private static GameProfile WOOT_GP_LOOTING_3 = new GameProfile(UUID.nameUUIDFromBytes(LOOTING_3_NAME.getBytes()), LOOTING_3_NAME);

    private static Map<Integer, FakePlayer> fakePlayerMap;

    private static void addFakePlayer(FakePlayer fp, int looting, Enchantment enchantment) {

        if (fp == null)
            return;

        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        if (looting > 0 && enchantment != null)
            sword.addEnchantment(enchantment, looting);
        fp.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, sword);
        fakePlayerMap.put(looting, fp);
    }

    private static void init(WorldServer worldServer) {

        fakePlayerMap = new HashMap<>();

        Enchantment enchantmentLooting = Enchantment.getEnchantmentByLocation("looting");
        if (enchantmentLooting == null)
            LogHelper.error("FakePlayerPool - failed to lookup enchantment looting");

        addFakePlayer(FakePlayerFactory.get(worldServer, WOOT_GP_NO_LOOTING), 0, enchantmentLooting);
        addFakePlayer(FakePlayerFactory.get(worldServer, WOOT_GP_LOOTING_1), 1, enchantmentLooting);
        addFakePlayer(FakePlayerFactory.get(worldServer, WOOT_GP_LOOTING_2), 2, enchantmentLooting);
        addFakePlayer(FakePlayerFactory.get(worldServer, WOOT_GP_LOOTING_3), 3, enchantmentLooting);
    }

    public static @Nullable
    FakePlayer getFakePlayer(WorldServer worldServer, int looting) {

        if (fakePlayerMap == null)
            init(worldServer);

        looting = MiscUtils.clampLooting(looting);
        return fakePlayerMap.get(looting);
    }

    public static boolean isWootFakePlayer(@Nonnull  Entity entity) {

        if (!(entity instanceof FakePlayer))
            return false;

        FakePlayer fp = (FakePlayer)entity;
        UUID uuid = fp.getUniqueID();

        return WOOT_GP_NO_LOOTING.getId().equals(uuid) ||
                WOOT_GP_LOOTING_1.getId().equals(uuid) ||
                WOOT_GP_LOOTING_2.getId().equals(uuid) ||
                WOOT_GP_LOOTING_3.getId().equals(uuid);
    }

}
