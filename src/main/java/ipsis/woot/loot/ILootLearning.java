package ipsis.woot.loot;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface ILootLearning {

    /**
     * Drops to learn - counts towards the sample size
     * @param fakeMobKey
     * @param looting 0 to 3
     * @param drops
     */
    void learn(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops);

    /**
     * Drops to learn - does NOT count towards the sample size
     * @param fakeMobKey
     * @param looting 0 to 3
     * @param drops
     */
    void learnSilent(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops);
}
