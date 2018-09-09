package ipsis.woot.loot;

import ipsis.Woot;
import ipsis.woot.util.Debug;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.MiscUtils;
import net.minecraft.item.ItemStack;
import scala.Int;

import java.util.*;

import static ipsis.woot.util.MiscUtils.clampLooting;

/**
 * Which mobs have dropped this specific item
 */
public class RawDropData {

    private ItemStack itemStack;
    private List<DropMob> mobs = new ArrayList<>();

    public RawDropData(ItemStack itemStack) {

        this.itemStack = itemStack.copy();
        if (this.itemStack.isItemStackDamageable())
            this.itemStack.setItemDamage(0);
    }

    public List<DropMob> getDropMobs() {
        return Collections.unmodifiableList(mobs);
    }

    public void incStackSizeForLooting(FakeMobKey fakeMobKey, int looting, int size) {

        Woot.debugging.trace(Debug.Group.LEARN, "RawDropData:incStackSizeForLooting: " + fakeMobKey + "/l" + looting + "/s" + size);

        looting = clampLooting(looting);

        DropMob dropMob = null;
        for (DropMob m : mobs) {
            if (m.fakeMobKey.equals(fakeMobKey)) {
                dropMob = m;
                break;
            }
        }

        if (dropMob == null) {
            dropMob = new DropMob(fakeMobKey);
            mobs.add(dropMob);
        }

        Map<Integer, Integer> map = dropMob.looting0;
        if (looting == 1)
            map = dropMob.looting1;
        else if (looting == 2)
            map = dropMob.looting2;
        else if (looting == 3)
            map = dropMob.looting3;

        Integer i = 1;
        if (map.containsKey(size))
            i = map.get(size) + 1;
        map.put(size, i);

    }

    public ItemStack getItemStack() {

        return this.itemStack.copy();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(itemStack.getDisplayName());
        for (DropMob dropMob : mobs)
            sb.append(" " + dropMob.fakeMobKey);

        return sb.toString();
    }

    public static class DropMob {

        private FakeMobKey fakeMobKey;
        private Map<Integer, Integer> looting0 = new HashMap<>();
        private Map<Integer, Integer> looting1 = new HashMap<>();
        private Map<Integer, Integer> looting2 = new HashMap<>();
        private Map<Integer, Integer> looting3 = new HashMap<>();

        public DropMob(FakeMobKey fakeMobKey) {
            this.fakeMobKey = fakeMobKey;
        }

        public FakeMobKey getFakeMobKey() { return this.fakeMobKey; }

        public Map<Integer, Integer> getLooting(int looting) {

            looting = MiscUtils.clampLooting(looting);
            if (looting == 0)
                return  looting0;
            else if (looting == 1)
                return  looting1;
            else if (looting == 2)
                return  looting2;
            else
                return looting3;
        }


        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder(fakeMobKey.toString());
            sb.append(" Looting 0: ");
            for (Integer i : looting0.keySet())
                sb.append(i + "/" + looting0.get(i) + " ");
            sb.append(" Looting 1: ");
            for (Integer i : looting1.keySet())
                sb.append(i + "/" + looting1.get(i) + " ");
            sb.append(" Looting 2: ");
            for (Integer i : looting2.keySet())
                sb.append(i + "/" + looting2.get(i) + " ");
            sb.append(" Looting 3: ");
            for (Integer i : looting3.keySet())
                sb.append(i + "/" + looting3.get(i) + " ");
            return sb.toString();
        }
    }
}
