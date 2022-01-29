package ipsis.woot.modules.squeezer.blocks;

import ipsis.woot.base.WootBlockHFacingInteract;
import ipsis.woot.datagen.Languages;
import ipsis.woot.modules.squeezer.SqueezerModule;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class DyeSqueezerBlock extends WootBlockHFacingInteract {

    public DyeSqueezerBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(3.5F));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DyeSqueezerTileEntity();
    }

    public static void addRecipe(Block block, Consumer<IFinishedRecipe> consumer) {

    }

    public static void addTranslations(String id, Languages languages) {

        languages.add(SqueezerModule.DYE_SQUEEZER.get(), "Dye Liquifier");
        languages.add("container.woot." + id, "Dye Liquifier");
    }

}
