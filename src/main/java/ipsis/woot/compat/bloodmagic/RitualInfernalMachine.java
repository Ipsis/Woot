package ipsis.woot.compat.bloodmagic;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.util.FakeMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wayoftime.bloodmagic.impl.BloodMagicAPI;
import wayoftime.bloodmagic.ritual.*;
import wayoftime.bloodmagic.tile.TileAltar;

import java.util.function.Consumer;

/**
 * This is essentially the Well Of Suffering, but uses the factory instead of "real mobs"
 * Ritual only requires a running factory
 */

@RitualRegister("ritualinfernalmachine")
public class RitualInfernalMachine extends Ritual {

    public static final String ALTAR_RANGE = "altar";
    public static final String HEART_RANGE = "heart";
    public static final String NAME = "ritualInfernalMachine";
    public static final int SACRIFICE_AMOUNT = 25;

    private BlockPos altarOffsetPos = new BlockPos(0, 0, 0);
    private BlockPos heartOffsetPos = new BlockPos(0, 0, 0);

    public RitualInfernalMachine() {
        super(NAME, 0, 40000, "ritual." + Woot.MODID + "." + NAME);
        addBlockRange(ALTAR_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-5, -10, -5), 11, 21, 11));
        addBlockRange(HEART_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, -10, -10), 21));

        setMaximumVolumeAndDistanceOfRange(ALTAR_RANGE, 0, 10, 15);
        setMaximumVolumeAndDistanceOfRange(HEART_RANGE, 0, 15, 15);
    }

    @Override
    public void performRitual(IMasterRitualStone iMasterRitualStone) {

        //Woot.setup.getLogger().debug("performRitual");
        World world = iMasterRitualStone.getWorldObj();
        int currEssence = iMasterRitualStone.getOwnerNetwork().getCurrentEssence();
        if (currEssence < getRefreshCost()) {
            iMasterRitualStone.getOwnerNetwork().causeNausea();
            return;
        }

        BlockPos ritualPos = iMasterRitualStone.getMasterBlockPos();

        int maxEffects = currEssence / getRefreshCost();
        int totalEffects = 0;

        /**
         * Find the altar
         */
        BlockPos altarPos = ritualPos.add(altarOffsetPos);
        TileEntity altarTile = world.getTileEntity(altarPos);
        AreaDescriptor altarRange = iMasterRitualStone.getBlockRange(ALTAR_RANGE);
        if (!altarRange.isWithinArea(altarOffsetPos) || !(altarTile instanceof TileAltar)) {
            for (BlockPos pos : altarRange.getContainedPositions(ritualPos)) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof TileAltar) {
                    //Woot.setup.getLogger().debug("Found altar");
                    altarTile = tile;
                    altarOffsetPos = pos.subtract(ritualPos);
                    altarRange.resetCache();
                    break;
                }
            }
        }

        if (altarTile instanceof TileAltar) {

            /**
             * Find the heart
             */
            BlockPos heartPos = ritualPos.add(heartOffsetPos);
            TileEntity heartTile = world.getTileEntity(heartPos);
            AreaDescriptor heartRange = iMasterRitualStone.getBlockRange(HEART_RANGE);
            if (!heartRange.isWithinArea(heartOffsetPos) || !(heartTile instanceof HeartTileEntity)) {
                for (BlockPos pos : heartRange.getContainedPositions(ritualPos)) {
                    TileEntity tile = world.getTileEntity(pos);
                    if (tile instanceof HeartTileEntity && ((HeartTileEntity) tile).isFormed()) {
                        //Woot.setup.getLogger().debug("Found formed factory");
                        heartTile = tile;
                        heartOffsetPos = pos.subtract(ritualPos);
                        heartRange.resetCache();
                        break;
                    }
                }
            }

            if (heartTile instanceof HeartTileEntity) {

                HeartTileEntity heart = (HeartTileEntity)heartTile;
                TileAltar altar = (TileAltar)altarTile;
                FormedSetup formedSetup = heart.getFormedSetup();
                if (formedSetup != null && heart.couldFinish() && heart.hasFlayedPerk()) {
                    for (FakeMob fakeMob : heart.getFormedMobs()) {

                        int count = formedSetup.getAllMobParams().get(fakeMob).getMobCount(formedSetup.getAllPerks().containsKey(Perk.Group.MASS), formedSetup.hasMassExotic());
                        if (count == 0)
                            continue;

                        if (BloodMagicAPI.INSTANCE.getBlacklist().getSacrifice().contains(fakeMob.getResourceLocation()))
                            continue;

                        int ratio = BloodMagicAPI.INSTANCE.getValueManager().getSacrificial().getOrDefault(fakeMob.getResourceLocation(), SACRIFICE_AMOUNT);
                        if (ratio <= 0)
                            continue;

                        //Woot.setup.getLogger().debug("Sacrifice {} {} {}", count, fakeMob, ratio);
                        for (int i = 0; i < count; i++)
                            altar.sacrificialDaggerCall(ratio, true);

                        // Each mob type is only one effect
                        totalEffects++;
                        if (totalEffects >= maxEffects)
                            break;

                    }
                }
            }
        }

        iMasterRitualStone.getOwnerNetwork().syphon(iMasterRitualStone.ticket(getRefreshCost() * totalEffects));
    }

    @Override
    public int getRefreshCost() {
        return 2;
    }

    @Override
    public void gatherComponents(Consumer<RitualComponent> consumer) {

        addCornerRunes(consumer, 4, -1, EnumRuneType.EARTH);
        addCornerRunes(consumer, 3,  0, EnumRuneType.FIRE);
        addCornerRunes(consumer, 2,  1, EnumRuneType.AIR);
        addCornerRunes(consumer, 1,  2, EnumRuneType.WATER);
        addParallelRunes(consumer, 1, 1, EnumRuneType.DUSK);
        addCornerRunes(consumer, 1, 0, EnumRuneType.DUSK);
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualInfernalMachine();
    }
}
