package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.api.ritual.EnumRuneType;
import WayofTime.bloodmagic.api.ritual.IMasterRitualStone;
import WayofTime.bloodmagic.api.ritual.Ritual;
import WayofTime.bloodmagic.api.ritual.RitualComponent;
import WayofTime.bloodmagic.api.saving.SoulNetwork;
import WayofTime.bloodmagic.api.util.helper.NetworkHelper;
import ipsis.woot.oss.LogHelper;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RitualInfernalMachine extends Ritual {

    private static final String RITUAL_INFERNAL_MACHINE = "ritualInfernalMachine";
    private static final int CRYSTAL_LEVEL = 0;
    private static final int ACTIVATION_COST = 40000;

    public RitualInfernalMachine() {

        super(RITUAL_INFERNAL_MACHINE, CRYSTAL_LEVEL, ACTIVATION_COST, "ritual.Woot." + RITUAL_INFERNAL_MACHINE);
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

        LogHelper.info("performRitual");

    }

    @Override
    public int getRefreshCost() {
        return 2;
    }

    @Override
    public ArrayList<RitualComponent> getComponents() {

        ArrayList<RitualComponent> components = new ArrayList<RitualComponent>();

        this.addParallelRunes(components, 1, 0, EnumRuneType.FIRE);
        this.addParallelRunes(components, 1, 1, EnumRuneType.WATER);

        return components;
    }

    @Override
    public Ritual getNewCopy() {

        return new RitualInfernalMachine();
    }
}
