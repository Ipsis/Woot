package ipsis.woot.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ValidateToolUtils {

    public static EnumValidateToolMode getModeFromNbt(ItemStack itemStack) {

        if (!itemStack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("mode", EnumValidateToolMode.VALIDATE_T1.ordinal());
            itemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = itemStack.getTagCompound();
        return EnumValidateToolMode.getValidateToolMode(tag.getInteger("mode"));
    }

    public static void cycleMode(ItemStack itemStack) {

        if (!itemStack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("mode", EnumValidateToolMode.VALIDATE_T1.ordinal());
            itemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = itemStack.getTagCompound();
        EnumValidateToolMode m = EnumValidateToolMode.getValidateToolMode(tag.getInteger("mode"));
        tag.setInteger("mode", m.getNext().ordinal());
    }

    public static void getInformation(ItemStack itemStack, List<String> tooltip, ITooltipFlag flag) {

        EnumValidateToolMode mode = getModeFromNbt(itemStack);
        tooltip.add(mode.toString());

        if (flag.isAdvanced())
            tooltip.add("Explanation of the mode");
    }
}
