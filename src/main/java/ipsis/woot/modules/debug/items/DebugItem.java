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
        super(new Item.Properties().stacksTo(1).tab(Woot.setup.getCreativeTab()));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        if (!context.getLevel().isClientSide) {
            Block b =  context.getLevel().getBlockState(context.getClickedPos()).getBlock();
            if (b instanceof WootDebug) {
                List<String> debug = new ArrayList<>();
                ((WootDebug)b).getDebugText(debug, context);
                for (String s : debug)
                    context.getPlayer().displayClientMessage(new StringTextComponent(s), false);
            }
        }
        return ActionResultType.SUCCESS;
    }

    public static List<String> getTileEntityDebug(List<String> debug, ItemUseContext context) {
        TileEntity te = context.getLevel().getBlockEntity(context.getClickedPos());
        if (te instanceof WootDebug)
            ((WootDebug) te).getDebugText(debug, context);
        return debug;
    }
}
