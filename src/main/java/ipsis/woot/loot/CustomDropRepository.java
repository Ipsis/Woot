package ipsis.woot.loot;

import cofh.core.util.helpers.MathHelper;
import ipsis.woot.util.FakeMobKey;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static ipsis.woot.util.MiscUtils.clampLooting;

public class CustomDropRepository implements ILootDropProvider {

    // ILootDropProvider
    @Override
    public void init() {

        // TODO load from the config file
        new CustomDropJson().load(this);
    }

    @Override
    public void shutdown() {

        // NO-OP
    }

    @Override
    public boolean isFull(@Nonnull FakeMobKey fakeMobKey, int looting) {

        // This is never full
        return false;
    }

    public @Nonnull MobDropData getDrops(@Nonnull FakeMobKey fakeMobKey, int looting) {

        looting = clampLooting(looting);

        return new MobDropData(fakeMobKey, looting);
    }

    public void getStatus(@Nonnull List<String> status) {
        status.add("CustomDropRepository");
    }
}
