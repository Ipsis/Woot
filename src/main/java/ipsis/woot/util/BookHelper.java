package ipsis.woot.util;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.List;

public class BookHelper {

    static List<String> pageList;
    static {
        pageList = new ArrayList<>();
        pageList.add(
        "Welcome to the user manual for your newly acquired mob factory. " +
                "Never again will you have to handle the messier aspects of murdering thousands of innocent mobs " +
                "for your loot desires." +
                "So relax as you flip through this beautifully designed manual and learn how to build and operate your Woot mob factory"
        );

        pageList.add(
        "Getting Started"  +
                "The first thing you need is a way to visualise the factory so you know how to build the factory." +
                "So create a Layout Block, place it one block above the ground and cycle through the different factory " +
                "layouts with a right click. All factories require a number of different coloured Factory Blocks and the " +
                "appropriate Cap Stone for the tier of factory"
        );
    }

    public static ItemStack getNewItemStack() {

        ItemStack itemStack = new ItemStack(Items.WRITTEN_BOOK);

        itemStack.setTagInfo("author", new NBTTagString("Ipsis"));
        itemStack.setTagInfo("title", new NBTTagString("Woot Manual"));

        /**
         * The quick way just now
         */
        NBTTagList pages = new NBTTagList();
        for (String s : pageList)
            pages.appendTag(new NBTTagString(s));

        itemStack.setTagInfo("pages", pages);
        return itemStack;
    }
}
