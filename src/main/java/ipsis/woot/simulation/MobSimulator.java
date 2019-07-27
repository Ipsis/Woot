package ipsis.woot.simulation;

import ipsis.woot.common.Config;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import javax.annotation.Nonnull;
import java.util.*;

public class MobSimulator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker MOBSIM = MarkerManager.getMarker("WOOT_MOBSIM");

    private static final int INVALID_CELL_ID = -1;

    HashMap<FakeMobKey, Pupil> pupils = new HashMap<>();
    List<FakeMobKey> waitingForCells = new ArrayList<>();

    public static MobSimulator get() { return INSTANCE; }
    static MobSimulator INSTANCE;
    static { INSTANCE = new MobSimulator(); }

    public boolean learn(@Nonnull FakeMobKey fakeMobKey) {

        if (!fakeMobKey.getMob().isValid())
            return false;

        // One mob of specific type/looting at a time
        if (pupils.keySet().contains(fakeMobKey))
            return false;

        // defer allocating the cells
        waitingForCells.add(fakeMobKey);
        return true;
    }

    public Set<FakeMobKey> getPupils() {
        return Collections.unmodifiableSet(pupils.keySet());
    }

    public void tick(World world) {

        if (world == null)
            return;

        Iterator iter = pupils.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            Pupil pupil = (Pupil)pair.getValue();
            pupil.tick();

            if (pupil.currentTicks > Config.COMMON.SIMULATION_TICKS.get()) {
                pupil.resetTicks();

                // Handle drops from previous kill
                DropRegistry.get().learnSilent(pupil.fakeMobKey, Tartarus.get().sweepCell(pupil.cellId, world));

                if (DropRegistry.get().isLearningFinished(pupil.fakeMobKey, Config.COMMON.SIMULATION_MOB_COUNT.get())) {
                    LOGGER.info(MOBSIM, "Finished simulating {}", pupil.fakeMobKey);
                    Tartarus.get().vacateCell(pupil.cellId);
                    iter.remove();
                } else {
                    LOGGER.info(MOBSIM, "Simulate {}", pupil.fakeMobKey);
                    Tartarus.get().simulateInCell(pupil.cellId, pupil.fakeMobKey, world);
                }
            }
        }

        iter = waitingForCells.iterator();
        while (iter.hasNext()) {
            FakeMobKey fakeMobKey = (FakeMobKey)iter.next();
            int cellId = Tartarus.get().allocateCell();
            if (cellId != INVALID_CELL_ID) {
                LOGGER.info(MOBSIM, "Allocated cell {} to {}", cellId, fakeMobKey);
                Pupil pupil = new Pupil(fakeMobKey, cellId);
                pupils.put(fakeMobKey, pupil);
                LOGGER.info(MOBSIM, "Simulate {}", pupil.fakeMobKey);
                Tartarus.get().simulateInCell(pupil.cellId, pupil.fakeMobKey, world);
                iter.remove();
            }
        }
    }

    public class Pupil {
        FakeMobKey fakeMobKey;
        int cellId = INVALID_CELL_ID;
        int currentTicks;

        private Pupil(){}
        public Pupil(@Nonnull FakeMobKey fakeMobKey, int cellId) {
            this.fakeMobKey = fakeMobKey;
            this.cellId = cellId;
            currentTicks = 0;
        }

        public void tick() { currentTicks++; }
        public void resetTicks() { currentTicks = 0; }
    }
}
