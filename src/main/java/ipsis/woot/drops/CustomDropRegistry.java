package ipsis.woot.drops;


import ipsis.woot.util.FakeMobKey;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CustomDropRegistry {

    /**
     * Custom drops are more limited
     * Looting level can drop an item of a specific size with a chance
     */

    private List<CustomDrop> drops = new ArrayList<>();

    public void init() {
        // @todo load from file
        FakeMobKey fakeMobKey = new FakeMobKey("minecraft:cow");
        addCustomDrop(fakeMobKey, 0, new ItemStack(Items.DIAMOND), 1, 50.0F);
        addCustomDrop(fakeMobKey, 0, new ItemStack(Blocks.DIRT), 1, 80.0F);
    }

    private void addCustomDrop(@Nullable FakeMobKey fakeMobKey, int looting, @Nonnull ItemStack itemStack, int stackSize, float chance) {

        if (itemStack.isEmpty())
            return;

        CustomDrop drop = null;
        for (CustomDrop d : drops) {
            if (d.isMatch(fakeMobKey, itemStack)) {
                drop = d;
                break;
            }
        }

        if (drop == null) {
            drop = new CustomDrop(fakeMobKey, itemStack);
            drops.add(drop);
        }

        drop.addLootingData(looting, stackSize, chance);
    }

    /**
     * Get the drops for a specific mob
     */
    @Nonnull public MobDropData getMobDropData(@Nonnull FakeMobKey fakeMobKey, int looting) {
        MobDropData mobDropData = new MobDropData(fakeMobKey, looting);

        for (CustomDrop d : drops) {
            if (d.fakeMobKey.equals(fakeMobKey)) {
                MobDropData.DropData dropData = new MobDropData.DropData(d.itemStack);
                dropData.setChance(d.getDropChance(looting));
                dropData.addSize(d.getStackSize(looting), d.getDropChance(looting));
                mobDropData.add(dropData);
            }
        }

        return mobDropData;
    }

}
