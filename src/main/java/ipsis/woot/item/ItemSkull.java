package ipsis.woot.item;

import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModItems;
import ipsis.woot.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSkull extends ItemWoot {

    public static final String BASENAME = "skull";

    public static final String[] VARIANTS = new String[] {
            EnumSkullType.IRON.getName(),
            EnumSkullType.GOLD.getName(),
            EnumSkullType.DIAMOND.getName(),
            EnumSkullType.EMERALD.getName(),
    };

    public enum EnumSkullType {
        IRON("iron"), GOLD("gold"), DIAMOND("diamond"), EMERALD("emerald");

        public int getMeta() {
            return ordinal();
        }

        EnumSkullType(String name) {
            this.name = name;
        }

        private String name;
        public String getName() {
            return this.name;
        }
    }

    public ItemSkull() {

        super(BASENAME);
        setMaxStackSize(16);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        for (int i = 0; i < VARIANTS.length; i++) {
            ModelHelper.registerItem(ModItems.itemSkull, i, BASENAME + "." + VARIANTS[i]);
            ModelBakery.registerItemVariants(ModItems.itemSkull, new ResourceLocation(Reference.MOD_ID + ":" + BASENAME + "." + VARIANTS[i]));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {

        if (isInCreativeTab(tab)) {
            for (int i = 0; i < VARIANTS.length; i++)
                items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {

        int idx = stack.getItemDamage() % VARIANTS.length;
        return super.getUnlocalizedName() + "." + VARIANTS[idx];
    }

}
