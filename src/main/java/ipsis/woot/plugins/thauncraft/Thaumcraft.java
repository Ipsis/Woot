package ipsis.woot.plugins.thauncraft;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;

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

    public static boolean isThaumcraftCrystal(ItemStack itemStack) {

        if (itemStack.isEmpty() || crystal == null)
            return false;

        if (itemStack.getItem() == crystal)
            return true;

        return false;
    }

    public static String getWispName() {
        return THAUMCRAFT_MODID + ":" + WISP_NAME;
    }

    public static ItemStack getCrystal(EnumEnchantKey key) {

        Aspect aspect = null;
        ArrayList<Aspect> aspects = null;

        int primalChance = 90;
        if (key == EnumEnchantKey.LOOTING_I)
            primalChance = 80;
        else if (key == EnumEnchantKey.LOOTING_II)
            primalChance = 60;
        else if (key == EnumEnchantKey.LOOTING_III)
            primalChance = 50;

        int roll = Woot.RANDOM.nextInt(100) + 1;
        if (roll <= primalChance)
            aspects = Aspect.getPrimalAspects();
        else
            aspects = Aspect.getCompoundAspects();

        aspect = aspects.get(Woot.RANDOM.nextInt(aspects.size()));
        return makeCrystal(aspect);
    }

}
