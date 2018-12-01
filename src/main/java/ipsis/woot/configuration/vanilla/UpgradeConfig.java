package ipsis.woot.configuration.vanilla;

import ipsis.Woot;
import net.minecraftforge.common.config.Config;

@Config(modid = Woot.MODID, category = "upgrade")
public class UpgradeConfig {

    /**
     * Upgrade parameters
     */
    @Config.Comment("Percentage chance of Headhunter 1 dropping a skull")
    public static int HEADHUNTER_1_DROP = 30;
    @Config.Comment("Percentage chance of Headhunter 2 dropping a skull")
    public static int HEADHUNTER_2_DROP = 50;
    @Config.Comment("Percentage chance of Headhunter 3 dropping a skull")
    public static int HEADHUNTER_3_DROP = 80;

    @Config.Comment("Number of mobs to spawn for Mass 1")
    public static int MASS_1_NUM_MOBS = 2;
    @Config.Comment("Number of mobs to spawn for Mass 2")
    public static int MASS_2_NUM_MOBS = 4;
    @Config.Comment("Number of mobs to spawn for Mass 3")
    public static int MASS_3_NUM_MOBS = 6;

    @Config.Comment("Percentage reduction in spawn time for Rate 1")
    public static int RATE_1_REDUCTION = 20;
    @Config.Comment("Percentage reduction in spawn time for Rate 2")
    public static int RATE_2_REDUCTION = 50;
    @Config.Comment("Percentage reduction in spawn time for Rate 3")
    public static int RATE_3_REDUCTION = 75;

    @Config.Comment("Percentage of XP generated for XP 1")
    public static int XP_1_AMOUNT = 100;
    @Config.Comment("Percentage of XP generated for XP 2")
    public static int XP_2_AMOUNT = 140;
    @Config.Comment("Percentage of XP generated for XP 3")
    public static int XP_3_AMOUNT = 180;

    /**
     * Upgrade costs
     */
    @Config.Comment("Mass 1 upgrade units/tick")
    public static int MASS_1_COST = 10;
    @Config.Comment("Mass 2 upgrade units/tick")
    public static int MASS_2_COST = 10;
    @Config.Comment("Mass 3 upgrade units/tick")
    public static int MASS_3_COST = 10;

    @Config.Comment("Rate 1 upgrade units/tick")
    public static int RATE_1_COST = 10;
    @Config.Comment("Rate 2 upgrade units/tick")
    public static int RATE_2_COST = 10;
    @Config.Comment("Rate 3 upgrade units/tick")
    public static int RATE_3_COST = 10;

    @Config.Comment("Experience 1 upgrade units/tick")
    public static int XP_1_COST = 10;
    @Config.Comment("Experience 2 upgrade units/tick")
    public static int XP_2_COST = 10;
    @Config.Comment("Experience 3 upgrade units/tick")
    public static int XP_3_COST = 10;


}
