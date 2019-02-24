package ipsis.woot.factory.layout;

public enum FactoryBlock {

    BONE("bone_structure"),
    FLESH("flesh_structure"),
    BLAZE("blaze_structure"),
    ENDER("ender_structure"),
    NETHER("nether_structure"),
    REDSTONE("redstone_structure"),
    UPGRADE("upgrade_structure"),
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
