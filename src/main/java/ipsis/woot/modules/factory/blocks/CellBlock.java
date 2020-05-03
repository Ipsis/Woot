package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.debug.items.DebugItem;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.FactoryComponentProvider;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.infuser.blocks.InfuserTileEntity;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;
import java.util.List;

public class CellBlock extends Block implements WootDebug, FactoryComponentProvider {

    final Class<? extends CellTileEntityBase> tileEntityClazz;
    public CellBlock(Class<? extends CellTileEntityBase> clazz) {
        super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(3.5F));
        setDefaultState(getStateContainer().getBaseState().with(BlockStateProperties.ATTACHED, false));
        this.tileEntityClazz = clazz;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.ATTACHED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        try {
            return tileEntityClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if (worldIn.isRemote)
            return ActionResultType.SUCCESS;

        if (!(worldIn.getTileEntity(pos) instanceof CellTileEntityBase))
            throw new IllegalStateException("Tile entity is missing");


        ItemStack heldItem = player.getHeldItem(handIn);
        if (FluidUtil.getFluidHandler(heldItem).isPresent())
            return FluidUtil.interactWithFluidHandler(player, handIn, worldIn, pos, null) ? ActionResultType.SUCCESS : ActionResultType.FAIL;

        return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int transfer = 0;
        int capacity = 0;
        if (stack.getItem() == FactorySetup.CELL_1_BLOCK.get().asItem()) {
            capacity = FactoryConfiguration.CELL_1_CAPACITY.get();
            transfer = FactoryConfiguration.CELL_1_MAX_TRANSFER.get();
        } else if (stack.getItem() == FactorySetup.CELL_2_BLOCK.get().asItem()) {
            capacity = FactoryConfiguration.CELL_2_CAPACITY.get();
            transfer = FactoryConfiguration.CELL_2_MAX_TRANSFER.get();
        } else if (stack.getItem() == FactorySetup.CELL_3_BLOCK.get().asItem()) {
            capacity = FactoryConfiguration.CELL_3_CAPACITY.get();
            transfer = FactoryConfiguration.CELL_3_MAX_TRANSFER.get();
        } else if (stack.getItem() == FactorySetup.CELL_4_BLOCK.get().asItem()) {
            capacity = FactoryConfiguration.CELL_4_CAPACITY.get();
            transfer = FactoryConfiguration.CELL_4_MAX_TRANSFER.get();
        }

        int contents = 0;
        CompoundNBT compoundNBT = stack.getChildTag("BlockEntityTag");
        if (compoundNBT != null && compoundNBT.contains("Tank")) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compoundNBT.getCompound("Tank"));
            contents = fluidStack.getAmount();
        }
        tooltip.add(new TranslationTextComponent("info.woot.cell.0", contents, capacity));
        tooltip.add(new TranslationTextComponent("info.woot.cell.1", transfer));
    }

    /**
     * FactoryComponentProvider
     */
    @Override
    public FactoryComponent getFactoryComponent() {
        return FactoryComponent.CELL;
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> CellBlock");
        DebugItem.getTileEntityDebug(debug, itemUseContext);
        return debug;
    }
}
