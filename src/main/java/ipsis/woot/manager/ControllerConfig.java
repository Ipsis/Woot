package ipsis.woot.manager;

public class ControllerConfig {

    String mobName;
    String displayName;

    public ControllerConfig() {

        this(MobManager.INVALID_MOB_NAME);
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

        this.mobName = MobManager.INVALID_MOB_NAME;
        this.displayName = MobManager.INVALID_MOB_NAME;
    }

}
