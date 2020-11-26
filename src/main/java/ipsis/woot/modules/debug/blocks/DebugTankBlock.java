package ipsis.woot.modules.debug.blocks;

import ipsis.woot.modules.debug.items.DebugItem;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class DebugTankBlock extends Block implements WootDebug {

    public DebugTankBlock() {
        super(Properties.create(Material.IRON).sound(SoundType.METAL));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DebugTankTileEntity();
    }


    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> DebugTank");
        DebugItem.getTileEntityDebug(debug, itemUseContext);
        return debug;
    }
}
