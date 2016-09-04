package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.util.BookHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemManual extends ItemWoot {

    public static final String BASENAME = "manual";

    public ItemManual() {

        super(BASENAME);
        setMaxStackSize(1);
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemManual, BASENAME);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (worldIn.isRemote) {
            ItemStack bookStack = BookHelper.getNewItemStack();
            if (bookStack != null)
                Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(playerIn, bookStack, false));
        }

        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
}
