package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;
import java.util.function.Consumer;

public class ItemTagsGen extends ItemTagsProvider {

    public static final ITag.INamedTag<Item> FACTORY_BLOCK = ItemTags.makeWrapperTag(Woot.MODID + ":factory_blocks");
    //public static final ITag.INamedTag<Item> BLACK_DYE = ItemTags.makeWrapperTag("forge:dyes/black");

    public ItemTagsGen(DataGenerator generator, BlockTagsProvider provider, ExistingFileHelper existingFileHelper) {
        super(generator, provider, Woot.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(FACTORY_BLOCK).add(
                FactorySetup.FACTORY_A_BLOCK_ITEM.get(),
                FactorySetup.FACTORY_B_BLOCK_ITEM.get(),
                FactorySetup.FACTORY_C_BLOCK_ITEM.get(),
                FactorySetup.FACTORY_D_BLOCK_ITEM.get(),
                FactorySetup.FACTORY_E_BLOCK_ITEM.get(),
                FactorySetup.FACTORY_CONNECT_BLOCK_ITEM.get(),
                FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK_ITEM.get(),
                FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK_ITEM.get(),
                FactorySetup.FACTORY_UPGRADE_BLOCK_ITEM.get()
        );

        addColored(getOrCreateBuilder(Tags.Items.DYES)::addTags, Tags.Items.DYES, "{color}_dyeplate");
    }

    // Straight from forge
    private void addColored(Consumer<ITag.INamedTag<Item>> consumer, ITag.INamedTag<Item> group, String pattern)
    {
        String prefix = group.getName().getPath().toUpperCase(Locale.ENGLISH) + '_';
        for (DyeColor dyeColor : DyeColor.values()) {
            ResourceLocation key = new ResourceLocation(Woot.MODID, pattern.replace("{color}", dyeColor.getTranslationKey()));
            ITag.INamedTag<Item> tag = getForgeItemTag(prefix + dyeColor.getTranslationKey());
            Item item = ForgeRegistries.ITEMS.getValue(key);
            if (item == null || item == Items.AIR)
                throw new IllegalStateException("Unknown woot item: " + key.toString());
            getOrCreateBuilder(tag).add(item);
            consumer.accept(tag);
        }
    }

    // Straight from forge
    private ITag.INamedTag<Item> getForgeItemTag(String name)
    {
        try {
            name = name.toUpperCase(Locale.ENGLISH);
            return (ITag.INamedTag<Item>)Tags.Items.class.getDeclaredField(name).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(Tags.Items.class.getName() + " is missing tag name: " + name);
        }
    }

    @Override
    public String getName() {
        return "Woot Item Tags";
    }
}
