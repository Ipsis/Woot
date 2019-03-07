package ipsis.woot.machines.squeezer;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.IGuiTile;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntitySqueezer extends TileEntity implements IWootDebug, IGuiTile, IInteractionObject {

    public TileEntitySqueezer() {
        super(ModTileEntities.squeezerTileEntity);
    }

    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return !isRemoved() && entityPlayer.getDistanceSq(0.5D, 0.5D, 0.5D) <= 64D;
    }

    /**
     * IGuiTile
     */
    @Override
    public GuiContainer createGui(EntityPlayer entityPlayer) {
        return new GuiSqueezer(this, new ContainerSqueezer(entityPlayer.inventory, this));
    }

    /**
     * IInteractionObject
     */
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerSqueezer(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "woot:squeezer";
    }

    /**
     * IINameable
     */

    @Override
    public ITextComponent getName() {
        return new TextComponentTranslation("gui.woot.squeezer");
    }

    @Override
    public boolean hasCustomName() { return false; }

    @Nullable
    @Override
    public ITextComponent getCustomName() { return null; }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext context) {
        debug.add("====> TileEntitySqueezer");
        return debug;
    }

}
