package ipsis.woot.blocks;

import ipsis.Woot;
import ipsis.woot.ModItems;
import ipsis.woot.factory.structure.FactoryScanner;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.factory.structure.pattern.ScannedPattern;
import ipsis.woot.items.ItemYaHammer;
import ipsis.woot.util.FactoryBlock;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.WorldHelper;
import ipsis.woot.util.helpers.PlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockHeart extends Block implements ITileEntityProvider {

    private static final String BASENAME = "heart";

    public BlockHeart() {

        super(Material.ROCK);
        setCreativeTab(Woot.tab);
        setUnlocalizedName(Woot.MODID + "." + BASENAME);
        setRegistryName(BASENAME);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (WorldHelper.isClientWorld(worldIn))
            return true;

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityHeart) {
            TileEntityHeart heart = (TileEntityHeart)te;
            ItemStack heldItem = playerIn.getHeldItemMainhand();

            if (heldItem.isEmpty()) {
                // TODO show gui
                return true;
            }

            if (heldItem.getItem() instanceof ItemYaHammer) {
                ScannedPattern scannedPattern = FactoryScanner.scanTier(worldIn, FactoryTier.TIER_1, pos, EnumFacing.SOUTH);

                PlayerHelper.sendChatMessage(playerIn, "Validating tier " + FactoryTier.TIER_1.toString());
                if (scannedPattern.hasBadBlocks()) {
                    for (ScannedPattern.BadLayoutBlockInfo info : scannedPattern.getBadBlocks()) {
                        if (info.reason == ScannedPattern.BadBlockReason.MISSING_BLOCK)
                            PlayerHelper.sendChatMessage(playerIn, "Missing block " + info.pos);
                        else if (info.reason == ScannedPattern.BadBlockReason.INCORRECT_BLOCK)
                            PlayerHelper.sendChatMessage(playerIn, "Wrong block " + info.pos + " " + info.incorrectBlock + "->" + info.correctBlock);
                        else if (info.reason == ScannedPattern.BadBlockReason.INCORRECT_TYPE)
                            PlayerHelper.sendChatMessage(playerIn, "Wrong type " + info.pos + " " + info.incorrectBlock + "->" + info.correctBlock);
                    }
                }
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
