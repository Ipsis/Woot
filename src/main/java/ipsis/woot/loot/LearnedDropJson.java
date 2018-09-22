package ipsis.woot.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.LogHelper;

import java.io.File;
import java.util.Map;

public class LearnedDropJson implements ILearnedDropReader, ILearnedDropWriter {

    private static final int VERSION = 1;

    // ILearnedDropReader
    @Override
    public void load(LearnedDropRepository repository) {

    }

    // ILearnedDropWriter
    @Override
    public void write(LearnedDropRepository repository) {

        File file;

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

        LogHelper.info(jsonObject.toString());
    }
}
