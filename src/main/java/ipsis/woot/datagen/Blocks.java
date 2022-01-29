package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryModule;
import ipsis.woot.modules.squeezer.SqueezerModule;
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

        // Factory Module
        horizontalBlock(FactoryModule.LAYOUT.get(), FACTORY_SIDE, modLoc("block/" + FactoryModule.LAYOUT_ID), FACTORY_SIDE);
        horizontalBlock(FactoryModule.HEART.get(), FACTORY_SIDE, modLoc("block/" + FactoryModule.HEART_ID), FACTORY_SIDE);
        simpleBlock(FactoryModule.IMPORTER.get(), models().cubeAll(FactoryModule.IMPORTER_ID, modLoc("block/" + FactoryModule.IMPORTER_ID)));
        simpleBlock(FactoryModule.EXPORTER.get(), models().cubeAll(FactoryModule.EXPORTER_ID, modLoc("block/" + FactoryModule.EXPORTER_ID)));
        simpleBlock(FactoryModule.BASE_1.get(), models().cubeAll(FactoryModule.BASE_1_ID, modLoc("block/" + FactoryModule.BASE_1_ID)));
        simpleBlock(FactoryModule.BASE_2.get(), models().cubeAll(FactoryModule.BASE_2_ID, modLoc("block/" + FactoryModule.BASE_2_ID)));
        simpleBlock(FactoryModule.BASE_3.get(), models().cubeAll(FactoryModule.BASE_3_ID, modLoc("block/" + FactoryModule.BASE_3_ID)));
        simpleBlock(FactoryModule.BASE_GLASS.get(), models().cubeAll(FactoryModule.BASE_GLASS_ID, modLoc("block/" + FactoryModule.BASE_GLASS_ID)));
        simpleBlock(FactoryModule.CORE_1A.get(), models().cubeAll(FactoryModule.CORE_1A_ID, modLoc("block/" + FactoryModule.CORE_1A_ID)));
        simpleBlock(FactoryModule.CORE_1B.get(), models().cubeAll(FactoryModule.CORE_1B_ID, modLoc("block/" + FactoryModule.CORE_1B_ID)));
        simpleBlock(FactoryModule.CORE_2A.get(), models().cubeAll(FactoryModule.CORE_2A_ID, modLoc("block/" + FactoryModule.CORE_2A_ID)));
        simpleBlock(FactoryModule.CORE_2B.get(), models().cubeAll(FactoryModule.CORE_2B_ID, modLoc("block/" + FactoryModule.CORE_2B_ID)));
        simpleBlock(FactoryModule.CORE_3A.get(), models().cubeAll(FactoryModule.CORE_3A_ID, modLoc("block/" + FactoryModule.CORE_3A_ID)));
        simpleBlock(FactoryModule.CORE_3B.get(), models().cubeAll(FactoryModule.CORE_3B_ID, modLoc("block/" + FactoryModule.CORE_3B_ID)));
        simpleBlock(FactoryModule.CORE_4A.get(), models().cubeAll(FactoryModule.CORE_4A_ID, modLoc("block/" + FactoryModule.CORE_4A_ID)));
        simpleBlock(FactoryModule.CORE_4B.get(), models().cubeAll(FactoryModule.CORE_4B_ID, modLoc("block/" + FactoryModule.CORE_4B_ID)));
        simpleBlock(FactoryModule.CORE_5A.get(), models().cubeAll(FactoryModule.CORE_5A_ID, modLoc("block/" + FactoryModule.CORE_5A_ID)));
        simpleBlock(FactoryModule.CORE_5B.get(), models().cubeAll(FactoryModule.CORE_5B_ID, modLoc("block/" + FactoryModule.CORE_5B_ID)));

        // Squeezer Module
        horizontalBlock(SqueezerModule.DYE_SQUEEZER.get(), FACTORY_SIDE, modLoc("block/" + SqueezerModule.DYE_SQUEEZER_ID), FACTORY_SIDE);
    }
}
