package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.tile.TileAltar;
import ipsis.Woot;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class RitualLifeEssenceAltar extends Ritual {

    private static final String RITUAL_NAME = "ritualLifeEssenceAltar";
    private static final int CRYSTAL_LEVEL = 0;
    private static final int ACTIVATION_COST = 40000;
    private static final int REFRESH_COST = 2;
    private static final int REFRESH_TIME = 40;

    /**
     * Same range as the well of suffering
     */
    private static final String ALTAR_RANGE = "altar";
    private static final String HEART_RANGE = "heart";

    private BlockPos altarOffsetPos = new BlockPos(0, 0, 0);
    private BlockPos heartOffsetPos = new BlockPos(0, 0, 0);


    private TileAltar findAltar(World world, BlockPos pos) {

        BlockPos altarPos = pos.add(altarOffsetPos);
        TileEntity tile = world.getTileEntity(altarPos);

        AreaDescriptor altarRange = getBlockRange(ALTAR_RANGE);

        if (!altarRange.isWithinArea(altarOffsetPos) || !(tile instanceof TileAltar)) {

            for (BlockPos newPos : altarRange.getContainedPositions(pos)) {
                TileEntity nextTile = world.getTileEntity(newPos);
                if (nextTile instanceof TileAltar) {
                    tile = nextTile;
                    altarOffsetPos = newPos.subtract(pos);
                    altarRange.resetCache();
                    break;
                }
            }
        }

        return tile instanceof TileAltar ? (TileAltar)tile : null;
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

    public RitualLifeEssenceAltar() {
        super(RITUAL_NAME, CRYSTAL_LEVEL, ACTIVATION_COST, "ritual." + Reference.MOD_ID + "." + RITUAL_NAME);
        addBlockRange(ALTAR_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-5, -10, -5), 11, 21, 11));
        addBlockRange(HEART_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, -10, -10), 21));

        setMaximumVolumeAndDistanceOfRange(ALTAR_RANGE, 0, 10, 15);
        setMaximumVolumeAndDistanceOfRange(HEART_RANGE, 0, 15, 15);
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "performRitual - LifeEssenceAltar", "");

        if (!BloodMagicHelper.canPerformRitual(masterRitualStone, getRefreshCost()))
            return;

        World world = masterRitualStone.getWorldObj();
        int maxEffects = masterRitualStone.getOwnerNetwork().getCurrentEssence() / getRefreshCost();
        int totalEffects = 0;

        TileAltar tileAltar = findAltar(world, masterRitualStone.getBlockPos());
        IBloodMagicHandler bloodMagicHandler = findHandler(world, masterRitualStone.getBlockPos());

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "performRitual - LifeEssenceAltar", tileAltar + "/" + bloodMagicHandler);

        if (tileAltar != null && bloodMagicHandler != null && bloodMagicHandler.getAltarSacrificeNumMobs() > 0 && bloodMagicHandler.getWootMobName() != null) {

            int lifeEssenceRatio = BloodMagicHelper.getLifeEssenceRatio(bloodMagicHandler.getWootMobName());
            if (lifeEssenceRatio > 0) {

                // We scale the underlying life essence based off the upgrade level
                int p = bloodMagicHandler.getAltarSacrificePercentage();
                int scaledLifeEssenceRatio = (int)(((float)lifeEssenceRatio / 100.0F) * p);

                Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "performRitual - LifeEssenceAltar", bloodMagicHandler.getWootMobName() + "*" + bloodMagicHandler.getAltarSacrificeNumMobs());
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "performRitual - LifeEssenceAltar", "lifeEssenceRatio:" + lifeEssenceRatio + " scaledLifeEssenceRation:" + scaledLifeEssenceRatio + "/" + p);
                for (int c = 0; c < bloodMagicHandler.getAltarSacrificeNumMobs(); c++) {

                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "performRitual - LifeEssenceAltar", "sacrificialDaggerCall:" + scaledLifeEssenceRatio);
                    tileAltar.sacrificialDaggerCall(scaledLifeEssenceRatio, true);
                    totalEffects++;
                    if (totalEffects >= maxEffects)
                        break;
                }
            }

            bloodMagicHandler.clearAltarSacrificeNumMobs();
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
        addCornerRunes(components, 3,  0, EnumRuneType.FIRE);
        addCornerRunes(components, 2,  1, EnumRuneType.AIR);
        addCornerRunes(components, 1,  2, EnumRuneType.WATER);
        addParallelRunes(components, 1, 1, EnumRuneType.DUSK);
        addCornerRunes(components, 1, 0, EnumRuneType.DUSK);
    }

    @Override
    public Ritual getNewCopy() {

        return new RitualLifeEssenceAltar();
    }
}
