package ipsis.woot.modules.factory.layout;

import ipsis.woot.modules.factory.ComponentType;
import ipsis.woot.modules.factory.FactoryTier;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PatternLibrary {

    public static PatternLibrary get() { return INSTANCE; }
    private static PatternLibrary INSTANCE;
    static { INSTANCE = new PatternLibrary(); }

    private static final Logger LOGGER = LogManager.getLogger();
    private EnumMap<FactoryTier, Pattern> patterns = new EnumMap<>(FactoryTier.class);

    public Pattern get(FactoryTier tier) { return patterns.get(tier); }

    public void load() {

        if (validatePatterns() == false)
            return;

        final String TIER_1_MAPPING = "[copqrstuv]";
        final String TIER_2_MAPPING = "[cqrstuv]";
        final String TIER_3_MAPPING = "[cstuv]";
        final String TIER_4_MAPPING = "[cuv]";
        final String TIER_5_MAPPING = "";

        createPattern(FactoryTier.TIER_1, TIER_1_MAPPING, FULL_PATTERN);
        createPattern(FactoryTier.TIER_2, TIER_2_MAPPING, FULL_PATTERN);
        createPattern(FactoryTier.TIER_3, TIER_3_MAPPING, FULL_PATTERN);
        createPattern(FactoryTier.TIER_4, TIER_4_MAPPING, FULL_PATTERN);
        createPattern(FactoryTier.TIER_5, TIER_5_MAPPING, FULL_PATTERN);
    }

    private boolean validatePatterns() {
        return validatePattern(FactoryTier.TIER_1, FULL_PATTERN);
    }

    private boolean validatePattern(FactoryTier tier, String[][] rawPattern) {
        if (rawPattern.length <= 0 || rawPattern[0].length <= 0 || rawPattern[0][0].length() <0) {
            LOGGER.error("Pattern for {} is invalid", tier);
            return false;
        }

        boolean hasOrigin = false;
        for (int layer = 0; layer < rawPattern.length; layer++) {
            for (int row = 0; row < rawPattern[0].length; row++) {
                if (rawPattern[layer][row].length() != rawPattern[0][0].length()) {
                    LOGGER.error("Pattern for {} row {} is invalid", tier, row);
                    return false;
                }
                for (int col = 0; col < rawPattern[0][0].length(); col++) {
                    if (rawPattern[layer][row].charAt(col) == HEART) {
                        hasOrigin = true;
                        break;
                    }
                }
            }
        }

        return hasOrigin;
    }

    private void createPattern(FactoryTier tier, String mapping, String[][] rawPattern) {

        String[][] tmp = new String[rawPattern.length][rawPattern[0].length];
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                if (!mapping.isEmpty())
                    tmp[i][j] = rawPattern[i][j].replaceAll(mapping, WILDCARD.toString());
                else
                    tmp[i][j] = rawPattern[i][j];
            }
        }

        createPattern(tier, tmp);
    }

    private void createPattern(FactoryTier tier, String[][] rawPattern) {

        int height = rawPattern.length;
        int rows = rawPattern[0].length;
        int cols = rawPattern[0][0].length();

        Pattern pattern = new Pattern(rows, height);

        outerorigin: for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (rawPattern[layer][row].charAt(col) == HEART) {
                        pattern.setOrigin(layer, row, col);
                        break outerorigin;
                    }
                }
            }
        }

        for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    char c = rawPattern[layer][row].charAt(col);
                    if (c == WILDCARD)
                        continue;

                    ComponentType componentType = CHAR_MAPPINGS.getOrDefault(c, null);
                    if (componentType == null) {
                        LOGGER.error("Pattern for {} has invalid char {}", tier, c);
                        continue;
                    }

                    pattern.add(componentType, layer, row, col);
                }
            }
        }

        patterns.put(tier, pattern);
    }

    public class Pattern {
        public int width, height;
        public Pattern(int width, int height) {
            this.width = width;
            this.height = height;
        }

        int originLayer, originRow, originCol;
        public void setOrigin(int layer, int row, int col) {
            this.originLayer = layer;
            this.originRow = row;
            this.originCol = col;
        }

        private List<PatternBlockInfo> blocks = new ArrayList<>();
        public List<PatternBlockInfo> getBlocks() {
            return Collections.unmodifiableList(blocks);
        }

        private HashMap<ComponentType, Integer> counts = new HashMap<>();
        public Map<ComponentType, Integer> getCounts() {
            return Collections.unmodifiableMap(counts);
        }
        public void add(ComponentType componentType, int layer, int row, int col) {
            blocks.add(new PatternBlockInfo(
                    new BlockPos(
                            (originCol - col) * -1,
                            (originLayer - layer) * -1,
                            (originRow - row) * -1),
                            componentType));
            counts.put(componentType, counts.getOrDefault(componentType, 0) + 1);
        }
    }

    private final Character HEART = 'H';
    private final Character WILDCARD = '-';
    private final HashMap<Character, ComponentType> CHAR_MAPPINGS = new HashMap<>();
    {
        CHAR_MAPPINGS.put('a', ComponentType.BASE_1);
        CHAR_MAPPINGS.put('b', ComponentType.BASE_2);
        CHAR_MAPPINGS.put('c', ComponentType.BASE_3);
        CHAR_MAPPINGS.put('g', ComponentType.BASE_GLASS);
        CHAR_MAPPINGS.put('m', ComponentType.CORE_1A);
        CHAR_MAPPINGS.put('n', ComponentType.CORE_1B);
        CHAR_MAPPINGS.put('o', ComponentType.CORE_2A);
        CHAR_MAPPINGS.put('p', ComponentType.CORE_2B);
        CHAR_MAPPINGS.put('q', ComponentType.CORE_3A);
        CHAR_MAPPINGS.put('r', ComponentType.CORE_3B);
        CHAR_MAPPINGS.put('s', ComponentType.CORE_4A);
        CHAR_MAPPINGS.put('t', ComponentType.CORE_4B);
        CHAR_MAPPINGS.put('u', ComponentType.CORE_5A);
        CHAR_MAPPINGS.put('v', ComponentType.CORE_5B);
        CHAR_MAPPINGS.put('I', ComponentType.IMPORTER);
        CHAR_MAPPINGS.put('X', ComponentType.EXPORTER);
        CHAR_MAPPINGS.put(HEART, ComponentType.HEART);
    }
    
    private final String FULL_PATTERN[][] = {
            {
                    "---aba---",
                    "--baaab--",
                    "-aaaaaaa-",
                    "IbaaaaabX",
                    "-aaaaaaa-",
                    "--baaab--",
                    "---aba---",
                    "----H----"
            },
            {
                    "---ggg---",
                    "--c---c--",
                    "-g-tqt-g-",
                    "-g-omo-g-",
                    "-g-uqu-g-",
                    "--c---c--",
                    "---ggg---",
                    "---------"
            },
            {
                    "---ggg---",
                    "--c-v-c--",
                    "-g-srs-g-",
                    "-gvpnpvg-",
                    "-g-vrv-g-",
                    "--c-v-c--",
                    "---ggg---",
                    "---------"
            },
            {
                    "---ggg---",
                    "--c---c--",
                    "-g-tqt-g-",
                    "-g-omo-g-",
                    "-g-uqu-g-",
                    "--c---c--",
                    "---ggg---",
                    "---------"
            },
            {
                    "---aaa---",
                    "--b---b--",
                    "-a--s--a-",
                    "-a-svs-a-",
                    "-a--s--a-",
                    "--b---b--",
                    "---aaa---",
                    "---------"
            },
            {
                    "---------",
                    "---aaa---",
                    "--aagaa--",
                    "--aggga--",
                    "--aagaa--",
                    "---aaa---",
                    "---------",
                    "---------"
            }
    };

    private final String FULL_PATTERN2[][] = {
            {
                    "---aba---",
                    "--baaab--",
                    "-aaaaaaa-",
                    "IbaaaaabX",
                    "-aaaaaaa-",
                    "--baaab--",
                    "---aba---",
                    "----H----"
            },
            {
                    "--cgggc--",
                    "-ca---ac-",
                    "-g-tqt-g-",
                    "-g-omo-g-",
                    "-g-uqu-g-",
                    "-ca---ac-",
                    "--cgggc--",
                    "---------",
            },
            {
                    "--cgggc--",
                    "-ca-v-ac-",
                    "-g-srs-g-",
                    "-gvpnpvg-",
                    "-g-vrv-g-",
                    "-ca-v-ac-",
                    "--cgggc--",
                    "---------",
            },
            {
                    "--cgggc--",
                    "-ca---ac-",
                    "-g-tqt-g-",
                    "-g-omo-g-",
                    "-g-uqu-g-",
                    "-ca---ac-",
                    "--cgggc--",
                    "---------",
            },
            {
                    "---aaa---",
                    "--b---b--",
                    "-a-----a-",
                    "-a-----a-",
                    "-a-----a-",
                    "--b---b--",
                    "---aaa---",
                    "---------",
            },
            {
                    "---------",
                    "---aaa---",
                    "--aagaa--",
                    "--aggga--",
                    "--aagaa--",
                    "---aaa---",
                    "---------",
                    "---------",
            }
    };
}
