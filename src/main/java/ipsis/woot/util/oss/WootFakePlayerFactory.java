package ipsis.woot.util.oss;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import ipsis.woot.Woot;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

/**
 * Darkhax Dark-Utilities
 * Temporary FakePlayer extension
 * Currently Forge 6358 causes a NPE when potion effects
 * are applied to FakePlayer
 */
@Mod.EventBusSubscriber(modid = Woot.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WootFakePlayerFactory {

    private static Map<GameProfile, WootFakePlayer> fakePlayers = Maps.newHashMap();

    public static WootFakePlayer get(ServerWorld world, GameProfile username) {

        if (!fakePlayers.containsKey(username)) {

            final WootFakePlayer fakePlayer = new WootFakePlayer(world, username);
            fakePlayers.put(username, fakePlayer);
        }

        return fakePlayers.get(username);
    }

    public static void unloadWorld (ServerWorld world) {

        fakePlayers.entrySet().removeIf(entry -> entry.getValue().world == world);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDimensionUnload (WorldEvent.Unload event) {

        if (event.getWorld() instanceof ServerWorld) {

            unloadWorld((ServerWorld) event.getWorld());
        }
    }
}
