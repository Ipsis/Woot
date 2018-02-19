package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.ritual.data.AreaDescriptor;
import WayofTime.bloodmagic.ritual.data.IMasterRitualStone;
import WayofTime.bloodmagic.ritual.data.Ritual;
import WayofTime.bloodmagic.ritual.data.RitualComponent;
import WayofTime.bloodmagic.tile.TileAltar;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * This is the Well Of Suffering, except the mobs come from the factory, no the surrounding areas.
 */
public class RitualA extends Ritual {

    private static final String RITUAL_NAME = "ritualA";
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

    public RitualA() {
        super(RITUAL_NAME, CRYSTAL_LEVEL, ACTIVATION_COST, "ritual." + Reference.MOD_ID + "." + RITUAL_NAME);
        addBlockRange(ALTAR_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-5, -10, -5), 11, 21, 11));
        addBlockRange(HEART_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, -10, -10), 21));
    }

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

    private TileEntityMobFactoryHeart findHeart(World world, BlockPos pos) {

        BlockPos heartPos = pos.add(heartOffsetPos);
        TileEntity te = world.getTileEntity(heartPos);

        AreaDescriptor heartRange = getBlockRange(HEART_RANGE);

        if (!heartRange.isWithinArea(heartOffsetPos) || !isValidFactory(te)) {

            for (BlockPos newPos : heartRange.getContainedPositions(pos)) {
                TileEntity nextTile = world.getTileEntity(newPos);
                if (nextTile instanceof TileEntityMobFactoryHeart) {
                    te = nextTile;
                    heartOffsetPos = newPos.subtract(pos);
                    heartRange.resetCache();
                    break;
                }
             }
        }

        return isValidFactory(te) ? (TileEntityMobFactoryHeart)te : null;
    }


    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {

        World world = masterRitualStone.getWorldObj();
        SoulNetwork network = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
        int currentEssence = network.getCurrentEssence();
        int maxEffects = currentEssence / getRefreshCost();

        if (currentEssence < getRefreshCost()) {
            network.causeNausea();
            return;
        }

        int effects = 1;

        network.syphon(getRefreshCost() * effects);

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
    public ArrayList<RitualComponent> getComponents() {

        ArrayList<RitualComponent> components = new ArrayList<>();

        return components;
    }

    @Override
    public Ritual getNewCopy() {


        return new RitualA();
    }
}
