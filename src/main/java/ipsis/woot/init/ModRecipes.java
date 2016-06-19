package ipsis.woot.init;

import ipsis.woot.block.BlockMobFactoryUpgradeBase;
import ipsis.woot.init.recipes.ShapedOreHammerRecipe;
import ipsis.woot.init.recipes.ShapelessOreFileRecipe;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
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

    static void initToolRecipes() {

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemYahHammer),
                        "fff", " w ", "sws",
                        'f', ModItems.itemFerrocrete,
                        'w', "stickWood",
                        's', Items.STRING));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemIronFile),
                        "iii", "fff", "   ",
                        'i', "ingotIron", 'f', Items.FLINT));
    }

    static void initDyeRecipes() {

        /**
         * Dyes
         */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDyePlate),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', "slabWood"));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDyeCasing),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', "stone"));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemDyeCasing),
                        "cgc", " d ", "c c",
                        'c', Items.CLAY_BALL,
                        'g', "ingotGold",
                        'd', ModOreDictionary.ORE_DICT_SKULL));

        /**
         * Hammered Outputs
         */
        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemFerrocretePlate, 4),
                        "hf ", " d ", " o ",
                        'h', ModItems.itemYahHammer,
                        'f', ModItems.itemFerrocrete,
                        'd', ModItems.itemDyePlate,
                        'o', Blocks.OBSIDIAN));

        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemFactoryCasing, 4),
                        "php",
                        " d ",
                        "pop",
                        'h', ModItems.itemYahHammer,
                        'p', ModItems.itemFerrocretePlate,
                        'd', ModItems.itemDyeCasing,
                        'o', Blocks.OBSIDIAN));

        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemSkull, 2, 0),
                        "hi ",
                        "sds",
                        " o ",
                        's', ModOreDictionary.ORE_DICT_SKULL,
                        'i', "ingotIron",
                        'h', ModItems.itemYahHammer,
                        'd', ModItems.itemDyeSkull,
                        'o', Blocks.OBSIDIAN));

        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemSkull, 4, 1),
                        "hg ",
                        "sds",
                        "sos",
                        's', new ItemStack(ModItems.itemSkull, 1, 0),
                        'g', "ingotGold",
                        'h', ModItems.itemYahHammer,
                        'd', ModItems.itemDyeSkull,
                        'o', Blocks.OBSIDIAN));

        GameRegistry.addRecipe(
                new ShapedOreHammerRecipe(
                        new ItemStack(ModItems.itemSkull, 5, 2),
                        "hgs",
                        "sds",
                        "sos",
                        's', new ItemStack(ModItems.itemSkull, 1, 1),
                        'g', "gemDiamond",
                        'h', ModItems.itemYahHammer,
                        'd', ModItems.itemDyeSkull,
                        'o', Blocks.OBSIDIAN));
    }

    static void initItemRecipes() {

        GameRegistry.addRecipe(
                new ShapelessOreFileRecipe(
                        new ItemStack(ModItems.itemPulverisedFerrocrete, 2),
                        "ingotIron",
                        Blocks.NETHER_BRICK,
                        Items.CLAY_BALL,
                        ModItems.itemIronFile));

        GameRegistry.addSmelting(ModItems.itemPulverisedFerrocrete, new ItemStack(ModItems.itemFerrocrete), 0.01F);

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemPrism),
                        "fgf", "geg", "fgf",
                        'f', ModItems.itemFerrocrete,
                        'g', "paneGlass", 'e', Items.ENDER_EYE));
    }

    static void initUpgradeRecipes() {

    }

    static void initBlockRecipes() {

        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        ModBlocks.blockLayout,
                        ModItems.itemFactoryCasing, "stone"));

        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        ModItems.itemFactoryCap,
                        ModItems.itemFactoryCasing, "ingotGold"));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        ModItems.itemFactoryUpgrade,
                        "s s", "gfg", "srs",
                        's', "stone", 'g', "nuggetGold", 'f', ModItems.itemFactoryCasing,
                        'r', Items.REDSTONE));

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
    }

    static void initRecipesCompat() {

        /* Factory Frame -> Factory Casing */
        GameRegistry.addShapelessRecipe(
                        new ItemStack(ModItems.itemFactoryCasing),
                        ModItems.itemFactoryFrame);

        initBlockRecipesCompat();
    }

    static void initBlockRecipesCompat() {


        /* Rate upgrades */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.RATE_I.ordinal()),
                        "rbr", "rur", "rbr",
                        'r', "dustRedstone", 'u', ModItems.itemFactoryUpgrade, 'b', Blocks.REDSTONE_BLOCK));

        /* Looting upgrades */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.LOOTING_I.ordinal()),
                        "e e", " u ", "e e",
                        'e', Items.GLOWSTONE_DUST, 'u', ModItems.itemFactoryUpgrade));

        /* XP upgrades */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.XP_I.ordinal()),
                        "e e", " u ", "e e",
                        'e', Items.BLAZE_POWDER, 'u', ModItems.itemFactoryUpgrade));

        /* Mass upgrades */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.MASS_I.ordinal()),
                        "h h", " u ", "h h",
                        'h', ModOreDictionary.ORE_DICT_SKULL, 'u', ModItems.itemFactoryUpgrade));

        /* Decapitate upgrades */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.DECAPITATE_I.ordinal()),
                        "e e", " u ", "e e",
                        'e', Items.ENDER_PEARL, 'u', ModItems.itemFactoryUpgrade));

        /* Efficiency upgrades */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockUpgradeB, 1, BlockMobFactoryUpgradeBase.getBlockSplitMeta(EnumSpawnerUpgrade.EFFICIENCY_I)),
                        "c c", " u ", "t t",
                        'c', Blocks.LAPIS_BLOCK, 'u', ModItems.itemFactoryUpgrade, 't', Blocks.REDSTONE_TORCH));

        /**
         * Upgrades
         */
        EnumSpawnerUpgrade[][] maps = new EnumSpawnerUpgrade[][] {
                { EnumSpawnerUpgrade.RATE_II, EnumSpawnerUpgrade.RATE_I },
                { EnumSpawnerUpgrade.RATE_III, EnumSpawnerUpgrade.RATE_II },
                { EnumSpawnerUpgrade.LOOTING_II, EnumSpawnerUpgrade.LOOTING_I },
                { EnumSpawnerUpgrade.LOOTING_III, EnumSpawnerUpgrade.LOOTING_II },
                { EnumSpawnerUpgrade.XP_II, EnumSpawnerUpgrade.XP_I },
                { EnumSpawnerUpgrade.XP_III, EnumSpawnerUpgrade.XP_II },
                { EnumSpawnerUpgrade.MASS_II, EnumSpawnerUpgrade.MASS_I },
                { EnumSpawnerUpgrade.MASS_III, EnumSpawnerUpgrade.MASS_II },
                { EnumSpawnerUpgrade.DECAPITATE_II, EnumSpawnerUpgrade.DECAPITATE_I },
                { EnumSpawnerUpgrade.DECAPITATE_III, EnumSpawnerUpgrade.DECAPITATE_II },
        };

        for (int x = 0; x < maps.length; x++) {
            GameRegistry.addShapedRecipe(
                    new ItemStack(ModBlocks.blockUpgrade, 1, maps[x][0].ordinal()),
                    "r r", " u ", "r r",
                    'r', new ItemStack(ModBlocks.blockUpgrade, 1, maps[x][1].ordinal()), 'u', ModItems.itemFactoryUpgrade);
        }

        maps = new EnumSpawnerUpgrade[][]{
                {EnumSpawnerUpgrade.EFFICIENCY_II, EnumSpawnerUpgrade.EFFICIENCY_I},
                {EnumSpawnerUpgrade.EFFICIENCY_III, EnumSpawnerUpgrade.EFFICIENCY_II}
        };

        for (int x = 0; x < maps.length; x++) {
            GameRegistry.addShapedRecipe(
                    new ItemStack(ModBlocks.blockUpgradeB, 1, BlockMobFactoryUpgradeBase.getBlockSplitMeta(maps[x][0])),
                    "r r", " u ", "r r",
                    'r', new ItemStack(ModBlocks.blockUpgradeB, 1, BlockMobFactoryUpgradeBase.getBlockSplitMeta(maps[x][1])),
                    'u', ModItems.itemFactoryUpgrade);
        }
    }
}