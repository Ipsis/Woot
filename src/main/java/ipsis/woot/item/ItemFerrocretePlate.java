package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFerrocretePlate extends ItemWoot {

    public static final String BASENAME = "ferrocreteplate";

    public ItemFerrocretePlate() {

        super(BASENAME);
        setMaxStackSize(64);
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemFerrocretePlate, BASENAME.toLowerCase());
    }
}
