package ipsis.woot.tileentity.multiblock;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactory;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.reference.Lang;
import ipsis.woot.tileentity.LayoutBlockInfo;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.util.BlockPosHelper;
import ipsis.woot.util.StringHelper;
import ipsis.woot.util.UnlocalizedName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class MobFactoryMultiblockLogic {

    static {
        EnumMobFactoryTier.TIER_ONE.buildStructureMap();
        EnumMobFactoryTier.TIER_TWO.buildStructureMap();
        EnumMobFactoryTier.TIER_THREE.buildStructureMap();
        EnumMobFactoryTier.TIER_FOUR.buildStructureMap();
    }

    public static class FactorySetup {

        List<BlockPos> blockPosList;
        EnumMobFactoryTier size;
        String mobName;
        String displayName;

        public FactorySetup() {

            blockPosList = new ArrayList<BlockPos>();
            size = null;
            mobName = "";
            displayName = "";
        }

        public EnumMobFactoryTier getSize() { return this.size; }
        public List<BlockPos> getBlockPosList() { return this.blockPosList; }
        public boolean isValid() { return this.size != null; }
        public String getMobName() { return this.mobName; }
        public String getDisplayName() { return this.displayName; }
    }

    /**
     * Validates a factory
     * @param factory - the main factory TE
     * @param feedback - should be tell the client what is wrong
     * @return null if invalid else the size of the factory
     */
    public static FactorySetup validateFactory(TileEntityMobFactory factory, boolean feedback, EntityPlayer player) {

        FactorySetup factorySetup;

        factorySetup = validateFactory(factory, EnumMobFactoryTier.TIER_FOUR, feedback, player);
        if (factorySetup.size != null)
            return factorySetup;

        factorySetup = validateFactory(factory, EnumMobFactoryTier.TIER_THREE, feedback, player);
        if (factorySetup.size != null)
            return factorySetup;

        factorySetup = validateFactory(factory, EnumMobFactoryTier.TIER_TWO, feedback, player);
        if (factorySetup.size != null)
            return factorySetup;

        return validateFactory(factory, EnumMobFactoryTier.TIER_ONE, feedback, player);
    }

    public static FactorySetup validateFactory(TileEntityMobFactory factory) {
        return validateFactory(factory, false, null);
    }

    public static void getFactoryLayout(EnumMobFactoryTier tier, BlockPos origin, EnumFacing facing, List<LayoutBlockInfo> layoutBlockInfoList) {

        for (MobFactoryModule s : tier.structureModules) {

            BlockPos p = BlockPosHelper.rotateFromSouth(s.getOffset(), facing.getOpposite());
            p = origin.add(p);
            layoutBlockInfoList.add(new LayoutBlockInfo(p, s.moduleType));
        }
    }

    static void validateChat(EntityPlayer player, String s) {

        if (player != null)
            player.addChatComponentMessage(new TextComponentString(s));
    }

    static FactorySetup validateFactory(TileEntityMobFactory factory, EnumMobFactoryTier tier, boolean feedback, EntityPlayer player) {

        FactorySetup factorySetup = new FactorySetup();

        BlockPos controllerPos = factory.getPos().up(1);
        TileEntity te = factory.getWorld().getTileEntity(controllerPos);
        if (!(te instanceof TileEntityMobFactoryController)) {
            /* FAIL - No mob controller */
            if (feedback)
                validateChat(player, TextFormatting.RED + String.format(StringHelper.localize(Lang.VALIDATE_FACTORY_MISSING_CONTROLLER), tier));
            return factorySetup;
        }

        TileEntityMobFactoryController teController = (TileEntityMobFactoryController)te;
        if (teController.getMobName().equals("")) {
            /* FAIL - No mob programmed */
            if (feedback && player != null)
                validateChat(player, TextFormatting.RED + String.format(StringHelper.localize(Lang.VALIDATE_FACTORY_MISSING_MOB), tier));
            return factorySetup;
        }

        if (!Woot.mobRegistry.isPrismValid(teController.getMobName())) {
            /* FAIL - Mob blacklisted */
            if (feedback)
                validateChat(player, TextFormatting.RED + String.format(StringHelper.localize(Lang.CHAT_MOB_INVALID), teController.getModDisplayName(), teController.getMobName()));
            return new FactorySetup();
        }

        factorySetup.mobName = teController.getMobName();
        factorySetup.displayName = teController.getModDisplayName();

        BlockPos patternOrigin = factory.getPos();

        /**
         * Only do this if not manually validating as this will shortcut the check
         * For manual validation we want to start checking
         */
        if (!feedback) {
            if (isSize(factory, tier))
                factorySetup.size = tier;
            else
                return factorySetup;
        } else {
            factorySetup.size = tier;
        }

        EnumFacing f = factory.getWorld().getBlockState(factory.getPos()).getValue(BlockMobFactory.FACING);
        for (MobFactoryModule s : factorySetup.size.structureModules) {

            BlockPos p = BlockPosHelper.rotateFromSouth(s.getOffset(), f.getOpposite());
            p = patternOrigin.add(p);

            if (!factory.getWorld().isBlockLoaded(p))
                return new FactorySetup();

            IBlockState iBlockState = factory.getWorld().getBlockState(p);
            Block block = iBlockState.getBlock();

            if (!(block instanceof BlockMobFactoryStructure)) {
                if (feedback) {
                    String name = UnlocalizedName.getUnlocalizedNameBlock(BlockMobFactoryStructure.BASENAME) + "." + s.moduleType + ".name";
                    validateChat(player, TextFormatting.RED + String.format(StringHelper.localize(Lang.VALIDATE_FACTORY_INVALID_BLOCK), tier, p.getX(), p.getY(), p.getZ(),  StringHelper.localize(name)));
                }
                return new FactorySetup();
            }

            if (!(((BlockMobFactoryStructure)block).getModuleTypeFromState(iBlockState) == s.moduleType)) {
                if (feedback) {
                    String name = UnlocalizedName.getUnlocalizedNameBlock(BlockMobFactoryStructure.BASENAME) + "." + s.moduleType + ".name";
                    validateChat(player, TextFormatting.RED + String.format(StringHelper.localize(Lang.VALIDATE_FACTORY_INVALID_BLOCK), tier, p.getX(), p.getY(), p.getZ(),  StringHelper.localize(name)));
                }
                return new FactorySetup();
            }

            factorySetup.blockPosList.add(p);
        }

        /* OK - all structure blocks are present */
        validateChat(player, TextFormatting.GREEN + String.format(StringHelper.localize(Lang.VALIDATE_FACTORY_BLOCKS_OK), tier));

        boolean validMobLevel = Woot.tierMapper.isTierValid(teController.getMobName(), teController.getXpValue(), factorySetup.size);
        if (!validMobLevel) {
            /* FAIL - invalid tier for mob */
            if (feedback)
                validateChat(player, TextFormatting.RED + String.format(StringHelper.localize(Lang.VALIDATE_FACTORY_MOB_TIER), tier));
            return new FactorySetup();
        }

        return factorySetup;
    }

    static boolean isSize(TileEntityMobFactory factory, EnumMobFactoryTier size) {

        BlockPos pos;
        EnumMobFactoryModule module;
        if (size == EnumMobFactoryTier.TIER_ONE) {
            pos = new BlockPos(2, -1, -2);
            module = EnumMobFactoryModule.BLOCK_1;
        } else if (size == EnumMobFactoryTier.TIER_TWO) {
            pos = new BlockPos(3, -1, -3);
            module = EnumMobFactoryModule.BLOCK_3;
        } else if (size == EnumMobFactoryTier.TIER_THREE) {
            pos = new BlockPos(4, -1, -4);
            module = EnumMobFactoryModule.BLOCK_4;
        } else {
            pos = new BlockPos(5, -1, -5);
            module = EnumMobFactoryModule.BLOCK_5;
        }

        EnumFacing f = factory.getWorld().getBlockState(factory.getPos()).getValue(BlockMobFactory.FACING);
        pos = BlockPosHelper.rotateFromSouth(pos, f.getOpposite());
        pos = factory.getPos().add(pos.getX(), pos.getY(), pos.getZ());

        IBlockState iBlockState = factory.getWorld().getBlockState(pos);
        Block b = iBlockState.getBlock();
        if (b instanceof BlockMobFactoryStructure)
            return ((BlockMobFactoryStructure)b).getModuleTypeFromState(iBlockState) == module;

        return false;
    }
}
