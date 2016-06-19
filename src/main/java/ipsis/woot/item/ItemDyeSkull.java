package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDyeSkull extends ItemDye {

    public static final String BASENAME = "dyeSkull";

    public ItemDyeSkull() {

        super(BASENAME);
        setMaxStackSize(64);
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemDyeSkull, BASENAME.toLowerCase());
    }
}
