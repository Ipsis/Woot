package ipsis.woot.tileentity.ng.loot;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import ipsis.woot.manager.loot.Drop;
import ipsis.woot.manager.loot.LootPool;
import ipsis.woot.manager.loot.LootTable;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.SerializationHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class LootRepositorySerialization {

    private static final Type LOOT_MAP_TYPE = new TypeToken<HashMap<String, LootTable>>(){}.getType();
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(LootTable.class, new LootTable.Serializer())
            .registerTypeAdapter(LootPool.class, new LootPool.Serializer())
            .registerTypeAdapter(Drop.class, new Drop.Serializer())
            .registerTypeAdapter(Drop.DropData.class, new Drop.DropData.Serializer())
            .create();

    public static HashMap<String, LootTable> readFromFile(File file) throws FileNotFoundException {

        HashMap<String, LootTable> hashMap;
        try {
            hashMap = GSON.fromJson(SerializationHelper.readJsonFile(file), LOOT_MAP_TYPE);
        } catch (JsonParseException e) {
            LogHelper.warn("Failed to load loot from \'" + file.toString() + "\'" + e.getMessage());
            hashMap = new HashMap<>();
        }

        return hashMap;
    }

    public static void writeToFile(HashMap<String, LootTable> hashMap, File file) {
        SerializationHelper.writeJsonFile(file, GSON.toJson(hashMap, LOOT_MAP_TYPE));
    }
}
