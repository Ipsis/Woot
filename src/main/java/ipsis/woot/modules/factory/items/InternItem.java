package ipsis.woot.modules.factory.items;

import com.mojang.datafixers.util.Pair;
import ipsis.woot.Woot;
import ipsis.woot.base.WootItem;
import ipsis.woot.modules.factory.ComponentType;
import ipsis.woot.modules.factory.FactoryModule;
import ipsis.woot.modules.factory.FactoryTier;
import ipsis.woot.modules.factory.layout.PatternBlockInfo;
import ipsis.woot.modules.factory.layout.PatternLibrary;
import ipsis.woot.util.BlockPosHelper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class InternItem extends WootItem {

    public InternItem() {
        super(new Properties().tab(Woot.modSetup.getItemGroup()));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        ItemStack itemStack = playerEntity.getItemInHand(hand);
        RayTraceResult rayTraceResult = getPlayerPOVHitResult(world, playerEntity, RayTraceContext.FluidMode.NONE);
        if (rayTraceResult != null && rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
            return new ActionResult<>(ActionResultType.PASS, itemStack);

        if (playerEntity.isCrouching()) {
            if (!world.isClientSide)
                nextMode(itemStack, playerEntity);

            return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
        }

        return new ActionResult<>(ActionResultType.PASS, itemStack);
    }

    private static final String KEY_MODE = "mode";
    private static void setToolMode(ItemStack tool, InternModes mode) {

        CompoundNBT tag = tool.getOrCreateTag();
        tag.putString(KEY_MODE, mode.getName());
    }

    private static InternModes getToolMode(ItemStack tool) {

        CompoundNBT tag = tool.getOrCreateTag();
        return InternModes.getFromName(tag.getString(KEY_MODE));
    }

    private void nextMode(ItemStack itemStack, PlayerEntity playerEntity) {

        InternModes nextMode = getToolMode(itemStack).getNextMode();
        setToolMode(itemStack, nextMode);
        playerEntity.displayClientMessage(new TranslationTextComponent(nextMode.getDescriptionId()), true);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

        InternModes internMode = getToolMode(stack);

        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
        if (blockState.getBlock() == FactoryModule.HEART.get()) {
            Direction facing = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);
            if (internMode.isBuildMode()) {
                if (addFactoryBlock(context.getLevel(), context.getPlayer(), context.getClickedPos(), facing, internMode.getTier())) {
                    if (context.getLevel().isClientSide)
                        spawnParticle(context.getLevel(), context.getClickedPos().relative(context.getClickedFace()), 10);
                    return ActionResultType.SUCCESS;
                }
            } else if (internMode.isValidateMode()) {
                if (context.getLevel().isClientSide) {
                    List<BlockPos> invalidBlocks = validateFactoryBlocks(context.getLevel(), context.getPlayer(), context.getClickedPos(), facing, internMode.getTier());
                    invalidBlocks.forEach(x -> Woot.instance.getClientInfo().addHighlightedBlock(x, System.currentTimeMillis() + 1000 * 5));
                }
            } else if (internMode == InternModes.DESTROY && context.getPlayer().getOffhandItem().getItem() == Items.GUNPOWDER) {
                if (removeFactoryBlock(context.getLevel(), context.getPlayer(), context.getClickedPos(), facing, internMode.getTier())) {
                    if (context.getLevel().isClientSide)
                        spawnParticle(context.getLevel(), context.getClickedPos().relative(context.getClickedFace()), 10);
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.PASS;
    }

    private List<BlockPos> validateFactoryBlocks(World world, PlayerEntity playerEntity, BlockPos origin, Direction facing, FactoryTier tier) {

        List<BlockPos> invalidBlocks = new ArrayList<>();

        PatternLibrary.Pattern pattern = PatternLibrary.get().get(tier);
        if (pattern == null)
            return invalidBlocks;

        for (PatternBlockInfo patternBlockInfo : pattern.getBlocks()) {
            if (patternBlockInfo.componentType == ComponentType.HEART)
                continue;

            BlockPos blockPos = origin.offset(BlockPosHelper.rotateFromSouth(patternBlockInfo.offset, facing)).relative(facing.getOpposite(), 1);
            BlockState blockState = world.getBlockState(blockPos);
            Block currBlock = blockState.getBlock();
            if (currBlock != ComponentType.getBlock(patternBlockInfo.componentType))
                invalidBlocks.add(blockPos);
        }

        return invalidBlocks;
    }

    private boolean removeFactoryBlock(World world, PlayerEntity playerEntity, BlockPos origin, Direction facing, FactoryTier tier) {

        PatternLibrary.Pattern pattern = PatternLibrary.get().get(FactoryTier.TIER_5);
        if (pattern == null)
            return false;

        ObjectArrayList<Pair<ItemStack, BlockPos>> drops = new ObjectArrayList<>();
        for (PatternBlockInfo patternBlockInfo : pattern.getBlocks()) {
            if (patternBlockInfo.componentType == ComponentType.HEART)
                continue;

            BlockPos blockPos = origin.offset(BlockPosHelper.rotateFromSouth(patternBlockInfo.offset, facing)).relative(facing.getOpposite(), 1);
            BlockState blockState = world.getBlockState(blockPos);
            Block currBlock = blockState.getBlock();
            if (currBlock == ComponentType.getBlock(patternBlockInfo.componentType)) {
                ItemStack drop = ComponentType.getItemStack(patternBlockInfo.componentType);
                blockState.onBlockExploded(world, blockPos, null);
                int count = drops.size();
                for (int j = 0; j < count; j++) {
                    Pair<ItemStack, BlockPos> pair = drops.get(j);
                    ItemStack itemStack = pair.getFirst();
                    if (ItemEntity.areMergable(itemStack, drop)) {
                        ItemStack itemStack1 = ItemEntity.merge(itemStack, drop, 16);
                        drops.set(j, Pair.of(itemStack1, pair.getSecond()));
                    }
                }

                if (!drop.isEmpty())
                    drops.add(Pair.of(drop, blockPos));
            }
        }

        for (Pair<ItemStack, BlockPos> pair : drops)
            Block.popResource(world, pair.getSecond(), pair.getFirst());

        return true;
    }

    private boolean addFactoryBlock(World world, PlayerEntity playerEntity, BlockPos origin, Direction facing, FactoryTier tier) {

        PatternLibrary.Pattern pattern = PatternLibrary.get().get(tier);
        if (pattern == null)
            return false;

        for (PatternBlockInfo patternBlockInfo : pattern.getBlocks()) {
            if (patternBlockInfo.componentType == ComponentType.HEART)
                continue;

            BlockPos blockPos = origin.offset(BlockPosHelper.rotateFromSouth(patternBlockInfo.offset, facing)).relative(facing.getOpposite(), 1);
            Block currBlock = world.getBlockState(blockPos).getBlock();
            if (currBlock != ComponentType.getBlock(patternBlockInfo.componentType)) {
                BlockState blockState = ComponentType.getDefaultBlockState(patternBlockInfo.componentType);
                placeBlock(world, playerEntity, blockPos, blockState);
                return true;
            }
        }
        return false;
    }

    private void placeBlock(World world, PlayerEntity playerEntity, BlockPos pos, BlockState blockState) {

        if (playerEntity instanceof ServerPlayerEntity) {
            BlockSnapshot blockSnapshot = BlockSnapshot.create(world.dimension(), world, pos);
            world.setBlockAndUpdate(pos, blockState);
            if (ForgeEventFactory.onBlockPlace(playerEntity, blockSnapshot, Direction.UP)) {
                blockSnapshot.restore(true, false);
                return;
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

        InternModes internMode = getToolMode(itemStack);
        if (tooltipKey != null) {
            tooltip.add((new TranslationTextComponent(tooltipKey)).withStyle(TextFormatting.LIGHT_PURPLE));
            tooltip.add(new TranslationTextComponent(internMode.getDescriptionId()).withStyle(TextFormatting.AQUA));
        }
        if (!sneakTooltipKeys.isEmpty()) {
            if (Screen.hasShiftDown()) {
                sneakTooltipKeys.forEach(x -> tooltip.add(new TranslationTextComponent(x)));
                if (internMode != InternModes.DESTROY) {
                    PatternLibrary.Pattern pattern = PatternLibrary.get().get(internMode.getTier());
                    if (pattern != null) {
                        for (ComponentType componentType : ComponentType.values()) {
                            int count = pattern.getCounts().getOrDefault(componentType, 0);
                            if (count > 0) {
                                // If it's good enough for the vanilla shulker box, it's good enough for this
                                ItemStack itemStack1 = new ItemStack(ComponentType.getBlock(componentType));
                                IFormattableTextComponent textComponent = itemStack1.getHoverName().copy();
                                textComponent.append(" x").append(String.valueOf(count));
                                tooltip.add(textComponent);
                            }
                        }
                    }
                }
            } else {
                tooltip.add((new TranslationTextComponent("info.woot.sneakforinfo")).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.BLUE));
            }
        }
        if (flag.isAdvanced() && advTooltipKey != null)
            tooltip.add((new TranslationTextComponent(advTooltipKey)));

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
}
