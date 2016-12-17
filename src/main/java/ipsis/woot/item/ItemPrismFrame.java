package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPrismFrame extends ItemWoot {

    public static final String BASENAME = "prismframe";

    public ItemPrismFrame() {

        super(BASENAME);
        setRegistryName(Reference.MOD_ID, BASENAME);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemPrismFrame, BASENAME.toLowerCase());
    }
}
