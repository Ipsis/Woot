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

public class ItemShard extends ItemWoot {

    public static final String BASENANE = "shard";

    public static final String[] VARIANTS = new String[] {
            EnumShardType.DIAMOND.getName(),
            EnumShardType.EMERALD.getName(),
            EnumShardType.QUARTZ.getName(),
            EnumShardType.NETHERSTAR.getName()
    };

    public enum EnumShardType {
        DIAMOND("diamond"), EMERALD("emerald"), QUARTZ("quartz"), NETHERSTAR("netherstar");

        public int getMeta() {
            return ordinal();
        }

        EnumShardType(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return this.name;
        }
    }

    public ItemShard() {

        super(BASENANE);
        setMaxStackSize(64);
        setHasSubtypes(true);
        setRegistryName(Reference.MOD_ID, BASENANE);
    }

    @Override
    public void initModel() {

        for (int i = 0; i < VARIANTS.length; i++) {
            ModelHelper.registerItem(ModItems.itemShard, i, BASENANE + "." + VARIANTS[i]);
            ModelBakery.registerItemVariants(ModItems.itemShard,  new ResourceLocation(Reference.MOD_ID + ":" + BASENANE + "." + VARIANTS[i]));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {

        for (int i = 0; i < VARIANTS.length; i++)
            items.add(new ItemStack(this, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {

        int idx = stack.getItemDamage() % VARIANTS.length;
        return super.getUnlocalizedName() + "." + VARIANTS[idx];
    }

    @Override
    public boolean hasEffect(ItemStack stack) {

        if (stack != null && stack.getItemDamage() == EnumShardType.NETHERSTAR.getMeta())
            return true;

        return false;
    }
}
