package ipsis.woot.factory;

import ipsis.woot.util.FakeMob;

import java.util.HashMap;

/**
 * Defines the valid configuration of the formed factory.
 * It already takes into account any blacklisted upgrades etc.
 */
public class Setup {

    public Tier tier;
    public HashMap<Upgrade, Integer> upgrades = new HashMap<>();
    public FakeMob fakeMob;

    Setup() { };

    public Setup(FakeMob fakeMob, Tier tier) {
        this.tier = tier;
        this.fakeMob = fakeMob;
    }

}
