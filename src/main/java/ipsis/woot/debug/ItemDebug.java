package ipsis.woot.debug;

import ipsis.woot.Woot;
import ipsis.woot.util.WootItem;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemDebug extends WootItem {

    public static final String BASENAME = "debug";
    public ItemDebug() {
        super(new Item.Properties().group(Woot.TAB_WOOT), BASENAME);
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, ItemUseContext context) {

        if (WorldHelper.isServerWorld(context.getWorld())) {
            Block b =  context.getWorld().getBlockState(context.getPos()).getBlock();
            if (b instanceof IWootDebug) {
                List<String> debug = new ArrayList<>();
                ((IWootDebug)b).getDebugText(debug, context);
                for (String s : debug)
                    PlayerHelper.sendChatMessage(context.getPlayer(), s);
            }
        }

        return EnumActionResult.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentString(StringHelper.getInfoText("info.woot.debug")));
    }
}
