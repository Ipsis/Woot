package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFactoryBase extends ItemWoot {

    public static final String BASENAME = "factorybase";

    public ItemFactoryBase() {

        super(BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemFactoryBase, BASENAME.toLowerCase());
    }
}
