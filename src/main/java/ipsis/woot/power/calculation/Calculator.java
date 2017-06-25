package ipsis.woot.power.calculation;

import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.farming.PowerRecipe;

import java.util.ArrayList;
import java.util.List;

public class Calculator implements IPowerCalculator {

    private List<AbstractUpgradePowerCalc> powerCalcList = new ArrayList<>();

    public Calculator() {

        powerCalcList.add(new UpgradePowerCalcMass());
    }

    /**
     * IPowerCalculator
     */
    @Override
    public PowerRecipe calculate(IFarmSetup farmSetup) {

        int upgradePowerPerTick = 0;

        for (AbstractUpgradePowerCalc calc : powerCalcList)
            upgradePowerPerTick += calc.calculate(farmSetup);

        return new PowerRecipe(320, 320);
    }
}
