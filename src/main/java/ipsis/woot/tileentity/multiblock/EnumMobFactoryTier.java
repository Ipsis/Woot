package ipsis.woot.tileentity.multiblock;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * This is based off the approach that WayOfTime handled the patterns for the BloodMagic Blood Altar.
 * BloodMagic/src/main/java/WayofTime/bloodmagic/api/altar/EnumAltarTier.java
 */

public enum EnumMobFactoryTier {

    // All mappings are when facing south
    // When facing south, left is east (+z), right is west (-z)
    TIER_ONE() {
        @Override
        void buildStructureMap() {

            structureModules.add(new MobFactoryModule(new BlockPos(0, 1, 1), EnumMobFactoryModule.BLOCK_1));

            // Level 0
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    if (x == 0 && z == 0)
                        continue;
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_1));
                }
            }

            for (int z = -2; z <= 2; z+=4) {
                for (int x = -2; x <= 2; x++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_2));
            }
            for (int x = -2; x <= 2; x+=4) {
                for (int z = -1; z <= 1; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_2));
            }

            structureModules.add(new MobFactoryModule(new BlockPos(2, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(2, 2, 2), EnumMobFactoryModule.BLOCK_5));
            structureModules.add(new MobFactoryModule(new BlockPos(-2, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(-2, 2, 2), EnumMobFactoryModule.BLOCK_5));
        }
    },
    TIER_TWO() {
        @Override
        void buildStructureMap() {

            structureModules.add(new MobFactoryModule(new BlockPos(0, 1, 1), EnumMobFactoryModule.BLOCK_1));

            // Level 0
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    if (x == 0 && z == 0)
                        continue;
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_1));
                }
            }

            for (int z = -2; z <= 2; z+=4) {
                for (int x = -2; x <= 2; x++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_2));
            }
            for (int x = -2; x <= 2; x+=4) {
                for (int z = -1; z <= 1; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_2));
            }

            structureModules.add(new MobFactoryModule(new BlockPos(2, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(2, 2, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(-2, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(-2, 2, 2), EnumMobFactoryModule.BLOCK_2));


            for (int z = -3; z <= 3; z+=6) {
                for (int x = -3; x <= 3; x++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_3));
            }
            for (int x = -3; x <= 3; x+=6) {
                for (int z = -2; z <= 2; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_3));
            }

            for (int y = 1; y <= 3; y++) {
                structureModules.add(new MobFactoryModule(new BlockPos(3, y, -3), EnumMobFactoryModule.BLOCK_3));
                structureModules.add(new MobFactoryModule(new BlockPos(3, y, 3), EnumMobFactoryModule.BLOCK_3));
                structureModules.add(new MobFactoryModule(new BlockPos(-3, y, 3), EnumMobFactoryModule.BLOCK_3));
                structureModules.add(new MobFactoryModule(new BlockPos(-3, y, -3), EnumMobFactoryModule.BLOCK_3));
            }

            structureModules.add(new MobFactoryModule(new BlockPos(1, 1, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(1, 2, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(-1, 1, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(-1, 2, 3), EnumMobFactoryModule.BLOCK_3));

            structureModules.add(new MobFactoryModule(new BlockPos(3, 4, 3), EnumMobFactoryModule.BLOCK_5));
            structureModules.add(new MobFactoryModule(new BlockPos(1, 3, 3), EnumMobFactoryModule.BLOCK_5));
            structureModules.add(new MobFactoryModule(new BlockPos(-1, 3, 3), EnumMobFactoryModule.BLOCK_5));
            structureModules.add(new MobFactoryModule(new BlockPos(-3, 4, 3), EnumMobFactoryModule.BLOCK_5));
        }
    },
    TIER_THREE() {
        @Override
        void buildStructureMap() {

            structureModules.add(new MobFactoryModule(new BlockPos(0, 1, 1), EnumMobFactoryModule.BLOCK_1));

            // Level 0
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    if (x == 0 && z == 0)
                        continue;
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_1));
                }
            }

            for (int z = -2; z <= 2; z+=4) {
                for (int x = -2; x <= 2; x++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_2));
            }
            for (int x = -2; x <= 2; x+=4) {
                for (int z = -1; z <= 1; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_2));
            }

            structureModules.add(new MobFactoryModule(new BlockPos(2, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(-2, 1, 2), EnumMobFactoryModule.BLOCK_2));


            for (int z = -3; z <= 3; z+=6) {
                for (int x = -3; x <= 3; x++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_3));
            }
            for (int x = -3; x <= 3; x+=6) {
                for (int z = -2; z <= 2; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_3));
            }

            for (int y = 1; y <= 3; y++) {
                structureModules.add(new MobFactoryModule(new BlockPos(3, y, -3), EnumMobFactoryModule.BLOCK_3));
                structureModules.add(new MobFactoryModule(new BlockPos(-3, y, -3), EnumMobFactoryModule.BLOCK_3));
            }

            for (int y = 1; y <= 4; y++) {
                structureModules.add(new MobFactoryModule(new BlockPos(3, y, 3), EnumMobFactoryModule.BLOCK_3));
                structureModules.add(new MobFactoryModule(new BlockPos(-3, y, 3), EnumMobFactoryModule.BLOCK_3));
            }

            structureModules.add(new MobFactoryModule(new BlockPos(1, 1, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(1, 2, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(1, 3, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(-1, 1, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(-1, 2, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(-1, 3, 3), EnumMobFactoryModule.BLOCK_3));

            structureModules.add(new MobFactoryModule(new BlockPos(3, 4, 3), EnumMobFactoryModule.BLOCK_3));
            structureModules.add(new MobFactoryModule(new BlockPos(-3, 4, 3), EnumMobFactoryModule.BLOCK_3));

            for (int z = -4; z <= 4; z+=8) {
                for (int x = -4; x <= 4; x++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_4));
            }
            for (int x = -4; x <= 4; x+=8) {
                for (int z = -3; z <= 3; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_4));
            }

            for (int y = 1; y <= 4; y++) {
                structureModules.add(new MobFactoryModule(new BlockPos(4, y, -4), EnumMobFactoryModule.BLOCK_4));
                structureModules.add(new MobFactoryModule(new BlockPos(4, y, 4), EnumMobFactoryModule.BLOCK_4));
                structureModules.add(new MobFactoryModule(new BlockPos(-4, y, 4), EnumMobFactoryModule.BLOCK_4));
                structureModules.add(new MobFactoryModule(new BlockPos(-4, y, -4), EnumMobFactoryModule.BLOCK_4));
                structureModules.add(new MobFactoryModule(new BlockPos(0, y, 4), EnumMobFactoryModule.BLOCK_4));
            }

            structureModules.add(new MobFactoryModule(new BlockPos(4, 5, 4), EnumMobFactoryModule.BLOCK_5));
            structureModules.add(new MobFactoryModule(new BlockPos(0, 5, 4), EnumMobFactoryModule.BLOCK_5));
            structureModules.add(new MobFactoryModule(new BlockPos(-4, 5, 4), EnumMobFactoryModule.BLOCK_5));
            structureModules.add(new MobFactoryModule(new BlockPos(4, 5, -4), EnumMobFactoryModule.BLOCK_5));
            structureModules.add(new MobFactoryModule(new BlockPos(-4, 5, -4), EnumMobFactoryModule.BLOCK_5));
        }
    };

    ArrayList<MobFactoryModule> structureModules = new ArrayList<MobFactoryModule>();

    public List<MobFactoryModule> getStructureModules() { return structureModules; }

    void buildStructureMap() { }

    public static EnumMobFactoryTier getTier(int v) {

        if (v < 0 || v > values().length - 1)
            v = 0;

        return values()[v];
    }
}
