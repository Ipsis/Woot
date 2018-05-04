package ipsis.woot.item;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactoryHeart;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.multiblock.EnumMobFactoryModule;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.multiblock.MobFactoryModule;
import ipsis.woot.oss.ItemHelper;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBuilder extends ItemWoot {

    public static final String BASENAME = "builder";

    public ItemBuilder() {

        super(BASENAME);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemBuilder, BASENAME.toLowerCase());
    }

    private static String TIER_TAG = "tier";
    private EnumMobFactoryTier getTierFromNbt(ItemStack itemStack) {

        if (!itemStack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger(TIER_TAG, EnumMobFactoryTier.TIER_ONE.ordinal());
            itemStack.setTagCompound(tag);
        }

        NBTTagCompound tag = itemStack.getTagCompound();
        return EnumMobFactoryTier.getTier(tag.getInteger(TIER_TAG));
    }

    private void cycleTier(ItemStack itemStack, EntityPlayer entityPlayer) {

        if (!itemStack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger(TIER_TAG, EnumMobFactoryTier.TIER_ONE.ordinal());
            itemStack.setTagCompound(tag);
        }

        NBTTagCompound tag = itemStack.getTagCompound();
        EnumMobFactoryTier next = EnumMobFactoryTier.getTier(tag.getInteger(TIER_TAG));
        next = next.getNext();
        tag.setInteger(TIER_TAG, next.ordinal());

        entityPlayer.sendStatusMessage(new TextComponentString(StringHelper.localize(next.getTranslated("info.woot.tier"))), false);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        tooltip.add(TextFormatting.ITALIC + "Builds the factory for you");

        EnumMobFactoryTier tier = getTierFromNbt(stack);
        tooltip.add(StringHelper.localize(tier.getTranslated("info.woot.tier")));

        for (EnumMobFactoryModule m : EnumMobFactoryModule.values()) {
            int c = Woot.factoryPatternRepository.getBlockCount(tier, m);
            if (c > 0)
                tooltip.add(c + " " + StringHelper.localize("tile.woot.structure." + m.getName() + ".name"));

        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemStack = playerIn.getHeldItem(handIn);

        // Sneak click, not on block, to cycle
        if (playerIn.isSneaking()) {
            RayTraceResult rayTraceResult = rayTrace(worldIn, playerIn, false);
            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
                return new ActionResult<>(EnumActionResult.PASS, itemStack);

            if (!worldIn.isRemote)
                cycleTier(itemStack, playerIn);

            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        EnumActionResult result = EnumActionResult.PASS;

        ItemStack itemStack = player.getHeldItem(hand);

        if (!player.isSneaking()) {
            Block b = worldIn.getBlockState(pos).getBlock();
            if (b instanceof BlockMobFactoryHeart) {
                EnumFacing factoryFacing = worldIn.getBlockState(pos).getValue(BlockMobFactoryHeart.FACING);
                if (tryBuild(player, worldIn, pos, factoryFacing, getTierFromNbt(itemStack)))
                    result = EnumActionResult.SUCCESS;
            }
        }

        return result;
    }

    private boolean playerHasBlock(EntityPlayer player, EnumMobFactoryModule m) {

        if (player.capabilities.isCreativeMode)
            return true;

        ItemStack itemStack = ((BlockMobFactoryStructure)ModBlocks.blockStructure).getItemStack(m);
        for (ItemStack playerItemStack : player.inventory.mainInventory) {

            if (playerItemStack.isEmpty())
                continue;

            if (ItemHelper.itemsEqualWithMetadata(itemStack, playerItemStack))
                return true;
        }

        return false;
    }

    private void takePlayerBlock(EntityPlayer player, EnumMobFactoryModule m) {

        if (player.capabilities.isCreativeMode)
            return;

        ItemStack itemStack = ((BlockMobFactoryStructure)ModBlocks.blockStructure).getItemStack(m);
        for (ItemStack playerItemStack : player.inventory.mainInventory) {

            if (playerItemStack.isEmpty())
                continue;

            if (ItemHelper.itemsEqualWithMetadata(itemStack, playerItemStack)) {
                playerItemStack.shrink(1);
                return;
            }
        }
    }

    private boolean isBlockCorrect(Block block, IBlockState iBlockState, EnumMobFactoryModule m) {

        if (!(block instanceof BlockMobFactoryStructure))
            return false;

        BlockMobFactoryStructure s = (BlockMobFactoryStructure)block;

        return s.getModuleTypeFromState(iBlockState) == m;
    }

    private boolean tryBuild(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, EnumMobFactoryTier tier) {

        for (MobFactoryModule m : Woot.factoryPatternRepository.getAllModules(tier)) {

            BlockPos placePos = new BlockPos(pos).add(ipsis.woot.util.BlockPosHelper.rotateFromSouth(m.getOffset(), facing.getOpposite()));
            IBlockState currState = world.getBlockState(placePos);
            Block currBlock = currState.getBlock();

            if (isBlockCorrect(currBlock, currState, m.getModuleType()))
                continue;

            if (!playerHasBlock(player, m.getModuleType())) {
                player.sendStatusMessage(new TextComponentString("Block not in inventory (" + StringHelper.localize("tile.woot.structure." + m.getModuleType().getName() + ".name") + ")"), false);
                return false;
            }

            if (currBlock.isAir(currState, world, placePos) || currBlock.isReplaceable(world, placePos)) {
                int meta = m.getModuleType().getMetadata();
                world.setBlockState(placePos, ModBlocks.blockStructure.getStateFromMeta(meta));
                takePlayerBlock(player, m.getModuleType());
                return true;
            } else {
                player.sendStatusMessage(new TextComponentString("Cannot replace block at " + placePos), false);
                return false;
            }
        }

        return false;
    }
}
