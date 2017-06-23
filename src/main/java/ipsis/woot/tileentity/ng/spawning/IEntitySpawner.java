package ipsis.woot.tileentity.ng.spawning;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.tileentity.ng.WootMobName;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEntitySpawner {

    void spawn(WootMobName wootMobName, EnumEnchantKey key, World world, BlockPos pos);
}
