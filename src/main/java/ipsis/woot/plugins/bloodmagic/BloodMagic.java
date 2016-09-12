package ipsis.woot.plugins.bloodmagic;

import WayofTime.bloodmagic.api.registry.RitualRegistry;
import WayofTime.bloodmagic.api.ritual.Ritual;

public class BloodMagic {

    public static Ritual infernalMachineRitual;
    public static void init() {

        infernalMachineRitual = new RitualInfernalMachine();
        RitualRegistry.registerRitual(infernalMachineRitual);
    }
}
