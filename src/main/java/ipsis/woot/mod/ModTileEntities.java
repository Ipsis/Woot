package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.anvil.BlockAnvil;
import ipsis.woot.anvil.TileEntityAnvil;
import ipsis.woot.creative.BlockCreativeRF;
import ipsis.woot.creative.TileEntityCreativeRF;
import ipsis.woot.factory.*;
import ipsis.woot.factory.power.*;
import ipsis.woot.factory.heart.BlockHeart;
import ipsis.woot.factory.heart.TileEntityHeart;
import ipsis.woot.layout.BlockLayout;
import ipsis.woot.layout.TileEntityLayout;
import ipsis.woot.machines.squeezer.BlockSqueezer;
import ipsis.woot.machines.squeezer.TileEntitySqueezer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModTileEntities {

    @ObjectHolder(Woot.MODID + ":" + BlockLayout.BASENAME)
    public static TileEntityType<TileEntityLayout> layoutTileEntity;

    @ObjectHolder(Woot.MODID + ":" + BlockController.BASENAME)
    public static TileEntityType<TileEntityController> controllerTileEntity;

    @ObjectHolder(Woot.MODID + ":" + BlockSqueezer.BASENAME)
    public static TileEntityType<TileEntitySqueezer> squeezerTileEntity;

    @ObjectHolder(Woot.MODID + ":" + BlockCreativeRF.BASENAME)
    public static TileEntityType<TileEntityCreativeRF> createRFTileEntity;

    @ObjectHolder(Woot.MODID + ":" + TileEntityTotem.BASENAME)
    public static TileEntityType<TileEntityTotem> totemTileEntityType;

    @ObjectHolder(Woot.MODID + ":" + TileEntityFactory.BASENAME)
    public static TileEntityType<TileEntityFactory> factoryTileEntityType;

    @ObjectHolder(Woot.MODID + ":" + BlockHeart.BASENAME)
    public static TileEntityType<TileEntityHeart> heartTileEntityType;

    @ObjectHolder(Woot.MODID + ":" + TileEntityCellSimple.BASENAME)
    public static TileEntityType<TileEntityCellSimple> cellSimpleTileEntityType;
    @ObjectHolder(Woot.MODID + ":" + TileEntityCellAdvanced.BASENAME)
    public static TileEntityType<TileEntityCellAdvanced> cellAdvancedTileEntityType;
    @ObjectHolder(Woot.MODID + ":" + TileEntityCellUltimate.BASENAME)
    public static TileEntityType<TileEntityCellUltimate> cellUltimateTileEntityType;

    @ObjectHolder(Woot.MODID + ":" + BlockImport.BASENAME)
    public static TileEntityType<TileEntityImport> importTileEntityType;

    @ObjectHolder(Woot.MODID + ":" + BlockExport.BASENAME)
    public static TileEntityType<TileEntityExport> exportTileEntityType;

    @ObjectHolder(Woot.MODID + ":" + BlockConvertorTick.BASENAME)
    public static TileEntityType<TileEntityConvertorTick> convertorTickTileEntityType;

    @ObjectHolder(Woot.MODID + ":" + BlockAnvil.BASENAME)
    public static TileEntityType<TileEntityAnvil> anvilTileEntityType;

}
