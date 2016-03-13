package ipsis.woot.tileentity.multiblock;

import net.minecraft.util.BlockPos;

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
                            "bbbbb",
                            "baaab",
                            "ba-ab",
                            "baaab",
                            "bbbbb"
                    },
                    {
                            "-----",
                            "-----",
                            "--o--",
                            "--a--",
                            "b---b"
                    },
                    {
                            "-----",
                            "-----",
                            "-----",
                            "-----",
                            "x---x"
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
                            "ccccccc",
                            "cbbbbbc",
                            "cbaaabc",
                            "cba-abc",
                            "cbaaabc",
                            "cbbbbbc",
                            "ccccccc"
                    },
                    {
                            "c-----c",
                            "-------",
                            "-------",
                            "---o---",
                            "---a---",
                            "-b---b-",
                            "c-c-c-c"
                    },
                    {
                            "c-----c",
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "-b---b-",
                            "c-c-c-c"
                    },
                    {
                            "c-----c",
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "c-y-y-c"
                    },
                    {
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "-------",
                            "y-----y"
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
                            "ddddddddd",
                            "dcccccccd",
                            "dcbbbbbcd",
                            "dcbaaabcd",
                            "dcba-abcd",
                            "dcbaaabcd",
                            "dcbbbbbcd",
                            "dcccccccd",
                            "ddddddddd"
                    },
                    {
                            "d-------d",
                            "-c-----c-",
                            "---------",
                            "---------",
                            "----o----",
                            "---------",
                            "--b---b--",
                            "-c-c-c-c-",
                            "d---d---d"
                    },
                    {
                            "d-------d",
                            "-c-----c-",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "-c-c-c-c-",
                            "d---d---d"
                    },
                    {
                            "d-------d",
                            "-c-----c-",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "-c-c-c-c-",
                            "d---d---d"
                    },
                    {
                            "d-------d",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "-c-----c-",
                            "d---d---d"
                    },
                    {
                            "z-------z",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "---------",
                            "z---z---z"
                    }
            };

            parsePattern(structureModules, pattern, 1, 4, 4);
        }
    };

    private static void parsePattern(List<MobFactoryModule> modules, String[][] pattern, int originLayer, int originRow, int originColumn) {

        for (int currLayer = 0; currLayer < pattern.length; currLayer++) {
            int dLayer = (originLayer - currLayer) * -1;
            for (int currRow = 0; currRow < pattern[0].length; currRow++) {
                int dRow = (originRow - currRow) * -1;
                for (int currCol = 0; currCol < pattern[0][0].length(); currCol++) {
                    char c = pattern[currLayer][currRow].charAt(currCol);
                    int dCol = (originColumn - currCol) * -1;

                    // - = anything, o = origin marker
                    if (c == '-' || c == 'o')
                        continue;

                    EnumMobFactoryModule m = EnumMobFactoryModule.BLOCK_1;
                    if (c == 'a')
                        m = EnumMobFactoryModule.BLOCK_1;
                    else if (c == 'b')
                        m = EnumMobFactoryModule.BLOCK_2;
                    else if (c == 'c')
                        m = EnumMobFactoryModule.BLOCK_3;
                    else if (c == 'd')
                        m = EnumMobFactoryModule.BLOCK_4;
                    else if (c == 'e')
                        m = EnumMobFactoryModule.BLOCK_5;
                    else if (c == 'x')
                        m = EnumMobFactoryModule.CAP_I;
                    else if (c == 'y')
                        m = EnumMobFactoryModule.CAP_II;
                    else if (c == 'z')
                        m = EnumMobFactoryModule.CAP_III;

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
