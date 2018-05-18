package ipsis.woot.loot.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.Woot;
import ipsis.woot.command.ITextStatus;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.oss.ItemHelper;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.plugins.thauncraft.Thaumcraft;
import ipsis.woot.util.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

public class LootRepository implements ILootRepositoryLoad, ILootRepositoryLearn, ILootRepositoryLookup, ILootRepositoryWriter, ITextStatus {

    public static final int VERSION = 1;

    private Map<WootMobName, Integer[]> samples = new HashMap<>();
    private List<LootDrop> drops = new ArrayList<>();

    private boolean equalDrops(ItemStack a, ItemStack b) {

        // Same item and NBT
        if (!ItemHelper.itemsEqualWithoutMetadata(a, b, true))
            return false;

        if (a.getHasSubtypes()) {
            // Same subtype
            if (ItemHelper.getItemDamage(a) != ItemHelper.getItemDamage(b))
                return false;
        }

        // We dont care about damage
        return true;
    }

    private LootDrop getLootDrop(ItemStack itemStack) {

        if (itemStack.isEmpty())
            return null;

        for (LootDrop curr : drops) {

            if (equalDrops(curr.getItemStack(), itemStack))
                return curr;
        }

        return null;
    }

    private @Nullable LootDrop.LootMob getLootMob(LootDrop lootDrop, WootMobName wootMobName) {

        if (!wootMobName.isValid())
            return null;

        for (LootDrop.LootMob lootMob : lootDrop.getMobs()) {
            if (lootMob.getWootMobName().equals(wootMobName))
                return lootMob;
        }

        return null;
    }

    private Integer[] getMobSamples(WootMobName wootMobName) {

        return samples.get(wootMobName);
    }

    public int getSampleCount(WootMobName wootMobName, EnumEnchantKey key) {

        Integer[] s = samples.get(wootMobName);
        if (s != null)
            return s[key.ordinal()];

        return 0;
    }

    private void incMobSample(WootMobName wootMobName, EnumEnchantKey key) {

        Integer[] mobSamples = getMobSamples(wootMobName);
        if (mobSamples == null) {
            mobSamples = new Integer[]{0, 0, 0, 0};
            samples.put(wootMobName, mobSamples);
        }

        mobSamples[key.ordinal()]++;
    }

    private List<ItemStack> convertDrops(List<EntityItem> drops) {

        List<ItemStack> items = new ArrayList<>();
        for (EntityItem entityItem : drops) {
            ItemStack itemStack = entityItem.getItem();

            boolean updated = false;
            for (ItemStack i2 : items) {

                if (equalDrops(i2, itemStack)) {
                    i2.setCount(i2.getCount() + 1);
                    updated = true;
                }
            }

            if (!updated) {
                items.add(itemStack);
            }
        }

        return items;
    }

    /**
     * ILootRepositoryLoad
     */
    @Override
    public void loadMobSample(WootMobName wootMobName, int l0, int l1, int l2, int l3) {

        if (samples.containsKey(wootMobName)) {
            LogHelper.error("loadMobSample: duplicate mob sample found " + wootMobName);
        } else {
            samples.put(wootMobName, new Integer[]{l0, l1, l2, l3});
        }
    }

    @Override
    public void loadItem(ItemStack itemStack) {

        LootDrop lootDrop = getLootDrop(itemStack);
        if (lootDrop != null) {
            LogHelper.error("loadItem: duplicate item found " + itemStack);
            return;
        }

        drops.add(new LootDrop(itemStack));
    }

    @Override
    public void loadMobDrop(WootMobName wootMobName, EnumEnchantKey key, ItemStack itemStack, int stackSize, int drops) {

        LootDrop lootDrop = getLootDrop(itemStack);
        if (lootDrop == null) {
            LogHelper.error("loadMobDrop: no matching itemstack found");
            return;
        }

        LootDrop.LootMob lootMob = getLootMob(lootDrop, wootMobName);
        if (lootMob == null) {
            lootMob = new LootDrop.LootMob(wootMobName);
            lootDrop.getMobs().add(lootMob);
        }

        HashMap<Integer, Integer> map = lootMob.getLooting(key);
        map.put(stackSize, drops);
    }

    private static boolean hasCustomDropsOnly(WootMobName wootMobName) {

        if (wootMobName.isEnderDragon() || wootMobName.isChaosGuardian() || wootMobName.isThaumcraftWisp())
            return true;

        return false;
    }

    /**
     * ILootRepositoryLearn
     */
    @Override
    public boolean isEmpty(WootMobName wootMobName, EnumEnchantKey key) {

        if (hasCustomDropsOnly(wootMobName))
            return false;

        int sampleCount = getSampleCount(wootMobName, key);
        if (sampleCount == 0)
            return true;

        return false;
    }

    @Override
    public boolean isFull(WootMobName wootMobName, EnumEnchantKey key) {

        if (hasCustomDropsOnly(wootMobName))
            return true;

        int sampleCount = getSampleCount(wootMobName, key);
        return sampleCount >= Woot.wootConfiguration.getInteger(EnumConfigKey.SAMPLE_SIZE);
    }

    @Override
    public void learn(WootMobName wootMobName, EnumEnchantKey key, @Nonnull List<EntityItem> drops, boolean updateSampleCount) {

        // No drops is a completely valid learn

        if (hasCustomDropsOnly(wootMobName))
            return;

        if (updateSampleCount)
            incMobSample(wootMobName, key);

        List<ItemStack> flattenedDrops = convertDrops(drops);

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.LEARN, "learn", wootMobName + " with items " + flattenedDrops.size());

        for (ItemStack itemStack : flattenedDrops) {

            if (!Woot.policyRepository.canLearnDrop(itemStack)) {
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.LEARN, "learn", "policy removed " + itemStack);
                continue;
            }

            Woot.debugSetup.trace(DebugSetup.EnumDebugType.LEARN, "learn", "learning " + itemStack);
            LootDrop lootDrop = getLootDrop(itemStack);
            if (lootDrop == null) {
                lootDrop = new LootDrop(itemStack);
                this.drops.add(lootDrop);
            }

            LootDrop.LootMob lootMob = getLootMob(lootDrop, wootMobName);
            if (lootMob == null) {
                lootMob = new LootDrop.LootMob(wootMobName);
                lootDrop.getMobs().add(lootMob);
            }

            HashMap<Integer, Integer> map = lootMob.getLooting(key);

            int count = 0;
            Integer c = map.get(itemStack.getCount());
            if (c != null)
                count = c;

            count++;
            map.put(itemStack.getCount(), count);
        }
    }

    /**
     * ILootRepositoryLookup
     */
    @Override
    public List<ILootRepositoryLookup.LootItemStack> getDrops(WootMobName wootMobName, EnumEnchantKey key) {

        if (hasCustomDropsOnly(wootMobName))
            return new ArrayList<>();

        List<ILootRepositoryLookup.LootItemStack> lootDrops = new ArrayList<>();

        int sampleCount = getSampleCount(wootMobName, key);
        if (sampleCount > 0) {
            for (LootDrop curr : drops) {
                LootDrop.LootMob lootMob = getLootMob(curr, wootMobName);
                if (lootMob != null) {

                    HashMap<Integer, Integer> looting = lootMob.getLooting(key);
                    if (looting.isEmpty())
                        continue;

                    ILootRepositoryLookup.LootItemStack lootItemStack = new ILootRepositoryLookup.LootItemStack(curr.getItemStack().copy());

                    int maxChance = 0;
                    for (Integer s : looting.keySet()) {
                        Integer d = looting.get(s);
                        if (d > 0) {
                            int chance = Math.round(((float)d/(float)sampleCount) * 100.0F);
                            chance = MathHelper.clamp(chance, 1, 100);

                            lootItemStack.sizes.put(s, chance);
                            if (chance > maxChance)
                                maxChance = chance;
                        }
                    }

                    if (!lootItemStack.sizes.isEmpty()) {
                        lootItemStack.dropChance = maxChance;
                        lootDrops.add(lootItemStack);
                    }
                }
            }
        }

        return lootDrops;
    }

    @Override
    public List<String> getAllMobs() {

        List<String> mobs = new ArrayList<>();
        for (WootMobName wootMobName : samples.keySet())
            mobs.add(wootMobName.getName());
        return mobs;
    }

    // ITextStatus
    @Override
    public List<String> getStatus() {

        List<String> status = new ArrayList<>();
        for (WootMobName wootMobName : samples.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(wootMobName).append("-");
            sb.append(getSampleCount(wootMobName, EnumEnchantKey.NO_ENCHANT)).append("/");
            sb.append(getSampleCount(wootMobName, EnumEnchantKey.LOOTING_I)).append("/");
            sb.append(getSampleCount(wootMobName, EnumEnchantKey.LOOTING_II)).append("/");
            sb.append(getSampleCount(wootMobName, EnumEnchantKey.LOOTING_III));
            status.add(sb.toString());
        }
        return status;
    }

    @Override
    public List<String> getStatus(WorldServer worldServer) {

        return new ArrayList<>();
    }

    /**
     * ILootRepositoryWriter
     */
    @Override
    public void writeToJsonFile(File file) {

        JsonObject obj = new JsonObject();
        obj.addProperty("version", VERSION);

        JsonArray mobsArray = new JsonArray();
        {
            for (WootMobName wootMobName : samples.keySet()) {
                JsonObject obj2 = new JsonObject();
                obj2.addProperty("mobName", wootMobName.toString());
                JsonArray sampleArray = new JsonArray();
                for (Integer i : samples.get(wootMobName))
                    sampleArray.add(i);
                obj2.add("samples", sampleArray);
                mobsArray.add(obj2);
            }
        }
        obj.add("mobs", mobsArray);

        JsonArray dropsArray = new JsonArray();
        {
            for (LootDrop lootDrop : drops) {
                if (lootDrop.getMobs().isEmpty())
                    continue;

                JsonObject jsonObject = new JsonObject();
                JsonObject itemStackObject = JsonHelper.toJsonObject(lootDrop.getItemStack());
                jsonObject.add("drop", itemStackObject);

                mobsArray = new JsonArray();
                for (LootDrop.LootMob lootMob : lootDrop.getMobs()) {

                    JsonObject mobObject = new JsonObject();
                    mobObject.addProperty("mobName", lootMob.getWootMobName().toString());
                    JsonArray sizesArray = new JsonArray();
                    for (EnumEnchantKey key : EnumEnchantKey.values()) {
                        HashMap<Integer, Integer> map = lootMob.getLooting(key);
                        if (map.isEmpty())
                            continue;

                        for (Integer c : map.keySet()) {
                            JsonObject sizeObject = new JsonObject();
                            sizeObject.addProperty("count", c);
                            sizeObject.addProperty("looting", key.ordinal());
                            sizeObject.addProperty("samples", map.get(c));
                            sizesArray.add(sizeObject);
                        }
                    }
                    mobObject.add("sizes", sizesArray);
                    mobsArray.add(mobObject);
                }
                jsonObject.add("mobs", mobsArray);
                dropsArray.add(jsonObject);
            }
        }
        obj.add("drops", dropsArray);

        //Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
        String t = GSON.toJson(obj);
        SerializationHelper.writeJsonFile(file, t);
    }

    /**
     * ILootRepositoryControl
     */
    public void flushAll() {

        samples.clear();
        drops.clear();
    }

    public void flushMob(WootMobName wootMobName) {

        samples.remove(wootMobName);

        for (LootDrop lootDrop : drops) {
            if (lootDrop.getMobs().isEmpty())
                continue;

           Iterator<LootDrop.LootMob> iter = lootDrop.getMobs().listIterator();
           while (iter.hasNext()) {
               LootDrop.LootMob lootMob = iter.next();
               if (lootMob.getWootMobName().equals(wootMobName))
                   iter.remove();
           }
        }
    }

}
