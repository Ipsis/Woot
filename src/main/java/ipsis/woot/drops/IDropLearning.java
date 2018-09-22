package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public interface IDropLearning {

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

    /**
     * Have we finished learning
     * @param fakeMobKey
     * @param looting 0 to 3
     * @return true is we have hit the kill cap for this mob/looting
     */
    boolean isLearningComplete(@Nonnull FakeMobKey fakeMobKey, int looting);
}
