package ipsis.woot.blocks;

import ipsis.Woot;
import ipsis.woot.ModBlocks;
import ipsis.woot.ModItems;
import ipsis.woot.factory.structure.Factory;
import ipsis.woot.factory.structure.FactoryBuilder;
import ipsis.woot.factory.structure.FactoryScanner;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.factory.structure.pattern.ScannedPattern;
import ipsis.woot.items.IBuilderItem;
import ipsis.woot.items.ItemIntern;
import ipsis.woot.items.ItemYaHammer;
import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.WorldHelper;
import ipsis.woot.util.helpers.BuilderHelper;
import ipsis.woot.util.helpers.PlayerHelper;
import ipsis.woot.util.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockHeart extends Block implements ITileEntityProvider {

    private static final String BASENAME = "heart";

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockHeart() {

        super(Material.ROCK);
        setCreativeTab(Woot.tab);
        setUnlocalizedName(Woot.MODID + "." + BASENAME);
        setRegistryName(BASENAME);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    public static String getBasename() { return BASENAME; }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                0,
                new ModelResourceLocation(getRegistryName(), ".inventory"));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityHeart();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
    }

    private static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entityLivingBase) {
        return EnumFacing.getFacingFromVector(
                (float)(entityLivingBase.posX - clickedBlock.getX()),
                (float)(entityLivingBase.posY - clickedBlock.getY()),
                (float)(entityLivingBase.posZ - clickedBlock.getZ()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (WorldHelper.isClientWorld(worldIn))
            return true;

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityHeart) {
            TileEntityHeart heart = (TileEntityHeart)te;
            ItemStack heldItemStack = playerIn.getHeldItemMainhand();
            Item heldItem = heldItemStack.getItem();

            if (heldItemStack.isEmpty()) {
                // TODO show gui
                return true;
            }

            if (heldItem instanceof IBuilderItem) {

                IBuilderItem iBuilderItem = (IBuilderItem)heldItem;
                FactoryTier tier = iBuilderItem.getTier(heldItemStack);
                if (iBuilderItem.getBuilderMode(heldItemStack).isValidateMode()) {
                    EnumFacing heartFacing = worldIn.getBlockState(pos).getValue(FACING);
                    PlayerHelper.sendChatMessage(playerIn, StringHelper.localise("chat.woot.validate.tier", tier.getLocalisedName(), heartFacing.getName()));
                    ScannedPattern scannedPattern = FactoryScanner.scanTier(worldIn, tier, pos, heartFacing);
                    if (!scannedPattern.isComplete()) {
                        for (ScannedPattern.BadLayoutBlockInfo info : scannedPattern.getBadBlocks()) {
                            if (info.reason == ScannedPattern.BadBlockReason.MISSING_BLOCK) {
                                PlayerHelper.sendChatMessage(TextFormatting.RED, playerIn, StringHelper.localise("chat.woot.validate.missing", ModBlocks.getBlockFromFactoryBlock(info.correctBlock).getLocalizedName(), info.pos));
                            } else if (info.reason == ScannedPattern.BadBlockReason.INCORRECT_BLOCK || info.reason == ScannedPattern.BadBlockReason.INCORRECT_TYPE) {
                                Block c = ModBlocks.getBlockFromFactoryBlock(info.correctBlock);
                                PlayerHelper.sendChatMessage(TextFormatting.RED, playerIn, StringHelper.localise("chat.woot.validate.wrongblock", info.incorrectBlock.getLocalizedName(), info.pos, c.getLocalizedName()));
                            } else if (info.reason == ScannedPattern.BadBlockReason.INVALID_MOB) {
                                PlayerHelper.sendChatMessage(TextFormatting.RED, playerIn, StringHelper.localise("chat.woot.validate.invalidmod", "TODO"));
                            } else if (info.reason == ScannedPattern.BadBlockReason.MISSING_MOB) {
                                PlayerHelper.sendChatMessage(TextFormatting.RED, playerIn, StringHelper.localise("chat.woot.validate.missingmob"));
                            }
                        }
                    } else {
                        String displayName = "";
                        EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(scannedPattern.getControllerMob().getResourceLocation());
                        if (entityEntry != null)
                            displayName = "entity." + entityEntry.getName() + ".name";
                        PlayerHelper.sendChatMessage(TextFormatting.GREEN, playerIn, StringHelper.localise("chat.woot.validate.ok", tier.getLocalisedName(), StringHelper.localise(displayName)));
                    }
                } else if (iBuilderItem.getBuilderMode(heldItemStack).isBuildMode()) {
                    EnumFacing heartFacing = worldIn.getBlockState(pos).getValue(FACING);
                    AbsolutePattern absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(worldIn, tier, pos, heartFacing);
                    FactoryBuilder.autoBuildSingleBlock(playerIn, worldIn, absolutePattern);
                }
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
