package ipsis.woot.modules.factory.layout;

import ipsis.woot.modules.factory.Exotic;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.util.FakeMob;
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
    List<Perk> perks = new ArrayList<>();
    List<FakeMob> mobs = new ArrayList<>();
    List<BlockPos> validControllerPos = new ArrayList<>();
    Exotic exotic = Exotic.NONE;
    public List<PatternBlock> getBlocks() { return Collections.unmodifiableList(blocks); }

    public AbsolutePattern(Tier tier) {
        this.tier = tier;
    }

    public void addAbsoluteBlock(FactoryComponent component, BlockPos pos) {
        blocks.add(new PatternBlock(component, pos));
    }

    public Tier getTier() { return tier; }

    public Exotic getExotic() { return this.exotic; }

    public void addPerk(Perk perk) { this.perks.add(perk); }
    public void setExotic(Exotic exotic) { this.exotic = exotic; }
    public void clearPerks() { this.perks.clear(); }
    public void addMob(FakeMob fakeMob) { this.mobs.add(fakeMob); }
    public void clearMobs() { this.mobs.clear(); }
    public List<FakeMob> getMobs() { return Collections.unmodifiableList(mobs); }
    public void addControllerPos(BlockPos blockPos) { this.validControllerPos.add(blockPos); }
    public void clearControllerPos() { this.validControllerPos.clear(); }
    public boolean isValidControllerPos(BlockPos blockPos) { return this.validControllerPos.contains(blockPos); }

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

    @Override
    public String toString() {
        return "AbsolutePattern{" +
                "tier=" + tier +
                ", facing=" + facing +
                ", perks=" + perks +
                ", mobs=" + mobs +
                ", exotic=" + exotic +
                '}';
    }
}
