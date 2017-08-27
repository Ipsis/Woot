package ipsis.woot.util;

public class WootMob {

    public static String UNKNOWN_MOB = "Unknown Mob";


    public WootMobName getWootMobName() {
        return wootMobName;
    }

    private WootMobName wootMobName;

    public String getDisplayName() {
        return displayName;
    }

    private String displayName = UNKNOWN_MOB;

    public int getDeaths() { return deaths; }

    private int deaths = 0;

    public WootMob(WootMobName wootMobName, String displayName) {
        this(wootMobName, displayName, 0);
    }

    public WootMob(WootMobName wootMobName, String displayName, int deaths) {
        this.wootMobName = wootMobName;
        this.displayName = displayName;
        this.deaths = deaths;
    }

    public WootMob() {

        this.wootMobName = new WootMobName();
    }

    public boolean isValid() {

        return wootMobName.isValid();
    }

    public void incrementDeathCount(int count) {

        this.deaths += count;
    }

    @Override
    public String toString() {

        return displayName + ":" + deaths + ":" + wootMobName;
    }
}
