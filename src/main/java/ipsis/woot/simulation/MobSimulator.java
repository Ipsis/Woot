package ipsis.woot.simulation;

import ipsis.woot.common.configuration.Config;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;

public class MobSimulator {

    private static final Logger LOGGER = LogManager.getLogger();

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

        if (DropRegistry.get().isLearningFinished(fakeMobKey, Config.COMMON.SIMULATION_MOB_COUNT.get()))
            return false;

        // defer allocating the cells
        waitingForCells.add(fakeMobKey);
        return true;
    }

    public Set<FakeMobKey> getPupils() {
        return Collections.unmodifiableSet(pupils.keySet());
    }

    private int currTicks = 0;
    public void tick(World world) {

        if (world == null)
            return;

        currTicks = MathHelper.clamp(currTicks + 1, 0, Integer.MAX_VALUE);
        if (currTicks >= Config.COMMON.SIMULATION_TICKS_PER_SIM_TICK.get()) {
            // An actual simulation tick

            int processed = 0;
            Iterator iter = pupils.entrySet().iterator();
            while (iter.hasNext() && processed <= Config.COMMON.SIMULATION_CELLS_PER_SIM_TICK.get()) {
                Map.Entry pair = (Map.Entry) iter.next();
                Pupil pupil = (Pupil) pair.getValue();

                // Handle drops from previous kill
                DropRegistry.get().learnSilent(pupil.fakeMobKey, Tartarus.get().sweepCell(pupil.cellId, world));

                if (DropRegistry.get().isLearningFinished(pupil.fakeMobKey, Config.COMMON.SIMULATION_MOB_COUNT.get())) {
                    LOGGER.debug("MobSimulator:tick finished simulating {}", pupil.fakeMobKey);
                    Tartarus.get().vacateCell(pupil.cellId);
                    iter.remove();
                } else {
                    LOGGER.debug("MobSimulator:tick continue simulating {}", pupil.fakeMobKey);
                    Tartarus.get().simulateInCell(pupil.cellId, pupil.fakeMobKey, world);
                }
                processed++;
            }

            /**
             * Always try to allocate free cells
             */
            iter = waitingForCells.iterator();
            while (iter.hasNext()) {
                FakeMobKey fakeMobKey = (FakeMobKey)iter.next();
                int cellId = Tartarus.get().allocateCell();
                if (cellId != INVALID_CELL_ID) {
                    LOGGER.debug("Allocated cell {} to {}", cellId, fakeMobKey);
                    Pupil pupil = new Pupil(fakeMobKey, cellId);
                    pupils.put(fakeMobKey, pupil);
                    LOGGER.debug("Simulate {}", pupil.fakeMobKey);
                    Tartarus.get().simulateInCell(pupil.cellId, pupil.fakeMobKey, world);
                    iter.remove();
                }
            }
        }
    }

    public class Pupil {
        FakeMobKey fakeMobKey;
        int cellId = INVALID_CELL_ID;

        private Pupil(){}
        public Pupil(@Nonnull FakeMobKey fakeMobKey, int cellId) {
            this.fakeMobKey = fakeMobKey;
            this.cellId = cellId;
        }
    }
}
