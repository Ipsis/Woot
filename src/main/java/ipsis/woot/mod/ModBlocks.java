package ipsis.woot.mod;

import ipsis.woot.factory.blocks.*;
import ipsis.woot.factory.multiblock.MultiBlockTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("woot:layout")
    public static LayoutBlock LAYOUT_BLOCK;
    @ObjectHolder("woot:layout")
    public static TileEntityType<LayoutTileEntity> LAYOUT_BLOCK_TILE;

    @ObjectHolder("woot:controller")
    public static ControllerBlock CONTROLLER_BLOCK;
    @ObjectHolder("woot:controller")
    public static TileEntityType<ControllerTileEntity> CONTROLLER_BLOCK_TILE;

    @ObjectHolder("woot:heart")
    public static HeartBlock HEART_BLOCK;
    @ObjectHolder("woot:heart")
    public static TileEntityType<HeartTileEntity> HEART_BLOCK_TILE;

    @ObjectHolder("woot:import")
    public static FactoryBlock IMPORT_BLOCK;
    @ObjectHolder("woot:multiblock")
    public static TileEntityType<MultiBlockTileEntity> MULTIBLOCK_BLOCK_TILE;

    @ObjectHolder("woot:export")
    public static FactoryBlock EXPORT_BLOCK;

    @ObjectHolder("woot:factory_a")
    public static FactoryBlock FACTORY_A_BLOCK;
    @ObjectHolder("woot:factory_b")
    public static FactoryBlock FACTORY_B_BLOCK;
    @ObjectHolder("woot:factory_c")
    public static FactoryBlock FACTORY_C_BLOCK;
    @ObjectHolder("woot:factory_d")
    public static FactoryBlock FACTORY_D_BLOCK;
    @ObjectHolder("woot:factory_e")
    public static FactoryBlock FACTORY_E_BLOCK;
    @ObjectHolder("woot:cap_a")
    public static FactoryBlock CAP_A_BLOCK;
    @ObjectHolder("woot:cap_b")
    public static FactoryBlock CAP_B_BLOCK;
    @ObjectHolder("woot:cap_c")
    public static FactoryBlock CAP_C_BLOCK;
    @ObjectHolder("woot:cap_d")
    public static FactoryBlock CAP_D_BLOCK;
    @ObjectHolder("woot:factory_connect")
    public static FactoryBlock FACTORY_CONNECT_BLOCK;
    @ObjectHolder("woot:factory_ctr_base")
    public static FactoryBlock FACTORY_CTR_BASE_BLOCK;
    @ObjectHolder("woot:factory_upgrade")
    public static FactoryBlock FACTORY_UPGRADE_BLOCK;
}
