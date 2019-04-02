package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.mod.ModItems;
import ipsis.woot.mod.ModTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityTotem extends TileEntity implements IWootDebug {

    public static final String BASENAME = "factory_totem";

    private int level;
    public TileEntityTotem() {
        super(ModTileEntities.totemTileEntityType);
        this.level = 1;
    }

    public void setLevel(int level) {
        this.level = level;
        markDirty();
    }

    private ItemUpgrade.UpgradeType upgradeType = null;
    public boolean addUpgrade(ItemUpgrade.UpgradeType upgradeType) {
        if (this.upgradeType != null)
            return false;

        // @todo upgrade type must be of the correct level
        this.upgradeType = upgradeType;
        return true;
    }

    public @Nullable ItemUpgrade.UpgradeType getUpgradeType() {
        return upgradeType;
    }

    public ItemStack getDroppedItem() {
        return ModItems.getItemUpgradeByType(upgradeType);
    }

    /**
     * NBT
     */
    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        if (upgradeType != null)
            compound.setInt("upgrade", upgradeType.ordinal());
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        if (compound.hasKey("upgrade"))
            upgradeType = ItemUpgrade.UpgradeType.byIndex(compound.getInt("upgrade"));
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> TileEntityTotem");
        debug.add("level:" + level);
        debug.add("type:" + upgradeType);
        return debug;
    }
}
