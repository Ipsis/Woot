package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;

import javax.annotation.Nonnull;

public interface IDropProvider {

    /**
     * Get the drop chances for the loot that this mob/looting can drop
     * @param fakeMobKey
     * @param looting 0 to 3
     * @return
     */
    @Nonnull MobDropData getMobDropData(@Nonnull FakeMobKey fakeMobKey, int looting);
}
