package ipsis.woot.simulator.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.MathHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SimulatedMob {

    private FakeMob fakeMob;
    private int simulatedKills[];
    private List<SimulatedMobDrop> simulatedMobDrops;

    private SimulatedMob(){}

    public SimulatedMob(FakeMob fakeMob) {
        this.fakeMob = fakeMob;
        simulatedKills = new int[]{0, 0, 0, 0};
        simulatedMobDrops = new ArrayList<>();
    }

    private @Nonnull SimulatedMobDrop getOrCreateSimulatedMobDrop(@Nonnull ItemStack itemStack) {
        SimulatedMobDrop simulatedMobDrop = null;
        for (SimulatedMobDrop d : simulatedMobDrops) {
            if (MobSimulator.getInstance().isEqualForLearning(itemStack, d.itemStack)) {
                simulatedMobDrop = d;
                break;
            }
        }
        return simulatedMobDrop == null ? new SimulatedMobDrop(itemStack, this) : simulatedMobDrop;
    }

    /**
     * API
     */
    public void incrementSimulatedKills(int looting) {
        looting = MathHelper.clampLooting(looting);
        simulatedKills[looting]++;
    }

    public int getSimulatedKills(int looting) {
        return simulatedKills[MathHelper.clampLooting(looting)];
    }

    public void addSimulatedDrop(int looting, ItemStack itemStack) {
        if (itemStack.isEmpty() || itemStack.getCount() == 0)
            return;

        if (!PolicyRegistry.get().canLearnItem(itemStack.getItem().getRegistryName()))
            return;

        SimulatedMobDrop simulatedMobDrop = getOrCreateSimulatedMobDrop(itemStack);
        if (!simulatedMobDrops.contains(simulatedMobDrop))
            simulatedMobDrops.add(simulatedMobDrop);

        simulatedMobDrop.addSimulatedData(looting, itemStack.getCount());
    }

    public void addCustomDrop(int looting, ItemStack itemStack, float dropChance) {
        if (itemStack.isEmpty() || itemStack.getCount() == 0)
            return;

        SimulatedMobDrop simulatedMobDrop = getOrCreateSimulatedMobDrop(itemStack);
        if (!simulatedMobDrops.contains(simulatedMobDrop))
            simulatedMobDrops.add(simulatedMobDrop);

        simulatedMobDrop.addCustomData(looting, itemStack.getCount(), dropChance);
    }

    public @Nonnull List<SimulatedMobDropSummary> getDropSummary() {
        List<SimulatedMobDropSummary> drops = new ArrayList<>();
        simulatedMobDrops.forEach(d -> drops.add(d.createSummary()));
        return drops;
    }


    public @Nonnull List<ItemStack> getRolledDrops(int looting) {
        List<ItemStack> drops = new ArrayList<>();
        looting = MathHelper.clampLooting(looting);
        for (SimulatedMobDrop drop : simulatedMobDrops) {
            ItemStack itemStack = drop.getRolledDrop(looting);
            if (!itemStack.isEmpty())
                drops.add(itemStack);
        }
        return drops;
    }

    /**
     * Save/Load
     */
    private static final String TAG_MOB = "mob";
    private static final String TAG_SIM_KILLS = "simulatedKills";
    private static final String TAG_DROPS = "learnedDrops";
    public JsonObject toJson(FakeMob fakeMob) {
        JsonObject jsonObject = new JsonObject();
        {
            jsonObject.addProperty(TAG_MOB, fakeMob.toString());
            JsonArray simKillsArray = new JsonArray();
            for (int i = 0; i < 4; i++)
                simKillsArray.add(simulatedKills[i]);
            jsonObject.add(TAG_SIM_KILLS, simKillsArray);

            JsonArray dropsArray = new JsonArray();
            {
                simulatedMobDrops.forEach(d -> dropsArray.add(d.toJson()));
            }
            jsonObject.add(TAG_DROPS, dropsArray);
        }
        return jsonObject;
    }

}
