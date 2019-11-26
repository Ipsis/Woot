package ipsis.woot.factory.layout;

import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryComponentProvider;
import ipsis.woot.factory.Tier;
import ipsis.woot.factory.blocks.power.CellBlock;
import ipsis.woot.factory.blocks.power.CellTileEntityBase;
import ipsis.woot.factory.multiblock.MultiBlockGlueProvider;
import ipsis.woot.factory.multiblock.MultiBlockMaster;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.util.helper.PlayerHelper;
import ipsis.woot.util.helper.StringHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

            if (pb.getFactoryComponent() == FactoryComponent.CELL || pb.getFactoryComponent() == FactoryComponent.CONTROLLER)
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
                PlayerHelper.sendChatMessage(playerEntity, "Cannot replace block");
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
                blocks.add(ModBlocks.FACTORY_A_BLOCK);
                break;
            case FACTORY_B:
                blocks.add(ModBlocks.FACTORY_B_BLOCK);
                break;
            case FACTORY_C:
                blocks.add(ModBlocks.FACTORY_C_BLOCK);
                break;
            case FACTORY_D:
                blocks.add(ModBlocks.FACTORY_D_BLOCK);
                break;
            case FACTORY_E:
                blocks.add(ModBlocks.FACTORY_E_BLOCK);
                break;
            case FACTORY_CONNECT:
                blocks.add(ModBlocks.FACTORY_CONNECT_BLOCK);
                break;
            case FACTORY_CTR_BASE:
                blocks.add(ModBlocks.FACTORY_CTR_BASE_BLOCK);
                break;
            case FACTORY_UPGRADE:
                blocks.add(ModBlocks.FACTORY_UPGRADE_BLOCK);
                break;
            case HEART:
                blocks.add(ModBlocks.HEART_BLOCK);
                break;
            case CAP_A:
                blocks.add(ModBlocks.CAP_A_BLOCK);
                break;
            case CAP_B:
                blocks.add(ModBlocks.CAP_B_BLOCK);
                break;
            case CAP_C:
                blocks.add(ModBlocks.CAP_C_BLOCK);
                break;
            case CAP_D:
                blocks.add(ModBlocks.CAP_D_BLOCK);
                break;
            case IMPORT:
                blocks.add(ModBlocks.IMPORT_BLOCK);
                break;
            case EXPORT:
                blocks.add(ModBlocks.EXPORT_BLOCK);
                break;
            case CONTROLLER:
                blocks.add(ModBlocks.CONTROLLER_BLOCK);
                break;
            case CELL:
                blocks.add(ModBlocks.CELL_1_BLOCK);
                blocks.add(ModBlocks.CELL_2_BLOCK);
                blocks.add(ModBlocks.CELL_3_BLOCK);
                break;
        }
        return blocks;
    }

    public static List<ItemStack> getComponentStacks(FactoryComponent component) {
        List<ItemStack> stacks = new ArrayList<>();
        switch (component) {
            case FACTORY_A:
                stacks.add(new ItemStack(ModBlocks.FACTORY_A_BLOCK));
                break;
            case FACTORY_B:
                stacks.add(new ItemStack(ModBlocks.FACTORY_B_BLOCK));
                break;
            case FACTORY_C:
                stacks.add(new ItemStack(ModBlocks.FACTORY_C_BLOCK));
                break;
            case FACTORY_D:
                stacks.add(new ItemStack(ModBlocks.FACTORY_D_BLOCK));
                break;
            case FACTORY_E:
                stacks.add(new ItemStack(ModBlocks.FACTORY_E_BLOCK));
                break;
            case FACTORY_CONNECT:
                stacks.add(new ItemStack(ModBlocks.FACTORY_CONNECT_BLOCK));
                break;
            case FACTORY_CTR_BASE:
                stacks.add(new ItemStack(ModBlocks.FACTORY_CTR_BASE_BLOCK));
                break;
            case FACTORY_UPGRADE:
                stacks.add(new ItemStack(ModBlocks.FACTORY_UPGRADE_BLOCK));
                break;
            case HEART:
                stacks.add(new ItemStack(ModBlocks.HEART_BLOCK));
                break;
            case CAP_A:
                stacks.add(new ItemStack(ModBlocks.CAP_A_BLOCK));
                break;
            case CAP_B:
                stacks.add(new ItemStack(ModBlocks.CAP_A_BLOCK));
                break;
            case CAP_C:
                stacks.add(new ItemStack(ModBlocks.CAP_A_BLOCK));
                break;
            case CAP_D:
                stacks.add(new ItemStack(ModBlocks.CAP_A_BLOCK));
                break;
            case IMPORT:
                stacks.add(new ItemStack(ModBlocks.IMPORT_BLOCK));
                break;
            case EXPORT:
                stacks.add(new ItemStack(ModBlocks.EXPORT_BLOCK));
                break;
            case CONTROLLER:
                stacks.add(new ItemStack(ModBlocks.CONTROLLER_BLOCK));
                break;
            case CELL:
                stacks.add(new ItemStack(ModBlocks.CELL_1_BLOCK));
                stacks.add(new ItemStack(ModBlocks.CELL_2_BLOCK));
                stacks.add(new ItemStack(ModBlocks.CELL_3_BLOCK));
                break;
        }

        return stacks;
    }

    static Block getComponentBlock(FactoryComponent component) {
        switch (component) {
            case FACTORY_A: return ModBlocks.FACTORY_A_BLOCK;
            case FACTORY_B: return ModBlocks.FACTORY_B_BLOCK;
            case FACTORY_C: return ModBlocks.FACTORY_C_BLOCK;
            case FACTORY_D: return ModBlocks.FACTORY_D_BLOCK;
            case FACTORY_E: return ModBlocks.FACTORY_E_BLOCK;
            case FACTORY_CONNECT: return ModBlocks.FACTORY_CONNECT_BLOCK;
            case FACTORY_CTR_BASE: return ModBlocks.FACTORY_CTR_BASE_BLOCK;
            case FACTORY_UPGRADE: return ModBlocks.FACTORY_UPGRADE_BLOCK;
            case HEART: return ModBlocks.HEART_BLOCK;
            case CAP_A: return ModBlocks.CAP_A_BLOCK;
            case CAP_B: return ModBlocks.CAP_B_BLOCK;
            case CAP_C: return ModBlocks.CAP_C_BLOCK;
            case CAP_D: return ModBlocks.CAP_D_BLOCK;
            case IMPORT: return ModBlocks.IMPORT_BLOCK;
            case EXPORT: return ModBlocks.EXPORT_BLOCK;
            case CONTROLLER: return ModBlocks.CONTROLLER_BLOCK;
            case CELL: return ModBlocks.CELL_1_BLOCK;
            default: return ModBlocks.FACTORY_A_BLOCK;
        }
    }



}
