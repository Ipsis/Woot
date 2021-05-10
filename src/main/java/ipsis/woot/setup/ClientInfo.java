package ipsis.woot.setup;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientInfo {

    private List<BlockPos> highlightedBlocks = new ArrayList<>();
    private long expireTicks = 0;

    public void addHighlightedBlock(BlockPos blockPos, long expireTicks) {
        highlightedBlocks.add(blockPos);
        this.expireTicks = expireTicks;
    }

    public List<BlockPos> getHighlightedBlocks() { return Collections.unmodifiableList(highlightedBlocks); }
    public long getExpireTicks() { return expireTicks; }
    public void clearHighlightedBlocks() { expireTicks = 0; highlightedBlocks.clear();}
}
