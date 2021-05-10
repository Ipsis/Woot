package ipsis.woot.base;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WootItem extends Item {

    public WootItem(Properties properties) {
        super(properties);
    }

    protected String tooltipKey;
    protected List<String> sneakTooltipKeys = new ArrayList<>();

    /**
     * Custom tooltip
     */
    public WootItem addTooltip(String key) {
        this.tooltipKey = key;
        return this;
    }

    /**
     * One line tooltip based on fixed string format
     */
    public WootItem addSimpleTooltip(String key) {
        this.tooltipKey = "info.woot." + key;
        return this;
    }


    /**
     * Custom sneak tooltips
     */
    public WootItem addSneakTooltip(String key) {
        this.sneakTooltipKeys.add(key);
        return this;
    }

    /**
     * One line sneak tooltip based on fixed string format
     */
    public WootItem addSimpleSneakTooltip(String key) {
        this.sneakTooltipKeys.add("info.woot.sneak." + key);
        return this;
    }

    protected String advTooltipKey;
    public WootItem addAdvancedTooltip(String key) {
        this.advTooltipKey = key;
        return this;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

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

