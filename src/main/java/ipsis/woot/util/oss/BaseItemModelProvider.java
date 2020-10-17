package ipsis.woot.util.oss;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * McJtyMods/McJtyLib/1.15/src/main/java/mcjty/lib/datagen/BaseItemModelProvider.java
 */

public abstract class BaseItemModelProvider extends ItemModelProvider {

    public BaseItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    public void parentedBlock(Block block, String model) {
        getBuilder(block.getRegistryName().getPath())
                .parent(new ModelFile.UncheckedModelFile(modLoc(model)));
    }

    public void parentedItem(Item item, String model) {
        getBuilder(item.getRegistryName().getPath())
                .parent(new ModelFile.UncheckedModelFile(modLoc(model)));
    }

    public void itemGenerated(Item item, String texture) {
        getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", texture);
    }

    public void itemHandheld(Item item, String texture) {
        getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", texture);
    }


}
