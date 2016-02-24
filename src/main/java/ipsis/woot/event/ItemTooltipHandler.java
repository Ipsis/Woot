package ipsis.woot.event;

import ipsis.woot.block.ITooltipInfo;
import net.minecraft.block.Block;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ItemTooltipHandler {

    @SubscribeEvent
    public void onItemToolTipEvent(ItemTooltipEvent event) {

        if (event.entityPlayer == null)
            return;

        if (event.itemStack == null)
            return;

        Block block = Block.getBlockFromItem(event.itemStack.getItem());
        if (block == null || !(block instanceof ITooltipInfo))
            return;

        int meta = event.itemStack.getMetadata();

        boolean detail = false;
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            detail = true;

        ((ITooltipInfo)block).getTooltip(event.toolTip, event.showAdvancedItemTooltips, meta, detail);
    }

}
