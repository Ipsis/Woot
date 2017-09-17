package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoulSandDust extends ItemWoot {

    public static final String BASENAME = "soulsanddust";

    public ItemSoulSandDust() {

        super(BASENAME);
        setMaxStackSize(64);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemSoulSandDust, BASENAME.toLowerCase());
    }
}
