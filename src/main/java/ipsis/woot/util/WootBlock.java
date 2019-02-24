package ipsis.woot.util;

import ipsis.woot.Woot;
import net.minecraft.block.Block;

public class WootBlock extends Block {

    public WootBlock(Block.Properties properties, String basename) {
        super(properties);
        setRegistryName(Woot.MODID, basename);
    }
}
