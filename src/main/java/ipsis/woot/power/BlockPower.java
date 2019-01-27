package ipsis.woot.power;

import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.material.Material;

public class BlockPower extends WootBlock {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockPower(FactoryBlock factoryBlock) {
        super(Material.ROCK, factoryBlock.getName());
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
    }

    public FactoryBlock getFactoryBlockType() { return this.factoryBlock; }
}
