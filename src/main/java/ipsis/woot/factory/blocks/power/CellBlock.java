package ipsis.woot.factory.blocks.power;

import ipsis.woot.common.Config;
import ipsis.woot.debug.DebugItem;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryComponentProvider;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class CellBlock extends WootBlock implements WootDebug, FactoryComponentProvider {

    public static final String CELL_1_REGNAME = "cell_1";
    public static final String CELL_2_REGNAME = "cell_2";
    public static final String CELL_3_REGNAME = "cell_3";

    final Class<? extends CellTileEntityBase> tileEntityClazz;
    public CellBlock(String name, Class<? extends CellTileEntityBase> clazz) {
        super(Properties.create(Material.IRON).sound(SoundType.METAL), name);
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int capacity = 0;
        if (stack.getItem() == Item.getItemFromBlock(ModBlocks.CELL_1_BLOCK))
            capacity = Config.COMMON.CELL_1_CAPACITY.get();
        else if (stack.getItem() == Item.getItemFromBlock(ModBlocks.CELL_2_BLOCK))
            capacity = Config.COMMON.CELL_2_CAPACITY.get();
        else if (stack.getItem() == Item.getItemFromBlock(ModBlocks.CELL_3_BLOCK))
            capacity = Config.COMMON.CELL_3_CAPACITY.get();

        int contents = 0;
        CompoundNBT compoundNBT = stack.getChildTag("BlockEntityTag");
        if (compoundNBT != null && compoundNBT.contains("Tank")) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compoundNBT.getCompound("Tank"));
            contents = fluidStack.getAmount();
        }
        tooltip.add(new TranslationTextComponent("info.woot.cell.0", contents , capacity));
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
