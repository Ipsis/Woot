package ipsis.woot.item;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModItems;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemXpShard extends ItemWoot {

    public static final String BASENAME = "xpShard";
    public static final int XP_VALUE = 16;

    public ItemXpShard() {

        super(BASENAME);
        setMaxStackSize(64);
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemXpShard, BASENAME.toLowerCase());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (itemStackIn != null) {

            // TODO sound
            //worldIn.playSoundAtEntity(playerIn, "random.orb", 0.1F, 0.5F * ((itemRand.nextFloat() - itemRand.nextFloat()) * 0.7F + 1.8F));
            playerIn.addExperience(XP_VALUE);

            if (!playerIn.capabilities.isCreativeMode)
                itemStackIn.stackSize--;
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        tooltip.add(I18n.translateToLocal(Lang.TAG_TOOLTIP + BASENAME + ".0"));
        tooltip.add(String.format(I18n.translateToLocal(Lang.TAG_TOOLTIP + BASENAME + ".1"), XP_VALUE));
    }
}
