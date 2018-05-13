package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.tile.TileDemonCrystal;
import ipsis.Woot;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.util.DebugSetup;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This is the Ritual Of The Forsaken Soul but using the mobs in the factory instead of in world.
 * It is based off WayOfTime's BloodMagic code in
 * BloodMagic/src/main/java/WayofTime/bloodmagic/ritual/types/RitualForsakenSoul.java
 * BloodMagic/LICENSE
 *
 * The BloodMagic ritual supports the uniqueness of the mobs and can vary between 1-10
 * This ritual has a uniqueness of 1.
 *
 */

public class RitualClonedSoul extends Ritual {

    private static final String RITUAL_NAME = "ritualClonedSoul";
    private static final int CRYSTAL_LEVEL = 0;
    private static final int ACTIVATION_COST = 40000;
    private static final int REFRESH_COST = 2;
    private static final int REFRESH_TIME = 25;
    private static final int HEALTH_THRESHOLD = 20;

    private static final String HEART_RANGE = "heart";
    private static final String CRYSTAL_RANGE = "crystal";

    private double crystalBuffer = 0;
    private double willBuffer = 0;

    private BlockPos heartOffsetPos = new BlockPos(0, 0, 0);

    public RitualClonedSoul() {
        super(RITUAL_NAME, CRYSTAL_LEVEL, ACTIVATION_COST, "ritual." + Reference.MOD_ID + "." + RITUAL_NAME);
        addBlockRange(CRYSTAL_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-3, 2, -3), 7, 5, 7));
        addBlockRange(HEART_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, -10, -10), 21));

        setMaximumVolumeAndDistanceOfRange(CRYSTAL_RANGE, 250, 5, 7);
        setMaximumVolumeAndDistanceOfRange(HEART_RANGE, 0, 15, 15);
    }

    private static final String WILL_TAG = "willBuffer";
    private static final String CRYSTAL_TAG = "crystalBuffer";

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        willBuffer = tag.getDouble(WILL_TAG);
        crystalBuffer = tag.getDouble(CRYSTAL_TAG);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setDouble(WILL_TAG, willBuffer);
        tag.setDouble(CRYSTAL_TAG, crystalBuffer);
    }

    private boolean isValidFactory(TileEntity te) {

        if (!(te instanceof TileEntityMobFactoryHeart))
            return false;

        TileEntityMobFactoryHeart heart = (TileEntityMobFactoryHeart)te;
        return heart.getRunning() == 1;
    }

    private IBloodMagicHandler findHandler(World world, BlockPos pos) {

        BlockPos heartPos = pos.add(heartOffsetPos);
        TileEntity te = world.getTileEntity(heartPos);

        AreaDescriptor heartRange = getBlockRange(HEART_RANGE);

        if (!heartRange.isWithinArea(heartOffsetPos) || !isValidFactory(te)) {

            for (BlockPos newPos : heartRange.getContainedPositions(pos)) {
                TileEntity nextTile = world.getTileEntity(newPos);
                if (nextTile instanceof IBloodMagicHandler) {
                    te = nextTile;
                    heartOffsetPos = newPos.subtract(pos);
                    heartRange.resetCache();
                    break;
                }
            }
        }

        return isValidFactory(te) && te instanceof IBloodMagicHandler ? (IBloodMagicHandler) te : null;
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_CRYSTAL, "performRitual - ClonedSoul", "");

        if (!BloodMagicHelper.canPerformRitual(masterRitualStone, getRefreshCost()))
            return;

        BlockPos pos = masterRitualStone.getBlockPos();
        World world = masterRitualStone.getWorldObj();
        int maxEffects = 100;
        int totalEffects = 0;

        /**
         * Find the factory
         */
        IBloodMagicHandler bloodMagicHandler = findHandler(world, masterRitualStone.getBlockPos());
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_CRYSTAL, "performRitual - ClonedSoul", bloodMagicHandler);

        if (bloodMagicHandler != null && bloodMagicHandler.getCrystalNumMobs() > 0 && bloodMagicHandler.getWootMobName() != null && !BloodMagicAPI.INSTANCE.getBlacklist().getSacrifice().contains(bloodMagicHandler.getWootMobName().getResourceLocation())) {

            List<TileDemonCrystal> crystalList = new ArrayList<>();
            AreaDescriptor crystalRange = getBlockRange(CRYSTAL_RANGE);

            /**
             * Find the crystal
             */
            crystalRange.resetIterator();
            while (crystalRange.hasNext()) {
                BlockPos nextPos = crystalRange.next().add(pos);
                TileEntity te = world.getTileEntity(nextPos);
                if (te instanceof TileDemonCrystal)
                    crystalList.add((TileDemonCrystal) te);
            }

            int health = Woot.mobCosting.getMobSpawnCost(world, bloodMagicHandler.getWootMobName());
            if (health > 0) {

                int realHealth = (int)(((float)health / 100.0F) * (float)bloodMagicHandler.getCrystalMobHealthPercentage());
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_CRYSTAL, "performRitual - ClonedSoul", "health:" + health + "/" + realHealth);
                health = realHealth;

                for (int mob = 0; mob < bloodMagicHandler.getCrystalNumMobs(); mob++) {

                    feedWillAndCrystal(health);
                    totalEffects++;
                    if (totalEffects >= maxEffects)
                        break;
                }

                if (!crystalList.isEmpty() && crystalBuffer > 0) {

                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_CRYSTAL, "performRitual - ClonedSoul", "crystals:" + crystalList.size());
                    TileDemonCrystal crystal = crystalList.get(world.rand.nextInt(crystalList.size()));
                    tryFeedTheCrystal(crystal);
                }
            }

            bloodMagicHandler.clearCrystalNumMobs();
        }

        masterRitualStone.getOwnerNetwork().syphon(getRefreshCost() * totalEffects);
    }

    /**
     * The BloodMagic ritual does the following
     *
     * for each entity that it finds
     *     int uniqueness = min(number of unique mobs, MAX_UNIQUENESS(10)) => range 1->10
     *     double modifier = 1 (default) 4 (is an animal)
     *
     *     willForUniqueness = max(50 - 15 * sqrt(uniqueness), 0);
     *
     *     willBuffer += (((modifier * willForUniqueness) / HEALTH_THRESHOLD(20)) * entityMaxHealth)
     *     crystalBuffer += ((modifier * entityMaxHealth) / HEALTH_THRESHOLD(20))
     *
     *
     * if there are nearby crystals then pick one of them to grow
     *     double growth = min(crystalbuffer, 1)
     *     double willSyphonAmount = ((growth * willBuffer) / crystalBuffer)
     *
     *     double willDrain = ((growth * willBuffer) / crystalBuffer)
     *     double progressPercentage = growth
     *     double percentageGrowth = growCrystalWithWillAmount(willDrain, progressPercentage)
     *     if (percentageGrowth > 0)
     *         crystalBuffer -= percentageGrowth
     *         willBuffer -= percentageGrowth * willSyphonAmount
     *
     *
     * The Woot ritual will use the same mechanics, the only difference will be that there will be no uniqueness,
     * There is only ever one mob type from the factory.
     */
    private void feedWillAndCrystal(int mobHealth) {

        int uniqueness = 1;
        double modifier = 1;
        double willForUniqueness = Math.max(50 - 15 * Math.sqrt(uniqueness), 0);

        double willBufferInc = ((modifier * willForUniqueness) / HEALTH_THRESHOLD) * mobHealth;
        double crystalBufferInc = ((modifier * mobHealth) / HEALTH_THRESHOLD);

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_CRYSTAL, "performRitual - ClonedSoul", "feedWillAndCrystal health:" + mobHealth + " willInc:" + willBufferInc + " crystalInc:" + crystalBufferInc);

        willBuffer += willBufferInc;
        crystalBuffer += crystalBufferInc;
    }

    private void tryFeedTheCrystal(TileDemonCrystal crystal) {

        if (crystal == null)
            return;

        double growth = Math.min(crystalBuffer, 1);
        double willSyphonAmount = ((growth * willBuffer) / crystalBuffer);
        double willDrain = ((growth * willBuffer) / crystalBuffer);
        double progressPercentage = growth;

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_CRYSTAL, "performRitual - ClonedSoul", "tryFeedTheCrystal willDrain:" + willDrain + " progress:" + progressPercentage);
        double actualGrowthPercentage = crystal.growCrystalWithWillAmount(willDrain, progressPercentage);
        if (actualGrowthPercentage > 0) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_CRYSTAL, "performRitual - ClonedSoul", "tryFeedTheCrystal grew:" + actualGrowthPercentage);
            crystalBuffer -= actualGrowthPercentage;
            willBuffer -= actualGrowthPercentage * willSyphonAmount;
        }
    }

    @Override
    public int getRefreshCost() {
        return REFRESH_COST;
    }

    @Override
    public int getRefreshTime() {
        return REFRESH_TIME;
    }

    @Override
    public void gatherComponents(Consumer<RitualComponent> components) {


        addCornerRunes(components, 1, 0, EnumRuneType.AIR);
        addParallelRunes(components, 1, 0, EnumRuneType.DUSK);
        addCornerRunes(components, 2, 0, EnumRuneType.EARTH);
        addParallelRunes(components, 2, 0, EnumRuneType.FIRE);
        addCornerRunes(components, 2, 1, EnumRuneType.FIRE);
        addCornerRunes(components, 2, 2, EnumRuneType.EARTH);
        addParallelRunes(components, 3, 1, EnumRuneType.FIRE);
        addParallelRunes(components, 3, 2, EnumRuneType.FIRE);
        addCornerRunes(components, 4, 2, EnumRuneType.EARTH);

    }

    @Override
    public Ritual getNewCopy() {
        return new RitualClonedSoul();
    }
}
