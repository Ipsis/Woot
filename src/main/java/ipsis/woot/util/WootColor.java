package ipsis.woot.util;

public enum WootColor {
    /**
     * Color code values taken from CoFHLib/ColorHelper.java
     */
    BLACK(0x191919),
    RED(0xCC4C4C),
    GREEN(0x667F33),
    BROWN(0x7F664C),
    BLUE(0x3366CC),
    PURPLE(0xB266E5),
    CYAN(0x4C9982),
    LIGHTGRAY(0x999999),
    GRAY(0x4C4C4C),
    PINK(0xF2B2CC),
    LIME(0x7FCC19),
    YELLOW(0xE5E533),
    LIGHTBLUE(0x99B2F2),
    MAGENTA(0xE57FD8),
    ORANGE(0xF2B233),
    WHITE(0xFFFFFF);

    public static WootColor[] VALID_COLORS = { BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHTGRAY, GRAY, PINK, LIME, YELLOW, LIGHTBLUE, MAGENTA, ORANGE, WHITE };

    public static WootColor getColor(int id) {

        if (id >= 0 && id < VALID_COLORS.length)
            return VALID_COLORS[id];

        return WootColor.WHITE;
    }

    private int v;
    private float red;
    private float green;
    private float blue;

    WootColor(int v) {

        this.v = v;

        this.red = ((v & 0xFF0000) >> 16) & 0xFF;
        this.green = ((v & 0x00FF00) >> 8) & 0xFF;
        this.blue = v & 0xFF;

        this.red = this.red / 255.0F;
        this.green = this.green / 255.0F;
        this.blue = this.blue / 255.0F;
    }

    public float getRed() {

        return this.red;
    }

    public float getGreen() {

        return this.green;
    }

    public float getBlue() {

        return this.blue;
    }

    public int getV() {

        return this.v;
    }

    public WootColor getNext() {

        int next = ordinal();
        next++;

        if (next < 0 || next >= VALID_COLORS.length)
            next = 0;

        return VALID_COLORS[next];
    }

    @Override
    public String toString() {

        return super.toString() + " " + this.v + " R:" + this.red + " G:" + this.getGreen() + " B:" + this.getBlue();
    }
}
