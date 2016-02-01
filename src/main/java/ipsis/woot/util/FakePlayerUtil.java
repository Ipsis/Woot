package ipsis.woot.util;

import com.mojang.authlib.GameProfile;
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
}
