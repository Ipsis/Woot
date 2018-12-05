package ipsis.woot.heart;

import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.FakeMobKey;

public class HeartUIFixedInfo {

    public FactoryTier tier = FactoryTier.TIER_1;
    public FakeMobKey fakeMobKey = new FakeMobKey();
    public int recipeTicks = -1;
    public int recipeUnits = -1;

    private boolean formed = false;
    public boolean isFormed() { return formed; }
    public void setFormed() { formed = true; }


}
