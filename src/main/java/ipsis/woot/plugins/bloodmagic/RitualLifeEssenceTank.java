package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import ipsis.Woot;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.util.DebugSetup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class RitualLifeEssenceTank extends Ritual {

    private static final String RITUAL_NAME = "ritualLifeEssenceTank";
    private static final int CRYSTAL_LEVEL = 0;
    private static final int ACTIVATION_COST = 40000;
    private static final int REFRESH_COST = 2;
    private static final int REFRESH_TIME = 40;

    /**
     * Same range as the well of suffering
     */
    private static final String HEART_RANGE = "heart";

    private BlockPos heartOffsetPos = new BlockPos(0, 0, 0);

    public RitualLifeEssenceTank() {
        super(RITUAL_NAME, CRYSTAL_LEVEL, ACTIVATION_COST, "ritual." + Reference.MOD_ID + "." + RITUAL_NAME);
        addBlockRange(HEART_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, -10, -10), 21));
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

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "performRitual - LifeEssenceTank", "");

        if (!BloodMagicHelper.canPerformRitual(masterRitualStone, getRefreshCost()))
            return;
        World world = masterRitualStone.getWorldObj();
        IBloodMagicHandler iBloodMagicHandler = findHandler(world, masterRitualStone.getBlockPos());
        if (iBloodMagicHandler != null) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "performRitual - LifeEssenceTank", "keepAlive");
            iBloodMagicHandler.keepAliveTankRitual();
        }

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "performRitual - LifeEssenceTank", "syphon:" + getRefreshCost());
        SoulNetwork network = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
        network.syphon(getRefreshCost());

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

        addCornerRunes(components, 1, 0, EnumRuneType.DUSK);
        addParallelRunes(components, 2, -1, EnumRuneType.FIRE);
        addParallelRunes(components, 2, 0, EnumRuneType.EARTH);
        addCornerRunes(components, 1, -1, EnumRuneType.WATER);
        addCornerRunes(components, 1, 1, EnumRuneType.WATER);
        addParallelRunes(components, 2, 1, EnumRuneType.AIR);
        addParallelRunes(components, 1, -2, EnumRuneType.WATER);
    }

    @Override
    public Ritual getNewCopy() {

        return new RitualLifeEssenceTank();
    }
}
