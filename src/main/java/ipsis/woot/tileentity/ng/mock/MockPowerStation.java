package ipsis.woot.tileentity.ng.mock;

import ipsis.woot.tileentity.ng.IPowerStation;

public class MockPowerStation implements IPowerStation {

    @Override
    public boolean consume(int power) {

        return true;
    }
}
