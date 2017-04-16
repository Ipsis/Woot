package ipsis.woot.plugins.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.reference.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.awt.*;

public class GuideWoot {

    public static Book guideBook;

    private static boolean isGuideApiLoaded() {

        return Loader.isModLoaded("guideapi");
    }

    public static void preinit() {

        if (!isGuideApiLoaded())
            return;

        initBook();
        GameRegistry.register(guideBook);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            GuideAPI.setModel(guideBook);
    }

    public static void init() {

        if (!isGuideApiLoaded())
            return;

       GameRegistry.addShapelessRecipe(GuideAPI.getStackFromBook(guideBook), new ItemStack(Items.BOOK), new ItemStack(Items.ROTTEN_FLESH));
    }

    public static void postInit() {

        if (!isGuideApiLoaded())
            return;

       if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
           initCategories();
    }

    public static String BOOK_TAG = "guide." + Reference.MOD_ID;
    public static void initBook() {

        guideBook = new Book();
        guideBook.setTitle(BOOK_TAG + ".title");
        guideBook.setDisplayName(BOOK_TAG + ".display");
        guideBook.setWelcomeMessage(BOOK_TAG + ".welcome");
        guideBook.setAuthor(BOOK_TAG + ".author");
        guideBook.setRegistryName(Reference.MOD_ID);
        guideBook.setColor(Color.GREEN);
    }

    public static void initCategories() {

        guideBook.addCategory(new CategoryItemStack(CategoryGeneral.buildCategory(), BOOK_TAG + ".category.general", new ItemStack(ModBlocks.blockFactory)));
        guideBook.addCategory(new CategoryItemStack(CategoryUpgrade.buildCategory(), BOOK_TAG + ".category.upgrades", new ItemStack(ModBlocks.blockUpgrade)));
    }


}
