package ipsis.woot.creative;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockCreativeRF extends WootBlock {

    public static final String BASENAME = "creative_rf";
    public BlockCreativeRF() {
        super(Block.Properties.create(Material.ROCK), BASENAME);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityCreativeRF();
    }
}
