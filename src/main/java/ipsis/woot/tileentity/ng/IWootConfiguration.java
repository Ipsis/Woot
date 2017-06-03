package ipsis.woot.tileentity.ng;

import ipsis.woot.manager.ConfigManager;

public interface IWootConfiguration {

    boolean getBoolean(ConfigManager.EnumConfigKey key);
    boolean getBoolean(WootMobName wootMobName, ConfigManager.EnumConfigKey key);

    int getInteger(ConfigManager.EnumConfigKey key);
    int getInteger(WootMobName wootMobName, ConfigManager.EnumConfigKey key);

    void setBoolean(ConfigManager.EnumConfigKey key, boolean v);
    void setBoolean(WootMobName wootMobName, ConfigManager.EnumConfigKey key, boolean v);

    void setInteger(ConfigManager.EnumConfigKey key, int v);
    void setInteger(WootMobName wootMobName, ConfigManager.EnumConfigKey key, int v);
    void setInteger(WootMobName wootMobName, String key, int v);

}
