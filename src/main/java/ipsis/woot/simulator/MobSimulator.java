package ipsis.woot.simulator;

import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.simulator.library.LootLibrary;
import ipsis.woot.simulator.tartarus.Cell;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;

public class MobSimulator {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();

    private static final int NUM_SIMULATION_CELLS = 128;
    private static final BlockPos ORIGIN_POS = new BlockPos(0, 0, 0);

    private static MobSimulator INSTANCE = new MobSimulator();
    public static MobSimulator getInstance() { return INSTANCE; }

    private LootLibrary library;
    private Cell[] cells;

    private List<FakeMobKey> waitingForCell = new ArrayList<>();
    private int currTicks = 0;

    public MobSimulator() {
        library = new LootLibrary();
        cells = new Cell[NUM_SIMULATION_CELLS];

        BlockPos[] offsets = {
                new BlockPos(0, 0, 0),
                new BlockPos(0, 0, 9),
                new BlockPos(9, 0, 9),
                new BlockPos(9, 0, 9),
        };
        for (int floor = 0; floor < (NUM_SIMULATION_CELLS / 4); floor++) {
            for (int cell = 0; cell < 4; cell++) {
                int flootCell = floor * 4;
                cells[flootCell + cell] = new Cell(new BlockPos(
                        ORIGIN_POS.getX() + offsets[cell].getX(),
                        (flootCell * 16) + offsets[cell].getY(),
                        ORIGIN_POS.getZ() + offsets[cell].getZ()));
            }
        }
    }

    private boolean isLearningComplete(@Nonnull FakeMobKey fakeMobKey) {
        return library.getSimulatedKills(fakeMobKey) >= MobSimulatorConfiguration.SIMULATION_MOB_COUNT.get();
    }

    /**
     * API
     */
    /**
     * Returns a summary of the chance to drop for each looting level
     */
    public @Nonnull List<SimulatedMobDropSummary> getDropSummary(@Nonnull FakeMob fakeMob) {
        return library.getDropSummary(fakeMob);
    }

    /**
     * Rolls the dropped items for this specific mob/looting
     */
    public @Nonnull List<ItemStack> getRolledDrops(@Nonnull FakeMobKey fakeMobKey) {
        return library.getRolledDrops(fakeMobKey);
    }

    public @Nonnull Set<FakeMob> getKnownMobs() {
        return library.getKnownMobs();
    }

    public @Nonnull List<FakeMobKey> getSimulations() {
        List<FakeMobKey> mobs = new ArrayList<>();
        for (int i = 0; i < cells.length; i++)
            if (cells[i].isOccupied())
                mobs.add(cells[i].getOccupant());
        return  mobs;
    }

    public @Nonnull List<FakeMobKey> getWaiting() {
        List<FakeMobKey> mobs = new ArrayList<>();
        waitingForCell.forEach(m -> mobs.add(new FakeMobKey(m.getMob(), m.getLooting())));
        return mobs;
    }

    public JsonObject toJson() {
        return library.toJson();
    }

    public void fromJson(JsonObject jsonObject) {
        library.fromJson(jsonObject);
    }

    /**
     * Register a mob for learning
     */
    public boolean learn(@Nonnull FakeMob fakeMob) {
        if (!fakeMob.isValid())
            return false;

        if (!FakeMob.isInEntityList(fakeMob))
            return false;

        if (library.getKnownMobs().contains(fakeMob)) {
            Woot.setup.getLogger().debug("learn {} already known", fakeMob);
            return false;
        }

        if (!PolicyRegistry.get().canSimulate(fakeMob.getResourceLocation()))
            return false;

        for (int i = 0; i < 4; i++) {
            FakeMobKey fakeMobKey = new FakeMobKey(fakeMob, i);
            if (!isLearningComplete(fakeMobKey))
                waitingForCell.add(fakeMobKey);
        }

        return true;
    }

    public void flush(@Nonnull FakeMob fakeMob) {
        if (fakeMob.isValid())
            library.flush(fakeMob);
    }

    /**
     * Handle the drops from a simulated kill
     */
    public void learnSimulatedDrops(@Nonnull FakeMobKey fakeMobKey, @Nonnull List<ItemStack> drops) {
        library.learnSimulatedDrops(fakeMobKey, drops);
    }


    public void learnCustomDrop(@Nonnull FakeMobKey fakeMobKey, @Nonnull ItemStack drop, float dropChance, HashMap<Integer, Integer> stackSizes) {
        library.learnCustomDrop(fakeMobKey, drop, dropChance, stackSizes);
    }

    public boolean isEqualForLearning(@Nonnull ItemStack a, @Nonnull ItemStack b) {
        return ItemStack.areItemsEqualIgnoreDurability(a, b);
    }

    public void tick(World world) {
        if (world == null)
            return;

        currTicks = MathHelper.clamp(currTicks + 1, 0, Integer.MAX_VALUE);
        if (currTicks < MobSimulatorConfiguration.SIMULATION_TICKS_PER_SIM_TICK.get())
            return;


        int cellsProcessed = 0;
        for (int i = 0; i < cells.length && cellsProcessed <= MobSimulatorConfiguration.SIMULATION_CELLS_PER_SIM_TICK.get(); i++) {
            Cell cell = cells[i];
            if (!cell.isOccupied())
                continue;

            // Handle the drops from the previous kill
            library.learnSimulatedDropsSilent(cell.getOccupant(), cell.sweep(world));
            if (isLearningComplete(cell.getOccupant()))
                cell.free();
            else
                cell.run(world);
            cell.clean(world);

            cellsProcessed++;
        }

        if (!waitingForCell.isEmpty()) {
            Iterator iterator = waitingForCell.iterator();
            while (iterator.hasNext()) {
                FakeMobKey fakeMobKey = (FakeMobKey) iterator.next();
                for (Cell cell : cells) {
                    if (!cell.isOccupied()) {
                        Woot.setup.getLogger().debug("Allocated {} to cell {}", fakeMobKey, cell);
                        cell.setMob(fakeMobKey);
                        cell.run(world);
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }

}
