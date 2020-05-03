package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.debug.items.DebugItem;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.FactoryComponentProvider;
import ipsis.woot.modules.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class FactoryBlock extends Block implements FactoryComponentProvider, WootDebug {

    private final FactoryComponent component;

    public static final String FACTORY_A_REGNAME = "factory_a";
    public static final String FACTORY_B_REGNAME = "factory_b";
    public static final String FACTORY_C_REGNAME = "factory_c";
    public static final String FACTORY_D_REGNAME = "factory_d";
    public static final String FACTORY_E_REGNAME = "factory_e";
    public static final String CAP_A_REGNAME = "cap_a";
    public static final String CAP_B_REGNAME = "cap_b";
    public static final String CAP_C_REGNAME = "cap_c";
    public static final String CAP_D_REGNAME = "cap_d";
    public static final String FACTORY_CONNECT_REGNAME = "factory_connect";
    public static final String FACTORY_CTR_BASE_PRI_REGNAME = "factory_ctr_base_pri";
    public static final String FACTORY_CTR_BASE_SEC_REGNAME = "factory_ctr_base_sec";
    public static final String IMPORT_REGNAME = "import";
    public static final String EXPORT_REGNAME = "export";

    public FactoryBlock(FactoryComponent component) {
        super(Block.Properties.create(Material.IRON).sound(SoundType.STONE).hardnessAndResistance(3.5F));
        setDefaultState(getStateContainer().getBaseState().with(BlockStateProperties.ATTACHED, false));
        this.component = component;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.ATTACHED);
    }

    /**
     * Block display since we are less than a full block
     */
    private final VoxelShape shape = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (this.component == FactoryComponent.FACTORY_UPGRADE)
            return super.getShape(state, worldIn, pos, context);
        
        if (state.get(BlockStateProperties.ATTACHED))
            return VoxelShapes.fullCube();
        else
            return shape;
    }

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
