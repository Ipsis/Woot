package ipsis.woot.factory.layout;

public enum FactoryBlock {

    BONE("factory_bone"),
    FLESH("factory_flesh"),
    BLAZE("factory_blaze"),
    ENDER("factory_ender"),
    NETHER("factory_nether"),
    REDSTONE("factory_redstone"),
    UPGRADE("factory_upgrade"),
    CAP_1("cap_1"),
    CAP_2("cap_2"),
    CAP_3("cap_3"),
    CAP_4("cap_4"),
    CONTROLLER("controller"),
    HEART("heart"),
    CELL_1("cell_1"),
    CELL_2("cell_2"),
    CELL_3("cell_3"),
    IMPORT("import"),
    EXPORT("export");

    private String name;
    FactoryBlock(String name) {
        this.name = name;
    }
    public String getName() { return this.name; }

}
