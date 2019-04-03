package ipsis.woot.factory.layout;

import ipsis.woot.factory.FactoryTier;
import ipsis.woot.util.helper.BlockPosHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class AbsolutePatternBuilder {

    public static AbsolutePattern createAbsolutePattern(@Nonnull World world, FactoryTier factoryTier, @Nonnull BlockPos origin, EnumFacing facing) {

        FactoryPatternRepository.Pattern pattern = FactoryPatternRepository.PATTERN_REPOSITORY.getPattern(factoryTier);
        AbsolutePattern absolute = new AbsolutePattern(factoryTier);

        EnumFacing opposite = facing.getOpposite();
        for (PatternBlock patternBlock : pattern.getPatternBlocks()) {
            BlockPos pos = BlockPosHelper.rotateFromSouth(patternBlock.getBlockPos(), opposite);
            absolute.addAbsoluteBlock(patternBlock.getFactoryBlock(), origin.add(pos));
        }

        return absolute;
    }
}
