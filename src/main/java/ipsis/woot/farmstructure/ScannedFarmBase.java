package ipsis.woot.farmstructure;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class ScannedFarmBase {

    private Set<BlockPos> blocks = new HashSet<>();
    EnumMobFactoryTier tier = null;

    public static boolean isEqual(ScannedFarmBase a, ScannedFarmBase b) {

        if (a == null || b == null)
            return false;

        if (a.tier != b.tier)
            return false;

        if (a.blocks.size() != b.blocks.size())
            return false;

        return a.blocks.containsAll(b.blocks);
    }

    public boolean isValid() {

        return tier != null;
    }

    public void addBlocks(Set<BlockPos> blocks) {

        this.blocks.addAll(blocks);
    }

    public Set<BlockPos> getBlocks() {

        return blocks;
    }

    public void clearBlocks() {

        blocks.clear();
    }

}
