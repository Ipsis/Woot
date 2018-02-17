package ipsis.woot.configuration.loaders;

import com.google.gson.*;
import ipsis.Woot;
import ipsis.woot.farming.SpawnRecipe;
import ipsis.woot.oss.FileUtils;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.util.JsonHelper;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.fluids.FluidStack;

import static ipsis.woot.util.JsonHelper.getFluidStack;
import static ipsis.woot.util.JsonHelper.getItemStack;

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
            e.printStackTrace();
        }
    }

    private void parseConfig(JsonObject json) {

        if (json == null || json.isJsonNull())
            throw new JsonSyntaxException("Json cannot be null");

        int version = JsonUtils.getInt(json, "version", -1);
        if (version == -1 || version != 1)
            throw new JsonSyntaxException("Invalid version");

        if (JsonUtils.hasField(json, "default"))
            parseDefaultIngredients(JsonUtils.getJsonObject(json, "default"));

        for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients")) {
            parseIngredients(ele);
        }
    }

    private void parseDefaultIngredients(JsonObject ele) {

        if (ele == null || !ele.isJsonObject())
            throw new JsonSyntaxException("Default ingredients must be objects");

        JsonObject json = (JsonObject)ele;

        boolean efficiency = JsonUtils.getBoolean(json, "efficiency", true);
        Woot.spawnRecipeRepository.setDefaultEfficiency(efficiency);

        for (JsonElement ele2 : JsonUtils.getJsonArray(json, "items")) {

            if (ele2 == null || !ele.isJsonObject())
                throw new JsonSyntaxException("Recipe items must be object");

            JsonObject json2 = (JsonObject) ele2;
            ItemStack itemStack = getItemStack(json2);
            if (!itemStack.isEmpty())
                Woot.spawnRecipeRepository.addDefaultItem(itemStack);
        }

        for (JsonElement ele2 : JsonUtils.getJsonArray(json, "fluids")) {

            if (ele2 == null | !ele.isJsonObject())
                throw new JsonSyntaxException("Recipe fluids must be object");

            JsonObject json2 = (JsonObject)ele2;
            FluidStack fluidStack = getFluidStack(json2);
            if (fluidStack != null && fluidStack.amount > 0)
                Woot.spawnRecipeRepository.addDefaultFluid(fluidStack);
        }
    }

    private void parseIngredients(JsonElement ele) {

        if (ele == null || !ele.isJsonObject())
            throw new JsonSyntaxException("Ingredients must be objects");

        JsonObject json = (JsonObject)ele;

        WootMobName wootMobName = JsonHelper.getWootMobName(json);
        if (wootMobName.isValid()) {

            SpawnRecipe recipe = new SpawnRecipe();

            recipe.setEfficiency(true);

            boolean valid = true;
            for (JsonElement ele2 : JsonUtils.getJsonArray(json, "items")) {

                if (ele2 == null || !ele.isJsonObject())
                    throw new JsonSyntaxException("Recipe items must be object");

                JsonObject json2 = (JsonObject) ele2;
                ItemStack itemStack = getItemStack(json2);
                if (itemStack.isEmpty())
                    valid = false;
                else
                    recipe.addIngredient(itemStack);
            }

            for (JsonElement ele2 : JsonUtils.getJsonArray(json, "fluids")) {

                if (ele2 == null | !ele.isJsonObject())
                    throw new JsonSyntaxException("Recipe fluids must be object");

                JsonObject json2 = (JsonObject)ele2;
                FluidStack fluidStack = getFluidStack(json2);
                if (fluidStack == null) {
                    valid = false;
                } else {
                    if (fluidStack.amount < 1)
                        valid = false;
                    else
                        recipe.addIngredient(fluidStack);
                }
            }

            // valid will be false if any item of fluid was not found
            if (valid && (!recipe.getItems().isEmpty() || !recipe.getFluids().isEmpty()))
                Woot.spawnRecipeRepository.add(wootMobName, recipe);
        }
    }
}
