package ipsis.woot.school;

import ipsis.Woot;
import ipsis.woot.dimensions.tartarus.TartarusManager;
import ipsis.woot.util.*;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

import static ipsis.woot.dimensions.tartarus.TartarusManager.INVALID_CELL_ID;

public class SchoolManager {

    private static String getKey(FakeMobKey fakeMobKey, int looting) {

        return fakeMobKey.toString() + "#l" + looting;
    }

    private static HashMap<String, SchoolPupil> pupils = new HashMap<>();

    public static void teachMob(FakeMobKey fakeMobKey, int looting) {

        if (Woot.DROP_MANAGER.isLearningComplete(fakeMobKey, looting)) {
            // already taught
            if (Woot.debugging.isEnabled(Debug.Group.LEARN))
                Woot.debugging.trace(Debug.Group.LEARN, "SchoolManager:teachMob already taught + {}/{}", fakeMobKey, looting);
            return;
        }

        String key = getKey(fakeMobKey, looting);
        if (pupils.containsKey(key)) {
            // currently teaching
            if (Woot.debugging.isEnabled(Debug.Group.LEARN))
                Woot.debugging.trace(Debug.Group.LEARN, "SchoolManager:teachMob currently teaching {}/{}", fakeMobKey, looting);
            return;
        }

        int cellId = TartarusManager.assignCell(fakeMobKey, looting);
        if (cellId != INVALID_CELL_ID) {
            SchoolPupil pupil = new SchoolPupil(cellId, fakeMobKey, looting);
            pupils.put(key, pupil);
        }
    }

    public static void excludeMob(FakeMobKey fakeMobKey, int looting) {

        String key = getKey(fakeMobKey, looting);
        if (!pupils.containsKey(key))
            return;

        SchoolPupil pupil = pupils.get(key);
        TartarusManager.freeCell(pupil.getCellId());
        pupils.remove(key);
    }

    public static void tick() {

        for (String key : pupils.keySet()) {

            SchoolPupil pupil = pupils.get(key);
            pupil.tick();

            if (pupil.hasLearnTimeoutExpired()) {
                pupil.resetLearnTimeout();

                // Hoover up the last spawn drops
                List<ItemStack> drops = TartarusManager.getLoot(pupil.getCellId());
                Woot.DROP_MANAGER.learnSilent(pupil.getFakeMobKey(), pupil.getLooting(), drops);


                boolean isFull = Woot.DROP_MANAGER.isLearningComplete(pupil.getFakeMobKey(), pupil.getLooting());
                if (isFull) {

                    /**
                     * No more learning needed
                     */
                    if (Woot.debugging.isEnabled(Debug.Group.LEARN))
                        Woot.debugging.trace(Debug.Group.LEARN, "SchoolManager:tick free {}", key);

                    TartarusManager.freeCell(pupil.getCellId());
                    pupil.cellId = INVALID_CELL_ID;
                } else {

                    /**
                     * Continue learning
                      */
                    if (Woot.debugging.isEnabled(Debug.Group.LEARN))
                        Woot.debugging.trace(Debug.Group.LEARN, "SchoolManager:tick spawnKill {}@cell{}", key, pupil.getCellId());

                    TartarusManager.spawnKill(pupil.getCellId());
                }
            }
        }

        pupils.values().removeIf(schoolPupil -> schoolPupil.cellId == INVALID_CELL_ID);
    }

    public static void getStatus(@Nonnull List<String> status, String[] args) {

        status.add("SchoolManager");
        for (String key : pupils.keySet())
            status.add("    " + key + " " + pupils.get(key).getCellId());
    }

    private static class SchoolPupil {

        private static final int LEARN_TICKS = 20;
        private FakeMobKey fakeMobKey;
        private int looting;
        private int learnTicks;
        private int cellId;

        public SchoolPupil(int cellId, FakeMobKey fakeMobKey, int looting) {
            this.fakeMobKey = fakeMobKey;
            this.looting = MiscUtils.clampLooting(looting);
            resetLearnTimeout();
            this.cellId = cellId;
        }

        public FakeMobKey getFakeMobKey() { return this.fakeMobKey; }
        public int getLooting() { return this.looting; }
        public boolean hasLearnTimeoutExpired() { return this.learnTicks >= LEARN_TICKS; }
        public void tick() { this.learnTicks++; }
        public void resetLearnTimeout() { this.learnTicks = 0; }
        public int getCellId() { return this.cellId; }
    }
}
