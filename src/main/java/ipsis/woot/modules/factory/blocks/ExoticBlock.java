package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.factory.Exotic;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ExoticBlock extends Block implements WootDebug {

    private final Exotic exotic;

    public ExoticBlock(Exotic exotic) {
        super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(3.5F));
        this.exotic = exotic;
    }

    public Exotic getExotic() {
        return exotic;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos.down());
            if (te instanceof HeartTileEntity)
                ((HeartTileEntity) te).interrupt();
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos.down());
            if (te instanceof HeartTileEntity)
                ((HeartTileEntity) te).interrupt();
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (stack.getItem() == FactorySetup.EXOTIC_A_BLOCK_ITEM.get()) {
            tooltip.add(new TranslationTextComponent("info.woot.exotic.exotic_a", FactoryConfiguration.EXOTIC_A.get()));
        } else if (stack.getItem() == FactorySetup.EXOTIC_B_BLOCK_ITEM.get()) {
            tooltip.add(new TranslationTextComponent("info.woot.exotic.exotic_b", FactoryConfiguration.EXOTIC_B.get()));
        } else if (stack.getItem() == FactorySetup.EXOTIC_C_BLOCK_ITEM.get()) {
            tooltip.add(new TranslationTextComponent("info.woot.exotic.exotic_c", FactoryConfiguration.EXOTIC_C.get()));
        } else if (stack.getItem() == FactorySetup.EXOTIC_D_BLOCK_ITEM.get()) {
            tooltip.add(new TranslationTextComponent("info.woot.exotic.exotic_d", FactoryConfiguration.EXOTIC_D.get()));
        } else if (stack.getItem() == FactorySetup.EXOTIC_E_BLOCK_ITEM.get()) {
            tooltip.add(new TranslationTextComponent("info.woot.exotic.exotic_e", FactoryConfiguration.EXOTIC_E.get()));
        }
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> ExoticBlock (" + exotic + ")");
        return debug;
    }
}
