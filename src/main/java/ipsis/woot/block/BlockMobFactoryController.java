package ipsis.woot.block;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.item.ItemPrism;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.util.ItemStackHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMobFactoryController extends BlockContainerWoot {

    public static final String BASENAME = "controller";

    public BlockMobFactoryController() {

        super(Material.rock, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMobFactoryController();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockController, BASENAME);
    }

    @Override
    public int getRenderType() {

        return 3;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntityMobFactoryController te = (TileEntityMobFactoryController) worldIn.getTileEntity(pos);
        te.blockAdded();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryController) {
            if (!(((TileEntityMobFactoryController) te).getMobName().equals(""))) {
                ItemStackHelper.spawnInWorld(worldIn, pos, ItemPrism.getItemStack(((TileEntityMobFactoryController) te).getMobName()));
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
}
