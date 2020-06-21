package ipsis.woot.util.oss;

import com.mojang.authlib.GameProfile;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;

/**
 * Darkhax Dark-Utilities
 * Temporary FakePlayer extension
 * Currently Forge 6358 causes a NPE when potion effects
 * are applied to FakePlayer
 */

public class WootFakePlayer extends FakePlayer {

    public WootFakePlayer(ServerWorld world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    @Override
    public boolean isPotionApplicable(EffectInstance potioneffectIn) {
        return false;
    }
}
