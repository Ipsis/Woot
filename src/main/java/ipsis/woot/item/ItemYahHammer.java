package ipsis.woot.item;

import ipsis.Woot;
import ipsis.woot.crafting.AnvilHelper;
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

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

        if (world.isRemote)
            return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);

        if (world.getBlockState(pos).getBlock() == ModBlocks.blockAnvil) {
            if (AnvilHelper.isAnvilHot(world, pos)) {

                List<ItemStack> outputs = Woot.anvilManager.craft(AnvilHelper.getBaseItem(world, pos), AnvilHelper.getItems(world, pos));
                LogHelper.info("Recipe outputs: " + outputs);
                if (outputs != null) {
                    world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    for (ItemStack output : outputs) {
                        EntityItem out = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, output);
                        world.spawnEntity(out);
                    }
                }
            }
        }

        return EnumActionResult.PASS;
    }
}
