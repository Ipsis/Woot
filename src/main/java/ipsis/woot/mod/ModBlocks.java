package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.factory.*;
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
    @ObjectHolder(Woot.MODID + ":factory_cap_1")
    public static final BlockFactory cap1FactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_cap_2")
    public static final BlockFactory cap2FactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_cap_3")
    public static final BlockFactory cap3FactoryBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_cap_4")
    public static final BlockFactory cap4FactoryBlock = null;

    /**
     * Factory Misc
     */
    @ObjectHolder(Woot.MODID + ":factory_heart")
    public static final BlockHeart heartBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_controller")
    public static final BlockController controllerBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_import")
    public static final BlockImport importBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_export")
    public static final BlockExport exportBlock = null;
    @ObjectHolder(Woot.MODID + ":factory_cell_1")
    public static final BlockCell cell1Block = null;
    @ObjectHolder(Woot.MODID + ":factory_cell_2")
    public static final BlockCell cell2Block = null;
    @ObjectHolder(Woot.MODID + ":factory_cell_3")
    public static final BlockCell cell3Block = null;

}
