package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryModule;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Blocks extends BlockStateProvider {

    public Blocks(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Woot.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        final ResourceLocation FACTORY_SIDE = new ResourceLocation(Woot.MODID, "block/factory");

        horizontalBlock(FactoryModule.LAYOUT.get(), FACTORY_SIDE, modLoc("block/" + FactoryModule.LAYOUT_ID), FACTORY_SIDE);
        horizontalBlock(FactoryModule.HEART.get(), FACTORY_SIDE, modLoc("block/" + FactoryModule.HEART_ID), FACTORY_SIDE);
        simpleBlock(FactoryModule.IMPORTER.get(), models().cubeAll(FactoryModule.IMPORTER_ID, modLoc("block/" + FactoryModule.IMPORTER_ID)));
        simpleBlock(FactoryModule.EXPORTER.get(), models().cubeAll("exporter", modLoc("block/exporter")));
        simpleBlock(FactoryModule.BASE_1.get(), models().cubeAll("base_1", modLoc("block/base_1")));
        simpleBlock(FactoryModule.BASE_2.get(), models().cubeAll("base_2", modLoc("block/base_2")));
        simpleBlock(FactoryModule.BASE_GLASS.get(), models().cubeAll("base_glass", modLoc("block/base_glass")));
        simpleBlock(FactoryModule.CORE_1A.get(), models().cubeAll("core_1a", modLoc("block/core_1a")));
        simpleBlock(FactoryModule.CORE_1B.get(), models().cubeAll("core_1b", modLoc("block/core_1b")));
    }
}
