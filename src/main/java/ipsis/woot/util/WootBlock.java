package ipsis.woot.util;

import ipsis.woot.Woot;
import net.minecraft.block.Block;

public class WootBlock extends Block {

    public WootBlock(Properties properties, String name) {
        super(properties);
        setRegistryName(Woot.MODID, name);
    }
}
