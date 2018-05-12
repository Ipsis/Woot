package ipsis.woot.plugins.enderio;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class EnderIO {

    static final String ENDERIO_MODID = "enderio";
    static final String ENDERIO_ENDERMAN_SKULL = "block_enderman_skull";

    public static void loadRecipes() {

        /**
         * 1 xp shard == 16 XP (ItemXpShard.XP_VALUE)
         * According to EnderIO/XpUtil.java
         * experience to liquid = xp * 20
         * => 16 * 20 = 320mB
         */
        String s = String.format(XML_MESSAGE_VAT, "Liquid XP", 20, "woot:xpshard", 1.0, "water", 0.32, "xpjuice");
        FMLInterModComms.sendMessage(ENDERIO_MODID, "recipe:xml", s);
    }

    private static final String XML_MESSAGE_VAT = "" +
            "<recipes>" +
                "<recipe name=\"Vat: %s\" required=\"true\" disabled=\"false\">" +
                    "<fermenting energy=\"%d\">" +
                        "<inputgroup>" +
                            "<input name=\"%s\" multiplier=\"%f\"/>" +
                        "</inputgroup>" +
                        "<inputfluid name=\"%s\" multiplier=\"%f\"/>" +
                        "<outputfluid name=\"%s\"/>" +
                    "</fermenting>" +
                "</recipe>" +
            "</recipes>";

    public static ItemStack getEndermanSkull() {

        Item i = Item.getByNameOrId(ENDERIO_MODID + ":" + ENDERIO_ENDERMAN_SKULL);
        if (i == null) {
            return ItemStack.EMPTY;
        }

        return new ItemStack(i);

    }




}
