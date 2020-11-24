package ipsis.woot.modules.layout.items;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.blocks.HeartBlock;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.layout.FactoryHelper;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.setup.ModSetup;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

/**
 * This is the main tool for the mod.
 * It is used to construct, validate and form the factory.
 */
public class InternItem extends Item {

    public InternItem() {
        super(new Item.Properties().maxStackSize(1).group(Woot.setup.getCreativeTab()));
    }

    public enum ToolMode {
        BUILD_1(Tier.TIER_1),
        BUILD_2(Tier.TIER_2),
        BUILD_3(Tier.TIER_3),
        BUILD_4(Tier.TIER_4),
        BUILD_5(Tier.TIER_5),
        VALIDATE_1(Tier.TIER_1),
        VALIDATE_2(Tier.TIER_2),
        VALIDATE_3(Tier.TIER_3),
        VALIDATE_4(Tier.TIER_4),
        VALIDATE_5(Tier.TIER_5);

        public static ToolMode[] VALUES = values();
        private static EnumSet<ToolMode> BUILD_MODES = EnumSet.range(BUILD_1, BUILD_5);
        private static EnumSet<ToolMode> VALIDATE_TIERS = EnumSet.range(VALIDATE_1, VALIDATE_5);

        public ToolMode getNext() {
            return VALUES[(this.ordinal() + 1) % VALUES.length];
        }

        public boolean isBuildMode() { return BUILD_MODES.contains(this); }
        public boolean isValidateMode() { return VALIDATE_TIERS.contains(this); }

        Tier tier;
        ToolMode(Tier tier) {
            this.tier = tier;
        }

        public Tier getTier() { return this.tier; }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {

        ItemStack itemStack = playerEntity.getHeldItem(hand);
        playerEntity.setActiveHand(hand);
        if (!world.isRemote) {
            if (playerEntity.isSneaking()) {
                RayTraceResult rayTraceResult = rayTrace(world, playerEntity, RayTraceContext.FluidMode.NONE);
                if (rayTraceResult != null && rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
                    return super.onItemRightClick(world, playerEntity, hand);

                ToolMode mode = getToolModeFromStack(itemStack);
                mode = mode.getNext();
                setToolModeInStack(itemStack, mode);
                if (mode.isBuildMode()) {
                    playerEntity.sendStatusMessage(
                            new TranslationTextComponent(
                                    "info.woot.intern.mode.build",
                                    StringHelper.translate(mode.getTier().getTranslationKey())), true);
                } else if (mode.isValidateMode()) {
                    playerEntity.sendStatusMessage(
                            new TranslationTextComponent(
                                    "info.woot.intern.mode.validate",
                                    StringHelper.translate(mode.getTier().getTranslationKey())), true);
                }
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
    }

    /**
     * NBT
     */
    private static final String NBT_MODE = "mode";
    private static void setToolModeInStack(ItemStack itemStack, ToolMode toolMode) {
        CompoundNBT compound = itemStack.getTag();
        if (compound == null)
            compound = new CompoundNBT();

        compound.putString(NBT_MODE, toolMode.name());
        itemStack.setTag(compound);
    }

    private static ToolMode getToolModeFromStack(ItemStack itemStack) {
        ToolMode mode = ToolMode.BUILD_1; // default
        CompoundNBT compound = itemStack.getTag();
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

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

        ActionResultType result = ActionResultType.PASS;
        ItemStack itemStack = context.getItem();

        if (!context.getPlayer().isSneaking() && !context.getWorld().isRemote) {
            Block b = context.getWorld().getBlockState(context.getPos()).getBlock();
            if (b instanceof HeartBlock) {
                BlockState blockState = context.getWorld().getBlockState(context.getPos());
                Direction facing = blockState.get(BlockStateProperties.HORIZONTAL_FACING);
                ToolMode toolMode = getToolModeFromStack(itemStack);
                if (toolMode.isBuildMode() && context.getPlayer().isAllowEdit()) {
                    FactoryHelper.BuildResult buildResult = (FactoryHelper.tryBuild(context.getWorld(), context.getPos(), context.getPlayer(), facing, toolMode.getTier()));
                    if (buildResult == FactoryHelper.BuildResult.SUCCESS) {
                        context.getWorld().playSound(
                                null,
                                context.getPlayer().getPosX(),
                                context.getPlayer().getPosY(),
                                context.getPlayer().getPosZ(),
                                SoundEvents.BLOCK_STONE_PLACE,
                                SoundCategory.BLOCKS,
                                1.0F,
                                0.5F * ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.8F));
                    } else if (buildResult == FactoryHelper.BuildResult.ALL_BLOCKS_PLACED) {
                        FactoryHelper.tryValidate(context.getWorld(), context.getPos(), context.getPlayer(), facing, toolMode.getTier());
                    } else {
                        context.getWorld().playSound(
                                null,
                                context.getPlayer().getPosX(),
                                context.getPlayer().getPosY(),
                                context.getPlayer().getPosZ(),
                                SoundEvents.BLOCK_ANVIL_DESTROY,
                                SoundCategory.BLOCKS,
                                1.0F, 1.0F);
                    }
                    result = ActionResultType.SUCCESS;
                } else if (toolMode.isValidateMode()) {
                    if (!context.getWorld().isRemote)
                        FactoryHelper.tryValidate(context.getWorld(), context.getPos(), context.getPlayer(), facing, toolMode.getTier());
                    result = ActionResultType.SUCCESS;
                }
            }
        }

        // Returning SUCCESS will filter out the MAIN_HAND hand from onBlockActivated
        return ActionResultType.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    void spawnParticle(World world, BlockPos pos, int amount) {
        BlockState blockState = world.getBlockState(pos);
        Block b = world.getBlockState(pos).getBlock();

        // Based off the BoneMealItem code
        if (b.isAir(blockState, world, pos)) {
            for(int i = 0; i < amount; ++i) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.HAPPY_VILLAGER,
                        (double)((float)pos.getX() + random.nextFloat()),
                        (double)((float)pos.getY() + random.nextFloat()),
                        (double)((float)pos.getZ() + random.nextFloat()),
                        d0, d1, d2);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(StringHelper.translate("info.woot.intern")));
        tooltip.add(new StringTextComponent(StringHelper.translate("info.woot.intern.0")));
        tooltip.add(new StringTextComponent(StringHelper.translate("info.woot.intern.1")));

        ToolMode toolMode = getToolModeFromStack(stack);

        if (toolMode.isBuildMode()) {
            tooltip.add(new StringTextComponent(StringHelper.translateFormat(
                            "info.woot.intern.mode.build",
                            StringHelper.translate(toolMode.getTier().getTranslationKey()))));
            PatternRepository.Pattern pattern = PatternRepository.get().getPattern(toolMode.getTier());
            if (pattern != null) {
                for (FactoryComponent component : FactoryComponent.VALUES) {
                    int count = pattern.getFactoryBlockCount((component));
                    if (count > 0) {
                        String key = "info.woot.intern.other.count";
                        TranslationTextComponent text = new TranslationTextComponent(component.getTranslationKey());
                        if (component == FactoryComponent.CELL) {
                            text = new TranslationTextComponent("info.woot.intern.cell");
                        } else if (toolMode == ToolMode.BUILD_1 && component == FactoryComponent.CONTROLLER) {
                            key = "info.woot.intern.controller.count.0";
                        } else if (component == FactoryComponent.CONTROLLER) {
                            key = "info.woot.intern.controller.count.1";
                        }

                        tooltip.add(new TranslationTextComponent(key, count, text));
                    }
                }
            }
        } else if (toolMode.isValidateMode()) {
            tooltip.add(new StringTextComponent(StringHelper.translateFormat(
                    "info.woot.intern.mode.validate",
                    StringHelper.translate(toolMode.getTier().getTranslationKey()))));
        }
    }
}
