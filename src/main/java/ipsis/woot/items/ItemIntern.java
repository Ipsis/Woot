package ipsis.woot.items;

import ipsis.Woot;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.helpers.WorldHelper;
import ipsis.woot.util.helpers.BuilderHelper;
import ipsis.woot.util.helpers.PlayerHelper;
import ipsis.woot.util.helpers.StringHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemIntern extends Item implements IBuilderItem {

    public ItemIntern() {
        setRegistryName("intern");
        setTranslationKey(Woot.MODID + ".intern");
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public FactoryTier getTier(ItemStack itemStack) {
        return BuilderHelper.getBuilderMode(itemStack).getFactoryTier();
    }

    @Override
    public BuilderModes getBuilderMode(ItemStack itemStack) {
        return BuilderHelper.getBuilderMode(itemStack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (playerIn.isSneaking()) {
            RayTraceResult rayTraceResult = rayTrace(worldIn, playerIn, false);
            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
                return new ActionResult<>(EnumActionResult.PASS, itemStack);

            if (WorldHelper.isServerWorld(worldIn)) {
                BuilderHelper.setBuilderMode(itemStack, getBuilderMode(itemStack).getNext());
                PlayerHelper.sendActionBarMessage(playerIn, StringHelper.localise("item.woot.builder.mode." + getBuilderMode(itemStack).name().toLowerCase()));
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        BuilderModes mode = BuilderHelper.getBuilderMode(stack);
        tooltip.add(StringHelper.localise(TextFormatting.AQUA + "item.woot.builder.mode." + mode.name().toLowerCase()));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
