package ipsis.woot.modules.factory.blocks;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TickConverterBlock extends WootBlock {

    public static final String REGNAME = "tick_conv";

    public TickConverterBlock() {
        super(Block.Properties.create(Material.IRON), REGNAME);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TickConverterTileEntity();
    }
}
