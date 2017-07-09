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

public class ItemDie extends ItemWoot {

    public static final String BASENAME = "die";

    public static final String[] VARIANTS = new String[] {
            EnumDyeType.MESH.getName(),
            EnumDyeType.PLATE.getName(),
            EnumDyeType.CORE.getName(),
            EnumDyeType.SHARD.getName()
    };

    public enum EnumDyeType {
        MESH("mesh"), PLATE("plate"), CORE("core"), SHARD("shard");

        public int getMeta() {
            return ordinal();
        }

        EnumDyeType(String name) {
            this.name = name;
        }

        private String name;
        public String getName() {
            return this.name;
        }
    }

    public ItemDie() {

        super(BASENAME);
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        for (int i = 0; i < VARIANTS.length; i++) {
            ModelHelper.registerItem(ModItems.itemDie, i, BASENAME + "." + VARIANTS[i]);
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

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {

        return itemStack.copy();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {

        return true;
    }
}
