package ipsis.woot.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.debug.DebugItem;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryComponentProvider;
import ipsis.woot.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class FactoryBlock extends WootBlock implements FactoryComponentProvider, WootDebug {

    private final FactoryComponent component;
    /*
    private static final VoxelShape SHAPE =
            Block.makeCuboidShape(
                    15.0D, 15.0D, 15.0D,
                    15.0D, 15.0D, 15.0D); */

    public FactoryBlock(FactoryComponent component) {
        super(Block.Properties.create(Material.IRON), component.getName());
        setDefaultState(getStateContainer().getBaseState().with(BlockStateProperties.ATTACHED, false));
        this.component = component;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.ATTACHED);
    }

    /*
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        if (state.get(BlockStateProperties.ATTACHED))
            return VoxelShapes.fullCube();

        return SHAPE;
    } */

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MultiBlockTileEntity();
    }

    /**
     * FactoryComponentProvider
     */
    public FactoryComponent getFactoryComponent() {
        return this.component;
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> FactoryBlock (" + component + ")");
        DebugItem.getTileEntityDebug(debug, itemUseContext);
        return debug;
    }
}
