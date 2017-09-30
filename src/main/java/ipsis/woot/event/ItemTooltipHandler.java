package ipsis.woot.event;

import ipsis.woot.block.ITooltipInfo;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ItemTooltipHandler {

    @SubscribeEvent
    public void onItemToolTipEvent(ItemTooltipEvent event) {

        if (event.getEntityPlayer() == null)
            return;

        if (event.getItemStack().isEmpty())
            return;

        Block block = Block.getBlockFromItem(event.getItemStack().getItem());
        if (!(block instanceof ITooltipInfo))
            return;

        int meta = event.getItemStack().getMetadata();

        boolean detail = false;
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            detail = true;

        ((ITooltipInfo)block).getTooltip(event.getToolTip(), event.getFlags().isAdvanced(), meta, detail);
    }

}
