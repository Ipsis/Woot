package ipsis.woot.modules.infuser;

import ipsis.woot.crafting.InfuserRecipe;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.DyeMakeup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class InfuserRecipes {

    public static void load() {

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

    }

    private static void addInfuserPlateRecipe(Item plate, Item casing) {
        InfuserRecipe.addRecipe(
                new ItemStack(plate),
                new ItemStack(casing),
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), DyeMakeup.LCM));
    }
}
