package ipsis.woot.tileentity.multiblock;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;

public enum EnumMobFactorySize {

    // All mappings are when facing south
    // When facing south, left is east (+z), right is west (-z)
    SIZE_ONE() {
        @Override
        void buildStructureMap() {

            // Level 0
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    if (x == 0 && z == 0)
                        continue;
                    structureModules.add(new MobFactoryModule(new BlockPos(x, 0, z), EnumMobFactoryModule.BLOCK_1));
                }
            }
            structureModules.add(new MobFactoryModule(new BlockPos(0, 0, 1), EnumMobFactoryModule.BLOCK_1));
            structureModules.add(new MobFactoryModule(new BlockPos(0, 0, -1), EnumMobFactoryModule.BLOCK_1));
/*
            // Level 1
            structureModules.add(new MobFactoryModule(new BlockPos(0, 1, 1), EnumMobFactoryModule.BLOCK_1));
            structureModules.add(new MobFactoryModule(new BlockPos(-2, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(0, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(2, 1, 2), EnumMobFactoryModule.BLOCK_2));

            // Level 2
            structureModules.add(new MobFactoryModule(new BlockPos(-2, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(0, 1, 2), EnumMobFactoryModule.BLOCK_2));
            structureModules.add(new MobFactoryModule(new BlockPos(2, 1, 2), EnumMobFactoryModule.BLOCK_2)); */
        }
    },
    SIZE_TWO() {

    },
    SIZE_THREE() {

    };

    ArrayList<MobFactoryModule> structureModules = new ArrayList<MobFactoryModule>();

    void buildStructureMap() { }
}
