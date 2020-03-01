package ipsis.woot.modules.simulation.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.modules.simulation.SimulationConfiguration;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.MathHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MobData {

    private FakeMob fakeMob;
    private int simulatedKills[];
    private List<MobDropData> drops;

    public MobData(FakeMob fakeMob) {
        this.fakeMob = fakeMob;
        simulatedKills = new int[]{ 0, 0, 0, 0};
        drops = new ArrayList<>();
    }

    protected List<MobDropData> getDrops() {
        return Collections.unmodifiableList(drops);
    }

    /**
     * Gets the current entry in the list or initialises a new one
     */
    private @Nonnull
    MobDropData getMobDropData(@Nonnull ItemStack itemStack) {
        MobDropData mobDropData = null;
        Iterator<MobDropData> iter = drops.iterator();
        while (iter.hasNext() && mobDropData == null) {
            MobDropData currMobDropData = iter.next();
            if (DropLibrary.getInstance().isEqualForLearning(itemStack, currMobDropData.getItemStack()))
                mobDropData = currMobDropData;
        }

        return mobDropData == null ? new MobDropData(itemStack, this) : mobDropData;
    }

    public void incrementSimulatedKills(int looting) {
        looting = MathHelper.clampLooting(looting);
        simulatedKills[looting]++;
    }

    public int getSimulatedKills(int looting) {
        looting = MathHelper.clampLooting(looting);
        int kills = simulatedKills[looting];
        return net.minecraft.util.math.MathHelper.clamp(kills, 0, SimulationConfiguration.SIMULATION_MOB_COUNT.get());
    }

    public void addSimulatedDrop(int looting, ItemStack itemStack) {
        if (itemStack.isEmpty() || itemStack.getCount() == 0)
            return;

        if (!PolicyRegistry.get().canLearnItem(itemStack.getItem().getRegistryName())) {
            DropLibrary.LOGGER.debug("addSimulatedDrop: {} rejected by policy", itemStack.getTranslationKey());
            return;
        }

        MobDropData mobDropData = getMobDropData(itemStack);
        if (!drops.contains(mobDropData))
            drops.add(mobDropData);

        mobDropData.addSimulatedData(looting, itemStack.getCount());
    }

    private static final String SIMULATED_MOB_TAG = "mob";
    private static final String SIMULATED_KILLS_TAG = "simKills";
    private static final String DROPS_TAG = "drops";
    public JsonObject toJson(FakeMob fakeMob) {

        JsonObject jsonObject = new JsonObject();
        {
            jsonObject.addProperty(SIMULATED_MOB_TAG, fakeMob.toString());

            JsonArray simulatedKillsArray = new JsonArray();
            simulatedKillsArray.add(simulatedKills[0]);
            simulatedKillsArray.add(simulatedKills[1]);
            simulatedKillsArray.add(simulatedKills[2]);
            simulatedKillsArray.add(simulatedKills[3]);
            jsonObject.add(SIMULATED_KILLS_TAG, simulatedKillsArray);

            JsonArray dropsArray = new JsonArray();
            {
                for (MobDropData mobDropData : drops)
                    dropsArray.add(mobDropData.toJson());
            }
            jsonObject.add(DROPS_TAG, dropsArray);
        }
        return jsonObject;
    }
}
