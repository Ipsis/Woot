package ipsis.woot.plugins.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import ipsis.woot.plugins.guideapi.book.*;
import ipsis.woot.reference.Reference;
import ipsis.woot.util.StringHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

@GuideBook
public class GuideWoot implements IGuideBook {

    public static final Book GUIDE_BOOK = new Book();
    public static final int MAX_PAGE_LEN = 360;
    public static final int MAX_CHANGELOG_PAGE_LEN = 240;

    @Nullable
    @Override
    public Book buildBook() {

        GUIDE_BOOK.setAuthor("Ipsis");
        GUIDE_BOOK.setColor(Color.GREEN);
        GUIDE_BOOK.setDisplayName(StringHelper.localize("guide.woot.display"));
        GUIDE_BOOK.setTitle(StringHelper.localize("guide.woot.title"));
        GUIDE_BOOK.setWelcomeMessage(StringHelper.localize("guide.woot.welcome"));
        GUIDE_BOOK.setRegistryName(new ResourceLocation(Reference.MOD_ID, "guide"));

        // Categories with json recipes must be created here or the conversion to
        // actual recipes will not see them.
        return GUIDE_BOOK;
    }

    @Override
    public void handlePost(@Nonnull ItemStack bookStack) {

        // Do NOT add anything here with a json recipe page or the book will crash
        CategoryForeword.buildCategory(GUIDE_BOOK);
        CategoryFactory.buildCategory(GUIDE_BOOK);
        CategoryAnvil.buildCategory(GUIDE_BOOK);
        CategoryIngredients.buildCategory(GUIDE_BOOK);
        CategoryUpgrades.buildCategory(GUIDE_BOOK);
        CategoryBloodMagic.buildCategory(GUIDE_BOOK);
        CategoryEvilCraft.buildCategory(GUIDE_BOOK);
        CategoryChangelog.buildCategory(GUIDE_BOOK);
    }

    @Nullable
    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack) {

        return new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "guide"),
                GuideAPI.getStackFromBook(GUIDE_BOOK),
                new ItemStack(Items.BOOK), new ItemStack(Items.ROTTEN_FLESH)).setRegistryName("guide");
    }
}
