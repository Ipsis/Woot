package ipsis.woot.tileentity.multiblock;

import ipsis.woot.util.StringHelper;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * This is based off the approach that WayOfTime handled the patterns for the BloodMagic Blood Altar.
 * BloodMagic/src/main/java/WayofTime/bloodmagic/api/altar/EnumAltarTier.java
 */

public enum EnumMobFactoryTier {

    TIER_ONE() {
        @Override
        void buildStructureMap() {

            String pattern[][] = {
                    {
                            "-rgr-",
                            "rgrgr",
                            "gr-rg",
                            "rgrgr",
                            "-rgr-"
                    },
                    {
                            "-----",
                            "-----",
                            "--x--",
                            "-pgp-",
                            "-rgr-"
                    },
                    {
                            "-----",
                            "-----",
                            "-----",
                            "-----",
                            "-1-1-"
                    }
            };
            parsePattern(structureModules, pattern, 1, 2, 2);
        }
    },
    TIER_TWO() {
        @Override
        void buildStructureMap() {

            String pattern[][] = {
                    {
                            "--ooo--",
                            "-orgro-",
                            "orgrgro",
                            "ogr-rgo",
                            "orgrgro",
                            "-orgro-",
                            "--ooo--"
                    },
                    {
                            "-------",
                            "-------",
                            "-------",
                            "o--x--o",
                            "oppgppo",
                            "-orgro-",
                            "--ooo--",
                    },
                    {
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "o-----o",
                            "-o1-1o-",
                            "--o-o--",
                    },
                    {
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "-o---o-",
                            "--o-o--",
                    },
                    {
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "-2---2-",
                            "--2-2--",
                    }
            };
            parsePattern(structureModules, pattern, 1, 3, 3);
        }
    },
    TIER_THREE() {
        @Override
        void buildStructureMap() {

            String pattern[][] = {
                    {
                            "---hhh---",
                            "--hoooh--",
                            "-horgroh-",
                            "horgrgroh",
                            "hogr-rgoh",
                            "horgrgroh",
                            "-horgroh-",
                            "--hoooh--",
                            "---hhh---",
                    },
                    {
                            "---------",
                            "---------",
                            "---------",
                            "h-------h",
                            "ho--x--oh",
                            "hoppgppoh",
                            "-horgroh-",
                            "--hoooh--",
                            "---hhh---"
                    },
                    {
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "h-------h",
                            "ho-----oh",
                            "-ho1-1oh-",
                            "--ho-oh--",
                            "---hhh---",
                    },
                    {
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "h-------h",
                            "-ho---oh-",
                            "--ho-oh--",
                            "---hhh---",
                    },
                    {
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "-h2---2h-",
                            "--h2-2h--",
                            "---hhh---",
                    },
                    {
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "-3-----3-",
                            "--3---3--",
                            "----3----"
                    }
            };
            parsePattern(structureModules, pattern, 1, 4, 4);
        }
    },
    TIER_FOUR() {
        @Override
        void buildStructureMap() {
            String pattern[][] = {
                    {
                            "----www----",
                            "---whhhw---",
                            "--whooohw--",
                            "-whorgrohw-",
                            "whorgrgrohw",
                            "whogr-rgohw",
                            "whorgrgrohw",
                            "-whorgrohw-",
                            "--whooohw--",
                            "---whhhw---",
                            "----www----"
                    },
                    {
                            "-----------",
                            "-----------",
                            "--w-----w--",
                            "-w-------w-",
                            "wh-------hw",
                            "who--x--ohw",
                            "whoppgppohw",
                            "-whorgrohw-",
                            "--whooohw--",
                            "---whhhw---",
                            "----www----"
                    },
                    {
                            "-----------",
                            "-----------",
                            "-----------",
                            "-w-------w-",
                            "w---------w",
                            "wh-------hw",
                            "who-----ohw",
                            "-who1-1ohw-",
                            "--who-ohw--",
                            "---whhhw---",
                            "----www----"
                    },
                    {
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "w---------w",
                            "w---------w",
                            "wh-------hw",
                            "-who---ohw-",
                            "--who-ohw--",
                            "---whhhw---",
                            "----www----"
                    },
                    {
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "w---------w",
                            "w---------w",
                            "-wh2---2hw-",
                            "--wh2-2hw--",
                            "---whhhw---",
                            "----www----"
                    },
                    {
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "w---------w",
                            "-w3-----3w-",
                            "---3---3---",
                            "---w-3-w---",
                            "-----w-----"
                    },
                    {
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "-----------",
                            "4---------4",
                            "-4-------4-",
                            "-----------",
                            "---4---4---",
                            "-----4-----"
                    }
            };

            parsePattern(structureModules, pattern, 1, 5, 5);
        }
    };

    public String getTranslated(String format) {

        return String.format(StringHelper.localize(format),
                this == TIER_ONE ? "I" : this == TIER_TWO ? "II" : this == TIER_THREE ? "III" : "IV");
    }

    /**
     *
     * @return the offset from center of the widest factory tier
     */
    public static int getMaxXZOffset() {
        return 5;
    }

    /**
     * @return the height of the tallest factory tier
     */
    public static int getMaxYOffset() {
        return 8;
    }

    public static final EnumMobFactoryTier[] VALID_TIERS = new EnumMobFactoryTier[] { TIER_ONE, TIER_TWO, TIER_THREE, TIER_FOUR };

    public EnumMobFactoryTier getNext() {

        int next = ordinal();
        next++;

        if (next < 0 || next >= VALID_TIERS.length)
            next = 0;

        return VALID_TIERS[next];
    }

    private static void parsePattern(List<MobFactoryModule> modules, String[][] pattern, int originLayer, int originRow, int originColumn) {

        for (int currLayer = 0; currLayer < pattern.length; currLayer++) {
            int dLayer = (originLayer - currLayer) * -1;
            for (int currRow = 0; currRow < pattern[0].length; currRow++) {
                int dRow = (originRow - currRow) * -1;
                for (int currCol = 0; currCol < pattern[0][0].length(); currCol++) {
                    char c = pattern[currLayer][currRow].charAt(currCol);
                    int dCol = (originColumn - currCol) * -1;

                    // - = anything, o = origin marker
                    if (c == '-' || c == 'x')
                        continue;

                    EnumMobFactoryModule m = EnumMobFactoryModule.byChar(c);
                    modules.add(new MobFactoryModule(new BlockPos(dCol, dLayer, dRow), m));
                }
            }
        }
    }

    ArrayList<MobFactoryModule> structureModules = new ArrayList<MobFactoryModule>();

    public List<MobFactoryModule> getStructureModules() {
        return structureModules;
    }

    void buildStructureMap() {
    }

    public static EnumMobFactoryTier getTier(int v) {

        if (v < 0 || v > values().length - 1)
            v = 0;

        return values()[v];
    }
}
