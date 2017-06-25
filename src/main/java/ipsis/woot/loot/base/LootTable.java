package ipsis.woot.loot.base;

import com.google.gson.*;
import ipsis.Woot;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.configuration.EnumConfigKey;
import mezz.jei.util.MathUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
        return pool.getSamples() >= Woot.wootConfiguration.getInteger(EnumConfigKey.SAMPLE_SIZE);
    }

    public boolean isEmpty(EnumEnchantKey key) {

        LootPool pool = getLootPool(key);
        return pool.getSamples() == 0;
    }

    public int getSamples(EnumEnchantKey key) {

        LootPool pool = getLootPool(key);
        return pool.getSamples();
    }

    public List<ItemStack> getDropInfo(EnumEnchantKey key) {

        List<ItemStack> drops = new ArrayList<>();
        LootPool pool = getLootPool(key);
        for (Drop d : pool.getDrops()) {
            ItemStack addStack = d.itemStack.copy();
            float chance = d.getChance(pool.getSamples()) * 100.0F;
            addStack.setCount((int)chance);
            drops.add(addStack);
        }

        return drops;
    }

    private LootPool getLootPool(EnumEnchantKey key) {

        return pools[key.ordinal()];
    }

    public void flush(EnumEnchantKey key) {

        pools[key.ordinal()] =  new LootPool(key);
    }

    public void insertStatic(EnumEnchantKey key, ItemStack itemStack, int dropChance) {

        dropChance = MathUtil.clamp(dropChance, 1, 100);

        LootPool pool = getLootPool(key);
        pool.setSamples(100);

        /**
         * Pretend we saw it dropChance times in 100 samples
         */
        for (int i = 0; i < dropChance; i++) {
            boolean found = false;
            for (Drop d : pool.getDrops()) {
                if (d.itemStack.isItemEqualIgnoreDurability(itemStack)) {
                    d.update(itemStack.getCount());
                    found = true;
                    break;
                }
            }

            if (!found) {
                Drop d = new Drop(itemStack);
                d.update(itemStack.getCount());
                pool.addToDrops(d);
            }
        }
    }

    public void update(EnumEnchantKey key, List<EntityItem> drops, boolean updateCount) {

        LootPool pool = getLootPool(key);
        if (updateCount)
            pool.incSamples();

        for (EntityItem entityItem : drops) {
            ItemStack itemStack = entityItem.getItem();

            boolean found = false;
            for (Drop d : pool.getDrops()) {
                if (d.itemStack.isItemEqualIgnoreDurability(itemStack)) {
                    d.update(itemStack.getCount());
                    found = true;
                    break;
                }
            }

            if (!found) {
                Drop d = new Drop(itemStack);
                d.update(itemStack.getCount());
                pool.addToDrops(d);
            }
        }
    }

    public void getDrops(EnumEnchantKey key, List<ItemStack> loot) {

        LootPool pool = getLootPool(key);
        for (Drop d : pool.getDrops()) {
            float chance = Woot.RANDOM.nextFloat();
            if (chance <= d.getChance(pool.getSamples())) {
                if (Item.getItemFromBlock(Blocks.BEDROCK) == d.itemStack.getItem())
                    continue;

                ItemStack dropStack = d.itemStack.copy();
                dropStack.setCount(d.getWeightedSize());

                /* Set damage value */
                if (dropStack.isItemStackDamageable()) {
                    int dmg = Woot.RANDOM.nextInt(dropStack.getMaxDamage()) + 1;
                    dropStack.setItemDamage(dmg);
                }

                loot.add(dropStack);
            }
        }
    }

    public String getDrops(EnumEnchantKey key, boolean detail) {

        StringBuilder sb = new StringBuilder();
        LootPool pool = getLootPool(key);
        sb.append(pool.getSamples()).append(" ");
        for (Drop d : pool.getDrops()) {
            float chance = d.getChance(pool.getSamples()) * 100.0F;
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

    public List<FullDropInfo> getFullDropInfo(EnumEnchantKey key) {

        List<FullDropInfo> info = new ArrayList<FullDropInfo>();
        LootPool pool = getLootPool(key);
        for (Drop d : pool.getDrops()) {

            float chance = d.getChance(pool.getSamples()) * 100.0F;
            if (d.itemStack != null)
                info.add(new FullDropInfo(d.itemStack.copy(), chance));
        }

        return info;
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
