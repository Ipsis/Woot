package ipsis.woot.configuration.loaders;

import com.google.gson.*;
import ipsis.Woot;
import ipsis.woot.configuration.ChangeLog;
import ipsis.woot.oss.FileUtils;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import net.minecraft.util.JsonUtils;

public class ChangelogLoader {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public void load() {

        try {
            String changelog = FileUtils.getFileFromJar(Files.CHANGELOG_FILENAME);
            JsonObject jsonObject = JsonUtils.fromJson(GSON, changelog, JsonObject.class, false);
            parse(jsonObject);
        } catch (Exception e) {
            LogHelper.error("Could not load changelog");
            e.printStackTrace();
        }
    }

    private void parse(JsonObject jsonObject) {

        if (jsonObject == null || jsonObject.isJsonNull())
            throw new JsonSyntaxException("Json cannot be null");

        for (JsonElement ele : JsonUtils.getJsonArray(jsonObject, "versions")) {

            if (ele == null || !ele.isJsonObject())
                continue;

            JsonObject json2 = (JsonObject)ele;
            String version = JsonUtils.getString(json2, "version", "");
            if (!version.equalsIgnoreCase("")) {

                ChangeLog.Changes changes = Woot.changeLog.addVersion(version);
                if (changes == null)
                    continue;

                for (JsonElement ele2 : JsonUtils.getJsonArray(json2, "features")) {
                    if (ele2 == null || !ele2.isJsonObject())
                        continue;

                    JsonObject json3 = (JsonObject)ele2;
                    int id = JsonUtils.getInt(json3, "id", -1);
                    String desc = JsonUtils.getString(json3, "desc", "");
                    changes.addFeature(id, desc);
                }

                for (JsonElement ele2 : JsonUtils.getJsonArray(json2, "fixes")) {
                    if (ele2 == null || !ele2.isJsonObject())
                        continue;

                    JsonObject json3 = (JsonObject)ele2;
                    int id = JsonUtils.getInt(json3, "id", -1);
                    String desc = JsonUtils.getString(json3, "desc", "");
                    changes.addFix(id, desc);
                }
            }
        }
    }
}
