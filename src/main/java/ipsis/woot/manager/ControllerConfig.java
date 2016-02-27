package ipsis.woot.manager;

public class ControllerConfig {

    String mobName;
    String displayName;

    public ControllerConfig() {

        this(MobRegistry.INVALID_MOB_NAME);
    }

    public ControllerConfig(String mobName) {

        this(mobName, mobName);
    }

    public ControllerConfig(String mobName, String displayName) {

        this.mobName = mobName;
        this.displayName = displayName;
    }

    public String getMobName() {

        return mobName;
    }

    public String getDisplayName() {

        return displayName;
    }

    public void setMobName(String mobName) {

        setMobName(mobName, mobName);
    }

    public void setMobName(String mobName, String displayName) {

        this.mobName = mobName;
        this.displayName = displayName;
    }

    public void clearMobName() {

        this.mobName = MobRegistry.INVALID_MOB_NAME;
        this.displayName = MobRegistry.INVALID_MOB_NAME;
    }

}
