package ipsis.woot.loot.base;

import com.google.gson.*;
import ipsis.woot.util.EnumEnchantKey;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LootPool {

    private EnumEnchantKey key;
    private int samples; // count of times that we have sampled a kill of this mob
    private List<Drop> drops;

    public LootPool(EnumEnchantKey key) {
        this(key, 0, new ArrayList<Drop>());
    }

    public LootPool(EnumEnchantKey key, int samples, List<Drop> drops) {
        this.key = key;
        this.samples = samples;
        this.drops = drops;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public int getSamples() {
        return this.samples;
    }

    public void incSamples() {
        this.samples++;
    }

    public void addToDrops(Drop d) {
        this.drops.add(d);
    }

    public List<Drop> getDrops() {
        return this.drops;
    }

    /**
     * Serialization
     */
    public static class Serializer implements JsonSerializer<LootPool>, JsonDeserializer<LootPool> {

        @Override
        public LootPool deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject jsonObject = JsonUtils.getJsonObject(json, "pool");
            String keyString = JsonUtils.getString(jsonObject, "key");
            EnumEnchantKey key = EnumEnchantKey.valueOf(keyString);
            int samples = JsonUtils.getInt(jsonObject, "samples");

            Drop[] drops;
            if (jsonObject.has("drops"))
                drops = (Drop[])(JsonUtils.deserializeClass(jsonObject, "drops", context, Drop[].class));
            else
                drops = new Drop[0];

            LootPool lootPool = new LootPool(key);
            lootPool.samples = samples;
            Collections.addAll(lootPool.drops, drops);
            return lootPool;
        }

        @Override
        public JsonElement serialize(LootPool src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", src.key.toString());
            jsonObject.addProperty("samples", src.samples);
            jsonObject.add("drops", context.serialize(src.drops));
            return jsonObject;
        }
    }
}
