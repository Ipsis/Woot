package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.mod.ModItems;
import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
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
    public boolean addUpgrade(EntityPlayer player, ItemUpgrade.UpgradeType upgradeType) {
        if (this.upgradeType != null) {
            PlayerHelper.sendActionBarMessage(player, StringHelper.translate("chat.woot.totem.upgrade.full"));
            return false;
        }

        Block block = getWorld().getBlockState(getPos()).getBlock();
        if (!(block instanceof BlockTotem))
            return false;

        FactoryBlock factoryBlock = ((BlockTotem)block).getFactoryBlock();
        int upgradeTier = upgradeType.getUpgradeTier();
        if (factoryBlock == FactoryBlock.TOTEM_1 && upgradeTier > 1) {
            PlayerHelper.sendActionBarMessage(player, StringHelper.translate("chat.woot.totem.upgrade.low_tier"));
            return false;
        } else if (factoryBlock == FactoryBlock.TOTEM_2 && upgradeTier > 2) {
            PlayerHelper.sendActionBarMessage(player, StringHelper.translate("chat.woot.totem.upgrade.low_tier"));
            return false;
        } else if (factoryBlock == FactoryBlock.TOTEM_3 && upgradeTier > 3) {
            PlayerHelper.sendActionBarMessage(player, StringHelper.translate("chat.woot.totem.upgrade.low_tier"));
            return false; // upgrade tier is capped at 3 anyway
        }

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
