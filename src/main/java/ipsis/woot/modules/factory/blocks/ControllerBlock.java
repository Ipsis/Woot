package ipsis.woot.modules.factory.blocks;

import ipsis.woot.config.Config;
import ipsis.woot.modules.debug.items.DebugItem;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.FactoryComponentProvider;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class ControllerBlock extends Block implements FactoryComponentProvider, WootDebug {

    public ControllerBlock() {

        super(Properties.create(Material.IRON) .sound(SoundType.METAL).hardnessAndResistance(3.5F));
        setDefaultState(getStateContainer().getBaseState().with(BlockStateProperties.ATTACHED, false));
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
        return new ControllerTileEntity();
    }

    /**
     * Block display since we are less than a full block
     */
    private final VoxelShape shape = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(BlockStateProperties.ATTACHED))
            return VoxelShapes.fullCube();
        else
            return shape;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote)
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ControllerTileEntity) {
            Tier tier = ((ControllerTileEntity) te).getTier();
            if (tier != Tier.UNKNOWN)
                player.sendStatusMessage(new TranslationTextComponent(tier.getTranslationKey()), true);
        }

        return ActionResultType.SUCCESS;
    }

    /**
     * FactoryComponentProvider
     */
    @Override
    public FactoryComponent getFactoryComponent() {
        return FactoryComponent.CONTROLLER;
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> ControllerBlock");
        DebugItem.getTileEntityDebug(debug, itemUseContext);
        return debug;
    }
}
