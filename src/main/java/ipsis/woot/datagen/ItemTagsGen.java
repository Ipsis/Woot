package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ItemTagsGen extends ItemTagsProvider {

    public static final ITag.INamedTag<Item> FACTORY_BLOCK = ItemTags.makeWrapperTag(Woot.MODID + ":factory_blocks");

    public ItemTagsGen(DataGenerator generator, BlockTagsProvider provider) {
        super(generator, provider);
    }

    @Override
    protected void registerTags() {
        getOrCreateTagBuilder(FACTORY_BLOCK).add(
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
    }
}
