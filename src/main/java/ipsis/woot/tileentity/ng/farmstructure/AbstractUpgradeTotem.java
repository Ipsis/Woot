package ipsis.woot.tileentity.ng.farmstructure;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractUpgradeTotem {

    EnumSpawnerUpgrade spawnerUpgrade;
    int spawnerUpgradeLevel;
    Set<BlockPos> blockPosList;
    BlockPos origin;
    World world;

    public boolean isValid() {

        return spawnerUpgrade != null && spawnerUpgradeLevel != 0;
    }

    AbstractUpgradeTotem(@Nonnull World world, @Nonnull BlockPos origin) {

        spawnerUpgrade = null;
        spawnerUpgradeLevel = 0;
        blockPosList = new HashSet<>();
        this.world = world;
        this.origin = origin;
    }

    abstract public void scan();
}
