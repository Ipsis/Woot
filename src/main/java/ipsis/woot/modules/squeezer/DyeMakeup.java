package ipsis.woot.modules.squeezer;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public enum DyeMakeup {

    /**
     * Red/Yellow/Blue/White breakdown for each of the standard 16 Minecraft colors
     * Dye tags are provided by Forge
     */
    BLACK("black", DyeMakeup.LCM / 3, DyeMakeup.LCM / 3, DyeMakeup.LCM / 3, 0, Tags.Items.DYES_BLACK),
    RED("red", DyeMakeup.LCM, 0, 0, 0, Tags.Items.DYES_RED),
    GREEN("green", 0, DyeMakeup.LCM / 2, DyeMakeup.LCM / 2, 0, Tags.Items.DYES_GREEN),
    BROWN("brown", 3 * (DyeMakeup.LCM / 4), DyeMakeup.LCM / 8, DyeMakeup.LCM / 8, 0, Tags.Items.DYES_BROWN),
    BLUE("blue", 0, 0, DyeMakeup.LCM, 0, Tags.Items.DYES_BLUE),
    PURPLE("purple", DyeMakeup.LCM / 2, 0, DyeMakeup.LCM / 2, 0, Tags.Items.DYES_PURPLE),
    CYAN("cyan", 0, 0, DyeMakeup.LCM / 4, 3 * (DyeMakeup.LCM / 4), Tags.Items.DYES_CYAN),
    LIGHTGRAY("light_gray", DyeMakeup.LCM / 9, DyeMakeup.LCM / 9, DyeMakeup.LCM / 9, 2 * (DyeMakeup.LCM / 3), Tags.Items.DYES_LIGHT_GRAY),
    GRAY("gray", DyeMakeup.LCM / 6, DyeMakeup.LCM / 6, DyeMakeup.LCM / 6, DyeMakeup.LCM / 2, Tags.Items.DYES_GRAY),
    PINK("pink", DyeMakeup.LCM / 2, 0, 0, DyeMakeup.LCM / 2, Tags.Items.DYES_PINK),
    LIME("lime", 0, DyeMakeup.LCM / 4, DyeMakeup.LCM / 4, DyeMakeup.LCM / 2, Tags.Items.DYES_LIME),
    YELLOW("yellow", 0, DyeMakeup.LCM, 0, 0, Tags.Items.DYES_YELLOW),
    LIGHTBLUE("light_blue", 0, 0, DyeMakeup.LCM / 2, DyeMakeup.LCM / 2, Tags.Items.DYES_LIGHT_BLUE),
    MAGENTA("magenta", DyeMakeup.LCM / 2, 0, DyeMakeup.LCM / 4, DyeMakeup.LCM / 4, Tags.Items.DYES_MAGENTA),
    ORANGE("orange", DyeMakeup.LCM / 2, DyeMakeup.LCM / 2, 0, 0, Tags.Items.DYES_ORANGE),
    WHITE("white", 0, 0, 0, DyeMakeup.LCM, Tags.Items.DYES_WHITE);

    public static final int LCM = 72;

    private int red;
    private int yellow;
    private int blue;
    private int white;
    private String tag;
    private ITag.INamedTag<Item> itemTag;

    DyeMakeup(String tag, int red, int yellow, int blue, int white, ITag.INamedTag<Item> itemTag) {
        this.tag = tag;
        this.red = red;
        this.yellow = yellow;
        this.blue = blue;
        this.white = white;
        this.itemTag = itemTag;
    }

    public int getRed() { return this.red; }
    public int getYellow() { return this.yellow; }
    public int getBlue() { return this.blue; }
    public int getWhite() { return this.white; }
    public ResourceLocation getForgeTag() { return new ResourceLocation("forge", "dyes/" + this.tag); }
    public ITag.INamedTag<Item> getItemTag() { return itemTag; }

    public static final DyeMakeup[] VALUES = DyeMakeup.values();
}
