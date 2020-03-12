package ipsis.woot.simulator.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public class LootLibrary {

    private Map<FakeMob, SimulatedMob> mobs = new HashMap<>();

    private @Nonnull SimulatedMob getOrCreateSimulatedMob(@Nonnull FakeMob fakeMob) {
        if (!mobs.containsKey(fakeMob))
            mobs.put(fakeMob, new SimulatedMob(fakeMob));
        return mobs.get(fakeMob);
    }

    /**
     * API
     */
    public void learnSimulatedDrops(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        SimulatedMob simulatedMob = getOrCreateSimulatedMob(fakeMobKey.getMob());
        simulatedMob.incrementSimulatedKills(fakeMobKey.getLooting());
        learnSimulatedDropsSilent(fakeMobKey, drops);
    }

    public void learnSimulatedDropsSilent(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        SimulatedMob simulatedMob = getOrCreateSimulatedMob(fakeMobKey.getMob());
        drops.forEach(d -> simulatedMob.addSimulatedDrop(fakeMobKey.getLooting(), d));
    }

    public void learnCustomDrop(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack drop, float dropChance) {
        SimulatedMob simulatedMob = getOrCreateSimulatedMob(fakeMobKey.getMob());
        simulatedMob.addCustomDrop(fakeMobKey.getLooting(), drop, dropChance);
    }

    public int getSimulatedKills(@Nonnull FakeMobKey fakeMobKey) {
        int count = 0;
        SimulatedMob simulatedMob = mobs.get(fakeMobKey.getMob());
        if (simulatedMob != null)
            count = simulatedMob.getSimulatedKills(fakeMobKey.getLooting());
        return count;
    }

    public @Nonnull List<SimulatedMobDropSummary> getDropSummary(@Nonnull FakeMob fakeMob) {
        List<SimulatedMobDropSummary> drops = new ArrayList<>();
        SimulatedMob simulatedMob = mobs.get(fakeMob);
        if (simulatedMob != null)
            drops = simulatedMob.getDropSummary();

        return drops;
    }

    public @Nonnull Set<FakeMob> getKnownMobs() {
        return mobs.keySet();
    }

    public @Nonnull List<ItemStack> getRolledDrops(@Nonnull FakeMobKey fakeMobKey) {
        List<ItemStack> drops = new ArrayList<>();
        SimulatedMob simulatedMob = mobs.get(fakeMobKey.getMob());
        if (simulatedMob != null)
            drops.addAll(simulatedMob.getRolledDrops(fakeMobKey.getLooting()));

        return drops;
    }

    /**
     * Save/load
     */
    private static final int JSON_VERSION = 1;
    private static final String TAG_VERSION = "version";
    private static final String TAG_SIMULATED_MOBS = "simulatedMobs";
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_VERSION, JSON_VERSION);

        JsonArray mobsArray = new JsonArray();
        {
            for (Map.Entry<FakeMob, SimulatedMob> entry : mobs.entrySet()) {
                FakeMob fakeMob = entry.getKey();
                SimulatedMob simulatedMob = entry.getValue();

                if (!fakeMob.isValid())
                    continue;

                if (!PolicyRegistry.get().canSimulate(fakeMob.getResourceLocation()))
                    continue;

                mobsArray.add(simulatedMob.toJson(fakeMob));
            }
        }
        jsonObject.add(TAG_SIMULATED_MOBS, mobsArray);
        return jsonObject;
    }
}