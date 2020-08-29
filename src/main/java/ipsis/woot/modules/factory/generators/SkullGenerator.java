package ipsis.woot.modules.factory.generators;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import ipsis.woot.Woot;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.RandomHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;

public class SkullGenerator {

    private HashMap<FakeMob, ItemStack> skulls = new HashMap<>();

    public void loadFromConfig(List<String> config) {

        skulls.clear();

        for (String s : config) {
            String[] parts = s.split(",");
            if (parts.length != 2) {
                Woot.setup.getLogger().error(s + " == INVALID");
            } else {
                FakeMob fakeMob = new FakeMob(parts[0]);
                if (!fakeMob.isValid()) {
                    Woot.setup.getLogger().error(s + " == INVALID (mob {})", parts[0]);
                } else {
                    try {
                        JsonObject jsonObject = JSONUtils.fromJson(parts[1]);
                        if (jsonObject.isJsonObject()) {
                            ItemStack itemStack = ShapedRecipe.deserializeItem(jsonObject);
                            if (!itemStack.isEmpty()) {
                                Woot.setup.getLogger().info("SkullGenerator: {} -> {}", fakeMob, itemStack);
                                skulls.put(fakeMob, itemStack);
                            }
                        }
                    } catch (JsonParseException e) {
                        Woot.setup.getLogger().error("SkullGenerator: invalid head {}", parts[1]);
                    }
                }
            }
        }
    }

    public ItemStack getSkullDrop(FakeMob fakeMob, float chance) {

        ItemStack skull = skulls.getOrDefault(fakeMob, ItemStack.EMPTY);
        if (!skull.isEmpty()) {
            if (RandomHelper.rollPercentage(chance, "getSkullDrop"))
                return skull.copy();
        }

        return ItemStack.EMPTY;
    }
}
