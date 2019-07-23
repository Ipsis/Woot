package ipsis.woot.debug;

import ipsis.woot.util.WootDebug;
import ipsis.woot.util.WootItem;
import ipsis.woot.util.helper.PlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;

import java.util.ArrayList;
import java.util.List;

public class DebugItem extends WootItem {

    public DebugItem() {
        super(new Item.Properties().maxStackSize(1), "debug");
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote) {
            Block b =  context.getWorld().getBlockState(context.getPos()).getBlock();
            if (b instanceof WootDebug) {
                List<String> debug = new ArrayList<>();
                ((WootDebug)b).getDebugText(debug, context);
                for (String s : debug)
                    PlayerHelper.sendChatMessage(context.getPlayer(), s);
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        // TODO the Forge patch for onItemUseFirst isn't applied yet
        if (!context.getWorld().isRemote) {
            Block b =  context.getWorld().getBlockState(context.getPos()).getBlock();
            if (b instanceof WootDebug) {
                List<String> debug = new ArrayList<>();
                ((WootDebug)b).getDebugText(debug, context);
                for (String s : debug)
                    PlayerHelper.sendChatMessage(context.getPlayer(), s);
            }
        }
        return ActionResultType.PASS;
    }

    public static List<String> getTileEntityDebug(List<String> debug, ItemUseContext context) {
        TileEntity te = context.getWorld().getTileEntity(context.getPos());
        if (te instanceof WootDebug)
            ((WootDebug) te).getDebugText(debug, context);
        return debug;
    }
}
