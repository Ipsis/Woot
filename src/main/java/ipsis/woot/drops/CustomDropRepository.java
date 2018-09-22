package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.MiscUtils;

import javax.annotation.Nonnull;

public class CustomDropRepository implements IDropProvider {

    public void init() {

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
