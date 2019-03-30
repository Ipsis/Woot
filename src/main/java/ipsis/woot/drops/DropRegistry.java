package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.MiscHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class DropRegistry {

    private static final CustomDropRegistry CUSTOM_DROPS = new CustomDropRegistry();
    private static final LearnedDropRegistry LEARNED_DROPS = new LearnedDropRegistry();

    /**
     * Learning
     */
    public void learnSilent(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {
        looting = MiscHelper.clampLooting(looting);
        LEARNED_DROPS.learnSilent(fakeMobKey, looting, drops);
    }

    public void learn(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {
        looting = MiscHelper.clampLooting(looting);
        LEARNED_DROPS.learn(fakeMobKey, looting, drops);
    }

    public boolean isLearningComplete(@Nonnull FakeMobKey fakeMobKey, int looting) {
        looting = MiscHelper.clampLooting(looting);
        return LEARNED_DROPS.isLearningComplete(fakeMobKey, looting);
    }

    @Nonnull MobDropData getMobDropData(@Nonnull FakeMobKey fakeMobKey, int looting) {
        looting = MiscHelper.clampLooting(looting);
        MobDropData mobDropData = new MobDropData(fakeMobKey, looting);

        //MobDropData custom = CUSTOM_DROPS.getMobDropData(fakeMobKey, looting);
        MobDropData learned = LEARNED_DROPS.getMobDropData(fakeMobKey, looting);

        for (MobDropData.DropData d : learned.getDrops())
            mobDropData.add(d);

        return mobDropData;
    }

    public static boolean isEqualForLearning(@Nonnull ItemStack itemStack1, @Nonnull ItemStack itemStack2) {
        if (itemStack1.isEmpty() && itemStack2.isEmpty())
            return true;

        return itemStack1.getItem() == itemStack2.getItem();
    }
}
