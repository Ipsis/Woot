package ipsis.woot.init;

import ipsis.woot.block.BlockMobFactoryUpgrade;
import ipsis.woot.block.BlockMobFactoryUpgradeB;
import ipsis.woot.init.recipes.ShapedOreEnchBookRecipe;
import ipsis.woot.init.recipes.ShapedOreHammerRecipe;
import ipsis.woot.item.ItemDye;
import ipsis.woot.item.ItemFactoryUpgrade;
import ipsis.woot.item.ItemShard;
import ipsis.woot.item.ItemSkull;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
import ipsis.woot.util.BookHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

    public static void init() {

        initToolRecipes();
        initDyeRecipes();
        initItemRecipes();
        initBlockRecipes();
        initUpgradeRecipes();

        GameRegistry.addRecipe(
                new ShapelessOreRecipe(ModItems.itemManual, Items.BOOK, ModOreDictionary.ORE_DICT_SKULL));

        initRecipesCompat();
    }

    private static void initToolRecipes() {

        // Yah Hammer
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemYahHammer),
                        "fff", " w ", "sws",
                        'f', Items.NETHERBRICK,
                        'w', "stickWood",
                        's', Items.STRING));
    }


    private static void initDyeRecipe(Object in, ItemStack out) {

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        out,
                        " g ", "bib", " c ",
                        'c', Blocks.OBSIDIAN,
                        'g', "ingotGold",
                        'b', Blocks.IRON_BARS,
                        'i', in));
    }

    private static void initDyeRecipes() {

        /**
         * Dyes
         */

        initDyeRecipe(
                "stone",
                new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.CASING.getMeta()));
        initDyeRecipe(
                "dustRedstone",
                new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.CONNECTOR.getMeta()));
        initDyeRecipe(
                "slabWood",
                new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.PLATE.getMeta()));
        initDyeRecipe(
                Items.FLINT,
                new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()));
        initDyeRecipe(
                ModOreDictionary.ORE_DICT_SKULL,
                new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SKULL.getMeta()));
        initDyeRecipe(
                Blocks.IRON_BARS,
                new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.MESH.getMeta()));
        initDyeRecipe(
                "enderpearl",
                new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.PRISM.getMeta()));

        /**
         * Hammered Outputs
         */
        // UnderIron Plate
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemFerrocretePlate, 4),
                        "hf ", " d ", " o ",
                        'h', ModItems.itemYahHammer,
                        'f', ModItems.itemFerrocrete,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.PLATE.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Prism Frame
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemPrismFrame),
                        "he ", "fdf", " o ",
                        'h', ModItems.itemYahHammer,
                        'f', ModItems.itemFerrocrete,
                        'e', Items.ENDER_EYE,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.PRISM.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Factory Casing
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemFactoryCasing, 4),
                        "php",
                        " d ",
                        "pop",
                        'h', ModItems.itemYahHammer,
                        'p', ModItems.itemFerrocretePlate,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.CASING.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Iron Plated Skull
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemSkull, 2, ItemSkull.EnumSkullType.IRON.getMeta()),
                        "hi ",
                        "sds",
                        " o ",
                        's', ModOreDictionary.ORE_DICT_SKULL,
                        'i', "ingotIron",
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SKULL.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Gold Plated Skull
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemSkull, 4, ItemSkull.EnumSkullType.GOLD.getMeta()),
                        "hg ",
                        "sds",
                        "sos",
                        's', new ItemStack(ModItems.itemSkull, 1, 0),
                        'g', "ingotGold",
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SKULL.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Diamond Plated Skull
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemSkull, 5, ItemSkull.EnumSkullType.DIAMOND.getMeta()),
                        "hgs",
                        "sds",
                        "sos",
                        's', new ItemStack(ModItems.itemSkull, 1, 1),
                        'g', "gemDiamond",
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SKULL.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Emerald Plated Skull
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemSkull, 5, ItemSkull.EnumSkullType.EMERALD.getMeta()),
                        "hgs",
                        "sds",
                        "sos",
                        's', new ItemStack(ModItems.itemSkull, 1, 2),
                        'g', "gemEmerald",
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SKULL.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Factory Connector
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemFactoryConnector, 9),
                        "hr ",
                        "ldq",
                        " o ",
                        'r', Blocks.REDSTONE_BLOCK,
                        'q', Items.QUARTZ,
                        'l', Blocks.LAPIS_BLOCK ,
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.CONNECTOR.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Diamond Shard
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemShard, 9, 0),
                        "hd ",
                        " g ",
                        " o ",
                        'g', "gemDiamond",
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Emerald Shard
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemShard, 9, 1),
                        "hd ",
                        " g ",
                        " o ",
                        'g', "gemEmerald",
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Quartz Shard
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemShard, 9, 2),
                        "hd ",
                        " g ",
                        " o ",
                        'g', "gemQuartz",
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Netherstar Shard
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemShard, 9, 3),
                        "hd ",
                        " g ",
                        " o ",
                        'g', Items.NETHER_STAR,
                        'h', ModItems.itemYahHammer,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()),
                        'o', Blocks.OBSIDIAN));

        // Pulverizedd UnderIron
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemPulverisedFerrocrete, 2),
                        "hi ",
                        "nd ",
                        " o ",
                        'h', ModItems.itemYahHammer,
                        'i', "ingotIron",
                        'n', Blocks.NETHER_BRICK,
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.MESH.getMeta()),
                        'o', Blocks.OBSIDIAN));
    }

    private static void initItemRecipes() {

        // UnderIron
        GameRegistry.addSmelting(ModItems.itemPulverisedFerrocrete, new ItemStack(ModItems.itemFerrocrete), 0.5F);

        // Prism
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemPrism),
                        "roy", "wfw", "gbv",
                        'r', "dyeRed",
                        'o', "dyeOrange",
                        'y', "dyeYellow",
                        'g', "dyeGreen",
                        'b', "dyeBlue",
                        'v', "dyePurple",
                        'w', ModItems.itemFerrocrete,
                        'f', ModItems.itemPrismFrame));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemPrism),
                        "roy", "wfw", "gbv",
                        'r', "dyeRed",
                        'o', "dyeOrange",
                        'y', "dyeYellow",
                        'g', "dyeGreen",
                        'b', "dyeLightBlue",
                        'v', "dyePurple",
                        'w', ModItems.itemFerrocrete,
                        'f', ModItems.itemPrismFrame));

         GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemPrism), new ItemStack(ModItems.itemPrism));

        /**
         * Reform from shards
         */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(Items.QUARTZ),
                        "sss", "sss", "sss",
                        's', new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.QUARTZ.getMeta())));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(Items.EMERALD),
                        "sss", "sss", "sss",
                        's', new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.EMERALD.getMeta())));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(Items.DIAMOND),
                        "sss", "sss", "sss",
                        's', new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.DIAMOND.getMeta())));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(Items.NETHER_STAR),
                        "sss", "sss", "sss",
                        's', new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.NETHERSTAR.getMeta())));
    }

    private static void initUpgradeRecipes() {

        initUpgradeRate();
        initUpgradeLooting();
        initUpgradeXp();
        initUpgradeMass();
        initUpgradeDecapitate();
        initUpgradeEfficiency();
    }

    private static void initUpgradeRecipe(ItemStack out1, ItemStack out2, ItemStack out3, ItemStack book1, ItemStack book2, ItemStack book3, Object i1, Object i2) {

        Block t1 = Blocks.QUARTZ_BLOCK;
        Item t2 = Items.PRISMARINE_SHARD;
        Block t3 = Blocks.END_STONE;

        GameRegistry.addRecipe(
                new ShapedOreEnchBookRecipe(
                        out1,
                        "tet", "iui", "tit",
                        't', t1,
                        'u', new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_I.getMeta()),
                        'e', book1,
                        'i', i1
                )
        );

        GameRegistry.addRecipe(
                new ShapedOreEnchBookRecipe(
                        out2,
                        "tet", "iui", "tft",
                        't', t2,
                        'u', new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_II.getMeta()),
                        'e', book1,
                        'f', book2,
                        'i', i2
                )
        );

        GameRegistry.addRecipe(
                new ShapedOreEnchBookRecipe(
                        out3,
                        "tet", "gug", "tft",
                        't', t3,
                        'u', new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_III.getMeta()),
                        'e', book1,
                        'f', book2,
                        'g', book3
                )
        );
    }

    private static void initUpgradeRate() {

        ItemStack book1 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book3 = new ItemStack(Items.ENCHANTED_BOOK);
        Items.ENCHANTED_BOOK.addEnchantment(book1, new EnchantmentData(Enchantments.POWER, 1));
        Items.ENCHANTED_BOOK.addEnchantment(book2, new EnchantmentData(Enchantments.POWER, 2));
        Items.ENCHANTED_BOOK.addEnchantment(book3, new EnchantmentData(Enchantments.POWER, 3));

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.RATE_I));
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.RATE_II));
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.RATE_III));

        String i1 = "dustRedstone";
        String i2 = "blockRedstone";

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i2);
    }

    private static void initUpgradeLooting() {

        ItemStack book1 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book3 = new ItemStack(Items.ENCHANTED_BOOK);
        Items.ENCHANTED_BOOK.addEnchantment(book1, new EnchantmentData(Enchantments.LOOTING, 1));
        Items.ENCHANTED_BOOK.addEnchantment(book2, new EnchantmentData(Enchantments.LOOTING, 2));
        Items.ENCHANTED_BOOK.addEnchantment(book3, new EnchantmentData(Enchantments.LOOTING, 3));

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.LOOTING_I));
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.LOOTING_II));
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.LOOTING_III));

        String i1 = "gemLapis";
        String i2 = "blockLapis";

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i2);
    }

    private static void initUpgradeXp() {

        ItemStack book1 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book3 = new ItemStack(Items.ENCHANTED_BOOK);
        Items.ENCHANTED_BOOK.addEnchantment(book1, new EnchantmentData(Enchantments.LUCK_OF_THE_SEA, 1));
        Items.ENCHANTED_BOOK.addEnchantment(book2, new EnchantmentData(Enchantments.LUCK_OF_THE_SEA, 2));
        Items.ENCHANTED_BOOK.addEnchantment(book3, new EnchantmentData(Enchantments.LUCK_OF_THE_SEA, 3));

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.XP_I));
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.XP_II));
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.XP_III));

        Item i1 = Items.FISH;

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i1);
    }

    private static void initUpgradeMass() {

        ItemStack book1 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book3 = new ItemStack(Items.ENCHANTED_BOOK);
        Items.ENCHANTED_BOOK.addEnchantment(book1, new EnchantmentData(Enchantments.FORTUNE, 1));
        Items.ENCHANTED_BOOK.addEnchantment(book2, new EnchantmentData(Enchantments.FORTUNE, 2));
        Items.ENCHANTED_BOOK.addEnchantment(book3, new EnchantmentData(Enchantments.FORTUNE, 3));

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.MASS_I));
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.MASS_II));
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.MASS_III));

        Item i1 = Items.ENDER_PEARL;
        Item i2 = Items.END_CRYSTAL;

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i2);
    }

    private static void initUpgradeDecapitate() {

        ItemStack book1 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book3 = new ItemStack(Items.ENCHANTED_BOOK);
        Items.ENCHANTED_BOOK.addEnchantment(book1, new EnchantmentData(Enchantments.SHARPNESS, 1));
        Items.ENCHANTED_BOOK.addEnchantment(book2, new EnchantmentData(Enchantments.SHARPNESS, 2));
        Items.ENCHANTED_BOOK.addEnchantment(book3, new EnchantmentData(Enchantments.SHARPNESS, 3));

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.DECAPITATE_I));
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.DECAPITATE_II));
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, BlockMobFactoryUpgrade.getBlockSplitMeta(EnumSpawnerUpgrade.DECAPITATE_III));

        Item i1 = Items.ENDER_PEARL;

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i1);
    }

    private static void initUpgradeEfficiency() {

        ItemStack book1 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book3 = new ItemStack(Items.ENCHANTED_BOOK);
        Items.ENCHANTED_BOOK.addEnchantment(book1, new EnchantmentData(Enchantments.EFFICIENCY, 1));
        Items.ENCHANTED_BOOK.addEnchantment(book2, new EnchantmentData(Enchantments.EFFICIENCY, 2));
        Items.ENCHANTED_BOOK.addEnchantment(book3, new EnchantmentData(Enchantments.EFFICIENCY, 3));

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgradeB, 1, BlockMobFactoryUpgradeB.getBlockSplitMeta(EnumSpawnerUpgrade.EFFICIENCY_I));
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgradeB, 1, BlockMobFactoryUpgradeB.getBlockSplitMeta(EnumSpawnerUpgrade.EFFICIENCY_II));
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgradeB, 1, BlockMobFactoryUpgradeB.getBlockSplitMeta(EnumSpawnerUpgrade.EFFICIENCY_III));

        Item i1 = Items.COMPARATOR;

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i1);
    }

   private  static void initBlockRecipes() {

        // Layout
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        ModBlocks.blockLayout,
                        ModItems.itemFactoryCasing, "stone"));

        // Factory Cap
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    ModItems.itemFactoryCap,
                        "ogo", " c ", "oro",
                        'o', Blocks.OBSIDIAN,
                        'g', "dustGlowstone",
                        'c', ModItems.itemFactoryCasing,
                        'r', ModItems.itemFactoryConnector));

        // Controller
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockController),
                        "sgs", "gfg", "scs",
                        's', ModOreDictionary.ORE_DICT_SKULL,
                        'g', "blockGlass",
                        'c', ModItems.itemFactoryConnector,
                        'f', ModItems.itemFactoryCasing
                )
        );

        // Factory
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockFactory),
                        "cxc", "yfz", "c c",
                        'c', ModItems.itemFactoryConnector,
                        'x', new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_I.getMeta()),
                        'y', new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_II.getMeta()),
                        'z', new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_III.getMeta()),
                        'f', ModItems.itemFactoryCasing));

       // Extender
       GameRegistry.addRecipe(
               new ShapedOreRecipe(
                       new ItemStack(ModBlocks.blockExtender),
                       " c ", " f ", " c ",
                       'c', Items.REPEATER,
                       'f', ModItems.itemFactoryCasing));

       // Proxy
       GameRegistry.addRecipe(
               new ShapedOreRecipe(
                       new ItemStack(ModBlocks.blockProxy),
                       " r ", " f ", " h ",
                       'r', Items.REPEATER,
                       'h', Blocks.HOPPER,
                       'f', ModItems.itemFactoryCasing));

        /**
         * Factory Blocks
         */
       ItemStack casingStack = new ItemStack(ModItems.itemFactoryCasing);

        // Gray
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_1.ordinal()),
                        "bone", ModItems.itemFactoryCasing));

        // Red
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_2.ordinal()),
                        Items.ROTTEN_FLESH, ModItems.itemFactoryCasing));

        // Orange
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 4, EnumMobFactoryModule.BLOCK_3.ordinal()),
                        Items.BLAZE_POWDER,
                        casingStack, casingStack, casingStack, casingStack));

        // Green
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 4, EnumMobFactoryModule.BLOCK_4.ordinal()),
                        "enderpearl",
                        casingStack, casingStack, casingStack, casingStack));

        // White
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 8, EnumMobFactoryModule.BLOCK_5.ordinal()),
                        new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.NETHERSTAR.getMeta()),
                        casingStack, casingStack, casingStack, casingStack,
                        casingStack, casingStack, casingStack, casingStack));

       GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemFactoryCasing), new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_1.ordinal()));
       GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemFactoryCasing), new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_2.ordinal()));
       GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemFactoryCasing), new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_3.ordinal()));
       GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemFactoryCasing), new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_4.ordinal()));
       GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemFactoryCasing), new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_5.ordinal()));

        /**
         * Cap stones
         */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.CAP_I.ordinal()),
                        "scs", "gig", "sgs",
                        'c', ModItems.itemFactoryCap,
                        's', "stone", 'g', "dustGlowstone", 'i', new ItemStack(ModItems.itemSkull, 1, 0)));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.CAP_II.ordinal()),
                        "scs", "gig", "sgs",
                        'c', ModItems.itemFactoryCap,
                        's', "stone", 'g', "dustGlowstone", 'i', new ItemStack(ModItems.itemSkull, 1, 1)));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.CAP_III.ordinal()),
                        "scs", "gig", "sgs",
                        'c', ModItems.itemFactoryCap,
                        's', "stone", 'g', "dustGlowstone", 'i', new ItemStack(ModItems.itemSkull, 1, 2)));
       GameRegistry.addRecipe(
               new ShapedOreRecipe(
                       new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.CAP_IV.ordinal()),
                       "scs", "gig", "sgs",
                       'c', ModItems.itemFactoryCap,
                       's', "stone", 'g', "dustGlowstone", 'i', new ItemStack(ModItems.itemSkull, 1, 3)));

        /**
         * Factory Upgrades
         */

        // Tier I upgrade
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_I.getMeta()),
                        "iii", "ici", "ooo",
                        'i', new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.QUARTZ.getMeta()),
                        'c', ModItems.itemFactoryCasing,
                        'o', ModItems.itemFerrocrete));

        // Tier II upgrade
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_II.getMeta()),
                        "iii", "ici", "ooo",
                        'i', new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.EMERALD.getMeta()),
                        'c', ModItems.itemFactoryCasing,
                        'o', ModItems.itemFerrocrete));

        // Tier III upgrade
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_III.getMeta()),
                        "iii", "ici", "ooo",
                        'i', new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.DIAMOND.getMeta()),
                        'c', ModItems.itemFactoryCasing,
                        'o', ModItems.itemFerrocrete));

    }

    private static void initRecipesCompat() {

        // Factory Frame -> Factory Casing
        GameRegistry.addShapelessRecipe(
                        new ItemStack(ModItems.itemFactoryCasing),
                        ModItems.itemFactoryFrame);

        initBlockRecipesCompat();
    }

    private static void initBlockRecipesCompat() {
    }
}