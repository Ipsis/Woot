package ipsis.woot.tileentity;

import ipsis.woot.multiblock.EnumMobFactoryModule;
import net.minecraft.util.math.BlockPos;

public class StructureLayoutBlockInfo implements ILayoutBlockInfo {

    public BlockPos blockPos;
    public EnumMobFactoryModule module;

    public StructureLayoutBlockInfo(BlockPos blockPos, EnumMobFactoryModule module) {

        this.blockPos = blockPos;
        this.module = module;
    }

    @Override
    public String toString() {

        return this.blockPos + ":" + module;
    }

    @Override
    public BlockPos getPos() {
        return blockPos;
    }

    @Override
    public void offsetY(int offset) {

        this.blockPos = blockPos.up(offset);
    }
}
