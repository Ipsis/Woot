package ipsis.woot.factory.layout;

import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryTier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.*;

public class FactoryPatternRepository {

    /**
     * We only ever need one instance of the pattern repository
     */
    private static final FactoryPatternRepository INSTANCE = new FactoryPatternRepository();
    public static FactoryPatternRepository getInstance() { return INSTANCE; }

    public void load() {
        createRawPatterns();
        createPattern(FactoryTier.TIER_1, rawPatterns.get(FactoryTier.TIER_1));
        createPattern(FactoryTier.TIER_2, rawPatterns.get(FactoryTier.TIER_2));
        createPattern(FactoryTier.TIER_3, rawPatterns.get(FactoryTier.TIER_3));
        createPattern(FactoryTier.TIER_4, rawPatterns.get(FactoryTier.TIER_4));
        createPattern(FactoryTier.TIER_5, rawPatterns.get(FactoryTier.TIER_5));
    }

    public Pattern getPattern(FactoryTier tier) {
        return patterns.get(tier);
    }

    private int maxYOffset = 0;
    private int maxXZOffset = 0;
    public int getMaxYOffset() { return maxYOffset; }
    public int getMaxXZOffset() { return maxXZOffset; }

    private void createPattern(FactoryTier tier, String[][] rawPattern) {
        if (rawPattern.length == 0) {
            Woot.LOGGER.error("FactoryPatternRepository.createPattern {} length is 0", tier);
            return;
        }

        int rows = rawPattern[0].length;
        if (rows == 0) {
            Woot.LOGGER.error("FactoryPatternRepository.createPattern {} row count is 0", tier);
            return;
        }

        int cols = rawPattern[0][0].length();
        if (cols == 0) {
            Woot.LOGGER.error("FactoryPatternRepository.createPattern {} column count is 0", tier);
            return;
        }

        int height = rawPattern.length;
        Woot.LOGGER.info("Creating factory pattern for {} of w:{} c:{} h:{}", tier, rows, cols, height);

        Pattern pattern = new Pattern(rows, height);

        /**
         * Find the origin
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
            Woot.LOGGER.error("FactoryPatternRepository.createPattern {} has not origin", tier);
            return;
        }

        /**
         * Add the non-origin blocks
         */
        for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (rawPattern[layer][row].length() != cols) {
                        Woot.LOGGER.error("FactoryPatternRepository.createPattern {} row {} is too short", tier, row);
                        return;
                    }
                }

                for (int col = 0; col < cols; col++) {
                    char c = rawPattern[layer][row].charAt(col);
                    if (c == SPACE_CHAR)
                        continue;

                    FactoryBlock factoryBlock = CHAR_MAPPINGS.getOrDefault(c, null);
                    if (factoryBlock == null) {
                        Woot.LOGGER.error("FactoryPatternRepository.createPattern {} invalid char {} @ {},{},{}", c, layer, row, col);
                        continue;
                    }

                    pattern.addFactoryBlock(factoryBlock, layer, row, col);
                }
            }
        }

        patterns.put(tier, pattern);

        if (maxYOffset == 0 || maxYOffset < height)
            maxYOffset = height;

        if (maxXZOffset == 0 || maxXZOffset < rows)
            maxXZOffset = rows;
    }

    /**
     * Patterns
     * The pattern is a collection of positions and what type they should be
     */
    private EnumMap<FactoryTier, Pattern> patterns = new EnumMap<>(FactoryTier.class);
    public class Pattern {

        private Pattern() { }
        public Pattern(int width, int height) {
            this.width = width;
            this.height = height;
        }
        private int height;
        public int getHeight() { return height; }
        private int width;
        public int getWidth() { return width; }

        private int originLayer;
        private int originRow;
        private int originCol;
        public void setOrigin(int layer, int row, int col) {
            this.originLayer = layer;
            this.originRow = row;
            this.originCol = col;
        }

        private HashMap<FactoryBlock, Integer> factoryBlockCounts = new HashMap<>();
        public void addFactoryBlock(FactoryBlock factoryBlock, int layer, int row, int col) {
            int x = (originCol - col) * -1;
            int y = (originLayer - layer) * -1;
            int z = (originRow - row) * -1;
            blocks.add(new PatternBlock(factoryBlock, new BlockPos(x, y, z)));

            int count = factoryBlockCounts.getOrDefault(factoryBlock, 0);
            count++;
            factoryBlockCounts.put(factoryBlock, count);
        }

        public List<PatternBlock> getPatternBlocks() { return Collections.unmodifiableList(blocks); }
        public int getFactoryBlockCount(FactoryBlock factoryBlock) {
            return factoryBlockCounts.getOrDefault(factoryBlock, 0);
        }

        private List<PatternBlock> blocks = new ArrayList<>();
    }


    /**
     * Raw Patterns
     */
    private EnumMap<FactoryTier, String[][]> rawPatterns = new EnumMap<>(FactoryTier.class);
    private void createRawPatterns() {

        /**
         * The tiers are created by removing blocks from the layout.
         * Therefore all the tiers are subsets of LAYOUT.
         */
        final String TIER_1_PATTERN = "[%5$4^3!12u]";
        final String TIER_2_PATTERN = "[%5$4^3]";
        final String TIER_3_PATTERN = "[%5$4]";
        final String TIER_4_PATTERN = "[%5]";
        final String TIER_5_PATTERN = "";

        addTier(FactoryTier.TIER_1, TIER_1_PATTERN);
        addTier(FactoryTier.TIER_2, TIER_2_PATTERN);
        addTier(FactoryTier.TIER_3, TIER_3_PATTERN);
        addTier(FactoryTier.TIER_4, TIER_4_PATTERN);
        addTier(FactoryTier.TIER_5, TIER_5_PATTERN);
    }

    private void addTier(FactoryTier tier, String pattern) {
        String[][] tmp = new String[LAYOUT.length][LAYOUT[0].length];
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                if (!pattern.isEmpty()) {
                    tmp[i][j] = LAYOUT[i][j].replaceAll(pattern, SPACE_CHAR.toString());
                } else {
                    tmp[i][j] = LAYOUT[i][j];
                }
            }
        }

        
        rawPatterns.put(tier, tmp);
    }

    private final HashMap<Character, FactoryBlock> CHAR_MAPPINGS = new HashMap<>();
    {
        CHAR_MAPPINGS.put('1', FactoryBlock.BONE);
        CHAR_MAPPINGS.put('2', FactoryBlock.FLESH);
        CHAR_MAPPINGS.put('3', FactoryBlock.BLAZE);
        CHAR_MAPPINGS.put('4', FactoryBlock.ENDER);
        CHAR_MAPPINGS.put('5', FactoryBlock.NETHER);
        CHAR_MAPPINGS.put('6', FactoryBlock.REDSTONE);
        CHAR_MAPPINGS.put('u', FactoryBlock.UPGRADE);
        CHAR_MAPPINGS.put('!', FactoryBlock.CAP_1);
        CHAR_MAPPINGS.put('^', FactoryBlock.CAP_2);
        CHAR_MAPPINGS.put('$', FactoryBlock.CAP_3);
        CHAR_MAPPINGS.put('%', FactoryBlock.CAP_4);
        CHAR_MAPPINGS.put('H', FactoryBlock.HEART);
        CHAR_MAPPINGS.put('C', FactoryBlock.CONTROLLER);
        CHAR_MAPPINGS.put('I', FactoryBlock.IMPORT);
        CHAR_MAPPINGS.put('E', FactoryBlock.EXPORT);
        CHAR_MAPPINGS.put('X', FactoryBlock.CELL_1);
    }

    private final Character HEART_CHAR = 'H';
    private final Character SPACE_CHAR = '-';
    public final String LAYOUT[][] = {
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
                    "-54^!-!^45-",
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
