package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.creative.BlockCreativeRF;
import ipsis.woot.factory.*;
import ipsis.woot.factory.convertors.BlockConvertorTick;
import ipsis.woot.factory.heart.BlockHeart;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.layout.BlockLayout;
import ipsis.woot.machines.squeezer.BlockSqueezer;
import ipsis.woot.machines.stamper.BlockStamper;
import net.minecraft.block.state.IBlockState;
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
    @ObjectHolder(Woot.MODID + ":factory_totem_1")
    public static final BlockTotem totem1Block = null;
    @ObjectHolder(Woot.MODID + ":factory_totem_2")
    public static final BlockTotem totem2Block = null;
    @ObjectHolder(Woot.MODID + ":factory_totem_3")
    public static final BlockTotem totem3Block = null;

    /**
     * Machines
     */
    @ObjectHolder(Woot.MODID + ":squeezer")
    public static final BlockSqueezer squeezerBlock = null;
    @ObjectHolder(Woot.MODID + ":stamper")
    public static final BlockStamper stamperBlock = null;

    /**
     * Convertors
     */
    @ObjectHolder(Woot.MODID + ":convertor_tick")
    public static final BlockConvertorTick convertorTickBlock = null;

    /**
     * Creative
     */
    @ObjectHolder(Woot.MODID + ":creative_rf")
    public static final BlockCreativeRF creativeRFBlock = null;

    public static IBlockState getFactoryBlockDefaultState(FactoryBlock factoryBlock) {
        switch (factoryBlock) {
            case BONE: return boneFactoryBlock.getDefaultState();
            case FLESH: return fleshFactoryBlock.getDefaultState();
            case BLAZE: return blazeFactoryBlock.getDefaultState();
            case ENDER: return enderFactoryBlock.getDefaultState();
            case NETHER: return netherFactoryBlock.getDefaultState();
            case REDSTONE: return redstoneFactoryBlock.getDefaultState();
            case UPGRADE: return upgradeFactoryBlock.getDefaultState();
            case CAP_1: return cap1FactoryBlock.getDefaultState();
            case CAP_2: return cap2FactoryBlock.getDefaultState();
            case CAP_3: return cap3FactoryBlock.getDefaultState();
            case CAP_4: return cap4FactoryBlock.getDefaultState();
            case CONTROLLER: return controllerBlock.getDefaultState();
            case HEART: return heartBlock.getDefaultState();
            case CELL_1: return cell1Block.getDefaultState();
            case CELL_2: return cell2Block.getDefaultState();
            case CELL_3: return cell3Block.getDefaultState();
            case IMPORT: return importBlock.getDefaultState();
            case EXPORT: return exportBlock.getDefaultState();
            default: return boneFactoryBlock.getDefaultState();
        }
    }

}
