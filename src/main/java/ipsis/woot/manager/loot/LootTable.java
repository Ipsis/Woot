package ipsis.woot.manager.loot;

import com.google.gson.*;
import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Settings;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;
import java.util.List;

public class LootTable {

    String mobName;
    LootPool[] pools;

    public LootTable(String mobName) {

        this.mobName = mobName;
        pools = new LootPool[EnumEnchantKey.values().length];
        for (EnumEnchantKey k : EnumEnchantKey.values())
            pools[k.ordinal()] = new LootPool(k);
    }

    public LootTable(String mobName, LootPool[] pools) {

        this.mobName = mobName;
        this.pools = pools;
    }

    public boolean isFull(EnumEnchantKey key) {

        LootPool pool = getLootPool(key);
        return pool.samples == Settings.sampleSize;
    }

    public boolean isEmpty(EnumEnchantKey key) {

        LootPool pool = getLootPool(key);
        return pool.samples == 0;
    }

    private LootPool getLootPool(EnumEnchantKey key) {

        return pools[key.ordinal()];
    }

    public void flush(EnumEnchantKey key) {

        pools[key.ordinal()] =  new LootPool(key);
    }

    public void update(EnumEnchantKey key, List<EntityItem> drops) {

        LootPool pool = getLootPool(key);
        pool.samples++;

        for (EntityItem entityItem : drops) {
            ItemStack itemStack = entityItem.getEntityItem();

            boolean found = false;
            for (Drop d : pool.drops) {
                if (d.itemStack.isItemEqualIgnoreDurability(itemStack)) {
                    d.update(itemStack.stackSize);
                    found = true;
                    break;
                }
            }

            if (!found) {
                Drop d = new Drop(itemStack);
                d.update(itemStack.stackSize);
                pool.drops.add(d);
            }
        }
    }

    public void getDrops(EnumEnchantKey key, List<ItemStack> loot) {

        LootPool pool = getLootPool(key);
        for (Drop d : pool.drops) {
            float chance = Woot.RANDOM.nextFloat();
            if (chance <= d.getChance(pool.samples)) {
                if (Item.getItemFromBlock(Blocks.BEDROCK) == d.itemStack.getItem())
                    continue;

                ItemStack dropStack = ItemStack.copyItemStack(d.itemStack);
                dropStack.stackSize = d.getWeightedSize();
                loot.add(dropStack);
            }
        }
    }

    public String getDrops(EnumEnchantKey key, boolean detail) {

        StringBuilder sb = new StringBuilder();
        LootPool pool = getLootPool(key);
        sb.append(pool.samples).append(" ");
        for (Drop d : pool.drops) {
            float chance = ((float)d.count/(float)pool.samples) * 100.0F;
            sb.append(String.format("[ %dx%s @ %.2f%%",
                    d.count, d.itemStack.getDisplayName(), chance));

            if (detail) {
                sb.append(" ");
                for (Drop.DropData dd : d.weights) {
                   sb.append(String.format("(s%d:w%d)", dd.stackSize, dd.itemWeight));
                }
            }

            sb.append(" ]");
        }

        return sb.toString();
    }

    /**
     * Serialization
     */
    public static class Serializer implements JsonSerializer<LootTable>, JsonDeserializer<LootTable> {

        @Override
        public LootTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject jsonObject = JsonUtils.getJsonObject(json, "table");
            String mobName = JsonUtils.getString(jsonObject, "mob");

            LootPool[] pools;
            if (jsonObject.has("pools"))
                pools = (LootPool[])(JsonUtils.deserializeClass(jsonObject, "pools", context, LootPool[].class));
            else
                pools = new LootPool[0];

            LootTable lootTable = new LootTable(mobName);
            lootTable.pools = pools;
            return lootTable;
        }

        @Override
        public JsonElement serialize(LootTable src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mob", src.mobName);
            jsonObject.add("pools", context.serialize(src.pools));
            return jsonObject;
        }
    }
}
