package ipsis.woot.loot;

import ipsis.woot.util.FakeMobKey;

import javax.annotation.Nonnull;
import java.util.List;

public interface ILootDropProvider {

    void init();
    void shutdown();
    boolean isFull(@Nonnull FakeMobKey fakeMobKey, int looting);
    void getStatus(@Nonnull List<String> status, String[] args);
    @Nonnull
    MobDropData getDrops(@Nonnull FakeMobKey fakeMobKey, int looting);

}
