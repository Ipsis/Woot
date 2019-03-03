package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.factory.layout.IFactoryBlockProvider;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class BlockController extends WootBlock implements IWootDebug, IFactoryBlockProvider {

    public static final String BASENAME = "factory_controller";
    public BlockController() {
        super(Block.Properties.create(Material.ROCK), BASENAME);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityController();
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> BlockController");
        return debug;
    }

    /**
     * IFactoryBlockProvider
     */
    @Override
    public FactoryBlock getFactoryBlock() {
        return FactoryBlock.CONTROLLER;
    }
}
