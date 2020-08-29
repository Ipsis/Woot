package ipsis.woot.modules.oracle.blocks;

import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class OracleTileEntity extends TileEntity implements INamedContainerProvider {

    public OracleTileEntity() {
        super(OracleSetup.ORACLE_BLOCK_TILE.get());
    }

    /**
     * INamedContainerProvider
     */
    @Override
    public ITextComponent getDisplayName() {
        return StringHelper.translate("gui.woot.oracle.name");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new OracleContainer(i, world, pos, playerInventory, playerEntity);
    }
}
