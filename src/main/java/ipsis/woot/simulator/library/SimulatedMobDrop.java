package ipsis.woot.simulator.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ipsis.woot.Woot;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.helper.JsonHelper;
import ipsis.woot.util.helper.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulatedMobDrop {

    protected ItemStack itemStack;
    protected int[] simulatedDropCount;
    protected float[] customChanceToDrop;
    protected boolean hasCustom;
    protected SimulatedMob simulatedMob;

    protected List<HashMap<Integer, Integer>> simulatedStackSize;
    protected List<HashMap<Integer, Float>> customStackSize;

    private SimulatedMobDrop(){}
    public SimulatedMobDrop(ItemStack itemStack, SimulatedMob simulatedMob) {
        this.simulatedMob = simulatedMob;
        this.hasCustom = false;
        this.itemStack = itemStack.copy();
        simulatedDropCount = new int[]{0, 0, 0, 0};
        customChanceToDrop = new float[]{ 0.0F, 0.0F, 0.0F, 0.0F};
        simulatedStackSize = new ArrayList<>(4);
        customStackSize = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            simulatedStackSize.add(i, new HashMap<>());
            customStackSize.add(i, new HashMap<>());
        }
    }

    private float calculateDropChance(int looting) {
        looting = MathHelper.clampLooting(looting);
        if (hasCustom)
            return customChanceToDrop[looting];

        float dropChance = 0.0F;
        if (simulatedMob.getSimulatedKills(looting) > 0)
            dropChance = (100.0F / simulatedMob.getSimulatedKills(looting)) * simulatedDropCount[looting];

        return dropChance;
    }

    private int calculateDropSize(int looting) {
        int stackSize = 1;
        looting = MathHelper.clampLooting(looting);
        if (hasCustom && customStackSize.isEmpty()) {
            // Custom drops default to stack size of 1 if nothing specified
            stackSize = 1;
        } else if (hasCustom && !customStackSize.isEmpty()) {
            // The custom drop sizes are the percentage drop chance
            double completeWeight = 0.0F;
            for (Map.Entry<Integer, Float> entry : customStackSize.get(looting).entrySet())
                completeWeight += entry.getValue();

            double r = Math.random() * completeWeight;
            double countWeight = 0.0F;

            for (Map.Entry<Integer, Float> entry : customStackSize.get(looting).entrySet()) {
                countWeight += entry.getValue();
                if (countWeight >= r) {
                    stackSize = entry.getKey();
                    break;
                }
            }
        } else if (!hasCustom && simulatedStackSize.isEmpty()) {
            // Not really possible
            stackSize = 1;
        } else {
            // The simulated drop sizes are just the number of times the drop occurred
            double completeWeight = 0.0F;
            for (Map.Entry<Integer, Integer> entry : simulatedStackSize.get(looting).entrySet())
                completeWeight += (float)entry.getValue();

            double r = Math.random() * completeWeight;
            double countWeight = 0.0F;

            for (Map.Entry<Integer, Integer> entry : simulatedStackSize.get(looting).entrySet()) {
                countWeight += (float)entry.getValue();
                if (countWeight >= r) {
                    stackSize = entry.getKey();
                    break;
                }
            }
        }
        return stackSize;
    }

    public void addSimulatedData(int looting, int stackSize) {
        looting = MathHelper.clampLooting(looting);
        simulatedDropCount[looting]++;

        Integer count = simulatedStackSize.get(looting).getOrDefault(stackSize, 0);
        simulatedStackSize.get(looting).put(stackSize, count + 1);
    }

    public void addCustomData(int looting, int stackSize, float chance) {
        looting = MathHelper.clampLooting(looting);
        customChanceToDrop[looting] = chance;
    }

    public void addCustomSize(int looting, int stackSize, float chance) {
        looting = MathHelper.clampLooting(looting);
        customStackSize.get(looting).put(stackSize, chance);
    }

    public SimulatedMobDropSummary createSummary() {
        return new SimulatedMobDropSummary(itemStack.copy(),
                calculateDropChance(0),
                calculateDropChance(1),
                calculateDropChance(2),
                calculateDropChance(3));
    }

    public @Nonnull ItemStack getRolledDrop(int looting) {
        ItemStack dropStack = ItemStack.EMPTY;
        float dropChance = calculateDropChance(looting);
        float roll = MobSimulator.RANDOM.nextFloat() * 100.0F; // 0.0(inclusive) -> 1.0 exclusive

        MobSimulator.LOGGER.debug("getRolledDrop:chance {} roll {}", dropChance, roll);
        if (dropChance == 100.0F || roll <= dropChance) {
            dropStack = itemStack.copy();
            dropStack.setCount(calculateDropSize(looting));
        }

        MobSimulator.LOGGER.debug("getRolledDrop:{} {}", looting, dropStack);
        return dropStack;
    }

    /**
     * Save/Load
     */
    private static final String TAG_DROPPED_ITEM = "drop";
    private static final String TAG_SIM_KILLS = "simulatedCount";
    private static final String[] TAG_STACK_SIZES = { "stack0", "stack1", "stack2", "stack3" };

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        {
            jsonObject.add(TAG_DROPPED_ITEM, JsonHelper.toJsonObject(itemStack));
            JsonArray simulatedArray = new JsonArray();
            for (int i = 0; i < 4; i++)
                simulatedArray.add(simulatedDropCount[i]);
            jsonObject.add(TAG_SIM_KILLS, simulatedArray);

            for (int i = 0; i < 4; i++) {
                JsonArray stackArray = new JsonArray();
                for (Map.Entry<Integer, Integer> e : simulatedStackSize.get(i).entrySet()) {
                    stackArray.add(e.getKey());
                    stackArray.add(e.getValue());
                }
                jsonObject.add(TAG_STACK_SIZES[i], stackArray);
            }
        }
        return jsonObject;
    }

    public static @Nullable SimulatedMobDrop fromJson(SimulatedMob simulatedMob, JsonObject jsonObject) {
        ItemStack itemStack;
        try {
            itemStack = CraftingHelper.getItemStack(jsonObject.getAsJsonObject(TAG_DROPPED_ITEM), false);
        } catch (JsonSyntaxException e) {
            Woot.setup.getLogger().error("Failed to parse itemstack");
            return null;
        }

        JsonArray dropsArray = JSONUtils.getJsonArray(jsonObject, TAG_SIM_KILLS);
        if (dropsArray.size() != 4)
            throw new JsonSyntaxException("Simulated count array must be of size 4");

        SimulatedMobDrop simulatedMobDrop = new SimulatedMobDrop(itemStack, simulatedMob);
        for (int i = 0; i < 4; i++)
            simulatedMobDrop.simulatedDropCount[i] = dropsArray.get(i).getAsInt();

        for (int i = 0; i < 4; i++) {
            JsonArray sizesArray = JSONUtils.getJsonArray(jsonObject, TAG_STACK_SIZES[i]);
            if (sizesArray.size() > 0 && sizesArray.size() % 2 == 0) {
                for (int j = 0; j < sizesArray.size() / 2; j += 2) {
                    int size = sizesArray.get(j).getAsInt();
                    int count = sizesArray.get(j + 1).getAsInt();
                    simulatedMobDrop.simulatedStackSize.get(i).put(size, count);
                }
            }
        }

        return simulatedMobDrop;
    }

}
