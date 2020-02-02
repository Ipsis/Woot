package ipsis.woot.modules.factory.calculators;

import ipsis.woot.config.*;
import ipsis.woot.config.ConfigOverride;
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
        spawnTicks = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.SPAWN_TICKS);
        healthPoints = SpawnController.get().getMobHealth(fakeMob, world);

        massCount = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.MASS_COUNT);
        if (setup.getPerks().containsKey(PerkType.MASS)) {
            int level = setup.getPerks().getOrDefault(PerkType.MASS, 0);
            if (level > 0)
                massCount = Config.OVERRIDE.getIntegerOrDefault(fakeMob, Config.OVERRIDE.getKeyByPerk(PerkType.MASS, level));
        }

        unitsPerHealthPoint = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.UNITS_PER_HEALTH);
    }

    public int getCost() {
        if (Config.OVERRIDE.hasOverride(fakeMob, ConfigOverride.OverrideKey.FIXED_COST))
            return Config.OVERRIDE.getInteger(fakeMob, ConfigOverride.OverrideKey.FIXED_COST);
        return healthPoints * unitsPerHealthPoint;
    }
}
