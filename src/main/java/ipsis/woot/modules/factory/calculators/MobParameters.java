package ipsis.woot.modules.factory.calculators;

import ipsis.woot.config.ConfigHelper;
import ipsis.woot.config.WootConfig;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.Setup;
import ipsis.woot.modules.simulation.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import net.minecraft.world.World;

public class MobParameters {

    public FakeMob fakeMob;
    public int spawnTicks;
    public int healthPoints;
    public int massCount;
    public int unitsPerHealthPoint;

    /**
     * A lot of these parameters can use mob specific overrides
     */
    public MobParameters(FakeMob fakeMob, Setup setup, World world) {
        this.fakeMob = fakeMob;
        spawnTicks = WootConfig.get().getIntConfig(fakeMob, WootConfig.ConfigKey.SPAWN_TICKS);
        healthPoints = SpawnController.get().getMobHealth(fakeMob, world);
        massCount = ConfigHelper.getIntValueForPerk(
                fakeMob,
                PerkType.MASS,
                setup.getPerks().getOrDefault(PerkType.MASS, 0));
        unitsPerHealthPoint = WootConfig.get().getIntConfig(fakeMob, WootConfig.ConfigKey.UNITS_PER_HEALTH);

        // TODO check for master override of this mob to a specific conatus fluid amount
    }

    public int getCost() { return healthPoints * unitsPerHealthPoint; }
}
