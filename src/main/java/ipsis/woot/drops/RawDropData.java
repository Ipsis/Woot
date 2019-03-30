package ipsis.woot.drops;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RawDropData {

    protected ItemStack itemStack;
    protected List<RawDropMobData> mobData = new ArrayList();

    public RawDropData(ItemStack itemStack) {
        this.itemStack = itemStack.copy();
        this.itemStack.setCount(1);
    }

    public void update(@Nonnull FakeMobKey fakeMobKey, int looting, int stackSize) {

        RawDropMobData rawDropMobData = null;
        for (RawDropMobData d : mobData) {
            if (d.fakeMobKey.equals(fakeMobKey)) {
                rawDropMobData = d;
                break;
            }
        }

        if (rawDropMobData == null) {
            rawDropMobData = new RawDropMobData(fakeMobKey);
            mobData.add(rawDropMobData);
        }

        rawDropMobData.updateDropCount(looting);
        rawDropMobData.update(looting, stackSize);


    }
}
