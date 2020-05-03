package ipsis.woot.simulator;

import net.minecraft.util.WeightedRandom;

public class DropStackData extends WeightedRandom.Item {

    public int stackSize;
    public DropStackData(int stackSize, int weight) {
        super(weight);
        this.stackSize = stackSize;
    }

    @Override
    public String toString() {
        return "DropStackData{" +
                "stackSize=" + stackSize +
                ", itemWeight=" + itemWeight +
                '}';
    }
}
