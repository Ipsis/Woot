package ipsis.woot.misc;

import ipsis.woot.client.ui.OracleContainer;
import ipsis.woot.mod.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class OracleTileEntity extends TileEntity implements INamedContainerProvider {

    public OracleTileEntity() {
        super(ModBlocks.ORACLE_BLOCK_TILE);
    }

    /**
     * INamedContainerProvider
     */
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new OracleContainer(i, world, pos, playerInventory, playerEntity);
    }
}
