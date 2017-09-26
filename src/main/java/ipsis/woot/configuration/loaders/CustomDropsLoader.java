package ipsis.woot.configuration.loaders;

import com.google.gson.*;
import ipsis.Woot;
import ipsis.woot.oss.FileUtils;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.util.JsonHelper;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import static ipsis.woot.util.JsonHelper.getItemStack;

public class CustomDropsLoader {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final int VERSION = 1;

    public void loadConfig() {

        try {
            String configText = FileUtils.copyConfigFileFromJar(Files.CUSTOM_LOOT_FILENAME, false);
            JsonObject jsonObject = JsonUtils.fromJson(GSON, configText, JsonObject.class, false);
            parseConfig(jsonObject);
        } catch (Exception e) {
            LogHelper.error("Could not load custom loot config from " +
                FileUtils.getConfigFile(Files.CUSTOM_LOOT_FILENAME).getAbsolutePath());
            e.printStackTrace();
        }
    }

    private void parseConfig(JsonObject json) {

        if (json == null || json.isJsonNull())
            throw new JsonSyntaxException("Json cannot be null");

        int version = JsonUtils.getInt(json, "version", -1);
        if (version == -1 || version != VERSION)
            throw new JsonSyntaxException("Invalid version");

        for (JsonElement ele : JsonUtils.getJsonArray(json, "mobs")) {
            parseMob(ele);
        }
    }

    private void parseMob(JsonElement ele) {

        if (ele == null || !ele.isJsonObject())
            throw new JsonSyntaxException("Mobs must be an objects");

        JsonObject mobObject = (JsonObject)ele;
        WootMobName wootMobName = JsonHelper.getWootMobName(mobObject);
        if (wootMobName.isValid()) {

            for (JsonElement ele2 : JsonUtils.getJsonArray(mobObject, "drops")) {

                if (ele2 == null || !ele.isJsonObject())
                    throw new JsonSyntaxException("Drops must be objects");

                JsonObject dropObject = (JsonObject)ele2;

                ItemStack itemStack = getItemStack(dropObject.getAsJsonObject("drop"));
                if (itemStack.isEmpty()) {
                    LogHelper.info("Ignoring invalid itemstack");
                    continue;
                }

                JsonArray sizesArray = JsonUtils.getJsonArray(dropObject, "sizes");
                if (sizesArray.size() != 4)
                    throw new JsonSyntaxException("Sizes must contain 4 entries");

                List<Integer> sizes = new ArrayList<>(4);
                for (JsonElement jsonElement : sizesArray)
                    sizes.add(jsonElement.getAsInt());

                JsonArray chancesArray = JsonUtils.getJsonArray(dropObject, "chances");
                if (chancesArray.size() != 4)
                    throw new JsonSyntaxException("Chances must contain 4 entries");

                List<Integer> chances = new ArrayList<>(4);
                for (JsonElement jsonElement : chancesArray)
                    chances.add(jsonElement.getAsInt());

                Woot.customDropsRepository.addDrop(wootMobName, itemStack, chances, sizes);
            }

        }
    }
}
