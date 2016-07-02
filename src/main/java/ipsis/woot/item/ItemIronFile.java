package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIronFile extends ItemWoot {

    public static final String BASENAME = "ironFile";

    public ItemIronFile() {

        super(BASENAME);
        setMaxStackSize(1);
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemIronFile, BASENAME.toLowerCase());
    }
}
