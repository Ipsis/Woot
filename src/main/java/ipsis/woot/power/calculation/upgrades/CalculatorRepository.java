package ipsis.woot.power.calculation.upgrades;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.DebugSetup;

import java.util.ArrayList;
import java.util.List;

public class CalculatorRepository {

    private List<IUpgradePowerCalculator> calculators = new ArrayList<>();

    public CalculatorRepository() {

        calculators.add(new Decapitate());
        calculators.add(new Efficiency());
        calculators.add(new Looting());
        calculators.add(new Mass());
        calculators.add(new Rate());
        calculators.add(new Xp());
        calculators.add(new BloodMagicAltar());
        calculators.add(new BloodMagicTank());
        calculators.add(new BloodMagicCrystal());
        calculators.add(new EvilCraftBlood());
    }

    public List<IUpgradePowerCalculator> getAllCalculators() {

        return calculators;
    }

    public List<IUpgradePowerCalculator> getActiveCalculators(IFarmSetup iFarmSetup) {

        List<IUpgradePowerCalculator> calculatorList = new ArrayList<>();
        for (IUpgradePowerCalculator c : calculators)
            if (c.isActive(iFarmSetup)) {
                Woot.debugSetup.trace(DebugSetup.EnumDebugType.POWER_CALC, "getActiveCalculators", "added:" + c);
                calculatorList.add(c);
            }

        return calculatorList;
    }
}
