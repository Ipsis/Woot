package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.modules.debug.items.DebugItem;
import ipsis.woot.modules.infuser.blocks.InfuserTileEntity;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class EnchantSqueezerBlock extends Block implements WootDebug {

    public EnchantSqueezerBlock() {
        super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(3.5F));
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
        return new EnchantSqueezerTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote)
            return ActionResultType.SUCCESS;

        if (!(world.getTileEntity(pos) instanceof EnchantSqueezerTileEntity))
            throw new IllegalStateException("Tile entity is missing");

        EnchantSqueezerTileEntity squeezer = (EnchantSqueezerTileEntity) world.getTileEntity(pos);
        ItemStack heldItem = playerEntity.getHeldItem(hand);

        if (FluidUtil.getFluidHandler(heldItem).isPresent())
            return FluidUtil.interactWithFluidHandler(playerEntity, hand, world, pos, null) ? ActionResultType.SUCCESS : ActionResultType.FAIL;

        // open the gui
        if (squeezer instanceof INamedContainerProvider)
            NetworkHooks.openGui((ServerPlayerEntity)playerEntity, squeezer, squeezer.getPos());
        else
            throw new IllegalStateException("Named container provider is missing");

        return ActionResultType.SUCCESS; // Block was activated
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof EnchantSqueezerTileEntity)
                ((EnchantSqueezerTileEntity) te).dropContents(worldIn, pos);
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    //-------------------------------------------------------------------------
    //region WootDebug

    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> EnchantSqueezerBlock");
        DebugItem.getTileEntityDebug(debug, itemUseContext);
        return debug;
    }
    //endregion


    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        CompoundNBT nbt = stack.getChildTag("BlockEntityTag");
        if (nbt == null)
            return;

        if (nbt.contains("energy")) {
            CompoundNBT nbtEnergy = nbt.getCompound("energy");
            tooltip.add(new TranslationTextComponent("info.woot.energy",
                    nbtEnergy.getInt("energy"), SqueezerConfiguration.ENCH_SQUEEZER_MAX_ENERGY.get()));
        }

        if (nbt.contains("tank")) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(nbt.getCompound("tank"));
            if (!fluidStack.isEmpty()) {
                tooltip.add(new TranslationTextComponent("info.woot.output_tank",
                        StringHelper.translate(fluidStack.getTranslationKey()),
                        fluidStack.getAmount(),
                        SqueezerConfiguration.ENCH_SQUEEZER_TANK_CAPACITY.get()));
            } else {
                tooltip.add(new TranslationTextComponent("info.woot.output_tank.empty",
                        SqueezerConfiguration.ENCH_SQUEEZER_TANK_CAPACITY.get()));
            }
        }
    }
}
