package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
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

public class ItemDye extends ItemWoot {

    public static final String BASENAME = "dye";

    public static final String[] VARIANTS = new String[] {
            EnumDyeType.CASING.getName(),
            EnumDyeType.CONNECTOR.getName(),
            EnumDyeType.PLATE.getName(),
            EnumDyeType.SHARD.getName(),
            EnumDyeType.SKULL.getName(),
            EnumDyeType.MESH.getName(),
            EnumDyeType.PRISM.getName()
    };

    public enum EnumDyeType {
        CASING("casing"), CONNECTOR("connector"), PLATE("plate"), SHARD("shard"), SKULL("skull"), MESH("mesh"), PRISM("prism");

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

    public ItemDye() {

        super(BASENAME);
        setMaxStackSize(1);
        setHasSubtypes(true);
        setRegistryName(Reference.MOD_ID, BASENAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        for (int i = 0; i < VARIANTS.length; i++) {
            ModelHelper.registerItem(ModItems.itemDye, i, BASENAME + "." + VARIANTS[i]);
            ModelBakery.registerItemVariants(ModItems.itemDye, new ResourceLocation(Reference.MOD_ID + ":" + BASENAME + "." + VARIANTS[i]));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {

        for (int i = 0; i < VARIANTS.length; i++)
            subItems.add(new ItemStack(itemIn, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {

        int idx = stack.getItemDamage() % VARIANTS.length;
        return super.getUnlocalizedName() + "." + VARIANTS[idx];
    }
}
