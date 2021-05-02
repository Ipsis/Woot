package ipsis.woot.modules.factory.blocks;

import ipsis.woot.base.WootBlock;
import ipsis.woot.modules.factory.ComponentType;
import ipsis.woot.modules.factory.ComponentTypeProvider;
import ipsis.woot.modules.factory.FactoryModule;
import net.minecraft.block.AbstractBlock;

public class FactoryBlock extends WootBlock implements ComponentTypeProvider {

    private final ComponentType componentType;

    public FactoryBlock(ComponentType componentType) {
        super(FactoryModule.getDefaultBlockProperties());
        this.componentType = componentType;
    }

    public FactoryBlock(ComponentType componentType, AbstractBlock.Properties properties) {
        super(properties);
        this.componentType = componentType;
    }

    // Start ComponentTypeProvider
    @Override
    public ComponentType getComponentType() { return componentType; }
    // End ComponentTypeProvider

}
