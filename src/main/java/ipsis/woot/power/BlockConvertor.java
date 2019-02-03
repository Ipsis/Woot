package ipsis.woot.power;

import ipsis.woot.util.WootBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class BlockConvertor extends WootBlock implements ITileEntityProvider {

    private final String basename;
    private final GeneratorType generatorType;

    public BlockConvertor(GeneratorType generatorType) {

        super(Material.ROCK, generatorType.getName());
        this.generatorType = generatorType;
        this.basename = generatorType.getName();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        if (generatorType == GeneratorType.TICK)
            return new TileEntityConvertorTick();
        else if (generatorType == GeneratorType.FLUID)
            return new TileEntityConvertorFluid();
        else
            return new TileEntityConvertorRF();
    }

    public GeneratorType getGeneratorType() { return this.generatorType; }

    public enum GeneratorType {
        TICK("generator_tick"),
        RF("generator_rf"),
        FLUID("generator_fluid");

        private String name;
        GeneratorType(String name) { this.name = name; }

        public String getName() { return name; }
    }

}


