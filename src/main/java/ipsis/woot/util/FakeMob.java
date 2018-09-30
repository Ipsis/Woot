package ipsis.woot.util;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class FakeMob {

    // TODO do we ACTUALLY need this?
    private static final String UNKNOWN_MOB = "Unknown Mob";

    private FakeMobKey fakeMobKey;
    private String name = UNKNOWN_MOB;

    public FakeMobKey getFakeMobKey() { return fakeMobKey; }
    public String getName() { return name; }

    public boolean isValid() { return this.fakeMobKey.isValid(); }

    public FakeMob() { }
    public FakeMob(@Nonnull FakeMobKey fakeMobKey) { this.fakeMobKey = fakeMobKey; }
    public FakeMob(@Nonnull FakeMobKey fakeMobKey, String name) {
        this.fakeMobKey = fakeMobKey;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ":" + fakeMobKey;
    }

    public static final String NBT_FAKE_MOB_NAME = "fakeMobName";
    public static void writeToNBT(@Nonnull FakeMob fakeMob, NBTTagCompound tagCompound) {

        if (fakeMob.isValid()) {

            if (tagCompound == null)
                tagCompound = new NBTTagCompound();

            tagCompound.setString(NBT_FAKE_MOB_NAME, fakeMob.getName());
            FakeMobKey.writeToNBT(fakeMob.getFakeMobKey(), tagCompound);
        }
    }
}
