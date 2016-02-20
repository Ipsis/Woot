package ipsis.woot.tileentity.multiblock;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

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
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_1));
            }
            for (int x = -2; x <= 2; x+=4) {
                for (int z = -1; z <= 1; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_1));
            }
        }
    },
    TIER_TWO() {
        @Override
        void buildStructureMap() {

            structureModules.addAll(TIER_ONE.getStructureModules());

            for (int z = -3; z <= 3; z+=6) {
                for (int x = -3; x <= 3; x++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_2));
            }
            for (int x = -3; x <= 3; x+=6) {
                for (int z = -2; z <= 2; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_2));
            }
        }
    },
    TIER_THREE() {
        @Override
        void buildStructureMap() {

            structureModules.addAll(TIER_TWO.getStructureModules());

            for (int z = -4; z <= 4; z+=8) {
                for (int x = -4; x <= 4; x++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_3));
            }
            for (int x = -4; x <= 4; x+=8) {
                for (int z = -3; z <= 3; z++)
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_3));
            }
        }
    };

    ArrayList<MobFactoryModule> structureModules = new ArrayList<MobFactoryModule>();

    public List<MobFactoryModule> getStructureModules() { return structureModules; }

    void buildStructureMap() { }
}
