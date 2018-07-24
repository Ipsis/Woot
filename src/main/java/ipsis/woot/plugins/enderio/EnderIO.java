package ipsis.woot.plugins.enderio;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.oss.LogHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class EnderIO {

    static final String ENDERIO_MODID = "enderio";
    static final String ENDERIO_ENDERMAN_SKULL = "block_enderman_skull";

    public static void loadRecipes() {

        if (!Woot.wootConfiguration.getBoolean(EnumConfigKey.ENDERIO_IMC)) {
            LogHelper.info("EnderIO IMC support disabled");
            return;
        }

        /**
         * 1 xp shard == 16 XP (ItemXpShard.XP_VALUE)
         * According to EnderIO/XpUtil.java
         * experience to liquid = xp * 20
         * => 16 * 20 = 320mB
         */
        LogHelper.info("EnderIO: " + XML_MESSAGE_VAT);
        FMLInterModComms.sendMessage(ENDERIO_MODID, "recipe:xml", XML_MESSAGE_VAT);
    }

    private static final String XML_MESSAGE_VAT = "" +
            "<recipes>" +
                "<recipe name=\"Vat: Liquid XP\" required=\"true\" disabled=\"false\">" +
                    "<fermenting energy=\"20\">" +
                        "<inputgroup>" +
                            "<input name=\"woot:xpshard\" multiplier=\"1.0\"/>" +
                        "</inputgroup>" +
                        "<inputfluid name=\"water\" multiplier=\"0.32\"/>" +
                        "<outputfluid name=\"xpjuice\"/>" +
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
