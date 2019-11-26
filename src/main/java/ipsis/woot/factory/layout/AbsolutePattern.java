package ipsis.woot.factory.layout;

import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.Tier;
import ipsis.woot.util.helper.BlockPosHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A factory pattern located around a physical origin
 */
public class AbsolutePattern {

    Tier tier;
    Direction facing;
    List<PatternBlock> blocks = new ArrayList<>();
    public List<PatternBlock> getBlocks() { return Collections.unmodifiableList(blocks); }

    public AbsolutePattern(Tier tier) {
        this.tier = tier;
    }

    public void addAbsoluteBlock(FactoryComponent component, BlockPos pos) {
        blocks.add(new PatternBlock(component, pos));
    }

    public Tier getTier() { return tier; }

    public static AbsolutePattern create(@Nonnull World world, Tier tier, @Nonnull BlockPos origin, Direction facing) {

        PatternRepository.Pattern pattern = PatternRepository.get().getPattern(tier);
        AbsolutePattern absolutePattern = new AbsolutePattern(tier);

        Direction opposite = facing.getOpposite();
        for (PatternBlock patternBlock : pattern.getPatternBlocks()) {
            BlockPos pos = BlockPosHelper.rotateFromSouth(patternBlock.getBlockPos(), opposite);
            absolutePattern.addAbsoluteBlock(patternBlock.getFactoryComponent(), origin.add(pos));
        }

        absolutePattern.facing = facing;
        return absolutePattern;
    }
}
