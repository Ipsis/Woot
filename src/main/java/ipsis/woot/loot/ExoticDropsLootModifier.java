package ipsis.woot.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.modules.factory.Exotic;
import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.util.helper.RandomHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Testing
 * /execute in minecraft:the_end run teleport ~ ~ ~
 * /locate endcity
 * /loot insert x y z loot minecraft:chests/end_city_treasure
 * /setblock ~ ~ ~ minecraft:chest{LootTable:"minecraft:chests/end_city_treasure"}
 */

public class ExoticDropsLootModifier extends LootModifier {

    private int rolls;
    private double dropChance;

    private List<DropWeighted> drops = new ArrayList<>();

    public ExoticDropsLootModifier(ILootCondition[] conditions, int rolls, double chance, int[] weights) {
        super(conditions);
        // pull the rolls and drop chances from the config
        this.rolls = rolls;
        this.dropChance = FactoryConfiguration.EXOTIC.get();
        drops.add(new DropWeighted(Exotic.EXOTIC_A.getItemStack(), weights[0]));
        drops.add(new DropWeighted(Exotic.EXOTIC_B.getItemStack(), weights[1]));
        drops.add(new DropWeighted(Exotic.EXOTIC_C.getItemStack(), weights[2]));
        drops.add(new DropWeighted(Exotic.EXOTIC_D.getItemStack(), weights[3]));
        drops.add(new DropWeighted(Exotic.EXOTIC_E.getItemStack(), weights[4]));
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        for (int roll = 0; roll < rolls; roll++) {
            if (RandomHelper.rollPercentage(dropChance, "exoticDrops")) {
                ItemStack drop = getWeightedDrop();
                if (!drop.isEmpty())
                    generatedLoot.add(drop.copy());
                else
                    Woot.setup.getLogger().error("Rolled exotic dropping as empty stack");
            }
        }
        return generatedLoot;
    }

    private ItemStack getWeightedDrop() {
        if (WeightedRandom.getTotalWeight(drops) > 0)
            return WeightedRandom.getRandomItem(RandomHelper.RANDOM, drops).itemStack;
        return ItemStack.EMPTY;
    }

    private class DropWeighted extends WeightedRandom.Item {
        public ItemStack itemStack;
        public DropWeighted(ItemStack itemStack, int weight) {
            super(weight);
            this.itemStack = itemStack;
        }
    }

    public static class Serializer extends GlobalLootModifierSerializer<ExoticDropsLootModifier> {

        @Override
        public ExoticDropsLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            int rolls = JSONUtils.getInt(object, "rolls");
            double chance = JSONUtils.getFloat(object, "dropChance");
            int[] weights = new int[Exotic.getExoticCount()];
            for (int i = 0; i < weights.length; i++)
                weights[i] = 1;

            JsonArray jsonArray = JSONUtils.getJsonArray(object, "weights");
            if (jsonArray.isJsonArray() && jsonArray.size() == Exotic.getExoticCount()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonElement element = jsonArray.get(i);
                    try {
                        weights[i] = element.getAsInt();
                    } catch (Exception e) {
                        Woot.setup.getLogger().error("ExoticDrops: weights not all integers {}", element);
                        weights[i] = 1;
                    }
                }
            }

            Woot.setup.getLogger().info("ExoticDrops rolls:{} chance:{} weights:{}", rolls, chance, weights);
            return new ExoticDropsLootModifier(conditions, rolls, chance, weights);
        }

        @Override
        public JsonObject write(ExoticDropsLootModifier instance) {
            ILootCondition[] conditions = new ILootCondition[0];
            return makeConditions(conditions);
        }
    }
}
