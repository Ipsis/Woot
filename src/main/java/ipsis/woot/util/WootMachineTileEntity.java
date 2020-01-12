package ipsis.woot.util;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements a ticking machine with recipes that uses energy
 * This requires that enough power is available per tick to progress the recipe.
 * If you lose power then the progress is reset
 *
 * This is HEAVILY based off the CoFH's TileMachineBase processing algorithm
 *
 */
public abstract class WootMachineTileEntity extends TileEntity implements ITickableTileEntity {

    protected static final Logger LOGGER = LogManager.getLogger();

    public WootMachineTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
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

    protected abstract boolean hasEnergy();
    protected abstract int useEnergy();
    protected abstract void clearRecipe();
    protected abstract int getRecipeEnergy();
    protected abstract void processFinish();
    protected abstract boolean canStart();
    protected abstract boolean hasValidInput();
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

    protected void processOff() {
        //LOGGER.info("processOff: clearing remainder and recipe");
        isActive = false;
        processRem = 0;
        clearRecipe();
    }

    /**
     * Setup the required energy for the recipe
     */
    private void processStart() {
        processMax = getRecipeEnergy();
        processRem = getRecipeEnergy();
    }
}
