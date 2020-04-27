package ipsis.woot.modules.factory.layout;

import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.FactoryComponentProvider;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.multiblock.MultiBlockGlueProvider;
import ipsis.woot.modules.factory.multiblock.MultiBlockMaster;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;
import java.util.List;

public class FactoryHelper {

    public static void disconnectOld(World world, AbsolutePattern absolutePattern) {
        for (PatternBlock pb : absolutePattern.getBlocks()) {
            if (world.isBlockLoaded(pb.getBlockPos())) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof MultiBlockGlueProvider)
                    ((MultiBlockGlueProvider)te).getGlue().clearMaster();
            }
        }
    }

    public static void connectNew(World world, AbsolutePattern absolutePattern, MultiBlockMaster master) {
        for (PatternBlock pb : absolutePattern.getBlocks()) {
            if (world.isBlockLoaded(pb.getBlockPos())) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof MultiBlockGlueProvider)
                    ((MultiBlockGlueProvider)te).getGlue().setMaster(master);
            }
        }
    }

    static boolean isCorrectBlock(FactoryComponentProvider provider, FactoryComponent component) {
        return provider.getFactoryComponent() == component;
    }

    static boolean isCorrectBlock(Block block, List<Block> validBlocks) {
        for (Block b : validBlocks) {
            if (block == b)
                return true;
        }
        return false;
    }


    public static boolean tryBuild(World world, BlockPos pos, PlayerEntity playerEntity, Direction facing, Tier tier) {

        if (!playerEntity.isAllowEdit())
            return false;

        AbsolutePattern absolutePattern = AbsolutePattern.create(world, tier, pos, facing);
        for (PatternBlock pb : absolutePattern.getBlocks()) {
            BlockState currState = world.getBlockState(pb.getBlockPos());
            Block currBlock = currState.getBlock();

            List<Block> correctBlocks = getComponentBlocks(pb.getFactoryComponent());
            List<ItemStack> correctItemStacks = getComponentStacks(pb.getFactoryComponent());

            // Is it the correct block already
            if (isCorrectBlock(currBlock, correctBlocks))
                continue;

            if (pb.getFactoryComponent() == FactoryComponent.CONTROLLER || pb.getFactoryComponent() == FactoryComponent.CELL)
                continue;

            Block placeBlock = correctBlocks.get(0);
            if (!PlayerHelper.playerHasFactoryComponent(playerEntity, correctItemStacks)) {
                PlayerHelper.sendChatMessage(playerEntity, StringHelper.translate("Do not have " + pb.getFactoryComponent()));
                return false;
            }
            if (world.isBlockModifiable(playerEntity, pb.getBlockPos()) && (world.isAirBlock(pb.getBlockPos()) || currState.getMaterial().isReplaceable())) {
                ItemStack takenStack = PlayerHelper.takeFactoryComponent(playerEntity, correctItemStacks);
                if (!takenStack.isEmpty()) {

                    BlockSnapshot blockSnapshot = BlockSnapshot.getBlockSnapshot(world, pos);
                    world.setBlockState(pb.getBlockPos(), placeBlock.getDefaultState(), 11);
                    if (ForgeEventFactory.onBlockPlace(playerEntity, blockSnapshot, Direction.UP)) {
                        blockSnapshot.restore(true, false);
                        return false;
                    }
                }
                return true;
            } else {
                // cannot replace block
                PlayerHelper.sendChatMessage(playerEntity, "Cannot replace block with " + pb.getFactoryComponent());
                return false;
            }
        }
        return false;
    }

    public static void tryValidate(World world, BlockPos pos, PlayerEntity playerEntity, Direction facing, Tier tier) {

        PlayerHelper.sendChatMessage(playerEntity,
                "Validating " + tier);

        List<String> feedback = new ArrayList<>();
        AbsolutePattern absolutePattern = AbsolutePattern.create(world, tier, pos, facing);
        if (!FactoryScanner.compareToWorld(absolutePattern, world, feedback)) {
            for (String s : feedback)
                PlayerHelper.sendChatMessage(playerEntity, s);
        } else {
            PlayerHelper.sendChatMessage(playerEntity,"Found valid " + tier);
        }
    }

    static List<Block> getComponentBlocks(FactoryComponent component) {
        List<Block> blocks = new ArrayList<>();
        switch (component) {
            case FACTORY_A:
                blocks.add(FactorySetup.FACTORY_A_BLOCK.get());
                break;
            case FACTORY_B:
                blocks.add(FactorySetup.FACTORY_B_BLOCK.get());
                break;
            case FACTORY_C:
                blocks.add(FactorySetup.FACTORY_C_BLOCK.get());
                break;
            case FACTORY_D:
                blocks.add(FactorySetup.FACTORY_D_BLOCK.get());
                break;
            case FACTORY_E:
                blocks.add(FactorySetup.FACTORY_E_BLOCK.get());
                break;
            case FACTORY_CONNECT:
                blocks.add(FactorySetup.FACTORY_CONNECT_BLOCK.get());
                break;
            case FACTORY_CTR_BASE_PRI:
                blocks.add(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get());
                break;
            case FACTORY_CTR_BASE_SEC:
                blocks.add(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get());
                break;
            case FACTORY_UPGRADE:
                blocks.add(FactorySetup.FACTORY_UPGRADE_BLOCK.get());
                break;
            case HEART:
                blocks.add(FactorySetup.HEART_BLOCK.get());
                break;
            case CAP_A:
                blocks.add(FactorySetup.CAP_A_BLOCK.get());
                break;
            case CAP_B:
                blocks.add(FactorySetup.CAP_B_BLOCK.get());
                break;
            case CAP_C:
                blocks.add(FactorySetup.CAP_C_BLOCK.get());
                break;
            case CAP_D:
                blocks.add(FactorySetup.CAP_D_BLOCK.get());
                break;
            case IMPORT:
                blocks.add(FactorySetup.IMPORT_BLOCK.get());
                break;
            case EXPORT:
                blocks.add(FactorySetup.EXPORT_BLOCK.get());
                break;
            case CONTROLLER:
                blocks.add(FactorySetup.CONTROLLER_BLOCK.get());
                break;
            case CELL:
                blocks.add(FactorySetup.CELL_1_BLOCK.get());
                blocks.add(FactorySetup.CELL_2_BLOCK.get());
                blocks.add(FactorySetup.CELL_3_BLOCK.get());
                break;
        }
        return blocks;
    }

    public static List<ItemStack> getComponentStacks(FactoryComponent component) {
        List<ItemStack> stacks = new ArrayList<>();
        switch (component) {
            case FACTORY_A:
                stacks.add(new ItemStack(FactorySetup.FACTORY_A_BLOCK.get()));
                break;
            case FACTORY_B:
                stacks.add(new ItemStack(FactorySetup.FACTORY_B_BLOCK.get()));
                break;
            case FACTORY_C:
                stacks.add(new ItemStack(FactorySetup.FACTORY_C_BLOCK.get()));
                break;
            case FACTORY_D:
                stacks.add(new ItemStack(FactorySetup.FACTORY_D_BLOCK.get()));
                break;
            case FACTORY_E:
                stacks.add(new ItemStack(FactorySetup.FACTORY_E_BLOCK.get()));
                break;
            case FACTORY_CONNECT:
                stacks.add(new ItemStack(FactorySetup.FACTORY_CONNECT_BLOCK.get()));
                break;
            case FACTORY_CTR_BASE_PRI:
                stacks.add(new ItemStack(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get()));
                break;
            case FACTORY_CTR_BASE_SEC:
                stacks.add(new ItemStack(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get()));
                break;
            case FACTORY_UPGRADE:
                stacks.add(new ItemStack(FactorySetup.FACTORY_UPGRADE_BLOCK.get()));
                break;
            case HEART:
                stacks.add(new ItemStack(FactorySetup.HEART_BLOCK.get()));
                break;
            case CAP_A:
                stacks.add(new ItemStack(FactorySetup.CAP_A_BLOCK.get()));
                break;
            case CAP_B:
                stacks.add(new ItemStack(FactorySetup.CAP_B_BLOCK.get()));
                break;
            case CAP_C:
                stacks.add(new ItemStack(FactorySetup.CAP_C_BLOCK.get()));
                break;
            case CAP_D:
                stacks.add(new ItemStack(FactorySetup.CAP_D_BLOCK.get()));
                break;
            case IMPORT:
                stacks.add(new ItemStack(FactorySetup.IMPORT_BLOCK.get()));
                break;
            case EXPORT:
                stacks.add(new ItemStack(FactorySetup.EXPORT_BLOCK.get()));
                break;
            case CONTROLLER:
                stacks.add(new ItemStack(FactorySetup.CONTROLLER_BLOCK.get()));
                break;
            case CELL:
                stacks.add(new ItemStack(FactorySetup.CELL_1_BLOCK.get()));
                stacks.add(new ItemStack(FactorySetup.CELL_2_BLOCK.get()));
                stacks.add(new ItemStack(FactorySetup.CELL_3_BLOCK.get()));
                break;
        }

        return stacks;
    }
}
