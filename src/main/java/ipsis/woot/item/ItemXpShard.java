package ipsis.woot.item;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModItems;
import ipsis.woot.reference.Lang;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemXpShard extends ItemWoot {

    public static final String BASENAME = "xpShard";
    public static final int XP_VALUE = 16;

    public ItemXpShard() {

        super(BASENAME);
        setMaxStackSize(64);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemXpShard, BASENAME.toLowerCase());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {

        if (itemStackIn != null) {

            worldIn.playSoundAtEntity(playerIn, "random.orb", 0.1F, 0.5F * ((itemRand.nextFloat() - itemRand.nextFloat()) * 0.7F + 1.8F));
            playerIn.addExperience(XP_VALUE);

            if (!playerIn.capabilities.isCreativeMode)
                itemStackIn.stackSize--;
        }

        return itemStackIn;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        tooltip.add(StatCollector.translateToLocal(Lang.TAG_TOOLTIP + BASENAME + ".0"));
        tooltip.add(String.format(StatCollector.translateToLocal(Lang.TAG_TOOLTIP + BASENAME + ".1"), XP_VALUE));
    }
}
