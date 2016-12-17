package ipsis.woot.item;

import ipsis.Woot;
import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
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
        setRegistryName(Reference.MOD_ID, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemManual, BASENAME.toLowerCase());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

        ItemStack stack = playerIn.getHeldItem(hand);

        if (worldIn.isRemote)
            playerIn.openGui(Woot.instance, Reference.GUI_MANUAL, worldIn,
                    playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
