package ipsis.woot.generators;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCreativeRF extends WootBlock implements ITileEntityProvider {

    private static final String BASENAME = "creative_rf";

    public BlockCreativeRF() {
        super(Material.ROCK, BASENAME);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCreativeRF();
    }
}
