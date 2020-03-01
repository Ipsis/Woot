package ipsis.woot.modules.simulation.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.modules.simulation.MobDrop;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.JsonHelper;
import ipsis.woot.util.helper.MathHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public class MobDropData {

    private static final Random RANDOM = new Random();

    private ItemStack itemStack;
    private int[] simulatedDropCount;
    private float[] customChanceToDrop;
    private boolean hasCustom;
    private MobData mobData;

    private List<HashMap<Integer, Integer>> stackSizeDropCountSimulated;
    private List<HashMap<Integer, Float>> stackSizeDropChanceCustom;

    public MobDropData(ItemStack itemStack, MobData mobData) {
        this.mobData = mobData;
        this.hasCustom = false;
        this.itemStack = itemStack.copy();
        simulatedDropCount = new int[]{ 0, 0, 0, 0};
        customChanceToDrop = new float[]{ 0.0F, 0.0F, 0.0F, 0.0F};
        stackSizeDropCountSimulated = new ArrayList<>(4);
        stackSizeDropChanceCustom = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            stackSizeDropCountSimulated.add(i, new HashMap<>());
            stackSizeDropChanceCustom.add(i, new HashMap<>());
        }
    }

    public void addSimulatedData(int looting, int stackSize) {
        looting = MathHelper.clampLooting(looting);
        simulatedDropCount[looting]++;
        Integer count = stackSizeDropCountSimulated.get(looting).getOrDefault(stackSize, 0);
        stackSizeDropCountSimulated.get(looting).put(stackSize, count + 1);
    }

    public void addCustomData(int looting, int stackSize, float chance) {
        hasCustom = true;
        looting = MathHelper.clampLooting(looting);
        stackSizeDropChanceCustom.get(looting).put(stackSize, chance);
    }

    public float getDropChance(int looting) {
        looting = MathHelper.clampLooting(looting);
        float dropChance = 0.0F;
        if (hasCustom)
            dropChance = customChanceToDrop[looting];
        else if (mobData.getSimulatedKills(looting) > 0)
            dropChance = (100.0F / mobData.getSimulatedKills(looting)) * simulatedDropCount[looting];

        return dropChance;
    }

    public int getStackSize(int looting) {
        if (hasCustom && !stackSizeDropChanceCustom.get(looting).isEmpty()) {

        } else if (!hasCustom && !stackSizeDropCountSimulated.get(looting).isEmpty()) {

        }
        return 1;
    }

    public @Nonnull ItemStack getItemStack() { return this.itemStack; }

    private static final String DROPPED_STACK_TAG = "drop";
    private static final String SIM_COUNT_TAG = "simulatedCount";
    private static final String CUSTOM_CHANCE_TAG = "customChance";
    private static final String[] SIM_COUNT_TAGS = { "sim0", "sim1", "sim2", "sim3" };
    private static final String[] CUSTOM_COUNT_TAGS = { "custom0", "custom1", "custom2", "custom3" };
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        {
            jsonObject.add(DROPPED_STACK_TAG, JsonHelper.toJsonObject(itemStack));
            JsonArray simulatedArray = new JsonArray();
            simulatedArray.add(simulatedDropCount[0]);
            simulatedArray.add(simulatedDropCount[1]);
            simulatedArray.add(simulatedDropCount[2]);
            simulatedArray.add(simulatedDropCount[3]);
            jsonObject.add(SIM_COUNT_TAG, simulatedArray);

            JsonArray customArray = new JsonArray();
            customArray.add(customChanceToDrop[0]);
            customArray.add(customChanceToDrop[1]);
            customArray.add(customChanceToDrop[2]);
            customArray.add(customChanceToDrop[3]);
            jsonObject.add(CUSTOM_CHANCE_TAG, customArray);

            for (int i = 0; i < 4; i++) {
                JsonArray simulatedSizesArray = new JsonArray();
                // Entries are pairs of stacksize, count
                for (Map.Entry<Integer, Integer> e : stackSizeDropCountSimulated.get(i).entrySet()) {
                    simulatedSizesArray.add(e.getKey());
                    simulatedSizesArray.add(e.getValue());
                }
                jsonObject.add(SIM_COUNT_TAGS[i], simulatedSizesArray);

                JsonArray customSizesArray = new JsonArray();
                // Entries are pairs of stacksize, chance
                for (Map.Entry<Integer, Float> e : stackSizeDropChanceCustom.get(i).entrySet()) {
                    customSizesArray.add(e.getKey());
                    customSizesArray.add(e.getValue());
                }
                jsonObject.add(CUSTOM_COUNT_TAGS[i], customSizesArray);
            }
        }
        return jsonObject;
    }

    /**
     * Rolls a random drop of the item
     * @return ItemStack.EMPTY if roll failed
     * @return itemstack and stack size on success
     */
    public @Nonnull ItemStack rollDrop(int looting) {

        ItemStack rolledStack = ItemStack.EMPTY;
        looting = MathHelper.clampLooting(looting);
        float roll = RANDOM.nextFloat() * 100.0F; // 0.0(inclusive) -> 1.0 (exclusive)
        float dropChance = getDropChance(looting);

        Woot.setup.getLogger().debug("rollDrop: {} roll:{} dropChance:{} ",
                itemStack.getTranslationKey(), roll, dropChance);

        if (dropChance == 100.0F || dropChance <= roll) {
            Woot.setup.getLogger().debug("rollDrop: successfully rolled the drop");

                rolledStack = itemStack.copy();
                rolledStack.setCount(getStackSize(looting));
                Woot.setup.getLogger().debug("rollDrop: random stack size of {}", rolledStack.getCount());
        }

        return rolledStack;
    }
    public @Nonnull ItemStack rollDropOld(int looting) {

        ItemStack rolledStack = ItemStack.EMPTY;

        /*
        looting = MathHelper.clampLooting(looting);
        float roll = RANDOM.nextFloat() * 100.0F; // 0.0(inclusive) -> 1.0 (exclusive)
        float dropChance = chanceToDrop[looting];

        Woot.setup.getLogger().debug("rollDrop: {} roll:{} dropChance:{} ",
                itemStack.getTranslationKey(), roll, dropChance);

        if (dropChance == 100.0F || dropChance <= roll) {
            Woot.setup.getLogger().debug("rollDrop: successfully rolled the drop");
            rolledStack = new ItemStack(itemStack.getItem());
            int finalStackSize = 1;
            if (!stackSizeDropChance.get(looting).isEmpty()) {
                // https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java/30362366
                double completeWeight = 0.0;
                for (Map.Entry<Integer, Float> entry : stackSizeDropChance.get(looting).entrySet())
                    completeWeight += entry.getValue();

                double r = Math.random() * completeWeight;
                double countWeight = 0.0;

                for (Map.Entry<Integer, Float> entry : stackSizeDropChance.get(looting).entrySet()) {
                    countWeight += entry.getValue();
                    if (countWeight >= r) {
                        finalStackSize = entry.getKey();
                        break;
                    }
                }

                Woot.setup.getLogger().debug("rollDrop: random stack size of {}", finalStackSize);
            }
            rolledStack.setCount(finalStackSize);
        } */

        return rolledStack;
    }
}
