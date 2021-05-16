package ipsis.woot.simulator.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.realmsclient.util.JsonUtils;
import ipsis.woot.Woot;
import ipsis.woot.modules.factory.generators.WoolGenerator;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
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

    public FakeMob getFakeMob() { return fakeMob; }

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

    public void addCustomDrop(int looting, @Nonnull ItemStack itemStack, float dropChance, HashMap<Integer, Integer> stackSizes) {
        if (itemStack.isEmpty())
            return;

        SimulatedMobDrop simulatedMobDrop = getOrCreateSimulatedMobDrop(itemStack);
        if (!simulatedMobDrops.contains(simulatedMobDrop))
            simulatedMobDrops.add(simulatedMobDrop);

        simulatedMobDrop.addCustomDrop(looting, dropChance, stackSizes);
    }

    public @Nonnull List<SimulatedMobDropSummary> getDropSummary() {
        List<SimulatedMobDropSummary> drops = new ArrayList<>();
        simulatedMobDrops.forEach(d -> drops.add(d.createSummary()));
        return drops;
    }


    public @Nonnull List<ItemStack> getRolledDrops(int looting) {
        //Woot.setup.getLogger().debug("getRolledDrop: *** looting {}:{}", looting, fakeMob);
        List<ItemStack> drops = new ArrayList<>();
        looting = MathHelper.clampLooting(looting);
        for (SimulatedMobDrop drop : simulatedMobDrops) {
            ItemStack itemStack = drop.getRolledDrop(looting);
            if (!itemStack.isEmpty() && PolicyRegistry.get().canGenerateItem(itemStack.getItem().getRegistryName()))
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
                simulatedMobDrops.forEach(d -> {
                    if (!d.hasCustom)
                        dropsArray.add(d.toJson());
                });
            }
            jsonObject.add(TAG_DROPS, dropsArray);
        }
        return jsonObject;
    }

    public static @Nullable SimulatedMob fromJson(JsonObject jsonObject) {
        String mob = JSONUtils.getString(jsonObject, TAG_MOB);
        FakeMob fakeMob = new FakeMob(mob);
        if (!fakeMob.isValid()) {
            Woot.setup.getLogger().info("SimulatedMob:fromJson invalid mob {}", mob);
            return null;
        }

        if (!FakeMob.isInEntityList(fakeMob)) {
            Woot.setup.getLogger().info("SimulatedMob:fromJson mob not in entity list {}", mob);
            return null;
        }

        JsonArray killsArray = JSONUtils.getJsonArray(jsonObject, TAG_SIM_KILLS);
        if (killsArray.size() != 4)
            throw new JsonSyntaxException("Simulated kills array must be of size 4");

        SimulatedMob simulatedMob = new SimulatedMob(fakeMob);
        for (int i = 0; i < 4; i++)
            simulatedMob.simulatedKills[i] = killsArray.get(i).getAsInt();

        for (JsonElement jsonElement : JSONUtils.getJsonArray(jsonObject, TAG_DROPS)) {
            if (jsonElement == null || !jsonElement.isJsonObject())
                throw new JsonSyntaxException("Simulated drop must be an object");

            SimulatedMobDrop simulatedMobDrop = SimulatedMobDrop.fromJson(simulatedMob, (JsonObject)jsonElement);
            if (simulatedMobDrop != null) {
                if (PolicyRegistry.get().canLearnItem(simulatedMobDrop.itemStack.getItem().getRegistryName())) {
                    if (fakeMob.isSheep() && !WoolGenerator.isWoolDrop(simulatedMobDrop.itemStack))
                        simulatedMob.simulatedMobDrops.add(simulatedMobDrop);
                    else
                        simulatedMob.simulatedMobDrops.add(simulatedMobDrop);
                } else {
                    Woot.setup.getLogger().info("Drop {} is blacklisted", simulatedMobDrop.itemStack.getItem());
                }
            }

        }


        return simulatedMob;
    }

}
