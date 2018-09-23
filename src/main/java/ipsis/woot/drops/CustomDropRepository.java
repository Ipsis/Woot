package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakeMobKeyFactory;
import ipsis.woot.util.MiscUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomDropRepository implements IDropProvider {

    private static final List<CustomDrop> customDrops = new ArrayList<>();

    public void init() {

        // TODO This needs to loaded from a file
        FakeMobKey fakeMobKey = FakeMobKeyFactory.createFromString("minecraft:creeper");
        ItemStack itemStack = new ItemStack(Items.DIAMOND);
        addCustomDrop(fakeMobKey, itemStack);
        addCustomDropStack(fakeMobKey, itemStack, 0, 1, 30);
        addCustomDropStack(fakeMobKey, itemStack, 3, 3, 0.45F);
    }

    private void addCustomDrop(@Nonnull FakeMobKey fakeMobKey, ItemStack itemStack) {

        CustomDrop customDrop = lookupCustomDrop(fakeMobKey, itemStack);
        if (customDrop == null)
            customDrops.add(new CustomDrop(fakeMobKey, itemStack));
    }

    private @Nullable CustomDrop lookupCustomDrop(@Nonnull FakeMobKey fakeMobKey, ItemStack itemStack) {
        CustomDrop customDrop = null;
        for (CustomDrop curr : customDrops) {
            if (curr.isMatch(fakeMobKey, itemStack)) {
                customDrop = curr;
                break;
            }
        }
        return customDrop;
    }

    private void addCustomDropStack(@Nonnull FakeMobKey fakeMobKey, ItemStack itemStack, int looting, int stackSize, float chance) {
        looting = MiscUtils.clampLooting(looting);
        CustomDrop customDrop = lookupCustomDrop(fakeMobKey, itemStack);
        if (customDrop != null)
            customDrop.addLootingData(looting, stackSize, chance);
    }

    private class CustomDrop {

        private FakeMobKey fakeMobKey;
        private ItemStack itemStack;
        private HashMap<Integer, HashMap<Integer, Float>> lootingData = new HashMap<>();

        private CustomDrop() { }

        public CustomDrop(@Nonnull FakeMobKey fakeMobKey, ItemStack itemStack) {
            this.itemStack = itemStack.copy();
            this.fakeMobKey = fakeMobKey;
            lootingData.put(0, new HashMap<>());
            lootingData.put(1, new HashMap<>());
            lootingData.put(2, new HashMap<>());
            lootingData.put(3, new HashMap<>());
        }

        public boolean isMatch(@Nonnull FakeMobKey fakeMobKey, ItemStack itemStack) {
            return this.fakeMobKey.equals(fakeMobKey) && DropManager.isEqualForLearning(this.itemStack, itemStack);
        }

        public void addLootingData(int looting, int stackSize, float chance) {
            looting = MiscUtils.clampLooting(looting);
            HashMap<Integer, Float> data = lootingData.get(looting);
            data.put(stackSize, chance);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(fakeMobKey.toString());
            sb.append((" " + itemStack.getDisplayName()) + "\n");
            for (Integer looting : lootingData.keySet()) {
                sb.append("Looting " + looting);
                HashMap<Integer, Float> data = lootingData.get(looting);
                for (Integer size : data.keySet())
                    sb.append(" " + size + "/" + data.get(size) + "%");
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public void getStatus(@Nonnull List<String> status, String[] args) {
        status.add(">>> CustomDropRepository");
        for (CustomDrop customDrop : customDrops)
            status.add(customDrop.toString());
        status.add("<<< CustomDropRepository");
    }

    /**
     * IDropProvider
     */
    @Nonnull
    @Override
    public MobDropData getMobDropData(@Nonnull FakeMobKey fakeMobKey, int looting) {
        looting = MiscUtils.clampLooting(looting);
        return new MobDropData(fakeMobKey, looting);
    }

}
