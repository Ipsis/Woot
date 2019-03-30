package ipsis.woot.anvil;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class BlockAnvil extends WootBlock implements IWootDebug {

    public static final String BASENAME = "anvil";
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
    public BlockAnvil() {
        super(Properties.create(Material.IRON), BASENAME);
        setDefaultState(getStateContainer().getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Nullable
    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityAnvil();
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> BlockAnvil - " + getStateContainer().getBaseState().get(FACING));
        TileEntity te = itemUseContext.getWorld().getTileEntity(itemUseContext.getPos());
        if (te instanceof IWootDebug)
            ((IWootDebug)te).getDebugText(debug, itemUseContext);
        return debug;
    }
}
