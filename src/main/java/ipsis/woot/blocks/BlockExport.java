package ipsis.woot.blocks;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.material.Material;

public class BlockExport extends WootBlock {

    private static final String BASENAME = "export";

    public BlockExport() {

        super(Material.ROCK, BASENAME);
    }

    public static String getBasename() { return BASENAME; }
}
