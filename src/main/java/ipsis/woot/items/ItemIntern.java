package ipsis.woot.items;

import ipsis.Woot;
import ipsis.woot.util.FactoryTier;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIntern extends Item implements IBuilderItem {

    public ItemIntern() {
        setRegistryName("intern");
        setUnlocalizedName(Woot.MODID + ".intern");
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public FactoryTier getTier() {
        return FactoryTier.TIER_1;
    }
}
