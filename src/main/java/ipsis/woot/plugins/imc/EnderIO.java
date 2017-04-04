package ipsis.woot.plugins.imc;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class EnderIO {

    static final String VAT_RECIPE = "recipe:vat";
    static final String ENDERIO_MODID = "EnderIO";

    public static void loadRecipes() {

        /**
         * 1 xp shard == 16 XP (ItemXpShard.XP_VALUE)
         * According to EnderIO/XpUtil.java
         * experience to liquid = xp * 20
         * => 16 * 20 = 320mB
         */
        String recipe =
                "<recipeGroup name=\"Woot\" >" +
                        "<recipe name=\"Liquid XP\" energyCost=\"20\" >" +
                        "<input>" +
                        "<itemStack modID=\"Woot\" itemName=\"xpShard\" slot=\"0\" />" +
                        "<fluidStack name=\"water\" multiplier=\"0.32\" />" +
                        "</input>" +
                        "<output>" +
                        "<fluidStack name=\"xpjuice\" />" +
                        "</output>" +
                        "</recipe>" +
                        "</recipeGroup>";

        FMLInterModComms.sendMessage(ENDERIO_MODID, VAT_RECIPE, recipe);
    }

}
