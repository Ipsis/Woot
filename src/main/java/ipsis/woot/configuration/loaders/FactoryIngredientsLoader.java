package ipsis.woot.configuration.loaders;

import com.google.gson.*;
import ipsis.woot.oss.FileUtils;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.util.JsonHelper;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import net.minecraft.util.JsonUtils;

public class FactoryIngredientsLoader {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public void loadConfig() {

        try {
            String configText = FileUtils.copyConfigFileFromJar(Files.FACTORY_ING_FILENAME, false);
            JsonObject jsonObject = JsonUtils.fromJson(GSON, configText, JsonObject.class, false);
            parseConfig(jsonObject);
        } catch (Exception e) {
            LogHelper.error("Could not load factory ingredients from " +
                FileUtils.getConfigFile(Files.FACTORY_ING_FILENAME).getAbsolutePath());
        }
    }

    private void parseConfig(JsonObject json) {

        if (json == null || json.isJsonNull())
            throw new JsonSyntaxException("Json cannot be null");

        int version = JsonUtils.getInt(json, "version", -1);
        if (version == -1 || version != 1)
            throw new JsonSyntaxException("Invalid version");

        for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients")) {
            parseIngredients(ele);
        }
    }

    private void parseIngredients(JsonElement ele) {

        if (ele == null || !ele.isJsonObject())
            throw new JsonSyntaxException("Ingredients must be objects");

        JsonObject json = (JsonObject)ele;

        WootMobName wootMobName = JsonHelper.getWootMobName(json);
        if (wootMobName.isValid()) {

            boolean efficieny = JsonUtils.getBoolean(json, "efficiency", false);

            for (JsonElement ele2 : JsonUtils.getJsonArray(json, "items")) {

                if (ele2 == null || !ele.isJsonObject())
                    throw new JsonSyntaxException("Mob config must be object");

                JsonObject json2 = (JsonObject) ele2;

                LogHelper.info(wootMobName + " - " + efficieny + " - "  + json2);
            }
        }
    }
}
