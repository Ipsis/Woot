package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.creative.BlockCreativeRF;
import ipsis.woot.creative.TileEntityCreativeRF;
import ipsis.woot.factory.BlockController;
import ipsis.woot.factory.BlockTotem;
import ipsis.woot.factory.TileEntityController;
import ipsis.woot.factory.TileEntityTotem;
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
}
