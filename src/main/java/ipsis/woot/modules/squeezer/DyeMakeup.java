package ipsis.woot.modules.squeezer;

import net.minecraft.util.ResourceLocation;

public enum DyeMakeup {

    /**
     * Red/Yellow/Blue/White breakdown for each of the standard 16 Minecraft colors
     * Dye tags are provided by Forge
     */
    BLACK("black", DyeMakeup.LCM / 3, DyeMakeup.LCM / 3, DyeMakeup.LCM / 3, 0),
    RED("red", DyeMakeup.LCM, 0, 0, 0),
    GREEN("green", 0, DyeMakeup.LCM / 2, DyeMakeup.LCM / 2, 0),
    BROWN("brown", 3 * (DyeMakeup.LCM / 4), DyeMakeup.LCM / 8, DyeMakeup.LCM / 8, 0),
    BLUE("blue", 0, 0, DyeMakeup.LCM, 0),
    PURPLE("purple", DyeMakeup.LCM / 2, 0, DyeMakeup.LCM / 2, 0),
    CYAN("cyan", 0, 0, DyeMakeup.LCM / 4, 3 * (DyeMakeup.LCM / 4)),
    LIGHTGRAY("light_gray", DyeMakeup.LCM / 9, DyeMakeup.LCM / 9, DyeMakeup.LCM / 9, 2 * (DyeMakeup.LCM / 3)),
    GRAY("gray", DyeMakeup.LCM / 6, DyeMakeup.LCM / 6, DyeMakeup.LCM / 6, DyeMakeup.LCM / 2),
    PINK("pink", DyeMakeup.LCM / 2, 0, 0, DyeMakeup.LCM / 2),
    LIME("lime", 0, DyeMakeup.LCM / 4, DyeMakeup.LCM / 4, DyeMakeup.LCM / 2),
    YELLOW("yellow", 0, DyeMakeup.LCM, 0, 0),
    LIGHTBLUE("light_blue", 0, 0, DyeMakeup.LCM / 2, DyeMakeup.LCM / 2),
    MAGENTA("magenta", DyeMakeup.LCM / 2, 0, DyeMakeup.LCM / 4, DyeMakeup.LCM / 4),
    ORANGE("orange", DyeMakeup.LCM / 2, DyeMakeup.LCM / 2, 0, 0),
    WHITE("white", 0, 0, 0, DyeMakeup.LCM);

    public static final int LCM = 72;

    private int red;
    private int yellow;
    private int blue;
    private int white;
    private String tag;

    DyeMakeup(String tag, int red, int yellow, int blue, int white) {
        this.tag = tag;
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
        this.white = white;
    }

    public int getRed() { return this.red; }
    public int getYellow() { return this.yellow; }
    public int getBlue() { return this.blue; }
    public int getWhite() { return this.white; }
    public ResourceLocation getForgeTag() { return new ResourceLocation("forge", "dyes/" + this.tag); }

    public static final DyeMakeup[] VALUES = DyeMakeup.values();
}
