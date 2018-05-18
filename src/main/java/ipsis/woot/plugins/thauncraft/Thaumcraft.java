package ipsis.woot.plugins.thauncraft;

import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Thaumcraft {

    public static final String THAUMCRAFT_MODID = "thaumcraft";
    private static final String CRYSTAL_NAME = "crystal_essence";
    private static final String WISP_NAME = "wisp";
    public static Item crystal = null;


    @Optional.Method(modid = THAUMCRAFT_MODID)
    public static void init() {

        crystal = ForgeRegistries.ITEMS.getValue(new ResourceLocation(THAUMCRAFT_MODID, CRYSTAL_NAME));
    }

    public static ItemStack makeCrystal(Aspect aspect, int stackSize) {

        if (aspect == null || crystal == null)
            return null;

        ItemStack is = new ItemStack(crystal, stackSize, 0);
        setAspects(is, new AspectList().add(aspect, 1));
        return is;
    }

    public static ItemStack makeCrystal(Aspect aspect) {
        return makeCrystal(aspect,1);
    }

    private static void setAspects(ItemStack itemStack, AspectList aspects) {

        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());

        aspects.writeToNBT(itemStack.getTagCompound());

    }

    public static String getWispName() {
        return THAUMCRAFT_MODID + ":" + WISP_NAME;
    }

    public static ItemStack getWispDrop(EnumEnchantKey key) {

        return ItemStack.EMPTY;
    }

}
