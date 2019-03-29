package ipsis.woot.factory;

import ipsis.woot.Woot;
import ipsis.woot.util.WootItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ItemUpgrade extends WootItem {

    private final String basename;
    private final UpgradeType upgradeType;

    public ItemUpgrade(UpgradeType upgradeType) {
        super(new Item.Properties().group(Woot.TAB_WOOT), upgradeType.getName());
        this.basename = upgradeType.getName();
        this.upgradeType = upgradeType;
    }

    public UpgradeType getUpgradeType() { return this.upgradeType; }

    public enum UpgradeType {
        LOOTING_1("upgrade_looting_1"),
        LOOTING_2("upgrade_looting_2"),
        LOOTING_3("upgrade_looting_3"),
        MASS_1("upgrade_mass_1"),
        MASS_2("upgrade_mass_2"),
        MASS_3("upgrade_mass_3"),
        RATE_1("upgrade_rate_1"),
        RATE_2("upgrade_rate_2"),
        RATE_3("upgrade_rate_3");

        private String name;
        UpgradeType(String name) {
            this.name = name;
        }
        public String getName() { return this.name; }
        public String getTranslationkey() { return "item.woot." + this.getName().toLowerCase(); }

        public static UpgradeType[] VALUES = values();
        public static EnumSet<UpgradeType> LOOTING = EnumSet.of(LOOTING_1, LOOTING_2, LOOTING_3);
        public static EnumSet<UpgradeType> MASS = EnumSet.of(MASS_1, MASS_2, MASS_3);
        public static EnumSet<UpgradeType> RATE = EnumSet.of(RATE_1, RATE_2, RATE_3);
        public static EnumSet<UpgradeType> TIER_1 = EnumSet.of(LOOTING_1, MASS_1, RATE_1);
        public static EnumSet<UpgradeType> TIER_2 = EnumSet.of(LOOTING_2, MASS_2, RATE_2);
        public static EnumSet<UpgradeType> TIER_3 = EnumSet.of(LOOTING_3, MASS_3, RATE_3);

        public static UpgradeType byIndex(int index) {
            index = MathHelper.clamp(index, 0, VALUES.length - 1);
            return VALUES[index];
        }
    }

}
