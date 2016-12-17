package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFerrocrete extends ItemWoot {

    public static final String BASENAME = "ferrocrete";

    public ItemFerrocrete() {

        super(BASENAME);
        setMaxStackSize(64);
        setRegistryName(Reference.MOD_ID, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemFerrocrete, BASENAME.toLowerCase());
    }
}
