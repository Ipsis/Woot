package ipsis.woot.util;

import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * Implements a ticking machine with recipes that uses energy
 * This requires that enough power is available per tick to progress the recipe.
 * If you lose power then the progress is reset
 *
 * This is HEAVILY based off the CoFH's TileMachineBase processing algorithm
 *
 */
public abstract class WootMachineTileEntity extends TileEntity implements ITickableTileEntity {

    protected enum Mode {
        NONE, INPUT, OUTPUT
    }
    protected HashMap<Direction, Mode> settings = new HashMap<>();

    protected static final Logger LOGGER = LogManager.getLogger();

    public WootMachineTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
        for (Direction direction : Direction.values())
            settings.put(direction, Mode.NONE);
    }


    @Override
    public void tick() {
        if (world.isRemote)
            return;

        machineTick();
    }

    private boolean isActive = false;
    private int processMax = 0; // total energy needed
    private int processRem = 0; // energy still to use
    private void machineTick() {

        if (isActive) {
            // Not physically disabled and we have a valid set of input items
            //LOGGER.info("machineTick: running");
            processTick(); // progress by one tick, consume energy
            if (canFinish()) {
                // energy required for recipe all used, input items still valid
                //LOGGER.info("machineTick: finished");
                processFinish(); //
                if (isDisabled() || !canStart()) {
                    //  disabled via redstone or dont have a valid set of input items and enough energy
                    processOff();
                    isActive = false;
                    //LOGGER.info("machineTick: redstone disabled or cannot start -> turn off");
                } else {
                    processStart(); // set processMax and processRem
                }
            } else if (!hasEnergy()) {
                //LOGGER.info("machineTick: no energy");
                processOff();;
            }
        } else if (!isDisabled()) {
            if (world.getGameTime() % 10 == 0 && canStart()) {
                // have a valid set of input items and enough energy
                processStart(); // set processMax and processRem
                processTick(); // use energy and update processRem
                isActive = true;
                //LOGGER.info("machineTick: turn on");
            }
        }
    }

    /**
     * All inputs must be present to form a recipe
     * Energy cell must not be empty
     * Must be space for all outputs
     */
    protected abstract boolean canStart();

    /**
     * Remove the inputs and generate the outputs
     */
    protected abstract void processFinish();

    /**
     * All inputs must be present to form a recipe
     * Energy and output space are not valid
     */
    protected abstract boolean hasValidInput();

    protected abstract boolean hasEnergy();
    protected abstract int useEnergy();
    protected abstract void clearRecipe();
    protected abstract int getRecipeEnergy();
    protected abstract boolean isDisabled();

    /**
     *
     * If energy still needs to be consumed then do so
     * Returns the amount of energy used
     */
    private int processTick() {
        //LOGGER.info("processTick: processRem {}", processRem);
        if (processRem <= 0)
            return 0;

        int energy = useEnergy();
        //LOGGER.info("processTick: energy used {}", energy);
        processRem -= energy;
        return energy;
    }

    /**
     * Can finish if all the energy has been consumed and the inputs still give a valid recipe
     */
    private boolean canFinish() {
        return processRem <= 0 && hasValidInput();
    }

    protected int calculateProgress() {
        return processMax == 0 ? 0 : 100 - (int)((100.0F / processMax) * processRem);
    }

    protected void processOff() {
        //LOGGER.info("processOff: clearing remainder and recipe");
        isActive = false;
        processRem = 0;
        processMax = 0;
        clearRecipe();
    }

    /**
     * Setup the required energy for the recipe
     */
    private void processStart() {
        processMax = getRecipeEnergy();
        processRem = getRecipeEnergy();
    }

    public void onContentsChanged(int slot) {
       if (!isActive)
           return;

       if (!hasValidInput())
           processOff();
    }

    public void dropContents(List<ItemStack> items) {
        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty())
                continue;
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
        }
        markDirty();
        WorldHelper.updateClient(world, pos);
    }

}
