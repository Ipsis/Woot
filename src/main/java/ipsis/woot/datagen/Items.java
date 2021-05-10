package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryModule;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Woot.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        parentedBlock(FactoryModule.LAYOUT.get(), "block/" + FactoryModule.LAYOUT_ID);
        parentedBlock(FactoryModule.HEART.get(), "block/" + FactoryModule.HEART_ID);
        parentedBlock(FactoryModule.IMPORTER.get(), "block/" + FactoryModule.IMPORTER_ID);
        parentedBlock(FactoryModule.EXPORTER.get(), "block/" + FactoryModule.EXPORTER_ID);
        parentedBlock(FactoryModule.BASE_1.get(), "block/" + FactoryModule.BASE_1_ID);
        parentedBlock(FactoryModule.BASE_2.get(), "block/" + FactoryModule.BASE_2_ID);
        parentedBlock(FactoryModule.BASE_3.get(), "block/" + FactoryModule.BASE_3_ID);
        parentedBlock(FactoryModule.BASE_GLASS.get(), "block/" + FactoryModule.BASE_GLASS_ID);
        parentedBlock(FactoryModule.CORE_1A.get(), "block/" + FactoryModule.CORE_1A_ID);
        parentedBlock(FactoryModule.CORE_1B.get(), "block/" + FactoryModule.CORE_1B_ID);
        parentedBlock(FactoryModule.CORE_2A.get(), "block/" + FactoryModule.CORE_2A_ID);
        parentedBlock(FactoryModule.CORE_2B.get(), "block/" + FactoryModule.CORE_2B_ID);
        parentedBlock(FactoryModule.CORE_3A.get(), "block/" + FactoryModule.CORE_3A_ID);
        parentedBlock(FactoryModule.CORE_3B.get(), "block/" + FactoryModule.CORE_3B_ID);
        parentedBlock(FactoryModule.CORE_4A.get(), "block/" + FactoryModule.CORE_4A_ID);
        parentedBlock(FactoryModule.CORE_4B.get(), "block/" + FactoryModule.CORE_4B_ID);
        parentedBlock(FactoryModule.CORE_5A.get(), "block/" + FactoryModule.CORE_5A_ID);
        parentedBlock(FactoryModule.CORE_5B.get(), "block/" + FactoryModule.CORE_5B_ID);

        itemGenerated(FactoryModule.INTERN_ITEM.get(), "item/" + FactoryModule.INTERN_ID);
    }

    private void parentedBlock(Block block, String model) {
        getBuilder(block.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(modLoc(model)));
    }

    private void itemGenerated(Item item, String texture) {
        getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", texture);
    }
}
