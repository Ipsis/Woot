package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.api.ritual.EnumRuneType;
import WayofTime.bloodmagic.api.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.api.ritual.Ritual;
import WayofTime.bloodmagic.api.ritual.RitualComponent;
import WayofTime.bloodmagic.api.saving.SoulNetwork;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import ipsis.woot.tileentity.TileEntityMobFactory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RitualInfernalMachine extends Ritual {

    private static final String RITUAL_INFERNAL_MACHINE = "ritualInfernalMachine";
    private static final int CRYSTAL_LEVEL = 0;
    private static final int ACTIVATION_COST = 40000;
    private static final int FACTORY_OFFSET_Y = 6;

    public RitualInfernalMachine() {

        super(RITUAL_INFERNAL_MACHINE, CRYSTAL_LEVEL, ACTIVATION_COST, "ritual.Woot:" + RITUAL_INFERNAL_MACHINE);
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {

        World world = masterRitualStone.getWorldObj();
        SoulNetwork network = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
        int currentEssence = network.getCurrentEssence();

        if (currentEssence < getRefreshCost()) {
            network.causeNausea();
            return;
        }

        /**
         * Find the factory and poke it
         */
        BlockPos pos = masterRitualStone.getBlockPos();
        BlockPos factoryPos = pos.offset(EnumFacing.DOWN, FACTORY_OFFSET_Y);
        TileEntity te = world.getTileEntity(factoryPos);
        if (te != null && te instanceof TileEntityMobFactory)
            ((TileEntityMobFactory) te).bmKeepAlive();
    }

    @Override
    public int getRefreshCost() {
        return 2;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {

        ArrayList<RitualComponent> components = new ArrayList<RitualComponent>();

        this.addCornerRunes(components, 4, -1, EnumRuneType.EARTH);
        this.addCornerRunes(components, 3,  0, EnumRuneType.FIRE);
        this.addCornerRunes(components, 2,  1, EnumRuneType.AIR);
        this.addCornerRunes(components, 1,  2, EnumRuneType.WATER);
        this.addParallelRunes(components, 1, 1, EnumRuneType.DUSK);
        this.addCornerRunes(components, 1, 0, EnumRuneType.DUSK);

        return components;
    }

    @Override
    public Ritual getNewCopy() {

        return new RitualInfernalMachine();
    }
}
