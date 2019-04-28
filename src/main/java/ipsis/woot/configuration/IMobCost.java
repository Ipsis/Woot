package ipsis.woot.configuration;

import ipsis.woot.util.WootMobName;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IMobCost {

    int getMobSpawnCost(@Nonnull World world, @Nonnull WootMobName wootMobName);
    boolean isAnimal(@Nonnull World world, @Nonnull WootMobName wootMobName);
}
