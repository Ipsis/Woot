package ipsis.woot.manager.loot;

import com.google.gson.*;
import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.text.TextComponentString;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Drop {

    ItemStack itemStack;
    int count;
    List<DropData> weights;

    public float getChance(int sampleSize) {

        if (sampleSize == 0)
            return 0.0F;

        return ((float)count / (float)sampleSize);
    }

    public Drop(ItemStack itemStack) {

        this.itemStack = ItemStack.copyItemStack(itemStack);
        count = 0;
        weights = new ArrayList<DropData>();
    }

    public void update(int stackSize) {

        count++;
        boolean found = false;
        for (DropData dropData: weights) {
            if (dropData.stackSize == stackSize) {
                dropData.incItemWeight();
                found = true;
            }
        }

        if (!found)
            weights.add(new DropData(stackSize, 1));
    }

    public int getWeightedSize() {

        DropData dropData = WeightedRandom.getRandomItem(Woot.RANDOM, weights);
        if (dropData != null)
            return dropData.stackSize;

        return 1;
    }

    /**
     * Serialization
     */
    public static class Serializer implements JsonSerializer<Drop>, JsonDeserializer<Drop> {

        @Override
        public Drop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }

        @Override
        public JsonElement serialize(Drop src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject jsonObject = new JsonObject();
            // TODO how to handle the itemstack
            jsonObject.addProperty("item", src.itemStack.getDisplayName());
            jsonObject.addProperty("count", src.count);
            jsonObject.add("weights", context.serialize(src.weights));
            return jsonObject;
        }
    }

    public static class DropData extends WeightedRandom.Item {

        public final int stackSize;

        public DropData(int stackSize, int itemWeight) {

            super(itemWeight);
            this.stackSize = stackSize;
        }

        public void incItemWeight() {
            itemWeight++;
        }

        /**
         * Serialization
         */
        public static class Serializer implements JsonSerializer<DropData>, JsonDeserializer<DropData> {

            @Override
            public DropData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return null;
            }

            @Override
            public JsonElement serialize(DropData src, Type typeOfSrc, JsonSerializationContext context) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("stack_size", src.stackSize);
                jsonObject.addProperty("weight", src.itemWeight);
                return jsonObject;
            }
        }
    }

}


