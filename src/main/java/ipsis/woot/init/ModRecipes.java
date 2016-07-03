package ipsis.woot.init;

import ipsis.woot.init.recipes.ShapedOreHammerRecipe;
import ipsis.woot.item.ItemDye;
import ipsis.woot.item.ItemFactoryUpgrade;
import ipsis.woot.item.ItemShard;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

    public static void init() {

        initToolRecipes();
        initDyeRecipes();
        initItemRecipes();
        initBlockRecipes();
        initUpgradeRecipes();

        initRecipesCompat();
    }

    private static void initToolRecipes() {

        // Yah Hammer
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemYahHammer),
                        "fff", " w ", "sws",
                        'f', ModItems.itemFerrocrete,
                        'w', "stickWood",
                        's', Items.STRING));
    }

    private static void initDyeRecipes() {

        /**
         * Dyes
         */

        // Plate Dye
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.PLATE.getMeta()),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', "slabWood"));

        // Casing Dye
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.CASING.getMeta()),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', "stone"));

        // Skull Dye
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SKULL.getMeta()),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', ModOreDictionary.ORE_DICT_SKULL));

        // Connector Dye
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.CONNECTOR.getMeta()),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', "dustRedstone"));

        // Shard Dye
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.SHARD.getMeta()),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', Items.FLINT));

        // Mesh Dye
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.MESH.getMeta()),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', Blocks.IRON_BARS));

        // TODO Prism Dye

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
                        new ItemStack(ModItems.itemSkull, 2, 0),
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
                        new ItemStack(ModItems.itemSkull, 4, 1),
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
                        new ItemStack(ModItems.itemSkull, 5, 2),
                        "hgs",
                        "sds",
                        "sos",
                        's', new ItemStack(ModItems.itemSkull, 1, 1),
                        'g', "gemDiamond",
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

        // Pulverizedd UnderIron
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemPulverisedFerrocrete, 2),
                        "hi ",
                        " d ",
                        " o ",
                        'h', ModItems.itemYahHammer,
                        'i', "ingotIron",
                        'd', new ItemStack(ModItems.itemDye, 1, ItemDye.EnumDyeType.MESH.getMeta()),
                        'o', Blocks.OBSIDIAN));
    }

    private static void initItemRecipes() {

        // UnderIron
        GameRegistry.addSmelting(ModItems.itemPulverisedFerrocrete, new ItemStack(ModItems.itemFerrocrete), 0.01F);

        // Prism
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemPrism),
                        "fgf", "geg", "fgf",
                        'f', ModItems.itemFerrocrete,
                        'g', "paneGlass", 'e', Items.ENDER_EYE));
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
                new ShapedOreRecipe(
                        out1,
                        "tet", "iui", "tit",
                        't', t1,
                        'u', new ItemStack(ModItems.itemFactoryUpgrade, 1, ItemFactoryUpgrade.EnumUpgradeTier.TIER_I.getMeta()),
                        'e', book1,
                        'i', i1
                )
        );

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
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
                new ShapedOreRecipe(
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

        ItemStack book1 = new ItemStack(Items.BOOK);
        ItemStack book2 = new ItemStack(Items.BOOK);
        ItemStack book3 = new ItemStack(Items.BOOK);
        book1.addEnchantment(Enchantments.POWER, 1);
        book2.addEnchantment(Enchantments.POWER, 2);
        book3.addEnchantment(Enchantments.POWER, 3);

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.RATE_I.ordinal());
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.RATE_II.ordinal());
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.RATE_III.ordinal());

        String i1 = "dustRedstone";
        String i2 = "blockRedstone";

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i2);
    }

    private static void initUpgradeLooting() {

        ItemStack book1 = new ItemStack(Items.BOOK);
        ItemStack book2 = new ItemStack(Items.BOOK);
        ItemStack book3 = new ItemStack(Items.BOOK);
        book1.addEnchantment(Enchantments.LOOTING, 1);
        book2.addEnchantment(Enchantments.LOOTING, 2);
        book3.addEnchantment(Enchantments.LOOTING, 3);

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.LOOTING_I.ordinal());
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.LOOTING_II.ordinal());
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.LOOTING_III.ordinal());

        String i1 = "gemLapis";
        String i2 = "blockLapis";

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i2);
    }

    private static void initUpgradeXp() {

        ItemStack book1 = new ItemStack(Items.BOOK);
        ItemStack book2 = new ItemStack(Items.BOOK);
        ItemStack book3 = new ItemStack(Items.BOOK);
        book1.addEnchantment(Enchantments.LUCK_OF_THE_SEA, 1);
        book2.addEnchantment(Enchantments.LUCK_OF_THE_SEA, 2);
        book3.addEnchantment(Enchantments.LUCK_OF_THE_SEA, 3);

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.XP_I.ordinal());
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.XP_II.ordinal());
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.XP_III.ordinal());

        Item i1 = Items.FISH;

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i1);
    }

    private static void initUpgradeMass() {

        ItemStack book1 = new ItemStack(Items.BOOK);
        ItemStack book2 = new ItemStack(Items.BOOK);
        ItemStack book3 = new ItemStack(Items.BOOK);
        book1.addEnchantment(Enchantments.FORTUNE, 1);
        book2.addEnchantment(Enchantments.FORTUNE, 2);
        book3.addEnchantment(Enchantments.FORTUNE, 3);

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.MASS_I.ordinal());
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.MASS_II.ordinal());
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.MASS_III.ordinal());

        Item i1 = Items.ENDER_PEARL;
        Item i2 = Items.END_CRYSTAL;

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i2);
    }

    private static void initUpgradeDecapitate() {

        ItemStack book1 = new ItemStack(Items.BOOK);
        ItemStack book2 = new ItemStack(Items.BOOK);
        ItemStack book3 = new ItemStack(Items.BOOK);
        book1.addEnchantment(Enchantments.SHARPNESS, 1);
        book2.addEnchantment(Enchantments.SHARPNESS, 2);
        book3.addEnchantment(Enchantments.SHARPNESS, 3);

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.DECAPITATE_I.ordinal());
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.DECAPITATE_II.ordinal());
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.DECAPITATE_III.ordinal());

        Item i1 = Items.ENDER_PEARL;

        initUpgradeRecipe(out1, out2, out3, book1, book2, book3, i1, i1);
    }

    private static void initUpgradeEfficiency() {

        ItemStack book1 = new ItemStack(Items.BOOK);
        ItemStack book2 = new ItemStack(Items.BOOK);
        ItemStack book3 = new ItemStack(Items.BOOK);
        book1.addEnchantment(Enchantments.EFFICIENCY, 1);
        book2.addEnchantment(Enchantments.EFFICIENCY, 2);
        book3.addEnchantment(Enchantments.EFFICIENCY, 3);

        ItemStack out1 = new ItemStack(ModBlocks.blockUpgradeB, 1, EnumSpawnerUpgrade.EFFICIENCY_I.ordinal());
        ItemStack out2 = new ItemStack(ModBlocks.blockUpgradeB, 1, EnumSpawnerUpgrade.EFFICIENCY_II.ordinal());
        ItemStack out3 = new ItemStack(ModBlocks.blockUpgradeB, 1, EnumSpawnerUpgrade.EFFICIENCY_III.ordinal());

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

        /* TODO Controller */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockController),
                        "fsf", "beb", "bfb",
                        'f', ModItems.itemFactoryCasing,
                        's', ModOreDictionary.ORE_DICT_SKULL,
                        'e', "gemEmerald",
                        'b', new ItemStack(ModBlocks.blockStructure, 1, OreDictionary.WILDCARD_VALUE)));

        /* TODO Factory */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockFactory),
                        "fuf", "bdb", "hfr",
                        'f', ModItems.itemFactoryCasing,
                        'u', ModItems.itemFactoryUpgrade,
                        's', ModOreDictionary.ORE_DICT_SKULL,
                        'e', "gemEmerald",
                        'b', new ItemStack(ModBlocks.blockStructure, 1, OreDictionary.WILDCARD_VALUE),
                        'h', Blocks.HOPPER,
                        'r', Items.REDSTONE));

        /**
         * Factory Blocks
         */

        /* Gray */
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_1.ordinal()),
                        "bone", ModItems.itemFactoryCasing));

        /* Red */
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_2.ordinal()),
                        Items.ROTTEN_FLESH, ModItems.itemFactoryCasing));

        /* Green */
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_3.ordinal()),
                        Items.BLAZE_POWDER, ModItems.itemFactoryCasing));

        /* Blue */
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_4.ordinal()),
                        "enderpearl", ModItems.itemFactoryCasing));

        /* Orange */
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_5.ordinal()),
                        Items.GHAST_TEAR, ModItems.itemFactoryCasing));


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

        /* Factory Frame -> Factory Casing */
        GameRegistry.addShapelessRecipe(
                        new ItemStack(ModItems.itemFactoryCasing),
                        ModItems.itemFactoryFrame);

        initBlockRecipesCompat();
    }

    private static void initBlockRecipesCompat() {
    }
}