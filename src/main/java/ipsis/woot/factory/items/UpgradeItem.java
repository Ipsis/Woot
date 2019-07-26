package ipsis.woot.factory.items;

import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.util.WootItem;
import net.minecraft.item.Item;

public class UpgradeItem extends WootItem {

    final FactoryUpgrade upgrade;

    public UpgradeItem(FactoryUpgrade upgrade) {
        super(new Item.Properties(), upgrade.getName());
        this.upgrade = upgrade;
    }

    public FactoryUpgrade getFactoryUpgrade() { return this.upgrade; }
}
