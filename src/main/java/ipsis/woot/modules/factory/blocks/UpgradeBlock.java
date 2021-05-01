package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.factory.items.PerkItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UpgradeBlock extends FactoryBlock {

    public UpgradeBlock(FactoryComponent component) {
        super(component);
        this.registerDefaultState(getStateDefinition().any().setValue(UPGRADE, Perk.EMPTY).setValue(BlockStateProperties.ATTACHED, false));
    }

    public static final EnumProperty<Perk> UPGRADE_TYPE;
    static { UPGRADE_TYPE = EnumProperty.create("upgrade", Perk.class); }

    public static final EnumProperty<Perk> UPGRADE = UPGRADE_TYPE;

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UPGRADE, BlockStateProperties.ATTACHED);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult blockRayTraceResult) {

        if (!worldIn.isClientSide) {
            ItemStack itemStack = player.getItemInHand(handIn);
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof PerkItem) {
                PerkItem perkItem = (PerkItem)itemStack.getItem();

                TileEntity te = worldIn.getBlockEntity(pos);
                if (te instanceof UpgradeTileEntity) {
                    if (((UpgradeTileEntity) te).tryAddUpgrade(worldIn, player, state, perkItem.getFactoryUpgrade())) {
                        if (!player.isCreative())
                            itemStack.shrink(1);
                    }
                }
            }
        }

        return super.use(state, worldIn, pos, player, handIn, blockRayTraceResult);
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        // This is how the chest, hopper etc drop their contents
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof UpgradeTileEntity) {
                ((UpgradeTileEntity) te).dropItems(state, worldIn, pos);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new UpgradeTileEntity();
    }
}
