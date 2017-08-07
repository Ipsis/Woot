package ipsis.woot.configuration.loaders;

import com.google.gson.*;
import ipsis.woot.oss.FileUtils;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.util.JsonHelper;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;

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

                int k = 0;
                int[] sizes = new int[4];
                for (JsonElement jsonElement : sizesArray) {
                    sizes[k++] = jsonElement.getAsInt();
                }

                JsonArray chancesArray = JsonUtils.getJsonArray(dropObject, "chances");
                if (chancesArray.size() != 4)
                    throw new JsonSyntaxException("Chances must contain 4 entries");

                k = 0;
                int[] chances = new int[4];
                for (JsonElement jsonElement : chancesArray) {
                    chances[k++] = jsonElement.getAsInt();
                }

                LogHelper.info(wootMobName + "-" + itemStack + "-" +
                        sizes[0] + "#" + sizes[1] + "#" + sizes[2] + "#" + sizes[3] + "#" +
                        chances[0] + "#" + chances[1] + "#" + chances[2] + "#" + chances[3]);
            }

        }
    }
}
