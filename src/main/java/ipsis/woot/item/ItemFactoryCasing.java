package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFactoryCasing extends ItemWoot {

    public static final String BASENAME = "factorycasing";

    public ItemFactoryCasing() {

        super(BASENAME);
        setMaxStackSize(64);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemFactoryCasing, BASENAME.toLowerCase());
    }
}
