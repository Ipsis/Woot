package ipsis.woot.factory.structure.pattern;

import ipsis.woot.util.FactoryBlock;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FactoryPattern {

    private FactoryPatternType patternType;
    public FactoryPattern(FactoryPatternType patternType) { this.patternType = patternType; }

    private int width;
    private int height;
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getHeight() { return this.height; }
    public int getWidth() { return this.width; }


    private int originLayer;
    private int originRow;
    private int originCol;
    public void setOrigin(int layer, int row, int col) {
        this.originLayer = layer;
        this.originRow = row;
        this.originCol = col;
    }

    private HashMap<FactoryBlock, Integer> blockCounts = new HashMap<>();
    public int getBlockCount(FactoryBlock factoryBlock) {
        return blockCounts.getOrDefault(factoryBlock, 0);
    }
    public void incBlockCount(FactoryBlock factoryBlock) {
        int count = blockCounts.getOrDefault(factoryBlock, 0);
        count++;
        blockCounts.put(factoryBlock, count);
    }

    private List<PatternBlock> blocks = new ArrayList<>();
    public void addFactoryBlock(FactoryBlock factoryBlock, int layer, int row, int col) {

        int x = (originCol - col) * -1;
        int y = (originLayer - layer) * -1;
        int z = (originRow - row) * -1;
        blocks.add(new PatternBlock(factoryBlock, new BlockPos(x, y, z)));
    }

    public List<PatternBlock> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

    public enum FactoryPatternType {
        BASE,
        CONTROLLER,
        TOTEM;
    }


    public class PatternBlock {

        protected BlockPos offset;
        protected FactoryBlock factoryBlock;

        public PatternBlock(FactoryBlock factoryBlock, BlockPos offset) {
            this.factoryBlock = factoryBlock;
            this.offset = offset;
        }

        @Override
        public String toString() {
            return String.format("%s (%s)", offset, factoryBlock.getName());
        }
    }
}
