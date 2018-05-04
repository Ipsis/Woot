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
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        tooltip.add(TextFormatting.ITALIC + "Builds the factory for you");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        EnumActionResult result = EnumActionResult.PASS;

        ItemStack itemStack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            LogHelper.info("Cycle tier");
            result = EnumActionResult.SUCCESS;
        } else {

            Block b = worldIn.getBlockState(pos).getBlock();
            if (b instanceof BlockMobFactoryHeart) {
                EnumFacing factoryFacing = worldIn.getBlockState(pos).getValue(BlockMobFactoryHeart.FACING);
                if (tryBuild(player, worldIn, pos, factoryFacing, EnumMobFactoryTier.TIER_ONE))
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

            if (!playerHasBlock(player, m.getModuleType()))
                return false;

            if (currBlock.isAir(currState, world, placePos) || currBlock.isReplaceable(world, placePos)) {

                int meta = m.getModuleType().getMetadata();
                world.setBlockState(placePos, ModBlocks.blockStructure.getStateFromMeta(meta));
                takePlayerBlock(player, m.getModuleType());
                return true;
            }
        }

        return false;
    }
}
