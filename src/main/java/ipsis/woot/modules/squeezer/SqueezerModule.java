package ipsis.woot.modules.squeezer;

import ipsis.woot.datagen.Languages;
import ipsis.woot.modules.Module;
import ipsis.woot.modules.squeezer.client.DyeSqueezerContainer;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerBlock;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerTileEntity;
import ipsis.woot.setup.Config;
import ipsis.woot.setup.ModSetup;
import ipsis.woot.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Consumer;

public class SqueezerModule implements Module {

    public static final String DYE_SQUEEZER_ID = "dyesqueezer";
    public static final String ENCH_SQUEEZER_ID = "enchsqueezer";

    public static final RegistryObject<Block> DYE_SQUEEZER = Registration.BLOCKS.register(DYE_SQUEEZER_ID,
            () -> new DyeSqueezerBlock());
    public static final RegistryObject<Item> DYE_SQUEEZER_ITEM = Registration.ITEMS.register(DYE_SQUEEZER_ID,
            () -> new BlockItem(DYE_SQUEEZER.get(), ModSetup.createStandardProperties()));
    public static final RegistryObject<TileEntityType<DyeSqueezerTileEntity>> DYE_SQUEEZER_TE = Registration.TILES.register(DYE_SQUEEZER_ID,
            () -> TileEntityType.Builder.of(() -> new DyeSqueezerTileEntity(), DYE_SQUEEZER.get()).build(null));
    public static final RegistryObject<ContainerType<DyeSqueezerContainer>> DYE_SQUEEZER_CONTAINER = Registration.CONTAINERS.register(DYE_SQUEEZER_ID,
            () -> IForgeContainerType.create(DyeSqueezerContainer::new));

    // Start Module
    @Override
    public void initConfig() {
        SqueezerConfig.setup(Config.SERVER_BUILDER, Config.CLIENT_BUILDER);
    }

    @Override
    public void runRecipes(Consumer<IFinishedRecipe> consumer) {

        DyeSqueezerBlock.addRecipe(DYE_SQUEEZER.get(), consumer);
    }

    @Override
    public void addTranslations(Languages languages) {

        DyeSqueezerBlock.addTranslations(DYE_SQUEEZER_ID, languages);
    }
    // End Module
}
