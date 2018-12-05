package ipsis.woot.generators;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class BlockGenerator extends WootBlock implements ITileEntityProvider {

    private final String basename;
    private final GeneratorType generatorType;

    public BlockGenerator(GeneratorType generatorType) {

        super(Material.ROCK, generatorType.getName());
        this.generatorType = generatorType;
        this.basename = generatorType.getName();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        if (generatorType == GeneratorType.TICK)
            return new TileEntityGeneratorTick();
        else
            return new TileEntityGeneratorRF();
    }

    public GeneratorType getGeneratorType() { return this.generatorType; }

    public enum GeneratorType {
        TICK("generator_tick"),
        RF("generator_rf");

        private String name;
        GeneratorType(String name) { this.name = name; }

        public String getName() { return name; }
    }

}


