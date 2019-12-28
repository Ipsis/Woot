package ipsis.woot.common.configuration;

import ipsis.woot.config.MobOverride;
import ipsis.woot.config.WootConfig;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.policy.PolicyConfiguration;
import ipsis.woot.util.FakeMob;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Woot has config values that can have an override for specific mobs.
 * Therefore configuration should not be pulled from here but from
 *
 * WootConfig and
 * ConfigHelper
 */

public class Config {

    static final Logger LOGGER = LogManager.getLogger();

    public static class Common {


        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configuration settings")
                    .push("common");

            String TAG2 = "";

            builder.push("factory");
            {

                builder.push("upgrades");
                {
                }
                builder.pop(); // upgrades
            }
            builder.pop(); // factory
            builder.pop(); // common
        }
    }

    public static class Client {

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client configuration settings")
                    .push("client");

            builder.pop();
        }
    }

    public static final ForgeConfigSpec CLIENT_CONFIG;
    public static final Client CLIENT;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_CONFIG = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_CONFIG = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
