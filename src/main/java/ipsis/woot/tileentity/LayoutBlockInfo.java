package ipsis.woot.tileentity;

import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
import net.minecraft.util.math.BlockPos;

public class LayoutBlockInfo {

    public BlockPos blockPos;
    public EnumMobFactoryModule module;

    public LayoutBlockInfo(BlockPos blockPos, EnumMobFactoryModule module) {

        this.blockPos = blockPos;
        this.module = module;
    }

    @Override
    public String toString() {

        return this.blockPos + ":" + module;
    }
}
