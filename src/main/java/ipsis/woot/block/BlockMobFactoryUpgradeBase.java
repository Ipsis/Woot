package ipsis.woot.block;

import ipsis.woot.util.EnumSpawnerUpgrade;
import ipsis.woot.tileentity.TileEntityMobFactoryUpgrade;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class BlockMobFactoryUpgradeBase extends BlockWoot implements ITileEntityProvider{

    public BlockMobFactoryUpgradeBase(String basename) {
        super(Material.ROCK, basename);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactoryUpgrade();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryUpgrade)
            ((TileEntityMobFactoryUpgrade) te).onBlockAdded();
    }

    public void getUpgradeTooltip(EnumSpawnerUpgrade u, List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        TileEntity te = blockAccess.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryUpgrade) {
            boolean validBlock =  !isAir(blockState, blockAccess, pos.offset(side.getOpposite()));

            if (validBlock && !((TileEntityMobFactoryUpgrade) te).isClientFormed())
                return true;
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    /**
     * The enum is split over multiple blocks because of metadata limits
     * This function returns the metadata value for the enum , depending on the block it is on
     */
    public static int getBlockSplitMeta(EnumSpawnerUpgrade u) {

        if (u.ordinal() >= EnumSpawnerUpgrade.RATE_I.ordinal() && u.ordinal() <= EnumSpawnerUpgrade.DECAPITATE_III.ordinal())
            return u.ordinal();

        return u.ordinal() - (EnumSpawnerUpgrade.DECAPITATE_III.ordinal() + 1);
    }
}
