package ipsis.woot.tileentity.ng.farmstructure;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class ScannedFarmProxy {

    private Set<BlockPos> blocks = new HashSet<>();

    public static boolean isEqual(ScannedFarmProxy a, ScannedFarmProxy b) {

        if (a == null || b == null)
            return false;

        if (a.blocks.size() != b.blocks.size())
            return false;

        return a.blocks.containsAll(b.blocks);
    }

    public boolean isValid() {

        return  blocks.size() > 0;
    }

    public void addBlock(BlockPos pos) {

        blocks.add(pos);
    }

    public Set<BlockPos> getBlocks() {

        return blocks;
    }
}
