package ipsis.woot.util;

import ipsis.woot.oss.LogHelper;

public class DebugSetup {

    private boolean traceLootEvents = false;
    private boolean needPower = true;

    public void setTraceLootEvents(boolean b) {

        if (this.traceLootEvents != b) {
            this.traceLootEvents = b;
            LogHelper.info("Tracing loot events: " + b);
        }
    }

    public boolean getTraceLootEvents() { return traceLootEvents; }

    public void setNeedPower(boolean b) {

        if (this.needPower != b) {
            this.needPower = b;
            LogHelper.info("Need power: " + b);
        }
    }

    public boolean getNeedPower() { return needPower; }
}
