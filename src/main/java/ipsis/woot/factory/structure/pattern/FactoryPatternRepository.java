package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.helpers.BlockPosHelper;
import ipsis.woot.util.helpers.LogHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

import static ipsis.woot.factory.structure.pattern.FactoryPatterns.HEART_CHAR;
import static ipsis.woot.factory.structure.pattern.FactoryPatterns.SPACE_CHAR;

/**
 * This is based off the approach that WayOfTime handled the patterns for the BloodMagic Blood Altar.
 * BloodMagic/src/main/java/WayofTime/bloodmagic/api/altar/EnumAltarTier.java
 */

public class FactoryPatternRepository {

    private HashMap<FactoryTier, FactoryPattern> patterns = new HashMap<>();
    private int maxYOffset = 0;
    private int maxXZOffset = 0;

    public int getMaxYOffset() { return this.maxYOffset; }
    public int getMaxXZOffset() { return this.maxXZOffset; }

    public void load() {

        FactoryPatterns.createPatterns();
        loadBasePattern(FactoryTier.TIER_1, FactoryPatterns.getPattern(FactoryTier.TIER_1));
        loadBasePattern(FactoryTier.TIER_2, FactoryPatterns.getPattern(FactoryTier.TIER_2));
        loadBasePattern(FactoryTier.TIER_3, FactoryPatterns.getPattern(FactoryTier.TIER_3));
        loadBasePattern(FactoryTier.TIER_4, FactoryPatterns.getPattern(FactoryTier.TIER_4));
        loadBasePattern(FactoryTier.TIER_5, FactoryPatterns.getPattern(FactoryTier.TIER_5));
    }

    private void loadBasePattern(FactoryTier factoryTier, String[][] pattern) {

        if (pattern.length == 0) {
            LogHelper.info(String.format("FactoryPatternRepository.loadBasePattern: %s length is 0", factoryTier));
            return;
        }

        int width = pattern[0].length;

        if (width == 0) {
            LogHelper.info(String.format("FactoryPatternRepository.loadBasePattern: %s row count is 0", factoryTier));
            return;
        }

        int depth = pattern[0][0].length();
        if (depth == 0) {
            LogHelper.info(String.format("FactoryPatternRepository.loadBasePattern: %s column count is 0", factoryTier));
            return;

        }

        int height = pattern.length;
        FactoryPattern factoryPattern = new FactoryPattern(FactoryPattern.FactoryPatternType.BASE);
        factoryPattern.setHeight(height);
        factoryPattern.setWidth(width);

        /**
         * Find the origin
         */
        boolean hasOrigin = false;
        for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < width; row++) {
                for (int col = 0; col < depth; col++) {
                    char c = pattern[layer][row].charAt(col);
                    if (c == HEART_CHAR) {
                        factoryPattern.setOrigin(layer, row, col);
                        hasOrigin = true;
                    }
                }
            }
        }

        if (!hasOrigin) {
            LogHelper.info(String.format("FactoryPatternRepository.loadBasePattern: %s has no origin", factoryTier));
            return;
        }

        for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < width; row++) {
                if (pattern[layer][row].length() != depth) {
                    LogHelper.info(String.format("FactoryPatternRepository.loadBasePattern: %s row %d is too short", factoryTier, row));
                    return;
                }

                for (int col = 0; col < depth; col++) {
                    char c = pattern[layer][row].charAt(col);

                    if (c == SPACE_CHAR)
                        continue;

                    FactoryBlock factoryBlock = FactoryPatterns.getFactoryBlock(c);
                    if (factoryBlock == null) {
                        LogHelper.info(String.format("FactoryPatternRepository.loadBasePattern: %s invalid char (%d, %d, %d) %c", factoryTier, layer, row, col, c));
                        continue;
                    }

                    factoryPattern.incBlockCount(factoryBlock);
                    factoryPattern.addFactoryBlock(factoryBlock, layer, row, col);
                }
            }
        }

        patterns.put(factoryTier, factoryPattern);

        if (maxYOffset == 0 || maxYOffset < height)
            maxYOffset = height;

        if (maxXZOffset == 0 || maxXZOffset < width)
            maxXZOffset = width;
    }

    public int getPatternHeight(FactoryTier factoryTier) {
        return patterns.get(factoryTier).getHeight();
    }

    public AbsolutePattern createAbsolutePattern(World world, FactoryTier factoryTier, BlockPos origin, EnumFacing facing) {

        FactoryPattern factoryPattern = patterns.get(factoryTier);
        AbsolutePattern absolutePattern = new AbsolutePattern(factoryTier);

        EnumFacing oppositeFacing = facing.getOpposite();
        for (FactoryPattern.PatternBlock patternBlock : factoryPattern.getBlocks()) {
            BlockPos pos = BlockPosHelper.rotateFromSouth(patternBlock.offset, oppositeFacing);
            absolutePattern.addAbsoluteBlock(origin.add(pos), patternBlock.factoryBlock);
        }

        return absolutePattern;
    }
}
