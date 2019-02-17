package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;

import javax.annotation.Nullable;
import java.util.HashMap;

public class FactoryPatterns {

    public static final Character HEART_CHAR = 'H';
    public static final Character CONTROLLER_CHAR = 'C';
    public static final Character SPACE_CHAR = '-';
    public static final HashMap<Character, FactoryBlock> PATTERN_MAPPINGS = new HashMap<>();
    static {
        PATTERN_MAPPINGS.put('1', FactoryBlock.BONE);
        PATTERN_MAPPINGS.put('2', FactoryBlock.FLESH);
        PATTERN_MAPPINGS.put('3', FactoryBlock.BLAZE);
        PATTERN_MAPPINGS.put('4', FactoryBlock.ENDER);
        PATTERN_MAPPINGS.put('5', FactoryBlock.NETHER);
        PATTERN_MAPPINGS.put('6', FactoryBlock.REDSTONE);
        PATTERN_MAPPINGS.put('u', FactoryBlock.UPGRADE);
        PATTERN_MAPPINGS.put('!', FactoryBlock.CAP_1);
        PATTERN_MAPPINGS.put('£', FactoryBlock.CAP_2);
        PATTERN_MAPPINGS.put('$', FactoryBlock.CAP_3);
        PATTERN_MAPPINGS.put('%', FactoryBlock.CAP_4);
        PATTERN_MAPPINGS.put('T', FactoryBlock.TOTEM);
        PATTERN_MAPPINGS.put(CONTROLLER_CHAR, FactoryBlock.CONTROLLER);
        PATTERN_MAPPINGS.put(HEART_CHAR, FactoryBlock.HEART);
        PATTERN_MAPPINGS.put('I', FactoryBlock.IMPORT);
        PATTERN_MAPPINGS.put('E', FactoryBlock.EXPORT);
        PATTERN_MAPPINGS.put('G', FactoryBlock.GENERATOR);
        PATTERN_MAPPINGS.put('X', FactoryBlock.POWER_1);
    }

    private static final HashMap<Integer, String[][]> PATTERNS = new HashMap<>();
    public static void createPatterns() {

        PATTERNS.put(FactoryTier.TIER_5.ordinal(), TIER_5);

        /**
         * Tier 4
         */
        String[][] tmp;

        tmp = new String[TIER_5.length][TIER_5[0].length];
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                tmp[i][j] = TIER_5[i][j].replaceAll("[%5]", "-");
            }
        }
        PATTERNS.put(FactoryTier.TIER_4.ordinal(), tmp);

        /**
         * Tier 3
         */
        tmp = new String[TIER_5.length][TIER_5[0].length];
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                tmp[i][j] = TIER_5[i][j].replaceAll("[%5$4]", "-");
            }
        }
        PATTERNS.put(FactoryTier.TIER_3.ordinal(), tmp);

        /**
         * Tier 2
         */
        tmp = new String[TIER_5.length][TIER_5[0].length];
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                tmp[i][j] = TIER_5[i][j].replaceAll("[%5$4£3]", "-");
            }
        }
        PATTERNS.put(FactoryTier.TIER_2.ordinal(), tmp);

        /**
         * Tier 1
         */
        tmp = new String[TIER_5.length][TIER_5[0].length];
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                tmp[i][j] = TIER_5[i][j].replaceAll("[%5$4£3!12u]", "-");
            }
        }
        PATTERNS.put(FactoryTier.TIER_1.ordinal(), tmp);
    }

    public static String[][] getPattern(FactoryTier tier) {
        return PATTERNS.get(tier.ordinal());
    }

    public static @Nullable FactoryBlock getFactoryBlock(char c) {
        return PATTERN_MAPPINGS.getOrDefault(c, null);
    }

    public static final String TIER_5[][] = {
            {

                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----X-----",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
            },
            {

                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----E-----",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
            },
            {

                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----I-----",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
            },
            {

                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----6-----",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
            },
            {

                    "-----5-----",
                    "----545----",
                    "---54345---",
                    "--5432345--",
                    "-543212345-",
                    "54321612345",
                    "-543212345-",
                    "--5432345--",
                    "---54345---",
                    "----545----",
                    "-----5-----",
            },
            {
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "---uuHuu---",
                    "-5432-2345-",
                    "--5432345--",
                    "---54345---",
                    "----545----",
                    "-----5-----",
            },
            {
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----C-----",
                    "-5432-2345-",
                    "--543-345--",
                    "---54345---",
                    "----545----",
                    "-----5-----",
            },
            {
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-54£!-!£45-",
                    "--54---45--",
                    "---54-45---",
                    "----545----",
                    "-----5-----",
            },
            {
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-5$-----$5-",
                    "--5-----5--",
                    "---5$-$5---",
                    "----5-5----",
                    "-----5-----",
            },
            {
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-----------",
                    "-%-------%-",
                    "-----------",
                    "---%---%---",
                    "-----------",
                    "-----%-----",
            }
    };
}
