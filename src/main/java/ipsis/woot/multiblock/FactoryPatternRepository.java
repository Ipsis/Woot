package ipsis.woot.multiblock;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.ILayoutBlockInfo;
import ipsis.woot.tileentity.StructureLayoutBlockInfo;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This is based off the approach that WayOfTime handled the patterns for the BloodMagic Blood Altar.
 * BloodMagic/src/main/java/WayofTime/bloodmagic/api/altar/EnumAltarTier.java
 */

public class FactoryPatternRepository {

    private int maxYOffset = 0;
    private int maxXZOffset = 0;
    private HashMap<EnumMobFactoryTier, FactoryTierPattern> tiers = new HashMap<>();

    public FactoryPatternRepository() {

        load(EnumMobFactoryTier.TIER_ONE, TIER_ONE_PATTERN);
        load(EnumMobFactoryTier.TIER_TWO, TIER_TWO_PATTERN);
        load(EnumMobFactoryTier.TIER_THREE, TIER_THREE_PATTERN);
        load(EnumMobFactoryTier.TIER_FOUR, TIER_FOUR_PATTERN);
    }

    private void load(EnumMobFactoryTier tier, String[][] pattern) {

        if (pattern.length == 0) {
            LogHelper.info("FactorPatternRepository.load: pattern length is invalid");
            return;
        }

        int height = pattern.length;
        int width = pattern[0].length;

        if (width == 0) {
            LogHelper.info("FactorPatternRepository.load: pattern row count is invalid");
            return;
        }

        int depth = pattern[0][0].length();
        if (depth == 0) {
            LogHelper.info("FactorPatternRepository.load: pattern column count is invalid");
            return;
        }

        FactoryTierPattern tierPattern = new FactoryTierPattern();
        tierPattern.setHeight(height);
        tierPattern.setWidth(width);

        /**
         * Find the origin first
         */
        boolean hasOrigin = false;
        for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < width; row++) {
                for (int col = 0; col < depth; col++) {
                    char c = pattern[layer][row].charAt(col);
                    if (isOrigin(c)) {
                        tierPattern.setOrigin(layer, row, col);
                        hasOrigin = true;
                    }
                }
            }
        }

        if (!hasOrigin) {
            LogHelper.info("FactorPatternRepository.load: pattern has no origin");
            return;
        }


        for (int layer = 0; layer < height; layer++) {
            for (int row = 0; row < width; row++) {
                if (pattern[layer][row].length() != depth) {
                    LogHelper.info("FactorPatternRepository.load: pattern row is too short");
                    return;
                }

                for (int col = 0; col < depth; col++) {
                    char c = pattern[layer][row].charAt(col);

                    if (isSpace(c) || isOrigin(c)) {
                        continue;
                    } else if (EnumMobFactoryModule.isValidChar(c)) {
                        EnumMobFactoryModule m = EnumMobFactoryModule.byChar(c);
                        tierPattern.incBlockCount(m);
                        tierPattern.addModules(new MobFactoryModule(tierPattern.calcBlockPos(layer, row, col), m));
                    } else {
                        LogHelper.info(String.format("FactoryPatternRepository.load: invalid character (%d,%d,%d) %c", layer, row, col, c));
                        return;
                    }

                }
            }
        }

        tiers.put(tier, tierPattern);

        if (maxYOffset == 0 || maxYOffset < height)
            maxYOffset = height;

        if (maxXZOffset == 0 || maxXZOffset < tierPattern.getOriginCol() / 2)
            maxXZOffset = width;
    }

    private boolean isSpace(char c) {
        return c == '-';
    }

    private boolean isOrigin(char c) {
        return c == 'x';
    }

    private @Nullable MobFactoryModule getMobFactoryModule(EnumMobFactoryTier tier, BlockPos offset) {

        if (!tiers.containsKey(tier))
            return null;

        for (MobFactoryModule m : tiers.get(tier).modules) {
            if (m.offset.equals(offset))
                return m;
        }

        return null;
    }

    public int getMaxXZOffset() {
        return maxXZOffset;
    }

    public int getMaxYOffset() {
        return maxYOffset;
    }

    public @Nullable EnumMobFactoryModule getModule(EnumMobFactoryTier tier, BlockPos offset) {

        MobFactoryModule module = getMobFactoryModule(tier, offset);
        return module != null ? module.moduleType : null;
    }

    public boolean isValid(EnumMobFactoryTier tier, EnumMobFactoryModule m, BlockPos offset) {

        MobFactoryModule module = getMobFactoryModule(tier, offset);
        return module != null && module.moduleType == m;
    }

    public List<MobFactoryModule> getAllModules(EnumMobFactoryTier tier) {

        return tiers.containsKey(tier) ? tiers.get(tier).modules : Collections.emptyList();
    }

    public void getFactoryLayout(EnumMobFactoryTier tier, BlockPos origin, EnumFacing facing, List<ILayoutBlockInfo> layoutBlockInfoList) {

        if (!tiers.containsKey(tier))
            return;

        for (MobFactoryModule s : tiers.get(tier).getModules()) {

            BlockPos p = BlockPosHelper.rotateFromSouth(s.getOffset(), facing.getOpposite());
            p = origin.add(p);
            layoutBlockInfoList.add(new StructureLayoutBlockInfo(p, s.moduleType));
        }
    }

    public int getTierHeight(EnumMobFactoryTier tier) {

        return tiers.get(tier).getHeight();
    }

    public int getBlockCount(EnumMobFactoryTier tier, EnumMobFactoryModule m) {

        return tiers.get(tier).getBlockCount(m);
    }

    private class FactoryTierPattern {

        private int originLayer;
        private int originRow;
        private int originCol;
        private int width;
        private int height;
        private HashMap<EnumMobFactoryModule, Integer> blockCounts = new HashMap<>();
        private List<MobFactoryModule> modules = new ArrayList<>();

        public void setOrigin(int layer, int row, int col) {
            this.originLayer = layer;
            this.originRow = row;
            this.originCol = col;
        }

        public BlockPos calcBlockPos(int layer, int row, int col) {

            int l = (originLayer - layer) * -1;
            int r = (originRow - row) * -1;
            int c = (originCol - col) * -1;

            return new BlockPos(c, l, r);
        }

        public void setWidth(int width) { this.width = width; }
        public void setHeight(int height) { this.height = height; }

        public int getOriginLayer() { return this.originLayer; }
        public int getOriginRow() { return this.originRow; }
        public int getOriginCol() { return this.originCol; }
        public int getWidth() { return this.width; }
        public int getHeight() { return this.height; }

        public int getBlockCount(EnumMobFactoryModule m) {

            return blockCounts.containsKey(m) ? blockCounts.get(m) : 0;
        }

        public void incBlockCount(EnumMobFactoryModule m) {

            if (!blockCounts.containsKey(m))
                blockCounts.put(m, 0);

            blockCounts.put(m, blockCounts.get(m) + 1);
        }

        public void addModules(MobFactoryModule m) {
            modules.add(m);
        }

        public List<MobFactoryModule> getModules() {
            return this.modules;
        }
    }

    /**
     * Patterns
     */
    public static final String TIER_ONE_PATTERN[][] = {
            {
                    "-rrr-",
                    "rgggr",
                    "rg-gr",
                    "rgggr",
                    "-rrr-"
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

    public static final String TIER_TWO_PATTERN[][] = {
            {
                    "--ooo--",
                    "-orrro-",
                    "orgggro",
                    "org-gro",
                    "orgggro",
                    "-orrro-",
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

    public static final String TIER_THREE_PATTERN[][] = {
            {
                    "---hhh---",
                    "--hoooh--",
                    "-horrroh-",
                    "horgggroh",
                    "horg-groh",
                    "horgggroh",
                    "-horrroh-",
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

    public static final String TIER_FOUR_PATTERN[][] = {

            {
                    "----www----",
                    "---whhhw---",
                    "--whooohw--",
                    "-whorrrohw-",
                    "whorgggrohw",
                    "whorg-grohw",
                    "whorgggrohw",
                    "-whorrrohw-",
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
}
