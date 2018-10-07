package ipsis.woot.factory.structure;

import ipsis.woot.util.FactoryTier;

public class FactoryStructure {

    /**
     * Where everything is and the configuration
     */

    private FactoryTier factoryTier;
    public void setFactoryTier(FactoryTier factoryTier) { this.factoryTier = factoryTier; }
    public FactoryTier getFactoryTier() { return this.factoryTier; }
    public boolean hasFactoryTier() { return factoryTier != null; }
}
