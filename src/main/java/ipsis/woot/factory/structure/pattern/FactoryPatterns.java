package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;

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
        PATTERN_MAPPINGS.put('P', FactoryBlock.POWER_1);
    }

    public static @Nullable FactoryBlock getFactoryBlock(char c) {
        return PATTERN_MAPPINGS.getOrDefault(c, null);
    }

    public static final String TIER_1[][] = {
            {
                "-----",
                "-----",
                "--E--",
                "-----",
                "-----",
            },
            {
                "-----",
                "-----",
                "--I--",
                "-----",
                "-----",
            },
            {
                "-----",
                "-----",
                "--G--",
                "-----",
                "-----",
            },
            {
                "-----",
                "-----",
                "--6--",
                "-----",
                "-----",
            },
            {
                "-----",
                "-----",
                "--6--",
                "-----",
                "-----",
            },
            {
                "-222-",
                "21112",
                "21612",
                "21112",
                "-222-",
            },
            {
                "-----",
                "-----",
                "--H--",
                "-u1u-",
                "-212-",
            },
            {
                "-----",
                "-----",
                "-----",
                "--C--",
                "-----",
            }
    };

    public static final String TIER_2[][] = {
            {
                "--333--",
                "-32223-",
                "3211123",
                "3216123",
                "3211123",
                "-32223-",
                "--333--"
            },
            {
                "-------",
                "-------",
                "-------",
                "---H---",
                "--u1u--",
                "--212--",
                "-------"
            },
            {
                "-------",
                "-------",
                "-------",
                "-------",
                "---C---",
                "-------",
                "-------"
            }
    };

    public static final String TIER_3[][] = {
            {
                "---444---",
                "--43334--",
                "-4322234-",
                "432111234",
                "432161234",
                "432111234",
                "-4322234-",
                "--43334--",
                "---444---",
            },
            {
                "---------",
                "---------",
                "---------",
                "---------",
                "----H----",
                "---u1u---",
                "---212---",
                "---------",
                "---------",
            },
            {
                "---------",
                "---------",
                "---------",
                "---------",
                "---------",
                "----C----",
                "---------",
                "---------",
                "---------",
            },
    };

    public static final String TIER_4[][] = {
            {
                "----555----",
                "---54445---",
                "--5433345--",
                "-543222345-",
                "54321112345",
                "54321612345",
                "54321112345",
                "-543222345-",
                "--5433345--",
                "---54445---",
                "----555----"
            },
            {
                "-----------",
                "-----------",
                "-----------",
                "-----------",
                "-----------",
                "-----H-----",
                "----u1u----",
                "----212----",
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
                "-----------",
                "-----C-----",
                "-----------",
                "-----------",
                "-----------",
                "-----------",
            }
    };
}
