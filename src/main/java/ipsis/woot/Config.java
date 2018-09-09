package ipsis.woot;

import ipsis.woot.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

// As I understand it 1.13 is going to change the config file format (TOML?)
// So this is temporary until that is available
// Plus I have my own config anyway via JSON, which might fit better into TOML

public class Config {

    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
        } finally {
            if (cfg.hasChanged())
                cfg.save();
        }
    }

    private static void initGeneralConfig(Configuration cfg) {

    }
}
