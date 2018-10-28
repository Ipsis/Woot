package ipsis.woot.drops;

import ipsis.Woot;
import ipsis.woot.util.Debug;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.MiscUtils;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class DropManager implements IDropProvider, IDropLearning {

    private static final CustomDropRepository customDrops = new CustomDropRepository();
    private static final LearnedDropRepository learnedDrops = new LearnedDropRepository();

    public void init() {
        customDrops.init();
        learnedDrops.init();
    }

    public void shutdown() {
        learnedDrops.shutdown();
    }

    public static boolean isEqualForLearning(ItemStack a, ItemStack b) {

        /**
         * We are planning for 1.13
         * No more metadata so just an item match.
         * TODO what about NBT?
         * TODO remove the metadata match
         */

        if (a == null || b == null)
            return false;

        /*
        return a != null && b != null && a.getItem() == b.getItem();
         */

        if (a.isEmpty() || b.isEmpty())
            return false;

        if (a.getItem() == null || b.getItem() == null)
            return false;

        if (!a.getItem().equals(b.getItem()))
            return false;

        if (a.getHasSubtypes() && a.getItemDamage() != b.getItemDamage())
            return false;

        return true;
    }

    /**
     * IDropLearning
     */
    @Override
    public void learnSilent(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {

        if (Woot.debugging.isEnabled(Debug.Group.LEARN))
            Woot.debugging.trace(Debug.Group.LEARN, "DropManager:learnSilent {}/{}/{}",fakeMobKey, looting, drops);

        looting = MiscUtils.clampLooting(looting);
        learnedDrops.learnSilent(fakeMobKey, looting, drops);
    }

    @Override
    public void learn(@Nonnull FakeMobKey fakeMobKey, int looting, @Nonnull List<ItemStack> drops) {

        if (Woot.debugging.isEnabled(Debug.Group.LEARN))
            Woot.debugging.trace(Debug.Group.LEARN, "DropManager:learn {}/{}/{}",fakeMobKey, looting, drops);

        looting = MiscUtils.clampLooting(looting);
        learnedDrops.learn(fakeMobKey, looting, drops);
    }

    @Override
    public boolean isLearningComplete(@Nonnull FakeMobKey fakeMobKey, int looting) {
        looting = MiscUtils.clampLooting(looting);
        return learnedDrops.isLearningComplete(fakeMobKey, looting);
    }

    /**
     * IDropProvider
     */
    @Nonnull
    @Override
    public MobDropData getMobDropData(@Nonnull FakeMobKey fakeMobKey, int looting) {
        looting = MiscUtils.clampLooting(looting);
        MobDropData mobDropData = new MobDropData(fakeMobKey, looting);

        MobDropData custom = customDrops.getMobDropData(fakeMobKey, looting);
        MobDropData learned = learnedDrops.getMobDropData(fakeMobKey, looting);

        // TODO - merge the custom and learned drops
        for (MobDropData.DropData dropData : learned.getDrops())
            mobDropData.addDropData(dropData);

        return mobDropData;
    }

    public void getStatus(@Nonnull List<String> status, String[] args) {
        status.add(">>> DropManager");
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("learned"))
                learnedDrops.getStatus(status, args);
            else if (args[1].equalsIgnoreCase("custom"))
                customDrops.getStatus(status, args);
        }
        status.add("<<< DropManager");
    }

}
