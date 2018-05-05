package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.tile.TileDemonCrystal;
import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
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
 */

public class RitualClonedSoul extends Ritual {

    private static final String RITUAL_NAME = "ritualClonedSoul";
    private static final int CRYSTAL_LEVEL = 0;
    private static final int ACTIVATION_COST = 40000;
    private static final int REFRESH_COST = 2;
    private static final int REFRESH_TIME = 40;
    private static final int HEALTH_THRESHOLD = 20;

    private static final String HEART_RANGE = "heart";
    private static final String CRYSTAL_RANGE = "crystal";

    double crystalBuffer = 0;
    double willBuffer = 0;

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

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_WILL, "performRitual - ClonedSoul", "");

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
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_WILL, "performRitual - ClonedSoul", bloodMagicHandler);

        if (bloodMagicHandler != null && bloodMagicHandler.getNumMobs() > 0 && bloodMagicHandler.getWootMobName() != null && !BloodMagicAPI.INSTANCE.getBlacklist().getSacrifice().contains(bloodMagicHandler.getWootMobName().getResourceLocation())) {

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
                else if (te != null)
                    LogHelper.info("Te:" + te);
            }

            // We do not support uniqueness
            int uniqueness = 1;
            double willForUniqueness = Math.max(50 - 15 * Math.sqrt(uniqueness), 0);

            int health = Woot.mobCosting.getMobSpawnCost(world, bloodMagicHandler.getWootMobName());
            if (health > 0) {

                for (int mob = 0; mob < bloodMagicHandler.getNumMobs(); mob++) {

                    // Update the will and crystal buffers
                    double modifier = 1;
                    willBuffer += modifier * willForUniqueness / HEALTH_THRESHOLD * health;
                    crystalBuffer += modifier * health / HEALTH_THRESHOLD;

                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_WILL, "performRitual - ClonedSoul", "willBuffer:" + willBuffer + " crystalBuffer:" + crystalBuffer);

                    totalEffects++;
                    if (totalEffects >= maxEffects)
                        break;
                }

                if (!crystalList.isEmpty() && crystalBuffer > 0) {

                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_WILL, "performRitual - ClonedSoul", "crystals:" + crystalList.size());
                    TileDemonCrystal crystal = crystalList.get(world.rand.nextInt(crystalList.size()));

                    double growth = Math.min(crystalBuffer, 1);
                    double willSyphonAmount = growth * willBuffer / crystalBuffer;
                    double willDrain = growth * willBuffer / crystalBuffer;
                    double progressPercentage = growth;


                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_WILL,
                            "performRitual - ClonedSoul", "growth:" + growth + " willSyphonAmount:" + willSyphonAmount + " willDrain:" + willDrain + " progressPercentage:" + growth);

                    double percentageGrown = crystal.growCrystalWithWillAmount(willDrain, progressPercentage);
                    if (percentageGrown > 0) {
                        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_WILL, "performRitual - ClonedSoul", "grew crystal " + percentageGrown);
                        crystalBuffer -= percentageGrown;
                        willBuffer -= percentageGrown * willSyphonAmount;
                    }
                }
            }
        }

        masterRitualStone.getOwnerNetwork().syphon(getRefreshCost() * totalEffects);

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

        addCornerRunes(components, 4, -1, EnumRuneType.EARTH);
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualClonedSoul();
    }
}
