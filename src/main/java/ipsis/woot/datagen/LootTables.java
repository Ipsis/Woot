package ipsis.woot.datagen;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ipsis.woot.modules.factory.FactoryModule;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * See BaseLootTableProvider from McJty's McJtyLib for his fuller implementation
 */

public class LootTables extends LootTableProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private DataGenerator dataGenerator;
    public LootTables(DataGenerator generator) {
        super(generator);
        this.dataGenerator = generator;
    }

    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();

    protected LootTable.Builder createSimpleTable(String name, Block block) {

        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(block));
        return LootTable.lootTable().withPool(builder);
    }

    @Override
    public void run(DirectoryCache cache) {

        addTables();
        Map<ResourceLocation, LootTable> tables = Maps.newHashMap();
        lootTables.forEach((l, r) -> {
            tables.put(l.getLootTable(), r.setParamSet(LootParameterSets.BLOCK).build());
        });

        writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {

        Path outputFolder = this.dataGenerator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable), path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    void addTables() {

        /**
         * Blocks that only drop themselves when broken
         */
        class SimpleBlocks {
            Block b;
            String id;
            public SimpleBlocks(Block b, String id) {
                this.b = b;
                this.id = id;
            }
        }

        SimpleBlocks simpleBlocks[] = {
                new SimpleBlocks(FactoryModule.LAYOUT.get(), FactoryModule.LAYOUT_ID),
                new SimpleBlocks(FactoryModule.HEART.get(), FactoryModule.HEART_ID),
                new SimpleBlocks(FactoryModule.IMPORTER.get(), FactoryModule.IMPORTER_ID),
                new SimpleBlocks(FactoryModule.EXPORTER.get(), FactoryModule.EXPORTER_ID),
                new SimpleBlocks(FactoryModule.BASE_1.get(), FactoryModule.BASE_1_ID),
                new SimpleBlocks(FactoryModule.BASE_2.get(), FactoryModule.BASE_2_ID),
                new SimpleBlocks(FactoryModule.BASE_GLASS.get(), FactoryModule.BASE_GLASS_ID),
                new SimpleBlocks(FactoryModule.CORE_1A.get(), FactoryModule.CORE_1A_ID),
                new SimpleBlocks(FactoryModule.CORE_1B.get(), FactoryModule.CORE_1B_ID),
                new SimpleBlocks(FactoryModule.CORE_2A.get(), FactoryModule.CORE_2A_ID),
                new SimpleBlocks(FactoryModule.CORE_2B.get(), FactoryModule.CORE_2B_ID),
                new SimpleBlocks(FactoryModule.CORE_3A.get(), FactoryModule.CORE_3A_ID),
                new SimpleBlocks(FactoryModule.CORE_3B.get(), FactoryModule.CORE_3B_ID),
                new SimpleBlocks(FactoryModule.CORE_4A.get(), FactoryModule.CORE_4A_ID),
                new SimpleBlocks(FactoryModule.CORE_4B.get(), FactoryModule.CORE_4B_ID),
                new SimpleBlocks(FactoryModule.CORE_5A.get(), FactoryModule.CORE_5A_ID),
                new SimpleBlocks(FactoryModule.CORE_5B.get(), FactoryModule.CORE_5B_ID),
        };

        for (SimpleBlocks s : simpleBlocks)
            lootTables.put(s.b, createSimpleTable(s.id, s.b));
    }

}
