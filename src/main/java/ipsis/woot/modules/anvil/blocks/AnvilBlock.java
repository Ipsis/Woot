package ipsis.woot.modules.anvil.blocks;

import ipsis.woot.debug.DebugItem;
import ipsis.woot.modules.anvil.AnvilCraftingManager;
import ipsis.woot.mod.ModItems;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AnvilBlock extends Block implements WootDebug {

    // From vanilla
    private static final VoxelShape PART_BASE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    private static final VoxelShape PART_LOWER_X = Block.makeCuboidShape(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
    private static final VoxelShape PART_MID_X = Block.makeCuboidShape(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    private static final VoxelShape PART_UPPER_X = Block.makeCuboidShape(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape PART_LOWER_Z = Block.makeCuboidShape(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D);
    private static final VoxelShape PART_MID_Z = Block.makeCuboidShape(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D);
    private static final VoxelShape PART_UPPER_Z = Block.makeCuboidShape(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    private static final VoxelShape X_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_X, PART_MID_X, PART_UPPER_X);
    private static final VoxelShape Z_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_Z, PART_MID_Z, PART_UPPER_Z);

    public AnvilBlock() {
        super(Properties.create(Material.IRON).sound(SoundType.METAL));
        setDefaultState(getStateContainer().getBaseState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AnvilTileEntity();
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(BlockStateProperties.HORIZONTAL_FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public boolean onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {

        if (world.isRemote)
            super.onBlockActivated(blockState, world, pos, playerEntity, hand, blockRayTraceResult);

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof AnvilTileEntity) {
            AnvilTileEntity anvil = (AnvilTileEntity)te;
            ItemStack heldItem = playerEntity.getHeldItem(hand);

            if (playerEntity.isSneaking() && heldItem.isEmpty()) {
                // Sneak with empty hand to empty
                anvil.dropItem(playerEntity);
            } else if (heldItem.getItem() == ModItems.INTERN_ITEM) {
                // Crafting
                anvil.tryCraft(playerEntity);
            } else {
                if (!anvil.hasBaseItem()) {
                    // Empty anvil so try to add a base item
                    AnvilCraftingManager.AnvilRecipe recipe = AnvilCraftingManager.get().getRecipe(heldItem);
                    if (recipe != null) {
                        ItemStack baseItem = heldItem.copy();
                        baseItem.setCount(1);
                        anvil.setBaseItem(baseItem);
                        heldItem.shrink(1);
                        if (heldItem.isEmpty())
                            playerEntity.inventory.setInventorySlotContents( playerEntity.inventory.currentItem, ItemStack.EMPTY);
                        else
                            playerEntity.inventory.setInventorySlotContents( playerEntity.inventory.currentItem, heldItem);
                        playerEntity.openContainer.detectAndSendChanges();
                    }
                } else {
                    // Base item already present
                    ItemStack ingredient = heldItem.copy();
                    ingredient.setCount(1);
                    if (anvil.addIngredient(ingredient)) {
                        heldItem.shrink(1);
                        if (heldItem.isEmpty())
                            playerEntity.inventory.setInventorySlotContents( playerEntity.inventory.currentItem, ItemStack.EMPTY);
                        else
                            playerEntity.inventory.setInventorySlotContents( playerEntity.inventory.currentItem, heldItem);
                        playerEntity.openContainer.detectAndSendChanges();
                    }
                }
            }

        }

        return true;
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (blockState.getBlock() != newBlockState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof AnvilTileEntity)
                ((AnvilTileEntity) te).dropContents(world, pos);
            super.onReplaced(blockState, world, pos, newBlockState, isMoving);
        }
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> AnvilBlock");
        DebugItem.getTileEntityDebug(debug, itemUseContext);
        return debug;
    }
}
