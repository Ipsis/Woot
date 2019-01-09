package ipsis.woot.machines.stamper;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockStamper extends WootBlock implements ITileEntityProvider {

    private static final String BASENAME = "stamper";

    public BlockStamper() { super(Material.ROCK, BASENAME); }

    public static String getBasename() { return BASENAME; }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityStamper();
    }
}
