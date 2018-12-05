package ipsis.woot;

import ipsis.woot.blocks.*;
import ipsis.woot.blocks.generators.BlockCreativeRF;
import ipsis.woot.blocks.generators.BlockGenerator;
import ipsis.woot.heart.BlockHeart;
import ipsis.woot.util.FactoryBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;

public class ModBlocks {

    @GameRegistry.ObjectHolder("woot:layout")
    public static final BlockLayout layoutBlock = null;

    @GameRegistry.ObjectHolder("woot:heart")
    public static final BlockHeart heartBlock = null;

    @GameRegistry.ObjectHolder("woot:controller")
    public static final BlockController controllerBlock = null;

    @GameRegistry.ObjectHolder("woot:power1")
    public static final BlockPower power1Block = null;
    @GameRegistry.ObjectHolder("woot:power2")
    public static final BlockPower power2Block = null;
    @GameRegistry.ObjectHolder("woot:power3")
    public static final BlockPower power3Block = null;

    @GameRegistry.ObjectHolder("woot:generator_tick")
    public static final BlockGenerator generatorTickBlock = null;
    @GameRegistry.ObjectHolder("woot:generator_rf")
    public static final BlockGenerator generatorRFBlock = null;

    @GameRegistry.ObjectHolder("woot:creative_rf")
    public static final BlockCreativeRF creativeRFBlock = null;

    @GameRegistry.ObjectHolder("woot:import")
    public static final BlockImport importBlock = null;

    @GameRegistry.ObjectHolder("woot:export")
    public static final BlockExport exportBlock = null;

    @GameRegistry.ObjectHolder("woot:bone_structure")
    public static final BlockStructure boneStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:flesh_structure")
    public static final BlockStructure fleshStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:blaze_structure")
    public static final BlockStructure blazeStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:ender_structure")
    public static final BlockStructure enderStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:nether_structure")
    public static final BlockStructure netherStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:redstone_structure")
    public static final BlockStructure redstoneStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:upgrade_structure")
    public static final BlockStructure upgradeStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:cap1")
    public static final BlockStructure cap1Block = null;
    @GameRegistry.ObjectHolder("woot:cap2")
    public static final BlockStructure cap2Block = null;
    @GameRegistry.ObjectHolder("woot:cap3")
    public static final BlockStructure cap3Block = null;
    @GameRegistry.ObjectHolder("woot:cap4")
    public static final BlockStructure cap4Block = null;

    /*
    public static Block getBlockFromFactoryBlock(FactoryBlock factoryBlock) {

        if (factoryBlock == FactoryBlock.BONE)
            return boneStructureBlock;
        else if (factoryBlock == FactoryBlock.FLESH)
            return fleshStructureBlock;
        else if (factoryBlock == FactoryBlock.BLAZE)
            return blazeStructureBlock;
        else if (factoryBlock == FactoryBlock.ENDER)
            return enderStructureBlock;
        else if (factoryBlock == FactoryBlock.NETHER)
            return netherStructureBlock;
        else if (factoryBlock == FactoryBlock.REDSTONE)
            return redstoneStructureBlock;
        else if (factoryBlock == FactoryBlock.UPGRADE)
            return upgradeStructureBlock;
        else if (factoryBlock == FactoryBlock.CAP_1)
            return cap1Block;
        else if (factoryBlock == FactoryBlock.CAP_2)
            return cap2Block;
        else if (factoryBlock == FactoryBlock.CAP_3)
            return cap3Block;
        else if (factoryBlock == FactoryBlock.CAP_4)
            return cap4Block;
        else if (factoryBlock == FactoryBlock.CONTROLLER)
            return controllerBlock;
        else if (factoryBlock == FactoryBlock.HEART)
            return heartBlock;
        else if (factoryBlock == FactoryBlock.POWER_1)
            return power1Block;
        else if (factoryBlock == FactoryBlock.POWER_2)
            return power2Block;
        else if (factoryBlock == FactoryBlock.POWER_3)
            return power3Block;
        else if (factoryBlock == FactoryBlock.EXPORT)
            return exportBlock;
        else if (factoryBlock == FactoryBlock.IMPORT)
            return importBlock;
        else return Blocks.DIRT;

    }

    public static ItemStack getItemStackForFactoryBlock(FactoryBlock factoryBlock) {

        switch (factoryBlock) {
            case BONE:
                return new ItemStack(boneStructureBlock);
            case FLESH:
                return new ItemStack(fleshStructureBlock);
            case BLAZE:
                return new ItemStack(blazeStructureBlock);
            case ENDER:
                return new ItemStack(enderStructureBlock);
            case NETHER:
                return new ItemStack(netherStructureBlock);
            case REDSTONE:
                return new ItemStack(redstoneStructureBlock);
            case UPGRADE:
                return new ItemStack(upgradeStructureBlock);
            case CAP_1:
                return new ItemStack(cap1Block);
            case CAP_2:
                return new ItemStack(cap2Block);
            case CAP_3:
                return new ItemStack(cap3Block);
            case CAP_4:
                return new ItemStack(cap4Block);
            case CONTROLLER:
                return new ItemStack(controllerBlock);
            case HEART:
                return new ItemStack(heartBlock);
            case POWER_1:
                return new ItemStack(power1Block);
            case POWER_2:
                return new ItemStack(power2Block);
            case POWER_3:
                return new ItemStack(power3Block);
            case EXPORT:
                return new ItemStack(exportBlock);
            case IMPORT:
                return new ItemStack(importBlock);
            default:
                return ItemStack.EMPTY;
        }
    } */

    private static HashMap<FactoryBlock, Block> factoryBlockToBlockMap;
    public static void setupFactoryBlockMapping() {

        /**
         * Not sure about this !?
         */
        factoryBlockToBlockMap = new HashMap<>();
        factoryBlockToBlockMap.put(FactoryBlock.BONE, boneStructureBlock);
        factoryBlockToBlockMap.put(FactoryBlock.FLESH, fleshStructureBlock);
        factoryBlockToBlockMap.put(FactoryBlock.BLAZE, blazeStructureBlock);
        factoryBlockToBlockMap.put(FactoryBlock.ENDER, enderStructureBlock);
        factoryBlockToBlockMap.put(FactoryBlock.NETHER, netherStructureBlock);
        factoryBlockToBlockMap.put(FactoryBlock.REDSTONE, redstoneStructureBlock);
        factoryBlockToBlockMap.put(FactoryBlock.UPGRADE, upgradeStructureBlock);
        factoryBlockToBlockMap.put(FactoryBlock.CAP_1, cap1Block);
        factoryBlockToBlockMap.put(FactoryBlock.CAP_2, cap2Block);
        factoryBlockToBlockMap.put(FactoryBlock.CAP_3, cap3Block);
        factoryBlockToBlockMap.put(FactoryBlock.CAP_4, cap4Block);
        factoryBlockToBlockMap.put(FactoryBlock.CONTROLLER, controllerBlock);
        factoryBlockToBlockMap.put(FactoryBlock.HEART, heartBlock);
        factoryBlockToBlockMap.put(FactoryBlock.POWER_1, power1Block);
        factoryBlockToBlockMap.put(FactoryBlock.POWER_2, power2Block);
        factoryBlockToBlockMap.put(FactoryBlock.POWER_3, power3Block);
        factoryBlockToBlockMap.put(FactoryBlock.IMPORT, importBlock);
        factoryBlockToBlockMap.put(FactoryBlock.EXPORT, exportBlock);
        factoryBlockToBlockMap.put(FactoryBlock.GENERATOR, generatorTickBlock);
    }

    public static Block getBlockFromFactoryBlock(FactoryBlock factoryBlock) {
        return factoryBlockToBlockMap.getOrDefault(factoryBlock, Blocks.AIR);
    }

    public static ItemStack getItemStackForFactoryBlock(FactoryBlock factoryBlock) {
        if (factoryBlockToBlockMap.containsKey(factoryBlock))
            return new ItemStack(factoryBlockToBlockMap.get(factoryBlock));
        else
            return ItemStack.EMPTY;
    }
}
