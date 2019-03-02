package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.factory.layout.IFactoryBlockProvider;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemUseContext;

import java.util.List;

public class BlockCell extends WootBlock implements IWootDebug, IFactoryBlockProvider {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockCell(FactoryBlock factoryBlock) {
        super(Block.Properties.create(Material.ROCK), factoryBlock.getName());
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add(String.format("====> BlockCell {}", factoryBlock.getName()));
        return debug;
    }

    /**
     * IFactoryBlockProvider
     */
    public FactoryBlock getFactoryBlock() { return this.factoryBlock; }
}
