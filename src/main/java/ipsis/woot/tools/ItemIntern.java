package ipsis.woot.tools;

import ipsis.woot.Woot;
import ipsis.woot.factory.BlockHeart;
import ipsis.woot.factory.FactoryTier;
import ipsis.woot.factory.layout.*;
import ipsis.woot.util.WootItem;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class ItemIntern extends WootItem {

    public enum ToolMode {
        BUILD_1(FactoryTier.TIER_1),
        BUILD_2(FactoryTier.TIER_2),
        BUILD_3(FactoryTier.TIER_3),
        BUILD_4(FactoryTier.TIER_4),
        BUILD_5(FactoryTier.TIER_5),
        VALID_1(FactoryTier.TIER_1),
        VALID_2(FactoryTier.TIER_2),
        VALID_3(FactoryTier.TIER_3),
        VALID_4(FactoryTier.TIER_4),
        VALID_5(FactoryTier.TIER_5),
        VALID_IMPORT(FactoryTier.TIER_1), // not valid tier
        VALID_EXPORT(FactoryTier.TIER_1); // not valid tier

        public static ToolMode[] VALUES = values();
        private static EnumSet<ToolMode> BUILD_MODES = EnumSet.range(BUILD_1, BUILD_5);
        private static EnumSet<ToolMode> VALIDATE_MODES = EnumSet.range(VALID_1, VALID_EXPORT);
        private static EnumSet<ToolMode> VALIDATE_TIERS = EnumSet.range(VALID_1, VALID_5);

        public ToolMode getNext() {
            return VALUES[(this.ordinal() + 1) % VALUES.length];
        }

        private FactoryTier factoryTier;
        ToolMode(FactoryTier factoryTier) {
            this.factoryTier = factoryTier;
        }
        public boolean isBuildMode() {
            return BUILD_MODES.contains(this);
        }

        public boolean isValidateMode() {
            return VALIDATE_MODES.contains(this);
        }
        public boolean isValidateTierMode() { return VALIDATE_TIERS.contains(this); }
        public FactoryTier getFactoryTier() { return this.factoryTier; }
    }

    public static final String BASENAME = "intern";
    public ItemIntern() {
        super(new Item.Properties().group(Woot.TAB_WOOT), BASENAME);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemStack = playerIn.getHeldItem(handIn);

        // Sneak click not on block to cycle
        if (playerIn.isSneaking()) {
            RayTraceResult rayTraceResult = rayTrace(worldIn, playerIn, false);
            if (rayTraceResult != null && rayTraceResult.type == RayTraceResult.Type.BLOCK)
                return new ActionResult<>(EnumActionResult.PASS, itemStack);

            if (WorldHelper.isServerWorld(worldIn)) {
                ToolMode mode = getToolModeFromStack(itemStack);
                mode = mode.getNext();
                setToolModeInStack(itemStack, mode);
                PlayerHelper.sendActionBarMessage(playerIn, StringHelper.translate("info.woot.intern.mode." + getToolModeFromStack(itemStack).toString().toLowerCase()));
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }
        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {

        EnumActionResult result = EnumActionResult.PASS;
        if (WorldHelper.isServerWorld(context.getWorld())) {
            ItemStack itemStack = context.getItem();
            if (!context.getPlayer().isSneaking()) {
                Block b = context.getWorld().getBlockState(context.getPos()).getBlock();
                if (b instanceof BlockHeart) {
                    IBlockState blockState = context.getWorld().getBlockState(context.getPos());
                    EnumFacing facing = blockState.get(BlockHeart.FACING);
                    ToolMode toolMode = getToolModeFromStack(itemStack);
                    if (toolMode.isBuildMode() && context.getPlayer().isAllowEdit()) {
                        FactoryHelper.tryBuild(context.getWorld(), context.getPos(), context.getPlayer(), facing, toolMode.getFactoryTier());
                        result = EnumActionResult.SUCCESS;
                    } else if (toolMode.isValidateMode()) {
                        // Only build or validate just now
                        FactoryHelper.tryValidate(context.getWorld(), context.getPos(), context.getPlayer(), facing, toolMode);
                        result = EnumActionResult.SUCCESS;
                    }
                }
            }
        } else {
            // So we swing
            result = EnumActionResult.SUCCESS;
        }

        return result;
    }

    /**
     * NBT
     */
    private static final String NBT_MODE = "mode";
    private static void setToolModeInStack(ItemStack itemStack, ToolMode toolMode) {
        NBTTagCompound compound = itemStack.getTag();
        if (compound == null)
            compound = new NBTTagCompound();

        compound.setString(NBT_MODE, toolMode.name());
        itemStack.setTag(compound);
    }

    private static ToolMode getToolModeFromStack(ItemStack itemStack) {
        ToolMode mode = ToolMode.BUILD_1; // default
        NBTTagCompound compound = itemStack.getTag();
        if (compound == null) {
            setToolModeInStack(itemStack, mode);
        } else {
            try {
                mode = ToolMode.valueOf(compound.getString(NBT_MODE));
            } catch (Exception e) {
                setToolModeInStack(itemStack, mode);
            }
        }
        return mode;
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.intern")));
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.intern.0")));

        ToolMode toolMode = getToolModeFromStack(stack);
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.intern.mode." + getToolModeFromStack(stack).toString().toLowerCase())));
        if (toolMode.isBuildMode()) {
            FactoryPatternRepository.Pattern pattern = FactoryPatternRepository.getInstance().getPattern(toolMode.getFactoryTier());
            for (FactoryBlock factoryBlock : FactoryBlock.VALUES) {
                int count = pattern.getFactoryBlockCount(factoryBlock);
                if (count > 0)
                    tooltip.add(new TextComponentString(count + " " + StringHelper.translate("block.woot." + factoryBlock.getName().toLowerCase())));
            }
        }
    }
}
