package ipsis.woot.mod;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.blocks.TickConverterBlock;
import ipsis.woot.modules.factory.blocks.TickConverterTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder(Woot.MODID + ":" + TickConverterBlock.REGNAME)
    public static TickConverterBlock TICK_CONVERTER_BLOCK;
    @ObjectHolder(Woot.MODID + ":" + TickConverterBlock.REGNAME)
    public static TileEntityType<TickConverterTileEntity> TICK_CONVERTER_BLOCK_TILE;
}
