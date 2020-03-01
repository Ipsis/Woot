package ipsis.woot.modules.simulation.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;

public class DropLibrary {

    protected static final Logger LOGGER = LogManager.getLogger();
    private static DropLibrary INSTANCE = new DropLibrary();
    private Map<FakeMob, MobData> mobs = new HashMap<>();

    /**
     * Gets the current entry in the map or initialises a new one
     */
    private @Nonnull MobData getMobData(@Nonnull FakeMob fakeMob) {
        if (!mobs.containsKey(fakeMob))
            mobs.put(fakeMob, new MobData(fakeMob));
        return mobs.get(fakeMob);
    }

    public static DropLibrary getInstance() { return INSTANCE; };
    public Set<FakeMob> getKnownMobs() { return mobs.keySet(); }

    public void learnSimulatedDrops(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        LOGGER.debug("learn {}", fakeMobKey);
        MobData mobData = getMobData(fakeMobKey.getMob());
        mobData.incrementSimulatedKills(fakeMobKey.getLooting());
        learnSimulatedDropsSilent(fakeMobKey, drops);
    }

    public void learnSimulatedDropsSilent(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        LOGGER.debug("learnSilent {}", fakeMobKey);
        MobData mobData = getMobData(fakeMobKey.getMob());
        drops.forEach(d -> mobData.addSimulatedDrop(fakeMobKey.getLooting(), d));
    }

    /**
     * Returns a list of drops with a high level summary
     */
    public @Nonnull List<DropSummary> getDropSummary(@Nonnull FakeMobKey fakeMobKey) {

        List<DropSummary> drops = new ArrayList<>();
        MobData mobData = mobs.getOrDefault(fakeMobKey.getMob(), null);
        if (mobData != null) {
            for (MobDropData mobDropData : mobData.getDrops())
                drops.add(DropSummary.createFromMobDropData(mobDropData));
        }

        return drops;
    }

    public @Nonnull List<ItemStack> getRolledDrops(@Nonnull FakeMobKey fakeMobKey) {
        List<ItemStack> drops = new ArrayList<>();
        MobData mobData = mobs.getOrDefault(fakeMobKey.getMob(), null);
        if (mobData != null) {
            for (MobDropData mobDropData : mobData.getDrops()) {
                ItemStack itemStack = mobDropData.rollDrop(fakeMobKey.getLooting());
                if (!itemStack.isEmpty())
                    drops.add(itemStack);
            }
        }
        return drops;
    }

    /**
     * Equality
     */
    public boolean isEqualForLearning(@Nonnull ItemStack a, @Nonnull ItemStack b) {
        return ItemStack.areItemsEqualIgnoreDurability(a, b);
    }

    /**
     * Save/Load
     */
    private static final int JSON_VERSION = 1;
    private static final String VERSION_TAG = "version";
    private static final String MOBS_TAG = "mobs";

    public void fromJson(JsonObject jsonObject) {
        if (jsonObject == null || jsonObject.isJsonNull())
            throw new JsonSyntaxException("JsonObject cannot be null");

        if (JSONUtils.getInt(jsonObject, VERSION_TAG, 0) != JSON_VERSION)
            throw new JsonSyntaxException("Loot file version invalid");

    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(VERSION_TAG, JSON_VERSION);

        JsonArray mobsArray = new JsonArray();
        {
            for (Map.Entry<FakeMob, MobData> entry : mobs.entrySet()) {
                FakeMob fakeMob = entry.getKey();
                MobData mobData = entry.getValue();

                if (!fakeMob.isValid())
                    continue;

                if (!PolicyRegistry.get().canSimulate(fakeMob.getResourceLocation()))
                    continue;

                JsonObject mobObject = mobData.toJson(fakeMob);
                mobsArray.add(mobObject);
            }
        }
        jsonObject.add(MOBS_TAG, mobsArray);

        return jsonObject;
    }









}
