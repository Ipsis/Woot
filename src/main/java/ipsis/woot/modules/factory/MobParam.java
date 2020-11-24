package ipsis.woot.modules.factory;

/**
 * Mob specific values
 */
public class MobParam {
    private static final int MOB_PARAM_UNDEFINED = -1;
    public int baseSpawnTicks;
    public int baseMassCount;
    public int baseFluidCost;

    // Perk values
    private int perkEfficiencyValue = MOB_PARAM_UNDEFINED;
    private int perkMassValue = MOB_PARAM_UNDEFINED;
    private int perkRateValue = MOB_PARAM_UNDEFINED;
    private int perkXpValue = MOB_PARAM_UNDEFINED;
    private int perkHeadlessValue = MOB_PARAM_UNDEFINED;

    // Mass has a value regardless of perk
    public int getMobCount(boolean hasMassPerk, boolean hasMassExotic) {
        if (hasMassExotic)
            return FactoryConfiguration.EXOTIC_E.get();

        return hasMassPerk ? perkMassValue : baseMassCount;
    }

    public boolean hasPerkEfficiencyValue() { return perkEfficiencyValue != MOB_PARAM_UNDEFINED; }
    public boolean hasPerkRateValue() { return perkRateValue != MOB_PARAM_UNDEFINED; }
    public boolean hasPerkXpValue() { return perkXpValue != MOB_PARAM_UNDEFINED; }
    public boolean hasPerkHeadlessValue() { return perkHeadlessValue != MOB_PARAM_UNDEFINED; }

    public int getPerkEfficiencyValue() { return perkEfficiencyValue; }
    public int getPerkRateValue() { return perkRateValue; }
    public int getPerkXpValue() { return perkXpValue; }
    public int getPerkHeadlessValue() { return perkHeadlessValue; }

    public void setPerkEfficiencyValue(int v) { perkEfficiencyValue = v;}
    public void setPerkMassValue(int v) { perkMassValue = v;}
    public void setPerkRateValue(int v) { perkRateValue = v;}
    public void setPerkXpValue(int v) { perkXpValue = v;}
    public void setPerkHeadlessValue(int v) { perkHeadlessValue = v; }

    @Override
    public String toString() {
        return "MobParam{" +
                "baseSpawnTicks=" + baseSpawnTicks +
                ", baseMassCount=" + baseMassCount +
                ", baseFluidCost=" + baseFluidCost +
                ", perkEfficiencyValue=" + perkEfficiencyValue +
                ", perkMassValue=" + perkMassValue +
                ", perkRateValue=" + perkRateValue +
                ", perkXpValue=" + perkXpValue +
                ", perkHeadlessValue=" + perkHeadlessValue +
                '}';
    }
}
