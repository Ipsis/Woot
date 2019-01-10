package ipsis.woot.items;

import ipsis.woot.util.WootItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemYaHammer extends WootItem {

    public ItemYaHammer() {
        super("yahammer");
        setMaxStackSize(1);
    }

    /**
     * Return the hammer when crafting
     */
    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.copy();
    }
}
