package ipsis.woot.item;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFactoryUpgrade extends ItemWoot {

    public static final String BASENAME = "factoryUpgrade";

    public ItemFactoryUpgrade() {

        super(BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemFactoryUpgrade, BASENAME.toLowerCase());
    }
}
