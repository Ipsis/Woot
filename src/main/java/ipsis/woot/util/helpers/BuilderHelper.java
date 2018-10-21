package ipsis.woot.util.helpers;

import ipsis.woot.items.IBuilderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static ipsis.woot.items.IBuilderItem.BuilderModes.DEFAULT_MODE;

public class BuilderHelper {

    private static final String MODE = "mode";

    public static void setBuilderMode(ItemStack itemStack, IBuilderItem.BuilderModes builderMode) {

        if (itemStack == null || itemStack.isEmpty())
            return;

        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        itemStack.getTagCompound().setString(MODE, builderMode.name());
    }

    public static IBuilderItem.BuilderModes getBuilderMode(ItemStack itemStack) {


        if (itemStack == null || itemStack.isEmpty())
            return DEFAULT_MODE;

        if (!itemStack.hasTagCompound())
            setBuilderMode(itemStack, DEFAULT_MODE);

        IBuilderItem.BuilderModes mode = DEFAULT_MODE;
        try {
            mode = IBuilderItem.BuilderModes.valueOf(itemStack.getTagCompound().getString(MODE));
        } catch (Exception e) {
            setBuilderMode(itemStack, mode);
        }

        return mode;
    }
}
