package ipsis.woot.item;

import ipsis.Woot;
import ipsis.woot.crafting.AnvilHelper;
import ipsis.woot.crafting.IAnvilRecipe;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.util.ItemStackHelper;
import ipsis.woot.util.WorldHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemYahHammer extends ItemWoot {

    public static final String BASENAME = "yahhammer";

    public ItemYahHammer() {

        super(BASENAME);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemYahHammer, BASENAME.toLowerCase());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        tooltip.add(TextFormatting.ITALIC + "Yet Another Hammer");
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {

        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {

        return itemStack.copy();
    }
}
