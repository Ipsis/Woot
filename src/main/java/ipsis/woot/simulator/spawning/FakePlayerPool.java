package ipsis.woot.simulator.spawning;

import com.mojang.authlib.GameProfile;
import ipsis.woot.Woot;
import ipsis.woot.util.helper.MathHelper;
import ipsis.woot.util.oss.WootFakePlayer;
import ipsis.woot.util.oss.WootFakePlayerFactory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FakePlayerPool {

    private static final String LOOT_0 = "[woot_0]";
    private static final String LOOT_1 = "[woot_1]";
    private static final String LOOT_2 = "[woot_2]";
    private static final String LOOT_3 = "[woot_3]";

    private static final GameProfile GP_LOOT_0 = new GameProfile(UUID.nameUUIDFromBytes(LOOT_0.getBytes()), LOOT_0);
    private static final GameProfile GP_LOOT_1 = new GameProfile(UUID.nameUUIDFromBytes(LOOT_1.getBytes()), LOOT_1);
    private static final GameProfile GP_LOOT_2 = new GameProfile(UUID.nameUUIDFromBytes(LOOT_2.getBytes()), LOOT_2);
    private static final GameProfile GP_LOOT_3 = new GameProfile(UUID.nameUUIDFromBytes(LOOT_3.getBytes()), LOOT_3);

    private static Map<Integer, WootFakePlayer> fakePlayerMap;

    private static void addFakePlayer(@Nonnull WootFakePlayer fakePlayer, int looting, Enchantment enchantment) {
        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        if (looting > 0 && enchantment != null)
            sword.addEnchantment(enchantment, looting);
        fakePlayer.setItemStackToSlot(EquipmentSlotType.MAINHAND, sword);
        fakePlayerMap.put(looting, fakePlayer);
    }

    private static void init(@Nonnull ServerWorld world) {
        fakePlayerMap = new HashMap<>();
        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("minecraft", "looting"));
        if (enchantment == null)
            Woot.setup.getLogger().warn("FakePlayerPool failed to find looting enchantment");

        addFakePlayer(WootFakePlayerFactory.get(world, GP_LOOT_0), 0, enchantment);
        addFakePlayer(WootFakePlayerFactory.get(world, GP_LOOT_1), 1, enchantment);
        addFakePlayer(WootFakePlayerFactory.get(world, GP_LOOT_2), 2, enchantment);
        addFakePlayer(WootFakePlayerFactory.get(world, GP_LOOT_3), 3, enchantment);
    }

    public static @Nullable FakePlayer getFakePlayer(@Nonnull ServerWorld world, int looting) {
        if (fakePlayerMap == null)
            init(world);

        return fakePlayerMap.get(MathHelper.clampLooting(looting));
    }

    public static boolean isFakePlayer(@Nonnull Entity entity) {
        if (!(entity instanceof FakePlayer))
            return false;

        FakePlayer fp = (FakePlayer)entity;
        UUID uuid = fp.getUniqueID();
        return GP_LOOT_0.getId().equals(uuid) || GP_LOOT_1.getId().equals(uuid) || GP_LOOT_2.getId().equals(uuid) || GP_LOOT_3.getId().equals(uuid);
    }


}
