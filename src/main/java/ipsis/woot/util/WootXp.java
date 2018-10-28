package ipsis.woot.util;

public class WootXp {

    private int vanillaXp = 0;
    private static final int XP_CHUNKS = 16;

    public WootXp incrementByVanilla(int vanillaXp) {
        vanillaXp += vanillaXp;
        return this;
    }

    public int consumeXpChunks() {
        int chunks = vanillaXp / XP_CHUNKS;
        vanillaXp = vanillaXp - (chunks * XP_CHUNKS);
        return chunks;
    }

    public int getVanillaXp() { return this.vanillaXp; }
}
