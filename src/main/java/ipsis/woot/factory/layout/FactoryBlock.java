package ipsis.woot.factory.layout;

public enum FactoryBlock {

    BONE("factory_bone"),
    FLESH("factory_flesh"),
    BLAZE("factory_blaze"),
    ENDER("factory_ender"),
    NETHER("factory_nether"),
    REDSTONE("factory_redstone"),
    UPGRADE("factory_upgrade"),
    CAP_1("factory_cap_1"),
    CAP_2("factory_cap_2"),
    CAP_3("factory_cap_3"),
    CAP_4("factory_cap_4"),
    CONTROLLER("factory_controller"),
    HEART("factory_heart"),
    CELL_1("factory_cell_1"),
    CELL_2("factory_cell_2"),
    CELL_3("factory_cell_3"),
    IMPORT("factory_import"),
    EXPORT("factory_export");

    public static FactoryBlock[] VALUES = values();

    private String name;
    FactoryBlock(String name) {
        this.name = name;
    }
    public String getName() { return this.name; }

    public boolean isCell() {
        return this == CELL_1 || this == CELL_2 || this == CELL_3;
    }

}
