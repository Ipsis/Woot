package ipsis.woot.util.oss;

import ipsis.woot.Woot;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * A subset of McJty's BaseBlockStateProvider from McJtyLib
 */
public abstract class BaseBlockStateProvider extends BlockStateProvider {

    public BaseBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    private ModelFile frontBasedModel(String modelName, ResourceLocation texture) {
        return models().orientable(
                modelName,
                new ResourceLocation(Woot.MODID, "block/factory"),
                texture,
                new ResourceLocation(Woot.MODID, "block/factory"));
    }

    private ModelFile sideOnlyBasedModel(String modelName, ResourceLocation texture) {
        return models().cubeBottomTop(modelName,
                texture,
                new ResourceLocation(Woot.MODID, "block/factory"),
                new ResourceLocation(Woot.MODID, "block/factory"));
    }

    public void singleTextureBlock(Block block, String modelName, String textureName) {
        ModelFile modelFile = models().cubeAll(modelName, modLoc(textureName));
        simpleBlock(block, modelFile);
    }

    public void machineBlock(Block block, String modelName, String textureName) {
        ModelFile modelFile = frontBasedModel(modelName, modLoc(textureName));
        horizontalBlock(block,
                new ResourceLocation(Woot.MODID, "block/factory"),
                modLoc(textureName),
                new ResourceLocation(Woot.MODID, "block/factory"));
    }

    public void sideOnlyBlock(Block block, String modelName, String textureName) {
        ModelFile modelFile = sideOnlyBasedModel(modelName, modLoc(textureName));
        simpleBlock(block, modelFile);
    }
}
