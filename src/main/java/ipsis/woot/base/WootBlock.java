package ipsis.woot.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class WootBlock extends Block {

    public WootBlock(Properties properties) {
        super(properties);
    }

    private String tooltipKey;

    /**
     * Custom tooltip
     */
    public WootBlock addTooltip(String key) {
        this.tooltipKey = key;
        return this;
    }

    /**
     * One line tooltip based on fixed string format
     */
    public WootBlock addSimpleTooltip(String key) {
        this.tooltipKey = "info.woot." + key;
        return this;
    }

    public List<String> sneakTooltipKeys = new ArrayList<>();

    /**
     * Custom sneak tooltips
     */
    public WootBlock addSneakTooltip(String key) {
        this.sneakTooltipKeys.add(key);
        return this;
    }

    /**
     * One line sneak tooltip based on fixed string format
     */
    public WootBlock addSimpleSneakTooltip(String key) {
        this.sneakTooltipKeys.add("info.woot.sneak." + key);
        return this;
    }

    public String advTooltipKey;
    public WootBlock addAdvancedTooltip(String key) {
        this.advTooltipKey = key;
        return this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState newBlockState, boolean isMoving) {

        if (!blockState.is(newBlockState.getBlock())) {
            TileEntity te = world.getBlockEntity(blockPos);
            // if implements IDropContents then do the Inventory.dropContents
        }

        super.onRemove(blockState, world, blockPos, newBlockState, isMoving);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flag) {

        if (tooltipKey != null)
            tooltip.add((new TranslationTextComponent(tooltipKey)).withStyle(TextFormatting.LIGHT_PURPLE));
        if (!sneakTooltipKeys.isEmpty()) {
            if (Screen.hasShiftDown())
                sneakTooltipKeys.forEach(x -> tooltip.add(new TranslationTextComponent(x)));
            else
                tooltip.add((new TranslationTextComponent("info.woot.sneakforinfo")).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.BLUE));
        }
        if (flag.isAdvanced() && advTooltipKey != null)
            tooltip.add((new TranslationTextComponent(advTooltipKey)));
    }
}
