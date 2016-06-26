package ipsis.woot.manager.loot;

import com.google.gson.*;
import ipsis.woot.manager.EnumEnchantKey;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LootPool {

    private EnumEnchantKey key;
    int samples;
    List<Drop> drops;

    public LootPool(EnumEnchantKey key) {
        this(key, 0, new ArrayList<Drop>());
    }

    public LootPool(EnumEnchantKey key, int samples, List<Drop> drops) {
        this.key = key;
        this.samples = samples;
        this.drops = drops;
    }

    /**
     * Serialization
     */
    public static class Serializer implements JsonSerializer<LootPool>, JsonDeserializer<LootPool> {

        @Override
        public LootPool deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
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
