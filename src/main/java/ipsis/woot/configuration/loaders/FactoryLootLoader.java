package ipsis.woot.configuration.loaders;

import com.google.gson.*;
import ipsis.woot.loot.repository.ILootRepositoryLoad;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.JsonHelper;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;

import java.io.FileReader;

import static ipsis.woot.util.JsonHelper.getItemStack;

public class FactoryLootLoader {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private ILootRepositoryLoad lootRepositoryLoad;

    public void loadConfig(ILootRepositoryLoad lootRepositoryLoad) {

        this.lootRepositoryLoad = lootRepositoryLoad;

        try {
            JsonObject jsonObject = JsonUtils.fromJson(GSON, new FileReader(Files.lootFile), JsonObject.class);
            parseConfig(jsonObject);
        } catch (Exception e) {
            LogHelper.error("Could not load factory loot from " +
                Files.lootFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private void parseConfig(JsonObject json) {

        if (json == null || json.isJsonNull())
            throw new JsonSyntaxException("Json cannot be null");

        int version = JsonUtils.getInt(json, "version", -1);
        if (version == -1 || version != 1)
            throw new JsonSyntaxException("Invalid version");

        for (JsonElement ele : JsonUtils.getJsonArray(json, "mobs")) {
            if (ele == null || !ele.isJsonObject())
                throw new JsonSyntaxException("Mob must be an object");

            JsonObject json2 = (JsonObject)ele;
            WootMobName wootMobName = JsonHelper.getWootMobName(json2);
            if (wootMobName.isValid()) {

                JsonArray jsonArray = JsonUtils.getJsonArray(json2, "samples");
                if (jsonArray.size() != 4)
                    throw new JsonSyntaxException("Samples must contain 4 entries");

                lootRepositoryLoad.loadMobSample(wootMobName,
                        jsonArray.get(0).getAsInt(),
                        jsonArray.get(1).getAsInt(),
                        jsonArray.get(2).getAsInt(),
                        jsonArray.get(3).getAsInt());

            }
        }

        for (JsonElement ele : JsonUtils.getJsonArray(json, "drops")) {
            if (ele == null || !ele.isJsonObject())
                throw new JsonSyntaxException("Drop must be an object");

            JsonObject json2 = (JsonObject)ele;
            ItemStack itemStack = getItemStack(json2.getAsJsonObject("drop"));
            if (itemStack.isEmpty()) {
                LogHelper.info("Ignoring invalid itemstack");
                continue;
            }

            lootRepositoryLoad.loadItem(itemStack);

            for (JsonElement ele2 : JsonUtils.getJsonArray(json2, "mobs")) {
                if (ele2 == null || !ele2.isJsonObject())
                    throw new JsonSyntaxException("Drop must be an object");

                JsonObject json3 = (JsonObject)ele2;
                WootMobName wootMobName = JsonHelper.getWootMobName(json3);
                if (wootMobName.isValid()) {

                    for (JsonElement ele3 : JsonUtils.getJsonArray(json3, "sizes")) {

                        if (ele3 == null || !ele3.isJsonObject())
                            throw new JsonSyntaxException("Size must be an object");

                        JsonObject json4 = (JsonObject)ele3;
                        int count = JsonUtils.getInt(json4, "count");
                        int looting = JsonUtils.getInt(json4, "looting");
                        int samples = JsonUtils.getInt(json4, "samples");

                        EnumEnchantKey key = EnumEnchantKey.getEnchantKey(looting);
                        lootRepositoryLoad.loadMobDrop(wootMobName, key, itemStack, count, samples);
                    }
                }
            }
        }
    }

}
