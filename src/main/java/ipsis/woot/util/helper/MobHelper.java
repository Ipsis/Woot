package ipsis.woot.util.helper;

import ipsis.woot.factory.FactoryTier;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MobHelper {

    public static boolean canGenerateFromTier(@Nonnull World world, @Nonnull FakeMobKey fakeMobKey, FactoryTier factoryTier) {
        return true;
    }
}
