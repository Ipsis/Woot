package ipsis.woot.config;

import ipsis.woot.Woot;
import ipsis.woot.policy.PolicyConfiguration;
import ipsis.woot.util.FakeMob;

public class OverrideLoader {

    public static void loadFromConfig() {

        for (String s : PolicyConfiguration.MOB_OVERRIDES.get()) {
            String[] parts = s.split(",");
            if (parts.length != 3) {
                Woot.setup.getLogger().error(s + " == INVALID");
            } else {
                String mob = parts[0];
                String ovd = parts[1];
                if (ConfigOverride.OverrideKey.isValidTag(ovd)) {
                    ConfigOverride.OverrideKey key = ConfigOverride.OverrideKey.getFromString(ovd);
                    FakeMob fakeMob = new FakeMob((mob));
                    if (!fakeMob.isValid()) {
                        Woot.setup.getLogger().error(s + " == INVALID (mob {})", mob);
                    } else {
                        if (key.getClazz() == Integer.class) {
                            try {
                                int v = Integer.valueOf(parts[2]);
                                Config.OVERRIDE.add(fakeMob, key, v);
                            } catch (NumberFormatException e) {
                                Woot.setup.getLogger().error(s + " == INVALID (integer value {})", parts[2]);
                            }
                        }
                    }

                } else {
                    Woot.setup.getLogger().error(s + " == INVALID (override {})", ovd);
                }
            }
        }
    }
}
