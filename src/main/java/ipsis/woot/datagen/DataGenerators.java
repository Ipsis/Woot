package ipsis.woot.datagen;

import ipsis.woot.Woot;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Woot.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent e) {

        DataGenerator generator = e.getGenerator();
        if (e.includeServer()) {
            generator.addProvider(new Recipes(generator));
            generator.addProvider(new LootTables(generator));
        }

        if (e.includeClient()) {
            generator.addProvider(new Items(generator, e.getExistingFileHelper()));
            generator.addProvider(new Blocks(generator, e.getExistingFileHelper()));
            generator.addProvider(new Languages(generator, "en_us"));
        }
    }
}
