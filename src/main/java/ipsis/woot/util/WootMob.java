package ipsis.woot.util;

public class WootMob {

    public static int UNKNOWN_XP = -1;
    public static String UNKNOWN_MOB = "Unknown Mob";

    public WootMobName getWootMobName() {
        return wootMobName;
    }

    private WootMobName wootMobName;

    public String getDisplayName() {
        return displayName;
    }

    private String displayName = UNKNOWN_MOB;

    public int getXpValue() {
        return xpValue;
    }

    public void setXpValue(int xpValue) {
        this.xpValue = xpValue;
    }

    private int xpValue = UNKNOWN_XP;

    public WootMob(WootMobName wootMobName, String displayName) {
        this.wootMobName = wootMobName;
        this.displayName = displayName;
    }

    public WootMob() {

        this.wootMobName = new WootMobName();
    }

    public boolean isValid() {

        return wootMobName.isValid();
    }
}
