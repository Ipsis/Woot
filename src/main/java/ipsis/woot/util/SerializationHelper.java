package ipsis.woot.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import ipsis.woot.manager.loot.Drop;
import ipsis.woot.manager.loot.LootPool;
import ipsis.woot.manager.loot.LootTable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Based off Pahimar's EE3 SerializationHelper.java
 * https://github.com/pahimar/Equivalent-Exchange-3/blob/1.9.4/src/main/java/com/pahimar/ee3/util/SerializationHelper.java
 */
public class SerializationHelper {

    public static final Type LOOT_MAP_TYPE = new TypeToken<HashMap<String, LootTable>>(){}.getType();
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(LootTable.class, new LootTable.Serializer())
            .registerTypeAdapter(LootPool.class, new LootPool.Serializer())
            .registerTypeAdapter(Drop.class, new Drop.Serializer())
            .registerTypeAdapter(Drop.DropData.class, new Drop.DropData.Serializer())
            .create();

    public static void writeHashMapToFile(HashMap<String, LootTable> map, File file) {
        writeJsonFile(file, GSON.toJson(map, LOOT_MAP_TYPE));
    }

    public static HashMap<String, LootTable> readHashMapFromFile(File file) throws FileNotFoundException {

        HashMap<String, LootTable> map = new HashMap<String, LootTable>();
        try {
            map = GSON.fromJson(readJsonFile(file), LOOT_MAP_TYPE);
        } catch (JsonParseException e) {
        }

        return map;
    }

    private static String readJsonFile(File file) throws FileNotFoundException {

        StringBuilder sb = new StringBuilder();
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
            } catch (IOException e) {
                if (e instanceof FileNotFoundException)
                    throw (FileNotFoundException)e;
                else
                    e.printStackTrace();
            }
        }

        return sb.toString();
    }

    private static void writeJsonFile(File file, String data) {

        if (file == null)
            return;

        file.getParentFile().mkdirs();
        File tmpFile = new File(file.getAbsolutePath() + "_tmp");

        if (tmpFile.exists())
            tmpFile.delete();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))) {
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.exists())
            file.delete();

        if (!file.exists())
            tmpFile.renameTo(file);
    }
}
