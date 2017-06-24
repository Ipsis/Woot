package ipsis.woot.plugins.bloodmagic;

//import WayofTime.bloodmagic.api.ritual.*;
//import WayofTime.bloodmagic.api.saving.SoulNetwork;
//import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
//import WayofTime.bloodmagic.tile.TileAltar;


/**
 * This is essentially the Well Of Suffering, except
 * the mobs come from the factory, not the surrounding area.
 */
//public class RitualInfernalMachine extends Ritual {
//
//    private static final String RITUAL_INFERNAL_MACHINE = "ritualInfernalMachine";
//    private static final int CRYSTAL_LEVEL = 0;
//    private static final int ACTIVATION_COST = 40000;
//
//    // Same range as the Well Of Suffering
//    public static final String ALTAR_RANGE = "altar";
//    public static final String FACTORY_RANGE = "factory";
//
//    public RitualInfernalMachine() {
//
//        super(RITUAL_INFERNAL_MACHINE, CRYSTAL_LEVEL, ACTIVATION_COST, "ritual.Woot:" + RITUAL_INFERNAL_MACHINE);
//
//        addBlockRange(ALTAR_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-5, -10, -5), 11, 21, 11));
//        addBlockRange(FACTORY_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, -10, -10), 21));
//    }
//
//    public BlockPos altarOffsetPos = new BlockPos(0, 0, 0);
//    public BlockPos factoryOffsetPos = new BlockPos(0, 0, 0);
//
//    private boolean isValidFactory(TileEntity te) {
//
//        if (te instanceof TileEntityMobFactory) {
//            TileEntityMobFactory factory = (TileEntityMobFactory) te;
//            if (factory.isFormed() && factory.isRunning())
//                return true;
//        }
//
//        return false;
//    }
//
//    private TileEntityMobFactory findFactory(World world, BlockPos pos) {
//
//        BlockPos factoryPos = pos.add(factoryOffsetPos);
//        TileEntity tile = world.getTileEntity(factoryPos);
//
//        AreaDescriptor factoryRange = getBlockRange(FACTORY_RANGE);
//
//        if (!factoryRange.isWithinArea(factoryOffsetPos) || !isValidFactory(tile)) {
//
//            for (BlockPos newPos : factoryRange.getContainedPositions(pos)) {
//                TileEntity nextTile = world.getTileEntity(newPos);
//                if (isValidFactory(nextTile)) {
//                    tile = nextTile;
//                    factoryOffsetPos = newPos.subtract(pos);
//                    factoryRange.resetCache();
//                    break;
//                }
//            }
//        }
//
//        return isValidFactory(tile) ? (TileEntityMobFactory)tile : null;
//    }
//
//    private TileAltar findAltar(World world, BlockPos pos) {
//
//        BlockPos altarPos = pos.add(altarOffsetPos);
//        TileEntity tile = world.getTileEntity(altarPos);
//
//        AreaDescriptor altarRange = getBlockRange(ALTAR_RANGE);
//
//        if (!altarRange.isWithinArea(altarOffsetPos) || !(tile instanceof TileAltar)) {
//
//            for (BlockPos newPos : altarRange.getContainedPositions(pos)) {
//                TileEntity nextTile = world.getTileEntity(newPos);
//                if (nextTile instanceof TileAltar) {
//                    tile = nextTile;
//                    altarOffsetPos = newPos.subtract(pos);
//                    altarRange.resetCache();
//                    break;
//                }
//            }
//        }
//
//        return tile instanceof TileAltar ? (TileAltar)tile : null;
//    }
//
//    private int fillAltar(TileAltar te, int mobCount, int sacrificeAmount, int maxEffects) {
//
//        int totalEffects = 0;
//        for (int m = 0; m < mobCount; m++) {
//
//            te.sacrificialDaggerCall(sacrificeAmount, true);
//            totalEffects++;
//            if (totalEffects >= maxEffects)
//                break;
//        }
//
//        return totalEffects;
//    }
//
//    @Override
//    public void performRitual(IMasterRitualStone masterRitualStone) {
//
//        World world = masterRitualStone.getWorldObj();
//        SoulNetwork network = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
//        int currentEssence = network.getCurrentEssence();
//        int maxEffects = currentEssence / getRefreshCost();
//
//        if (currentEssence < getRefreshCost()) {
//            network.causeNausea();
//            return;
//        }
//
//        int effects = 1;
//        TileEntityMobFactory teFactory = findFactory(world, masterRitualStone.getBlockPos());
//        if (teFactory != null) {
//
//            teFactory.bmKeepAlive();
//            if (teFactory.bmGetMobCount() > 0) {
//
//                TileAltar teAltar = findAltar(world, masterRitualStone.getBlockPos());
//                if (teAltar != null) {
//                    effects = fillAltar(teAltar, teFactory.bmGetMobCount(), teFactory.bmGetSacrificeAmount(), maxEffects);
//                }
//                teFactory.bmClear();
//            }
//        }
//
//        /* Syphon network */
//        network.syphon(getRefreshCost() * effects);
//    }
//
//    @Override
//    public int getRefreshCost() {
//        return 2;
//    }
//
//    @Override
//    public int getRefreshTime() {
//        return 40;
//    }
//
//    @Override
//    public ArrayList<RitualComponent> getComponents() {
//
//        ArrayList<RitualComponent> components = new ArrayList<RitualComponent>();
//
//        this.addCornerRunes(components, 4, -1, EnumRuneType.EARTH);
//        this.addCornerRunes(components, 3,  0, EnumRuneType.FIRE);
//        this.addCornerRunes(components, 2,  1, EnumRuneType.AIR);
//        this.addCornerRunes(components, 1,  2, EnumRuneType.WATER);
//        this.addParallelRunes(components, 1, 1, EnumRuneType.DUSK);
//        this.addCornerRunes(components, 1, 0, EnumRuneType.DUSK);
//
//        return components;
//    }
//
//    @Override
//    public Ritual getNewCopy() {
//
//        return new RitualInfernalMachine();
//    }
//}
