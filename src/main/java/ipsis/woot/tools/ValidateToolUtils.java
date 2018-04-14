package ipsis.woot.tools;

import ipsis.woot.util.StringHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class ValidateToolUtils {

    private static String MODE_TAG = "mode";

    public static EnumValidateToolMode getModeFromNbt(ItemStack itemStack) {

        if (!itemStack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger(MODE_TAG, EnumValidateToolMode.VALIDATE_T1.ordinal());
            itemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = itemStack.getTagCompound();
        return EnumValidateToolMode.getValidateToolMode(tag.getInteger(MODE_TAG));
    }

    public static void cycleMode(ItemStack itemStack, EntityPlayer entityPlayer) {

        if (!itemStack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger(MODE_TAG, EnumValidateToolMode.VALIDATE_T1.ordinal());
            itemStack.setTagCompound(new NBTTagCompound());
        } else if (!itemStack.getTagCompound().hasKey(MODE_TAG)) {
            itemStack.getTagCompound().setInteger(MODE_TAG, EnumValidateToolMode.VALIDATE_T1.ordinal());
        }

        NBTTagCompound tag = itemStack.getTagCompound();
        EnumValidateToolMode m = EnumValidateToolMode.getValidateToolMode(tag.getInteger(MODE_TAG));
        EnumValidateToolMode next = m.getNext();
        tag.setInteger(MODE_TAG, next.ordinal());

        entityPlayer.sendStatusMessage(new TextComponentString(StringHelper.localize(next.getUnlocalisedName())), false);
    }

    public static void getInformation(ItemStack itemStack, List<String> tooltip, ITooltipFlag flag) {

        EnumValidateToolMode mode = getModeFromNbt(itemStack);
        tooltip.add(StringHelper.localize(mode.getUnlocalisedName()));

        if (flag.isAdvanced())
            tooltip.add(StringHelper.localize(mode.getUnlocalisedName() + ".desc"));
    }
}
