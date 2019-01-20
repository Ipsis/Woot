package ipsis.woot.items;

import ipsis.woot.util.WootItem;

public class ItemEnchantedPlate extends WootItem {

    private final String basename;
    private final int level;

    public ItemEnchantedPlate(int level, String basename) {
        super(basename);
        this.level = level;
        this.basename = basename;
    }

    public int getLevel() { return this.level; }
}
