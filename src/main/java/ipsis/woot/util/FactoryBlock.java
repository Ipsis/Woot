package ipsis.woot.util;

public enum FactoryBlock {

    BONE("bone_structure", WootColor.WHITE),
    FLESH("flesh_structure", WootColor.BROWN),
    BLAZE("blaze_structure", WootColor.ORANGE),
    ENDER("ender_structure", WootColor.GREEN),
    NETHER("nether_structure", WootColor.LIGHTGRAY),
    REDSTONE("redstone_structure", WootColor.RED),
    UPGRADE("upgrade_structure", WootColor.PURPLE),
    CAP_1("cap1", WootColor.CYAN),
    CAP_2("cap2", WootColor.CYAN),
    CAP_3("cap3", WootColor.CYAN),
    CAP_4("cap4", WootColor.CYAN),
    TOTEM("totem", WootColor.LIME),
    CONTROLLER("controller", WootColor.YELLOW),
    HEART("heart", WootColor.PINK),
    POWER("power", WootColor.YELLOW),
    IMPORT("import", WootColor.YELLOW),
    EXPORT("export", WootColor.YELLOW);

    private String name;
    FactoryBlock(String name, WootColor color) {
        this.name = name;
        this.color = color;
    }
    public String getName() {
        return this.name;
    }

    private WootColor color;
    public WootColor getColor() { return this.color; }
}
