package ipsis.woot.school;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IMobSchool {

    void teachMob(FakeMobKey fakeMobKey, int looting);
    void excludeMob(FakeMobKey fakeMobKey, int looting);

    void tick();

    void handleDrops(FakeMobKey fakeMobKey, int looting, List<ItemStack> drops);

}
