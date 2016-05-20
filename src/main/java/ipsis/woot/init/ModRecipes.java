package ipsis.woot.init;

import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryModule;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

    public static void init() {

        initItemRecipes();
        initBlockRecipes();
    }

    static void initItemRecipes() {

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemPrism),
                        "ggg", "geg", "ggg",
                        'g', "paneGlass", 'e', Items.ENDER_EYE));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemFactoryFrame, 9),
                        "sis", "ici", "sis",
                        's', "slabWood",
                        'i', new ItemStack(Blocks.IRON_BARS),
                        'c', new ItemStack(Blocks.CHEST)));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemFactoryUpgrade),
                        "s s", "gfg", "srs",
                        's', "stone", 'g', "nuggetGold", 'f', ModItems.itemFactoryFrame,
                        'r', Items.REDSTONE));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemSkull, 2, 0),
                        "   ", "sis", "   ",
                        's', new ItemStack(Items.SKULL, 1, OreDictionary.WILDCARD_VALUE), 'i', "ingotIron"));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemSkull, 4, 1),
                        " i ", "igi", " i ",
                        'i', new ItemStack(ModItems.itemSkull, 1, 0), 'g', "ingotGold") );
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModItems.itemSkull, 5, 2),
                        "ggg", " d ", "g g",
                        'g', new ItemStack(ModItems.itemSkull, 1, 1), 'd', "gemDiamond") );
    }

    static void initBlockRecipes() {

        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        ModBlocks.blockLayout,
                        new ItemStack(ModItems.itemFactoryFrame), "stone"));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockController),
                        "fsf", "beb", "bfb",
                        'f', ModItems.itemFactoryFrame,
                        's', new ItemStack(Items.SKULL, 1, OreDictionary.WILDCARD_VALUE),
                        'e', "gemEmerald",
                        'b', new ItemStack(ModBlocks.blockStructure, 1, OreDictionary.WILDCARD_VALUE)));

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockFactory),
                        "fuf", "bdb", "hfr",
                        'f', ModItems.itemFactoryFrame,
                        'u', ModItems.itemFactoryUpgrade,
                        's', new ItemStack(Items.SKULL, 1, OreDictionary.WILDCARD_VALUE),
                        'e', "gemEmerald",
                        'b', new ItemStack(ModBlocks.blockStructure, 1, OreDictionary.WILDCARD_VALUE),
                        'h', Blocks.HOPPER,
                        'r', Items.REDSTONE));

        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_1.ordinal()),
                        "dyeGray", new ItemStack(ModItems.itemFactoryFrame)));
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_2.ordinal()),
                        "dyeRed", new ItemStack(ModItems.itemFactoryFrame)));
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_3.ordinal()),
                        "dyeGreen", new ItemStack(ModItems.itemFactoryFrame)));
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_4.ordinal()),
                        "dyeBlue", new ItemStack(ModItems.itemFactoryFrame)));

        /*
        GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.BLOCK_5.ordinal()),
                        "dyeOrange", new ItemStack(ModBlocks.blockFactoryFrame)));
                        */

        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.CAP_I.ordinal()),
                        "sgs", "gig", "sgs",
                        's', "stone", 'g', "dustGlowstone", 'i', new ItemStack(ModItems.itemSkull, 1, 0)));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.CAP_II.ordinal()),
                        "sgs", "gig", "sgs",
                        's', "stone", 'g', "dustGlowstone", 'i', new ItemStack(ModItems.itemSkull, 1, 1)));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockStructure, 1, EnumMobFactoryModule.CAP_III.ordinal()),
                        "sgs", "gig", "sgs",
                        's', "stone", 'g', "dustGlowstone", 'i', new ItemStack(ModItems.itemSkull, 1, 2)));

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
                        'h', new ItemStack(Items.SKULL, 1, OreDictionary.WILDCARD_VALUE), 'u', ModItems.itemFactoryUpgrade));

        /* Decapitate upgrades */
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(ModBlocks.blockUpgrade, 1, EnumSpawnerUpgrade.DECAPITATE_I.ordinal()),
                        "e e", " u ", "e e",
                        'e', Items.ENDER_PEARL, 'u', ModItems.itemFactoryUpgrade));

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
                { EnumSpawnerUpgrade.DECAPITATE_III, EnumSpawnerUpgrade.DECAPITATE_II }
        };

        for (int x = 0; x < maps.length; x++) {
            GameRegistry.addShapedRecipe(
                    new ItemStack(ModBlocks.blockUpgrade, 1, maps[x][0].ordinal()),
                    "r r", " u ", "r r",
                    'r', new ItemStack(ModBlocks.blockUpgrade, 1, maps[x][1].ordinal()), 'u', ModItems.itemFactoryUpgrade);
        }
    }
}