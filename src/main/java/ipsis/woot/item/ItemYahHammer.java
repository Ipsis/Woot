package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.tools.EnumValidateToolMode;
import ipsis.woot.tools.IValidateTool;
import ipsis.woot.tools.ValidateToolUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemYahHammer extends ItemWoot implements IValidateTool {

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

        if (!stack.hasTagCompound())
            return;
        ValidateToolUtils.getInformation(stack, tooltip, flagIn);
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemStack = playerIn.getHeldItem(handIn);

        // Sneak click, not on block, to cycle
        if (playerIn.isSneaking()) {
            RayTraceResult rayTraceResult = rayTrace(worldIn, playerIn, false);
            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
                return new ActionResult<>(EnumActionResult.PASS, itemStack);

            if (!worldIn.isRemote) {
                ValidateToolUtils.cycleMode(itemStack, playerIn);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    /**
     * IValidateTool
     */

    @Override
    public boolean isValidateTier(ItemStack itemStack) {

        EnumValidateToolMode mode = ValidateToolUtils.getModeFromNbt(itemStack);
        return mode.isValidateTierMode();
    }

    @Override
    public boolean isValidateImport(ItemStack itemStack) {
        return ValidateToolUtils.getModeFromNbt(itemStack) == EnumValidateToolMode.VALIDATE_IMPORT;
    }

    @Override
    public boolean isValidateExport(ItemStack itemStack) {
        return ValidateToolUtils.getModeFromNbt(itemStack) == EnumValidateToolMode.VALIDATE_EXPORT;
    }
}
