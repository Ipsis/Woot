package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.factory.BlockFactory;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.layout.BlockLayout;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder(Woot.MODID + ":" + BlockLayout.BASENAME)
    public static final BlockLayout layoutBlock = null;

    /**
     * Factory Blccks
     */
    @ObjectHolder(Woot.MODID + ":factory_blaze")
    public static final BlockFactory blazeFactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_bone")
    public static final BlockFactory boneFactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_ender")
    public static final BlockFactory enderFactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_flesh")
    public static final BlockFactory fleshFactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_nether")
    public static final BlockFactory netherFactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_redstone")
    public static final BlockFactory redstoneFactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_upgrade")
    public static final BlockFactory upgradeFactoryBlock = null;

}
