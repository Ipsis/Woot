package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsGen extends BlockTagsProvider {

    public static final ITag.INamedTag<Block> FACTORY_BLOCK = BlockTags.makeWrapperTag(Woot.MODID + ":factory_blocks");

    public BlockTagsGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Woot.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {

        getOrCreateBuilder(FACTORY_BLOCK).add(
                FactorySetup.FACTORY_A_BLOCK.get(),
                FactorySetup.FACTORY_B_BLOCK.get(),
                FactorySetup.FACTORY_C_BLOCK.get(),
                FactorySetup.FACTORY_D_BLOCK.get(),
                FactorySetup.FACTORY_E_BLOCK.get(),
                FactorySetup.FACTORY_CONNECT_BLOCK.get(),
                FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get(),
                FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get(),
                FactorySetup.FACTORY_UPGRADE_BLOCK.get()
        );
    }

    @Override
    public String getName() {
        return "Woot Block Tags";
    }
}
