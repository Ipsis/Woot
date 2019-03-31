package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class CustomDrop {

    protected FakeMobKey fakeMobKey;
    protected ItemStack itemStack;
    private HashMap<Integer, Drop> lootingData = new HashMap<>();

    public CustomDrop(@Nonnull FakeMobKey fakeMobKey, ItemStack itemStack) {
        this.itemStack = itemStack.copy();
        this.fakeMobKey = fakeMobKey;
    }

    public void addLootingData(int looting, int stackSize, float chance) {
        lootingData.put(looting, new Drop(chance, stackSize));
    }

    public float getDropChance(int looting) {
        if (lootingData.containsKey(looting))
            return lootingData.get(looting).chance;

        return 0.0F;
    }

    public int getStackSize(int looting) {
        if (lootingData.containsKey(looting))
            return lootingData.get(looting).stackSize;

        return 1;
    }

    public boolean isMatch(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack itemStack) {
        return this.fakeMobKey.equals(fakeMobKey) && DropRegistry.isEqualForLearning(this.itemStack, itemStack);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(fakeMobKey + "/" + itemStack.getTranslationKey() + "-");
        for (int i = 0; i <= 3; i++)
            sb.append(i + "/" + getStackSize(i) + "/" + getDropChance(i) + " ");
        return sb.toString();
    }

    public class Drop {
        protected float chance;
        protected int stackSize;

        public Drop(float chance, int stackSize) {
            this.chance = chance;
            this.stackSize = stackSize;
        }
    }
}
