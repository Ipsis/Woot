package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.client.ui.OracleContainer;
import ipsis.woot.factory.blocks.*;
import ipsis.woot.factory.blocks.heart.HeartBlock;
import ipsis.woot.factory.blocks.heart.HeartContainer;
import ipsis.woot.factory.blocks.heart.HeartTileEntity;
import ipsis.woot.factory.blocks.power.CellBlock;
import ipsis.woot.factory.blocks.power.Cell1TileEntity;
import ipsis.woot.factory.blocks.power.convertors.TickConverterBlock;
import ipsis.woot.factory.blocks.power.convertors.TickConverterTileEntity;
import ipsis.woot.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.misc.AnvilBlock;
import ipsis.woot.misc.OracleBlock;
import ipsis.woot.misc.OracleTileEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder(Woot.MODID + ":" + LayoutBlock.REGNAME)
    public static LayoutBlock LAYOUT_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + LayoutBlock.REGNAME)
    public static TileEntityType<LayoutTileEntity> LAYOUT_BLOCK_TILE;

    @ObjectHolder(Woot.MODID + ":" + OracleBlock.REGNAME)
    public static OracleBlock ORACLE_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + OracleBlock.REGNAME)
    public static ContainerType<OracleContainer> ORACLE_CONTAINER;
    @ObjectHolder(Woot.MODID + ":" + OracleBlock.REGNAME)
    public static TileEntityType<OracleTileEntity> ORACLE_BLOCK_TILE;

    @ObjectHolder(Woot.MODID + ":" + AnvilBlock.REGNAME)
    public static AnvilBlock ANVIL_BLOCK;

    @ObjectHolder(Woot.MODID + ":" + ControllerBlock.REGNAME)
    public static ControllerBlock CONTROLLER_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + ControllerBlock.REGNAME)
    public static TileEntityType<ControllerTileEntity> CONTROLLER_BLOCK_TILE;

    @ObjectHolder(Woot.MODID + ":" + HeartBlock.REGNAME)
    public static HeartBlock HEART_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + HeartBlock.REGNAME)
    public static TileEntityType<HeartTileEntity> HEART_BLOCK_TILE;
    @ObjectHolder(Woot.MODID + ":" + HeartBlock.REGNAME)
    public static ContainerType<HeartContainer> HEART_CONTAINER;

    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.IMPORT_REGNAME)
    public static FactoryBlock IMPORT_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.EXPORT_REGNAME)
    public static FactoryBlock EXPORT_BLOCK;

    @ObjectHolder(Woot.MODID + ":" + CellBlock.CELL_1_REGNAME)
    public static CellBlock CELL_1_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + CellBlock.CELL_1_REGNAME)
    public static TileEntityType<Cell1TileEntity> CELL_1_BLOCK_TILE;
    @ObjectHolder(Woot.MODID + ":" + CellBlock.CELL_2_REGNAME)
    public static CellBlock CELL_2_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + CellBlock.CELL_2_REGNAME)
    public static TileEntityType<Cell1TileEntity> CELL_2_BLOCK_TILE;
    @ObjectHolder(Woot.MODID + ":" + CellBlock.CELL_3_REGNAME)
    public static CellBlock CELL_3_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + CellBlock.CELL_3_REGNAME)
    public static TileEntityType<Cell1TileEntity> CELL_3_BLOCK_TILE;

    @ObjectHolder(Woot.MODID + ":" + TickConverterBlock.REGNAME)
    public static TickConverterBlock TICK_CONVERTER_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + TickConverterBlock.REGNAME)
    public static TileEntityType<TickConverterTileEntity> TICK_CONVERTER_BLOCK_TILE;

    @ObjectHolder(Woot.MODID + ":" + MultiBlockTileEntity.REGNAME)
    public static TileEntityType<MultiBlockTileEntity> MULTIBLOCK_BLOCK_TILE;

    @ObjectHolder(Woot.MODID + ":" + UpgradeBlock.REGNAME)
    public static UpgradeBlock FACTORY_UPGRADE_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + UpgradeBlock.REGNAME)
    public static TileEntityType<UpgradeTileEntity> FACTORY_UPGRADE_BLOCK_TILE;

    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.FACTORY_A_REGNAME)
    public static FactoryBlock FACTORY_A_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.FACTORY_B_REGNAME)
    public static FactoryBlock FACTORY_B_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.FACTORY_C_REGNAME)
    public static FactoryBlock FACTORY_C_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.FACTORY_D_REGNAME)
    public static FactoryBlock FACTORY_D_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.FACTORY_E_REGNAME)
    public static FactoryBlock FACTORY_E_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.CAP_A_REGNAME)
    public static FactoryBlock CAP_A_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.CAP_B_REGNAME)
    public static FactoryBlock CAP_B_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.CAP_C_REGNAME)
    public static FactoryBlock CAP_C_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.CAP_D_REGNAME)
    public static FactoryBlock CAP_D_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.FACTORY_CONNECT_REGNAME)
    public static FactoryBlock FACTORY_CONNECT_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + FactoryBlock.FACTORY_CTR_BASE_REGNAME)
    public static FactoryBlock FACTORY_CTR_BASE_BLOCK;
}
