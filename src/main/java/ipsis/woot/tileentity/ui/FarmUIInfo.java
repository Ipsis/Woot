package ipsis.woot.tileentity.ui;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.WootMob;

public class FarmUIInfo {

    public EnumMobFactoryTier tier;
    public WootMob wootMob;
    public boolean isRunning;
    public int recipeTotalPower;
    public int recipeTotalTime;
    public int recipePowerPerTick;
    public int consumedPower;
    public boolean isValid = false;

    public void setValid() {

        this.isValid = true;
    }

    public boolean isValid() {

        return this.isValid;
    }
}
