package ipsis.woot.tools;

import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.Tier;
import ipsis.woot.factory.blocks.HeartBlock;
import ipsis.woot.factory.blocks.HeartTileEntity;
import ipsis.woot.factory.layout.FactoryHelper;
import ipsis.woot.factory.layout.PatternRepository;
import ipsis.woot.util.WootItem;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

/**
 * This is the main tool for the mod.
 * It is used to construct, validate and form the factory.
 */
public class InternItem extends WootItem {

    public InternItem() {
        super(new Item.Properties().maxStackSize(1), "intern");
    }

    public enum ToolMode {
        FORM(Tier.UNKNOWN),
        BUILD_1(Tier.TIER_1),
        BUILD_2(Tier.TIER_2),
        BUILD_3(Tier.TIER_3),
        BUILD_4(Tier.TIER_4),
        VALIDATE_1(Tier.TIER_1),
        VALIDATE_2(Tier.TIER_2),
        VALIDATE_3(Tier.TIER_3),
        VALIDATE_4(Tier.TIER_4);

        public static ToolMode[] VALUES = values();
        private static EnumSet<ToolMode> BUILD_MODES = EnumSet.range(BUILD_1, BUILD_4);
        private static EnumSet<ToolMode> VALIDATE_TIERS = EnumSet.range(VALIDATE_1, VALIDATE_4);

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

        // Sneak click not on block to cycle
        if (playerEntity.isSneaking()) {
            RayTraceResult rayTraceResult = rayTrace(world, playerEntity, RayTraceContext.FluidMode.NONE);
            if (rayTraceResult != null && rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
                return new ActionResult<>(ActionResultType.PASS, itemStack);

            if (!world.isRemote) {
                ToolMode mode = getToolModeFromStack(itemStack);
                mode = mode.getNext();
                setToolModeInStack(itemStack, mode);
                PlayerHelper.sendActionBarMessage(playerEntity, StringHelper.translate("info.woot.intern.mode." + getToolModeFromStack(itemStack).toString().toLowerCase()));
            }
            return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
        }
        return new ActionResult<>(ActionResultType.PASS, itemStack);
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
    public ActionResultType onItemUse(ItemUseContext context) {

        ActionResultType result = ActionResultType.PASS;
        /*if (!context.getWorld().isRemote) */
            ItemStack itemStack = context.getItem();
            if (!context.getPlayer().isSneaking()) {
                Block b = context.getWorld().getBlockState(context.getPos()).getBlock();
                if (b instanceof HeartBlock) {
                    BlockState blockState = context.getWorld().getBlockState(context.getPos());
                    Direction facing = blockState.get(BlockStateProperties.HORIZONTAL_FACING);
                    ToolMode toolMode = getToolModeFromStack(itemStack);
                    if (toolMode == ToolMode.FORM) {
                        TileEntity te = context.getWorld().getTileEntity(context.getPos());
                        if (te instanceof HeartTileEntity)
                            ((HeartTileEntity) te).interrupt();

                    } else if (toolMode.isBuildMode() && context.getPlayer().isAllowEdit()) {
                        if (FactoryHelper.tryBuild(context.getWorld(), context.getPos(), context.getPlayer(), facing, toolMode.getTier())) {
                            if (context.getWorld().isRemote)
                                spawnParticle(context.getWorld(), context.getPos().up(), 10);
                        }

                        result = ActionResultType.SUCCESS;
                    } else if (toolMode.isValidateMode()) {
                        if (!context.getWorld().isRemote)
                            FactoryHelper.tryValidate(context.getWorld(), context.getPos(), context.getPlayer(), facing, toolMode.getTier());
                        result = ActionResultType.SUCCESS;
                    }
                }
            }
            /*
        } else {
            // So we swing
            result = ActionResultType.SUCCESS;
        }*/

        return result;
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

        ToolMode toolMode = getToolModeFromStack(stack);
        tooltip.add(new StringTextComponent(StringHelper.translate("info.woot.intern.mode." + toolMode.toString().toLowerCase())));
        if (toolMode.isBuildMode()) {
            PatternRepository.Pattern pattern = PatternRepository.get().getPattern(toolMode.getTier());
            if (pattern != null) {
                for (FactoryComponent component : FactoryComponent.VALUES) {
                    int count = pattern.getFactoryBlockCount((component));
                    if (count > 0)
                        tooltip.add(new StringTextComponent(
                                String.format(
                                "%2d * %s",
                                 count, StringHelper.translate(component.getTranslationKey()))));
                }
            }
        }
    }
}
