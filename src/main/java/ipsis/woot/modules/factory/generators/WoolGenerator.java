package ipsis.woot.modules.factory.generators;

import ipsis.woot.util.FakeMob;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Random;

public class WoolGenerator {

    private static final Random RANDOM = new Random();

    public static ItemStack getWoolDrop(FakeMob fakeMob) {

        if (!fakeMob.isSheep())
            return ItemStack.EMPTY;

        DyeColor dyeColor = DyeColor.byId(RANDOM.nextInt(16));
        if (dyeColor == DyeColor.BLACK)
            return new ItemStack(Items.BLACK_WOOL);
        else if (dyeColor == DyeColor.BLUE)
            return new ItemStack(Items.BLUE_WOOL);
        else if (dyeColor == DyeColor.BROWN)
            return new ItemStack(Items.BROWN_WOOL);
        else if (dyeColor == DyeColor.CYAN)
            return new ItemStack(Items.CYAN_WOOL);
        else if (dyeColor == DyeColor.GRAY)
            return new ItemStack(Items.GRAY_WOOL);
        else if (dyeColor == DyeColor.GREEN)
            return new ItemStack(Items.GREEN_WOOL);
        else if (dyeColor == DyeColor.LIGHT_BLUE)
            return new ItemStack(Items.LIGHT_BLUE_WOOL);
        else if (dyeColor == DyeColor.LIGHT_GRAY)
            return new ItemStack(Items.LIGHT_GRAY_WOOL);
        else if (dyeColor == DyeColor.LIME)
            return new ItemStack(Items.LIME_WOOL);
        else if (dyeColor == DyeColor.MAGENTA)
            return new ItemStack(Items.MAGENTA_WOOL);
        else if (dyeColor == DyeColor.ORANGE)
            return new ItemStack(Items.ORANGE_WOOL);
        else if (dyeColor == DyeColor.PINK)
            return new ItemStack(Items.PINK_WOOL);
        else if (dyeColor == DyeColor.PURPLE)
            return new ItemStack(Items.PURPLE_WOOL);
        else if (dyeColor == DyeColor.RED)
            return new ItemStack(Items.RED_WOOL);
        else if (dyeColor == DyeColor.WHITE)
            return new ItemStack(Items.WHITE_WOOL);
        else if (dyeColor == DyeColor.YELLOW)
            return new ItemStack(Items.YELLOW_WOOL);

        return ItemStack.EMPTY;
    }

    public static boolean isWoolDrop(ItemStack itemStack) {
        if (itemStack.isEmpty())
            return false;

        Item item = itemStack.getItem();
        if (item == Items.BLACK_WOOL || item == Items.BLUE_WOOL || item == Items.BROWN_WOOL ||
            item == Items.CYAN_WOOL || item == Items.GRAY_WOOL || item == Items.GREEN_WOOL ||
            item == Items.LIGHT_BLUE_WOOL || item == Items.LIGHT_GRAY_WOOL || item == Items.LIME_WOOL ||
            item == Items.MAGENTA_WOOL || item == Items.ORANGE_WOOL || item == Items.PINK_WOOL ||
            item == Items.PURPLE_WOOL || item == Items.RED_WOOL || item == Items.WHITE_WOOL ||
            item == Items.YELLOW_WOOL)
            return true;

        return false;
    }

}
