package ipsis.woot.manager.loot;

import ipsis.woot.manager.EnumEnchantKey;

import java.util.ArrayList;
import java.util.List;

public class LootPool {

    private EnumEnchantKey key;
    int samples;
    List<Drop> drops;

    public LootPool(EnumEnchantKey key) {
        this(key, 0, new ArrayList<Drop>());
    }

    public LootPool(EnumEnchantKey key, int samples, List<Drop> drops) {
        this.key = key;
        this.samples = samples;
        this.drops = drops;
    }
}
