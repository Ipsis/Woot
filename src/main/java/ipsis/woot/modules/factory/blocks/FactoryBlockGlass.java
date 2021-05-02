package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.factory.ComponentType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FactoryBlockGlass extends FactoryBlock {

    public FactoryBlockGlass() {
        super(ComponentType.BASE_GLASS,
                Properties.of(Material.GLASS)
                        .strength(0.3F)
                        .sound(SoundType.GLASS)
                        .noOcclusion()
                        .isValidSpawn(FactoryBlockGlass::never)
                        .isRedstoneConductor(FactoryBlockGlass::never)
                        .isSuffocating(FactoryBlockGlass::never)
                        .isViewBlocking(FactoryBlockGlass::never));
    }

    // From Blocks.java to match up with vanilla glass mechanics
    private static boolean never(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) { return false; }
    private static Boolean never(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, EntityType<?> entityType) { return (boolean)false; }

    @Override
    public VoxelShape getVisualShape(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_, ISelectionContext p_230322_4_) {
        return VoxelShapes.empty();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState p_200122_1_, BlockState p_200122_2_, Direction p_200122_3_) {
        return p_200122_2_.is(this) ? true : super.skipRendering(p_200122_1_, p_200122_2_, p_200122_3_);
    }
}
