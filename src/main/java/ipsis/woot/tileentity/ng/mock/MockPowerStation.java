package ipsis.woot.tileentity.ng.mock;

import ipsis.woot.tileentity.ng.IPowerStation;

public class MockPowerStation implements IPowerStation {

    @Override
    public int consume(int power) {

        return power;
    }
}
