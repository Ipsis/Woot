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

public class BlockFactory extends WootBlock implements IWootDebug, IFactoryBlockProvider {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockFactory(FactoryBlock factoryBlock) {
        super(Block.Properties.create(Material.ROCK), factoryBlock.getName());
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityFactory();
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> BlockFactory - " + factoryBlock.getName());
        TileEntity te = itemUseContext.getWorld().getTileEntity(itemUseContext.getPos());
        if (te instanceof IWootDebug)
            ((IWootDebug)te).getDebugText(debug, itemUseContext);
        return debug;
    }

    /**
     * IFactoryBlockProvider
     */
    public FactoryBlock getFactoryBlock() { return this.factoryBlock; }
}
