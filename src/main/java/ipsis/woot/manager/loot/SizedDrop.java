package ipsis.woot.manager.loot;

public class SizedDrop implements Comparable<SizedDrop>{

    int count;
    int stackSize;

    public SizedDrop(int count, int stackSize) {

        this.count = count;
        this.stackSize = stackSize;
    }

    public float getProbability(int sampleSize) {

        if (sampleSize == 0)
            return 0.0F;

        return ((float)count/(float)sampleSize) * 100.0F;
    }

    public boolean isHit(int sampleSize, float v) {

        if (sampleSize == 0)
            return false;

        return v <= ((float)count/(float)sampleSize);
    }

    public int getCount() {

        return count;
    }

    public int getStackSize() {

        return stackSize;
    }

    public void incCount() {

        count++;
    }

    @Override
    public int compareTo(SizedDrop o) {

        return Float.compare(this.stackSize, o.stackSize);
    }

    @Override
    public String toString() {

        return "stackSize:" + stackSize + " count:" + count;
    }
}
