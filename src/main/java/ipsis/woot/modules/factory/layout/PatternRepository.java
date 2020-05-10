package ipsis.woot.modules.factory.layout;

import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.Tier;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PatternRepository {

    private static final Logger LOGGER = LogManager.getLogger();

    public static PatternRepository get() { return INSTANCE; }
    static PatternRepository INSTANCE;
    static { INSTANCE = new PatternRepository(); }

    public void load() {
        createRawPatterns();
        createPattern(Tier.TIER_1);
        createPattern(Tier.TIER_2);
        createPattern(Tier.TIER_3);
        createPattern(Tier.TIER_4);
        createPattern(Tier.TIER_5);
    }

    void createPattern(Tier tier) {
        String[][] rawPattern = rawPatterns.get(tier);

        int height = rawPattern.length;
        if (rawPattern.length == 0) {
            LOGGER.error("Pattern for tier {} has height of 0", tier);
            return;
        }

        int rows = rawPattern[0].length;
        if (rows == 0) {
            LOGGER.error("Pattern for tier {} has row count of 0", tier);
            return;
        }

        int cols = rawPattern[0][0].length();
        if (cols == 0) {
            LOGGER.error("Pattern for tier {} has col count of 0", tier);
            return;
        }

        Pattern pattern = new Pattern(rows, height);

        for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (rawPattern[layer][row].length() != cols) {
                        LOGGER.error("Pattern for tier {} row {} is too short", tier, row);
                        return;
                    }
                }
            }
        }

        /**
         * Set the origin as all the other blocks are offsets of this
         */
        boolean hasOrigin = false;
        outer: for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (rawPattern[layer][row].charAt(col) == HEART_CHAR) {
                       pattern.setOrigin(layer, row, col);
                       hasOrigin = true;
                       break outer;
                    }
                }
            }
        }

        if (!hasOrigin) {
            LOGGER.error("Pattern for tier {} has no origin", tier);
            return;
        }

        /**
         * Add the other blocks
         */
        for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (rawPattern[layer][row].length() != cols) {
                        LOGGER.error("Pattern for tier {} row {} is too short", tier, row);
                        return;
                    }
                }

                for (int col = 0; col < cols; col++) {
                    char c = rawPattern[layer][row].charAt(col);
                    if (c == WILDSTAR_CHAR)
                        continue;

                    FactoryComponent component = CHAR_MAPPINGS.getOrDefault(c, null);
                    if (component == null) {
                        LOGGER.error("Pattern for tier {} invalid char {} @ {},{},{}", c, layer, row, col);
                        continue;
                    }

                    pattern.addComponent(component, layer, row, col);
                }
            }
        }

        patterns.put(tier, pattern);

        if (maxYOffset == 0 || maxYOffset < height)
            maxYOffset = height;

        if (maxXZOffset == 0 || maxXZOffset < rows)
            maxXZOffset = rows;
    }

    int maxYOffset = 0;
    int maxXZOffset = 0;
    public int getMaxYOffset() { return maxYOffset; }
    public int getMaxXZOffset() { return maxXZOffset; }

    public Pattern getPattern(Tier tier) {
        return patterns.get(tier);
    }

    /**
     * Pattern
     * The pattern is a collection of position and what type they should be
     */
    EnumMap<Tier, Pattern> patterns = new EnumMap<>(Tier.class);
    public class Pattern {
        public Pattern(int width, int height) {
            this.width = width;
            this.height = height;
        }

        int height;
        int width;
        public int getHeight() { return height; }
        public int getWidth() { return width; }

        int originLayer;
        int originRow;
        int originCol;
        public void setOrigin(int layer, int row, int col) {
            this.originLayer = layer;
            this.originRow = row;
            this.originCol = col;
        }

        HashMap<FactoryComponent, Integer> componentCounts = new HashMap<>();
        public void addComponent(FactoryComponent component, int layer, int row, int col) {
            int x = (originCol - col) * -1;
            int y = (originLayer - layer) * -1;
            int z = (originRow - row) * -1;

            blocks.add(new PatternBlock(component, new BlockPos(x, y, z)));
            componentCounts.put(component, componentCounts.getOrDefault(component, 0) + 1);
        }

        public List<PatternBlock> getPatternBlocks() { return Collections.unmodifiableList(blocks); }
        public int getFactoryBlockCount(FactoryComponent component) {
            return componentCounts.getOrDefault(component, 0);
        }

        private List<PatternBlock> blocks = new ArrayList<>();
    }

    /**
     * Raw Patterns
     * Tiers are created by removing blocks from the master layout
     */
    EnumMap<Tier, String[][]> rawPatterns = new EnumMap<>(Tier.class);
    void createRawPatterns() {

        final String TIER_2_PATTERN = "[cde234]";
        final String TIER_3_PATTERN = "[de34]";
        final String TIER_4_PATTERN = "[e4]";
        final String TIER_5_PATTERN = "";
        addTier(Tier.TIER_1, "", LAYOUT1);
        addTier(Tier.TIER_2, TIER_2_PATTERN, LAYOUT);
        addTier(Tier.TIER_3, TIER_3_PATTERN, LAYOUT);
        addTier(Tier.TIER_4, TIER_4_PATTERN, LAYOUT);
        addTier(Tier.TIER_5, TIER_5_PATTERN, LAYOUT);
    }

    void addTier(Tier tier, String pattern, String[][] layout) {
       String[][] tmp = new String[layout.length][layout[0].length];
       for (int i = 0; i < tmp.length; i++) {
           for (int j = 0; j < tmp[i].length; j++) {
               if (!pattern.isEmpty())
                   tmp[i][j] = layout[i][j].replaceAll(pattern, WILDSTAR_CHAR.toString());
               else
                   tmp[i][j] = layout[i][j];
           }
       }

       rawPatterns.put(tier, tmp);
    }

    /**
     * Layout Pattern
     */

    private final Character WILDSTAR_CHAR = '-';
    private final Character HEART_CHAR = 'H';
    private final HashMap<Character, FactoryComponent> CHAR_MAPPINGS = new HashMap<>();
    {
        CHAR_MAPPINGS.put('a', FactoryComponent.FACTORY_A);
        CHAR_MAPPINGS.put('b', FactoryComponent.FACTORY_B);
        CHAR_MAPPINGS.put('c', FactoryComponent.FACTORY_C);
        CHAR_MAPPINGS.put('d', FactoryComponent.FACTORY_D);
        CHAR_MAPPINGS.put('e', FactoryComponent.FACTORY_E);
        CHAR_MAPPINGS.put('z', FactoryComponent.FACTORY_CONNECT);
        CHAR_MAPPINGS.put('Y', FactoryComponent.FACTORY_CTR_BASE_PRI);
        CHAR_MAPPINGS.put('y', FactoryComponent.FACTORY_CTR_BASE_SEC);
        CHAR_MAPPINGS.put('U', FactoryComponent.FACTORY_UPGRADE);
        CHAR_MAPPINGS.put('I', FactoryComponent.IMPORT);
        CHAR_MAPPINGS.put('E', FactoryComponent.EXPORT);
        CHAR_MAPPINGS.put('1', FactoryComponent.CAP_A);
        CHAR_MAPPINGS.put('2', FactoryComponent.CAP_B);
        CHAR_MAPPINGS.put('3', FactoryComponent.CAP_C);
        CHAR_MAPPINGS.put('4', FactoryComponent.CAP_D);
        CHAR_MAPPINGS.put(HEART_CHAR, FactoryComponent.HEART);
        CHAR_MAPPINGS.put('C', FactoryComponent.CONTROLLER);
        CHAR_MAPPINGS.put('P', FactoryComponent.CELL);
    }

    private final String LAYOUT1[][] = {
            {
                    "-------",
                    "-------",
                    "---P---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---E---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---I---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---z---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---z---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "---Y---",
                    "---a---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "---C---",
                    "---a---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---U---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---H---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            }
    };

    /**
     * First line is the front of the structure
     */
    private final String LAYOUT[][] = {
            {
                    "-------",
                    "-------",
                    "---P---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---E---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---I---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "---z---",
                    "-------",
                    "-------",
                    "-------",
                    "-------",
            },
            {
                    "-------",
                    "---z---",
                    "---z---",
                    "-zzzzz-",
                    "---z---",
                    "---z---",
                    "-------",
            },
            {
                    "-e---e-",
                    "edcYcde",
                    "-caaac-",
                    "-yazay-",
                    "-caaac-",
                    "edcycde",
                    "-e---e-",
            },
            {
                    "-4---4-",
                    "4dcCcd4",
                    "-cbabc-",
                    "-CbbbC-",
                    "-cbbbc-",
                    "4dcCcd4",
                    "-4---4-",
            },
            {
                    "-------",
                    "-3c-c3-",
                    "-cbUbc-",
                    "--UaU--",
                    "-cbUbc-",
                    "-3c-c3-",
                    "-------",
            },
            {
                    "-------",
                    "--2-2--",
                    "-2bHb2-",
                    "---b---",
                    "-2b-b2-",
                    "--2-2--",
                    "-------",
            },
            {
                    "-------",
                    "-------",
                    "--1-1--",
                    "-------",
                    "--1-1--",
                    "-------",
                    "-------",
            }
    };
}
