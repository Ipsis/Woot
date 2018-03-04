package ipsis.woot.item;

import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModItems;
import ipsis.woot.util.StringHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemXpShard extends ItemWoot {

    public static final String BASENAME = "xpshard";
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

        ItemStack stack = playerIn.getHeldItem(hand);

        if (!worldIn.isRemote) {
            if (!stack.isEmpty()) {
                /**
                 * This is the EntityXPOrb playSound call
                 */
                worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ,
                        SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                        SoundCategory.PLAYERS,
                        0.2F, 0.5F * ((itemRand.nextFloat() - itemRand.nextFloat()) * 0.7F + 1.8F));
                if(playerIn instanceof FakePlayer) {
                    /**
                     * Fakeplayer can only use one at a time
                     * /
                     */
                    worldIn.spawnEntity(new EntityXPOrb(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, XP_VALUE));
                    stack.shrink(1);
                }
                else {

                    if (playerIn.isSneaking()) {
                        /**
                         * Sneak Use - consume the whole stack
                         */
                        playerIn.addExperience(XP_VALUE * stack.getCount());
                        if (!playerIn.capabilities.isCreativeMode)
                            stack.setCount(0);
                    } else {

                        /**
                         * Normal use - consume one
                         */
                        playerIn.addExperience(XP_VALUE);
                        if (!playerIn.capabilities.isCreativeMode)
                            stack.shrink(1);
                    }
                }

                return new ActionResult<>(EnumActionResult.SUCCESS, stack);
            }
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        tooltip.add(StringHelper.getInfoText("info.woot.xpshard.0"));
        tooltip.add(StringHelper.getInfoText("info.woot.xpshard.1"));
    }
}
