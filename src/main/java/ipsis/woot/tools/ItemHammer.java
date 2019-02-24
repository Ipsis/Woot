package ipsis.woot.tools;

import ipsis.woot.Woot;
import ipsis.woot.util.WootItem;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHammer extends WootItem {

    public static final String BASENAME = "hammer";
    public ItemHammer() {
        super(new Item.Properties().group(Woot.TAB_WOOT), BASENAME);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentString(StringHelper.getInfoText("info.woot.hammer")));
    }
}
