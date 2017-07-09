package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFactoryCore extends ItemWoot {

    public static final String BASENAME = "factorycore";

    public static final String[] VARIANTS = new String[] {
            EnumCoreType.HEART.getName(),
            EnumCoreType.CONTROLLER.getName(),
            EnumCoreType.T1_UPGRADE.getName(),
            EnumCoreType.T2_UPGRADE.getName(),
            EnumCoreType.T3_UPGRADE.getName()
    };

    public enum EnumCoreType {
        HEART("heart"), CONTROLLER("controller"), T1_UPGRADE("t1_upgrade"), T2_UPGRADE("t2_upgrade"), T3_UPGRADE("t3_upgrade");

        public int getMeta() { return ordinal(); }
        EnumCoreType(String name) { this.name = name; }

        private String name;
        public String getName() { return this.name; }
    }

    public ItemFactoryCore() {

        super(BASENAME);
        setMaxStackSize(16);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        for (int i = 0; i < VARIANTS.length; i++) {
            ModelHelper.registerItem(ModItems.itemFactoryCore, i, BASENAME + "." + VARIANTS[i]);
            ModelBakery.registerItemVariants(ModItems.itemDie, new ResourceLocation(Reference.MOD_ID + ":" + BASENAME + "." + VARIANTS[i]));
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
