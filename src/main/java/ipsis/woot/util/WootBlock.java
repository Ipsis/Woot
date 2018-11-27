package ipsis.woot.util;

import ipsis.Woot;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class WootBlock extends Block {

    public WootBlock(Material materialIn, String basename) {

        super(materialIn);
        setCreativeTab(Woot.tab);
        setTranslationKey(Woot.MODID + "." + basename);
        setRegistryName(basename);
    }
}
