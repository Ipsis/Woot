package ipsis.woot.modules.infuser;

import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import static net.minecraft.enchantment.Enchantments.SHARPNESS;

public class InfuserRecipes {

    public static void load() {

        InfuserRecipe.recipeList.clear();

        addInfuserPlateRecipe(InfuserSetup.WHITE_DYE_PLATE_ITEM.get(), InfuserSetup.WHITE_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.ORANGE_DYE_PLATE_ITEM.get(), InfuserSetup.ORANGE_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.MAGENTA_DYE_PLATE_ITEM.get(), InfuserSetup.MAGENTA_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM.get(), InfuserSetup.LIGHT_BLUE_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.YELLOW_DYE_PLATE_ITEM.get(), InfuserSetup.YELLOW_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.LIME_DYE_PLATE_ITEM.get(), InfuserSetup.LIME_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.PINK_DYE_PLATE_ITEM.get(), InfuserSetup.PINK_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.GRAY_DYE_PLATE_ITEM.get(), InfuserSetup.GRAY_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.LIGHT_GRAY_DYE_PLATE_ITEM.get(), InfuserSetup.LIGHT_GRAY_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.CYAN_DYE_PLATE_ITEM.get(), InfuserSetup.CYAN_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.PURPLE_DYE_PLATE_ITEM.get(), InfuserSetup.PURPLE_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.BLUE_DYE_PLATE_ITEM.get(), InfuserSetup.BLUE_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.BROWN_DYE_PLATE_ITEM.get(), InfuserSetup.BROWN_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.GREEN_DYE_PLATE_ITEM.get(), InfuserSetup.GREEN_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.RED_DYE_PLATE_ITEM.get(), InfuserSetup.RED_DYE_CASING_ITEM.get());
        addInfuserPlateRecipe(InfuserSetup.BLACK_DYE_PLATE_ITEM.get(), InfuserSetup.BLACK_DYE_CASING_ITEM.get());

        InfuserRecipe.addRecipe(
                new ItemStack(GenericSetup.PRISM_ITEM.get()),
                new ResourceLocation("forge", "glass"),
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), 1000),
                null,
                1000);

        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        book.addEnchantment(SHARPNESS, 1);
        InfuserRecipe.addRecipe(
                book,
                new ItemStack(Items.BOOK),
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), InfuserConfiguration.LVL_1_ENCHANT_COST.get()),
                new ItemStack(Items.LAPIS_LAZULI, 1),
                1000);
        book = new ItemStack(Items.ENCHANTED_BOOK);
        book.addEnchantment(SHARPNESS, 2);
        InfuserRecipe.addRecipe(
                book,
                new ItemStack(Items.BOOK),
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), InfuserConfiguration.LVL_2_ENCHANT_COST.get()),
                new ItemStack(Items.LAPIS_LAZULI, 2),
                2000);
        book = new ItemStack(Items.ENCHANTED_BOOK);
        book.addEnchantment(SHARPNESS, 3);
        InfuserRecipe.addRecipe(
                book,
                new ItemStack(Items.BOOK),
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), InfuserConfiguration.LVL_3_ENCHANT_COST.get()),
                new ItemStack(Items.LAPIS_LAZULI, 3),
                3000);
        book = new ItemStack(Items.ENCHANTED_BOOK);
        book.addEnchantment(SHARPNESS, 4);
        InfuserRecipe.addRecipe(
                book,
                new ItemStack(Items.BOOK),
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), InfuserConfiguration.LVL_4_ENCHANT_COST.get()),
                new ItemStack(Items.LAPIS_LAZULI, 4),
                4000);
        book = new ItemStack(Items.ENCHANTED_BOOK);
        book.addEnchantment(SHARPNESS, 5);
        InfuserRecipe.addRecipe(
                book,
                new ItemStack(Items.BOOK),
                new FluidStack(FluidSetup.ENCHANT_FLUID.get(), InfuserConfiguration.LVL_5_ENCHANT_COST.get()),
                new ItemStack(Items.LAPIS_LAZULI, 5),
                5000);
    }

    private static void addInfuserPlateRecipe(Item plate, Item casing) {
        InfuserRecipe.addRecipe(new ItemStack(plate),
                new ItemStack(casing),
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM), null, 1000);
    }
}
