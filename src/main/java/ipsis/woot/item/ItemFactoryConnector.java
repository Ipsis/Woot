package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFactoryConnector extends ItemWoot {

    public static final String BASENAME = "factoryconnector";

    public ItemFactoryConnector() {

        super(BASENAME);
        setMaxStackSize(64);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        initModel(ModItems.itemFactoryConnector, BASENAME.toLowerCase());
    }
}
