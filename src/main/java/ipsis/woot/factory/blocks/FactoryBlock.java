package ipsis.woot.factory.blocks;

import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryComponentProvider;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class FactoryBlock extends WootBlock implements FactoryComponentProvider {

    private final FactoryComponent component;

    public FactoryBlock(FactoryComponent component) {
        super(Block.Properties.create(Material.IRON), component.getName());
        this.component = component;
    }

    /**
     * FactoryComponentProvider
     */
    public FactoryComponent getFactoryComponent() {
        return this.component;
    }
}
