package ipsis.woot.tileentity.ui;

import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.util.StringHelper;
import net.minecraft.util.math.MathHelper;

public class UpgradeUIInfo {

    boolean[] upgradeEnabled;
    int[] upgradeLevel;
    int[] powerPerTick;
    int[] param1;

    public UpgradeUIInfo() {

        int numUpgrades = EnumFarmUpgrade.values().length;

        powerPerTick = new int[numUpgrades];
        upgradeEnabled = new boolean[numUpgrades];
        upgradeLevel = new int[numUpgrades];
        param1 = new int[numUpgrades];
    }

    public void setPowerPerTick(EnumFarmUpgrade upgrade, int power) {

        powerPerTick[upgrade.ordinal()] = power;
    }

    public void setParam1(EnumFarmUpgrade upgrade, int p) {

        param1[upgrade.ordinal()] = p;
    }

    public int getPowerPerTick(EnumFarmUpgrade upgrade) {

        return powerPerTick[upgrade.ordinal()];
    }

    public int getParam1(EnumFarmUpgrade upgrade) {

        return param1[upgrade.ordinal()];
    }

    public void setUpgrade(EnumFarmUpgrade upgrade, int level) {

        level = MathHelper.clamp(level, 1, 3);
        upgradeEnabled[upgrade.ordinal()] = true;
        upgradeLevel[upgrade.ordinal()] = level;
    }

    public boolean isUpgradeEnabled(EnumFarmUpgrade upgrade) {

        return upgradeEnabled[upgrade.ordinal()];
    }

    public int getUpgradeLevel(EnumFarmUpgrade upgrade) {

        return upgradeLevel[upgrade.ordinal()];
    }

    public String getUpgradeText(EnumFarmUpgrade upgrade) {

        if (!isUpgradeEnabled(upgrade))
            return "";

        String out;
        if (upgrade == EnumFarmUpgrade.MASS) {
            out = StringHelper.localizeFormat("ui.woot.mass.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.DECAPITATE) {
            out = StringHelper.localizeFormat("ui.woot.decapitate.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.LOOTING) {
            out = StringHelper.localizeFormat("ui.woot.looting.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.RATE) {
            out = StringHelper.localizeFormat("ui.woot.rate.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.EFFICIENCY) {
            out = StringHelper.localizeFormat("ui.woot.efficiency.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.XP) {
            out = StringHelper.localizeFormat("ui.woot.xp.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.EC_BLOOD) {
            out = StringHelper.localizeFormat("ui.woot.ec_blood.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.BM_LE_TANK) {
            out = StringHelper.localizeFormat("ui.woot.bm_le_tank.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.BM_LE_ALTAR) {
            out = StringHelper.localizeFormat("ui.woot.bm_le_alter.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else if (upgrade == EnumFarmUpgrade.BM_CRYSTAL) {
            out = StringHelper.localizeFormat("ui.woot.bm_crystal.desc", upgradeLevel[upgrade.ordinal()], powerPerTick[upgrade.ordinal()], param1[upgrade.ordinal()]);
        } else {
            out = upgrade.toString();
        }

        return out;
    }
}
