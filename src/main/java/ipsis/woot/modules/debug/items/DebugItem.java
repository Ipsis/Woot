package ipsis.woot.modules.debug.items;

import ipsis.woot.Woot;
import ipsis.woot.setup.ModSetup;
import ipsis.woot.util.WootDebug;
import ipsis.woot.util.helper.PlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class DebugItem extends Item {

    public DebugItem() {
        super(new Item.Properties().maxStackSize(1).group(Woot.setup.getCreativeTab()));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        if (!context.getWorld().isRemote) {
            Block b =  context.getWorld().getBlockState(context.getPos()).getBlock();
            if (b instanceof WootDebug) {
                List<String> debug = new ArrayList<>();
                ((WootDebug)b).getDebugText(debug, context);
                for (String s : debug)
                    context.getPlayer().sendStatusMessage(new StringTextComponent(s), false);
            }
        }
        return ActionResultType.SUCCESS;
    }

    public static List<String> getTileEntityDebug(List<String> debug, ItemUseContext context) {
        TileEntity te = context.getWorld().getTileEntity(context.getPos());
        if (te instanceof WootDebug)
            ((WootDebug) te).getDebugText(debug, context);
        return debug;
    }
}
