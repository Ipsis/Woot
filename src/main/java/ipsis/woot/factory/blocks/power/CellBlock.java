package ipsis.woot.factory.blocks.power;

import ipsis.woot.debug.DebugItem;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryComponentProvider;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class CellBlock extends WootBlock implements WootDebug, FactoryComponentProvider {

    final Class<? extends CellTileEntityBase> tileEntityClazz;
    public CellBlock(String name, Class<? extends CellTileEntityBase> clazz) {
        super(Properties.create(Material.IRON).sound(SoundType.METAL), name);
        this.tileEntityClazz = clazz;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        try {
            return tileEntityClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    /**
     * FactoryComponentProvider
     */
    @Override
    public FactoryComponent getFactoryComponent() {
        return FactoryComponent.CELL;
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> CellBlock");
        DebugItem.getTileEntityDebug(debug, itemUseContext);
        return debug;
    }
}
