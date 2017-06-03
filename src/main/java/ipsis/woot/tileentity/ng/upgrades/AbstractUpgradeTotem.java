package ipsis.woot.tileentity.ng.upgrades;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUpgradeTotem {

    EnumSpawnerUpgrade spawnerUpgrade;
    int spawnerUpgradeLevel;
    List<BlockPos> blockPosList;
    BlockPos origin;
    World world;

    public boolean isValid() {

        return spawnerUpgrade != null && spawnerUpgradeLevel != 0;
    }

    AbstractUpgradeTotem(@Nonnull World world, @Nonnull BlockPos origin) {

        spawnerUpgrade = null;
        spawnerUpgradeLevel = 0;
        blockPosList = new ArrayList<>();
        this.world = world;
        this.origin = origin;
    }

    abstract public void scan();
}
