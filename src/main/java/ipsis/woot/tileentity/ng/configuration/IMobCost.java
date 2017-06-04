package ipsis.woot.tileentity.ng.configuration;

import ipsis.woot.tileentity.ng.WootMobName;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IMobCost {

    int getMobSpawnCost(@Nonnull World world, @Nonnull WootMobName wootMobName);
}
