package ipsis.woot.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new Recipes(generator));
            BlockTagsProvider blockTagsProvider = new BlockTagsGen(generator, event.getExistingFileHelper());
            generator.addProvider(blockTagsProvider);
            generator.addProvider(new ItemTagsGen(generator, blockTagsProvider, event.getExistingFileHelper()));
            generator.addProvider(new Advancements(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new Items(generator, event.getExistingFileHelper()));
            generator.addProvider(new Blocks(generator, event.getExistingFileHelper()));
            generator.addProvider(new Languages(generator, "en_us"));
        }
    }
}
