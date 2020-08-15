package ipsis.woot.modules.layout.blocks;

import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LayoutBlock extends Block {

    public LayoutBlock() {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.3F));
        setDefaultState(getStateContainer().getBaseState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LayoutTileEntity();
    }

    @Override
    public ActionResultType onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult blockRayTraceResult) {
        if (worldIn.isRemote)
            return super.onUse(state, worldIn, pos, player, handIn, blockRayTraceResult);

        if (handIn == Hand.MAIN_HAND) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof LayoutTileEntity) {
                LayoutTileEntity layout = (LayoutTileEntity)te;
                if (player.isCrouching()) {
                    layout.setNextLevel();
                } else {
                    layout.setNextTier();
                }
                WorldHelper.updateClient(worldIn, pos);
            }
        }

        return ActionResultType.SUCCESS;
    }
}
