package ipsis.woot.util;

import ipsis.woot.util.helper.MathHelper;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FakeMobKey {

    private FakeMob mob;
    private int looting = 0;

    public FakeMobKey(@Nonnull FakeMob fakeMob, int looting) {
        this.mob = fakeMob;
        this.looting = MathHelper.clampLooting(looting);
    }

    public @Nonnull FakeMob getMob() { return this.mob; }
    public int getLooting() { return this.looting; }

    @Override
    public String toString() {
        return mob.toString() + "@" + looting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakeMobKey that = (FakeMobKey) o;
        return looting == that.looting &&
                mob.equals(that.mob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mob, looting);
    }
}
