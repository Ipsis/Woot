package ipsis.woot.item;

import ipsis.Woot;
import ipsis.woot.util.UnlocalizedName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWoot extends Item {

    public ItemWoot() {
        super();
        setCreativeTab(Woot.tabWoot);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

    }

    @Override
    public String getUnlocalizedName() {
        return UnlocalizedName.getUnlocalizedNameItem(super.getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName();
    }
}
