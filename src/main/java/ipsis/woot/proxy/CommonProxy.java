package ipsis.woot.proxy;

public class CommonProxy {

    public void preInit() {

        registerBlockItemModels();
        registerItemRenderers();
        registerEventHandlers();
    }

    public void init() {
    }
    public void postInit() {
    }

    protected void registerBlockItemModels() { }
    protected void registerItemRenderers() { }
    protected void registerEventHandlers() { }
}
