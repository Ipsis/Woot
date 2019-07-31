package ipsis.woot.factory.blocks.power.convertors;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TickConverterBlock extends WootBlock {

    public TickConverterBlock() {
        super(Block.Properties.create(Material.IRON), "tick_conv");
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
