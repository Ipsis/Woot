package ipsis.woot.factory;

import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFactory extends WootBlock {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockFactory(FactoryBlock factoryBlock) {
        super(Block.Properties.create(Material.ROCK), factoryBlock.getName());
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
    }

    public FactoryBlock getFactoryBlock() { return this.factoryBlock; }
}
