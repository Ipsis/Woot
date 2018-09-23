package ipsis.woot.drops;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.Files;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.SerializationHelper;

import java.util.Map;

public class LearnedDropJson {

    private static final int VERSION = 1;

    public static void load(LearnedDropRepository repository) {

    }

    public static void save(LearnedDropRepository repository) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("version", VERSION);

        // Sample counts
        JsonArray mobCounts = new JsonArray();
        {
            Map<FakeMobKey, Integer[]> sampleCounts = repository.getSampleCounts();
            for (Map.Entry<FakeMobKey, Integer[]> pair : sampleCounts.entrySet()) {
                FakeMobKey fakeMobKey = pair.getKey();
                Integer[] counts = pair.getValue();

                JsonObject object = new JsonObject();
                JsonArray array = new JsonArray();
                object.addProperty("mobName", fakeMobKey.toString());

                for (Integer count : counts)
                    array.add(count);

                object.add("sampleCount", array);
                mobCounts.add(object);
            }
        }
        jsonObject.add("mobCounts", mobCounts);

        JsonArray dropsArray = new JsonArray();
        {
            for (LearnedDropRepository.RawDropData dropData : repository.getRawDropData()) {
                if (dropData.hasModData())
                    dropsArray.add(dropData.toJsonObject());
            }
        }

        jsonObject.add("drops", dropsArray);
        Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        SerializationHelper.writeJsonFile(Files.lootFile, GSON.toJson(jsonObject));
    }
}
