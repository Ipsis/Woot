package ipsis.woot.item;

import ipsis.woot.tileentity.TileEntityMobFactoryController;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class ItemBlockController extends ItemBlock {

    public ItemBlockController(Block block) {

        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        if (stack != null && stack.hasTagCompound()) {

            String displayName = stack.getTagCompound().getString(TileEntityMobFactoryController.NBT_DISPLAY_NAME);
            if (!displayName.equals(""))
                tooltip.add(String.format("Mob: %s", I18n.translateToLocal(displayName)));
        }

        super.addInformation(stack, playerIn, tooltip, advanced);
    }
}
