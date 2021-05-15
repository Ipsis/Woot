package ipsis.woot.datagen;

import ipsis.woot.modules.factory.FactoryConfiguration;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguagesGuide {

    public static void addTranslations(LanguageProvider provider) {

        final String GUIDE_TAG = "guide.woot.";

        provider.add("item.woot.guide.name", "Woot Guide");
        provider.add("item.woot.guide.landing",
            "A guide to the big, bad, magic multiblock Mob Factory. " +
            "Like Marmite, you will either love it or hate it - you choose.");

        /**********************************************************************
         * Categories
         */
        {
            final String CATEGORY_TAG = GUIDE_TAG + "category.";
            String[][] text = {
                { CATEGORY_TAG + "basics", "Basics"},
                { CATEGORY_TAG + "basics.desc", "How to build and run your mob factory."},
                { CATEGORY_TAG + "exotics", "Factory Exotics"},
                { CATEGORY_TAG + "exotics.desc", "Exotic extensions to the factory."},
                { CATEGORY_TAG + "factoryBlocks", "Factory Components"},
                { CATEGORY_TAG + "factoryBlocks.desc", "The blocks that make up the factor."},
                { CATEGORY_TAG + "machines", "Machines"},
                { CATEGORY_TAG + "machines.desc", "Important machines for creating and running your Woot factory."},
                { CATEGORY_TAG + "perks", "Perks"},
                { CATEGORY_TAG + "perks.desc", "How to make your factory better."},
                { CATEGORY_TAG + "tools", "Tools"},
                { CATEGORY_TAG + "tools.desc", "The tools of Woot. (Yes, another mod with a hammer.)"}
            };

            for (String[] strings : text) provider.add(strings[0], strings[1]);
        }

        /**********************************************************************
         * Entries - Basics
         */
        {
            final String BASICS_TAG = GUIDE_TAG + "basics.";
            String[][] text = {
                // Credits
                {BASICS_TAG + "credits.name", "Credits"},
                { BASICS_TAG + "credits.0",
                    "Some appreciation for others in the Minecraft modding community. " +
                    "I don't interact with other modders, however their mod sources and tools have been a great help while developing Woot - they make not all like this mod, but I appreciate their contributions to the Minecraft modding scene."
                },
                { BASICS_TAG + "credits.1",
                    "$(li)The Forge developers" +
                    "$(li)Vazkii - for Patchouli" +
                    "$(li)McJty - for his modding tutorials" +
                    "$(li)The Forge Forum members that help with my questions."
                },
                // Intro
                { BASICS_TAG + "intro.name", "What is this mod for?"},
                { BASICS_TAG + "intro.0",
                    "Woot provides a solution to the ago old Minecraft problem of - $(#f00)I WANT ALL THE MOB DROPS!.$() " +
                    "The traditional solution is to spawn mobs as quickly as possible, kill them automatically and then pick up all the drops that they leave behind."
                },
                { BASICS_TAG + "intro.1",
                    "There are many interesting ways to perform this operation. " +
                    "The main drawback is that there are lots of entities generated and when scaled up on a server can impact performance. " +
                    "Woot gives you a mob farm/factory without generating all those messy entities and hopefully takes a bit of load off your server. " +
                    "It is also for lazy people like me that don't like building mob farms."
                },
                // Mobshard
                { BASICS_TAG + "mobshard.name", "Capturing mobs"},
                { BASICS_TAG + "mobshard.0",
                    "A $(item)Mob Shard$(0) is used to capture a mob to use in the factory. " +
                    "First you must create the $(item)Mob Shard$(0) then attack a mob to program that shard. " +
                    "With the shard in your inventory you must kill a certain number of that mob. " +
                    "When the kill count is reached the shard will be programmed and can then be converted into a $(l:mobs/controller)Mob Controller."
                },
                // Stygian Iron
                { BASICS_TAG + "stygianiron.name", "Stygian Iron"},
                { BASICS_TAG + "stygianiron.0",
                    "The main resource in Woot is $(item)Stygian Iron$(0). " +
                    "You will need this to create the machines, tools and factory components"
                },
                // Conatus Fluid
                { BASICS_TAG + "conatus.name", "Conatus Fluid" },
                { BASICS_TAG + "conatus.0",
                    "The factory is fueled by Conatus Fluid which is stored in the $(item)Conatus Cell$(0) at the base of the factory. " +
                    "This can be created in the $(item)Fluid Vat$(0) from a variety of different sources."
                },
                // Conatus Fluid
                { BASICS_TAG + "ingredients.name", "Ingredients" },
                { BASICS_TAG + "ingredients.0",
                     "Some mobs require ingredients to drop their loot and these must be provided in chests/tanks beside the $(item)Ingredient Importer$(0)." +
                     "ALL ingredients for ALL mobs be provided or no loot will be produced."
                },
                // Tiers
                { BASICS_TAG + "tiers.name", "Factory tiers"},
                { BASICS_TAG + "tiers.0",
                    "The factory comes in various tiers, with higher tiers support higher value mobs and higher level perks. " +
                    "Each factory tier builds upon the structure of the previous tier. " +
                    "Lower value mobs can be used in higher tier factories. " +
                    "You use Scale The Summit perk to get higher tier shards."
                },
                { BASICS_TAG + "tiers.1",
                    "Every factory requires the following$(br2)" + "" +
                    "$(li)1xHeart" +
                    "$(li)1xPrimary Base" +
                    "$(li)1xConatus Cell" +
                    "$(li)1xIngredient Importer" +
                    "$(li)1xLoot Exporter"
                },
                { BASICS_TAG + "tiers.2",
                    "$(l)Zelator [I]$()$(br2)" +
                    "Supports the following:$(br)" +
                    "$(li)1xMob Controller" +
                    "$(li)1xPerk I$(br2)"
                },
                { BASICS_TAG + "tiers.3",
                    "Requires the following extra blocks:$(br)" +
                    "$(li)2x$(l:factory_blocks/factory_a)Amaranth Block$()" +
                    "$(li)2x$(l:factory_blocks/factory_connector)Factory Connector$()" +
                    "$(li)1x$(l:factory_blocks/perkslot)Perk Slot$()"
                },
                { BASICS_TAG + "tiers.4",
                    "$(l)Theoricus [II]$()$(br2)" +
                    "Supports the following:$(br)" +
                    "$(li)4xMob Controller" +
                    "$(li)4xPerk I$(br2)"
                },
                { BASICS_TAG + "tiers.5",
                    "Requires the following extra blocks:$(br)" +
                        "$(li)10x$(l:factory_blocks/factory_a)Amaranth Block$()" +
                        "$(li)17x$(l:factory_blocks/factory_b)Amber Block$()" +
                        "$(li)11x$(l:factory_blocks/factory_connector)Factory Connector$()" +
                        "$(li)3x$(l:factory_blocks/secctrl_base)Secondary Base$()" +
                        "$(li)4x$(l:factory_blocks/perkslot)Perk Slot$()" +
                        "$(li)4x$(l:factory_blocks/cap_a)Theoricus Cap$()"
                },
                { BASICS_TAG + "tiers.6",
                    "$(l)Practicus [III]$()$(br2)" +
                        "Supports the following:$(br)" +
                        "$(li)4xMob Controller" +
                        "$(li)4xPerk II$(br2)"
                },
                { BASICS_TAG + "tiers.7",
                    "Requires the following extra blocks:$(br)" +
                        "$(li)10x$(l:factory_blocks/factory_a)Amaranth Block$()" +
                        "$(li)17x$(l:factory_blocks/factory_b)Amber Block$()" +
                        "$(li)24x$(l:factory_blocks/factory_c)Celadon Block$()" +
                        "$(li)11x$(l:factory_blocks/factory_connector)Factory Connector$()" +
                        "$(li)3x$(l:factory_blocks/secctrl_base)Secondary Base$()" +
                        "$(li)4x$(l:factory_blocks/perkslot)Perk Slot$()" +
                        "$(li)4x$(l:factory_blocks/cap_a)Theoricus Cap$()" +
                        "$(li)8x$(l:factory_blocks/cap_b)Practicus Cap$()"
                },
                { BASICS_TAG + "tiers.8",
                    "$(l)Exemptus [IV]$()$(br2)" +
                        "Supports the following:$(br)" +
                        "$(li)4xMob Controller" +
                        "$(li)4xPerk III$(br2)"
                },
                { BASICS_TAG + "tiers.9",
                    "Requires the following extra blocks:$(br)" +
                        "$(li)10x$(l:factory_blocks/factory_a)Amaranth Block$()" +
                        "$(li)17x$(l:factory_blocks/factory_b)Amber Block$()" +
                        "$(li)24x$(l:factory_blocks/factory_c)Celadon Block$()" +
                        "$(li)8x$(l:factory_blocks/factory_d)Cerulean Block$()" +
                        "$(li)11x$(l:factory_blocks/factory_connector)Factory Connector$()" +
                        "$(li)3x$(l:factory_blocks/secctrl_base)Secondary Base$()" +
                        "$(li)4x$(l:factory_blocks/perkslot)Perk Slot$()" +
                        "$(li)4x$(l:factory_blocks/cap_a)Theoricus Cap$()" +
                        "$(li)8x$(l:factory_blocks/cap_b)Practicus Cap$()" +
                        "$(li)4x$(l:factory_blocks/cap_c)Exemptus Cap$()"
                },
                { BASICS_TAG + "tiers.10",
                    "$(l)Magister [V]$()$(br2)" +
                        "This tier is normally used for bosses such as the Ender Dragon. " +
                        "It is the only tier that can use exotics.$(br2)" +
                        "Supports the following:$(br)" +
                        "$(li)4xMob Controller" +
                        "$(li)4xPerk III" +
                        "$(li)1xExotic$(br2)"
                },
                { BASICS_TAG + "tiers.11",
                    "Requires the following extra blocks:$(br)" +
                        "$(li)10x$(l:factory_blocks/factory_a)Amaranth Block$()" +
                        "$(li)17x$(l:factory_blocks/factory_b)Amber Block$()" +
                        "$(li)24x$(l:factory_blocks/factory_c)Celadon Block$()" +
                        "$(li)8x$(l:factory_blocks/factory_d)Cerulean Block$()" +
                        "$(li)8x$(l:factory_blocks/factory_e)Byzantium Block$()" +
                        "$(li)11x$(l:factory_blocks/factory_connector)Factory Connector$()" +
                        "$(li)3x$(l:factory_blocks/secctrl_base)Secondary Base$()" +
                        "$(li)4x$(l:factory_blocks/perkslot)Perk Slot$()" +
                        "$(li)4x$(l:factory_blocks/cap_a)Theoricus Cap$()" +
                        "$(li)8x$(l:factory_blocks/cap_b)Practicus Cap$()" +
                        "$(li)4x$(l:factory_blocks/cap_c)Exemptus Cap$()" +
                        "$(li)8x$(l:factory_blocks/cap_d)Magister Cap$()"
                },
                { BASICS_TAG + "tiers.12",
                        "Each tier has a maximum mob health that can be handled. The following " +
                        "pages show the default maximum health and any exceptions to the rules " +
                        "(cough, cough, Endermen)"
                },
                { BASICS_TAG + "tiers.13",
                        "$(l)Zelator [I]$()$(br2)" +
                        "$(li)Mobs with health <= 20"
                },
                { BASICS_TAG + "tiers.14",
                        "$(l)Theoricus [II]$()$(br2)" +
                        "$(li)Mobs with health <= 40"
                },
                { BASICS_TAG + "tiers.15",
                        "$(l)Practicus [III]$()$(br2)" +
                        "$(li)Mobs with health <= 60" +
                        "$(li)Magma Cube" +
                        "$(li)Blaze" +
                        "$(li)Witch" +
                        "$(li)Ghast" +
                        "$(li)Zombie Pigman"
                },
                { BASICS_TAG + "tiers.16",
                        "$(l)Exemptus [IV]$()$(br2)" +
                        "$(li)Any mob health" +
                        "$(li)Enderman" +
                        "$(li)Wither Skeleton" +
                        "$(li)Villager" +
                        "$(li)Guardian" +
                        "$(li)Villager Golem"
                },
                { BASICS_TAG + "tiers.17",
                        "$(l)Magister [V]$()$(br2)" +
                        "$(li)Any mob health" +
                        "$(li)Wither" +
                        "$(li)Ender Dragon"
                },
                // TLDR
                { BASICS_TAG + "tldr.name", "TLDR"},
                { BASICS_TAG + "tldr.0",
                    "$(li)$(item)Layout Guide$(0) shows you the factory layout and can step through each y-level" +
                    "$(li)$(item)Layout Guide$(0) and $(item)Factory Heart$(0) are directional" +
                    "$(li)Use $(item)The Intern$(0) on the $(item)Factory Heart$(0) to autobuild" +
                    "$(li)Factory needs Conatus Fluid to run, but no power"
                },
                { BASICS_TAG + "tldr.1",
                    "$(li)Some mobs need ingredients to generate loot" +
                    "$(li)$(item)The Intern$(0) can also validate your factory structure" +
                    "$(li)Mobs have a minimum tier of factory to spawn" +
                    "$(li)Must apply all the previous tiers of a perk to use higher tier ones" +
                    "$(li)Place chests beside the $(item)Loot Exporter$(0)" +
                    "$(li)Scale The Summit perk generates shards for higher tier blocks"
                }
            };

            for (String[] strings : text) provider.add(strings[0], strings[1]);
        }

        /**********************************************************************
         * Entries - Exotics
         */
        {
            final String EXOTICS_TAG = GUIDE_TAG + "exotics.";
            String[][] text = {
                // Intro
                { EXOTICS_TAG + "intro.name", "What are exotics for?" },
                { EXOTICS_TAG + "intro.0",
                    "Exotics give an extra boost to the factory by providing something in excess of perk abilities. " +
                    "Only one can be applied to a factory and it must be placed on top of the $(item)Heart$(0)."
                },
                { EXOTICS_TAG + "intro.1",
                     "Exotics can only be applied to Tier V factories. " +
                     "They cannot be crafted and are very rare drops from End City treasure chests."
                },

                // LIL
                { EXOTICS_TAG + "exotic_a.name", "Enochian Key: LIL" },
                { EXOTICS_TAG + "exotic_a.0", "This exotic reduces the amount of fluid recipe ingredients required by " + FactoryConfiguration.EXOTIC_A.get() + "%%." },

                // ARN
                { EXOTICS_TAG + "exotic_b.name", "Enochian Key: ARN" },
                { EXOTICS_TAG + "exotic_b.0", "This exotic reduces the amount of item recipe ingredients required by " + FactoryConfiguration.EXOTIC_B.get() + "%%." },

                // ZOM
                { EXOTICS_TAG + "exotic_c.name", "Enochian Key: ZOM" },
                { EXOTICS_TAG + "exotic_c.0", "This exotic reduces the amount of Conatus fluid required by " + FactoryConfiguration.EXOTIC_C.get() + "%%." },

                // PAZ
                { EXOTICS_TAG + "exotic_d.name", "Enochian Key: PAZ" },
                { EXOTICS_TAG + "exotic_d.0", "This exotic runs the factory at a fixed spawn time of " + FactoryConfiguration.EXOTIC_D.get() + " ticks." },

                // LAT
                { EXOTICS_TAG + "exotic_e.name", "Enochian Key: LAT" },
                { EXOTICS_TAG + "exotic_e.0", "This exotic spawns " + FactoryConfiguration.EXOTIC_E.get() + " mobs for each controller." }
            };

            for (String[] strings : text) provider.add(strings[0], strings[1]);
        }

        /**********************************************************************
         * Entries - Factory Blocks
         */
        {
            final String BLOCKS_TAG = GUIDE_TAG + "factory_blocks.";
            String[][] text = {
                { BLOCKS_TAG + "cap_a.name", "Theoricus Cap"},
                { BLOCKS_TAG + "cap_b.name", "Practicus Cap"},
                { BLOCKS_TAG + "cap_c.name", "Exemptus Cap"},
                { BLOCKS_TAG + "cap_d.name", "Magister Cap"},
                { BLOCKS_TAG + "cells.name", "Conatus Cells"},
                { BLOCKS_TAG + "cells.0",
                    "There are four types of $(item)Conatus Cell$(0). " +
                    "Each is compatible with all factories tiers. " +
                    "The only differences are the amount of fluid they can hold and the transfer rate. " +
                    "Fluids can be inserted or removed from any side."
                },
                { BLOCKS_TAG + "controller.name", "Mob Controller"},
                { BLOCKS_TAG + "controller.0",
                    "The $(item)Mob Controller$(0) tells the factory which mob you would like to spawn. " +
                    "However not all factories are created equal. " +
                    "Each $(item)Mob Controller$(0) requires a minimum tier of factory to use. " +
                    "The tier can be seen by right clicking on a $(item)Mob Controller$(0)"
                },
                { BLOCKS_TAG + "controller.1",
                    "The $(item)Mob Controller$(0) is created on the $(item)Stygian Anvil$(0) by combining a fully programmed $(item)Mob Shard$(0) with a $(item)Prism$(0) and $(item)Stygian Iron Plate$(0)."
                },
                { BLOCKS_TAG + "exporter.name", "Loot Exporter"},
                { BLOCKS_TAG + "exporter.0",
                    "This block is used by the factory for generating the loot drops. " +
                    "A chest or tank must be adjacent to any side of this block. " +
                    "If there is no space in the attached blocks, then the items will not be generated."
                },
                { BLOCKS_TAG + "factory_a.name", "Amaranth Block"},
                { BLOCKS_TAG + "factory_b.name", "Amber Block"},
                { BLOCKS_TAG + "factory_c.name", "Celadon Block"},
                { BLOCKS_TAG + "factory_d.name", "Cerulean Block"},
                { BLOCKS_TAG + "factory_e.name", "Byzantium Block"},
                { BLOCKS_TAG + "factory_connect.name", "Factory Connector"},
                { BLOCKS_TAG + "importer.name", "Ingredient Importer"},
                { BLOCKS_TAG + "importer.0",
                    "This block is used by the factory for importing ingredients that are required to spawn the mob. " +
                    "A chest or tank must be adjacent to any side of this block and contain the specified ingredients to complete the spawn process and generate loot."
                },
                { BLOCKS_TAG + "perkslot.name", "Perk Slot"},
                { BLOCKS_TAG + "perkslot.0",
                    "Perks are applied to the $(item)Perk Slot$(0). " +
                    "A $(item)Perk Slot$(0) can accept a single type of perk. " +
                    "An empty $(item)Perk Slot$(0) can take any level I perk. " +
                    "All previous levels of perk must be applied before the next level is accepted. " +
                    "Different tiers of factory have a maximum level of perk that they will work with."
                },
                { BLOCKS_TAG + "perkslot.1",
                    "Higher tier perks can be applied to lower tier factories, however the factory will ignore any perk benefits over its level capacity."
                },
                { BLOCKS_TAG + "pri_base.name", "Primary Controller Base"},
                { BLOCKS_TAG + "pri_base.0",
                    "A valid $(item)Mob Controller$(0) $(#f00)must$() be placed on this block for the factory to form."
                },
                { BLOCKS_TAG + "sec_base.name", "Secondary Controller Base"},
                { BLOCKS_TAG + "sec_base.0",
                    "A valid $(item)Mob Controller$(0) may be placed on this block."
                }
            };

            for (String[] strings : text) provider.add(strings[0], strings[1]);
        }

        /**********************************************************************
         * Entries - Machines
         */
        {
            final String MACHINES_TAG = GUIDE_TAG + "machines.";
            String[][] text = {

                // Dye Liquifier
                { MACHINES_TAG + "dyesqueezer.name", "Dye Liquifier" },
                { MACHINES_TAG + "dyesqueezer.0",
                    "The $(item)Dye Liquifier$(0) takes dye items, or items that can creates dyes and converts them into red, yellow, blue and white fluids. " +
                    "These are then combined into Pure Dye which can be used to create dye casings or any color in the $(item)Injection Press$(0)."
                },
                { MACHINES_TAG + "dyesqueezer.1",
                    "Items can be inserted from any side. " +
                    "Pure Dye can be extracted from any side. " +
                    "The $(item)Dye Liquifier$(0) will automatically output to an adjacent fluid handler."
                },

                // Enchant Liquifier
                { MACHINES_TAG + "enchsqueezer.name", "Enchant Liquifier" },
                { MACHINES_TAG + "enchsqueezer.0",
                    "The $(item)Enchant Liquifier$(0) takes enchanted items (books, swords etc), extracts the enchantments from them and creates Liquid Enchant fluid. " +
                    "Higher level enchants create more fluid."
                },
                { MACHINES_TAG + "enchsqueezer.1",
                    "Items can be inserted from any side. " +
                    "Liquid Enchant can be extracted from any side. " +
                    " The $(item)Enchant Liquifier$(0) will automatically output to an adjacent fluid handler."
                },
                { MACHINES_TAG + "enchsqueezer.2",
                    "$(li) Enchant I " + SqueezerConfiguration.ENCH_SQUEEZER_LVL_1_ENCHANT_MB.get() + "mb " +
                    "$(li) Enchant II " + SqueezerConfiguration.ENCH_SQUEEZER_LVL_2_ENCHANT_MB.get() + "mb " +
                    "$(li) Enchant III " + SqueezerConfiguration.ENCH_SQUEEZER_LVL_3_ENCHANT_MB.get() + "mb " +
                    "$(li) Enchant IV " + SqueezerConfiguration.ENCH_SQUEEZER_LVL_4_ENCHANT_MB.get() + "mb " +
                    "$(li) Enchant V " + SqueezerConfiguration.ENCH_SQUEEZER_LVL_5_ENCHANT_MB.get() + "mb " +
                    "$(li) Each extra level " + SqueezerConfiguration.ENCH_SQUEEZER_EXTRA_ENCHANT_MB.get() + "mb"
                },

                // Fluid Convertor
                { MACHINES_TAG + "fluidconvertor.name", "Fluid Vat" },
                { MACHINES_TAG + "fluidconvertor.0",
                    "The Woot factory runs on Conatus Fluid, which is primarily generated with the $(item)Fluid Vat$(0). " +
                    "The machine takes a fluid and a catalyst, then converts that into Conatus Fluid. " + "" +
                    "Different fluid/catalyst combinations give differing amounts on Conatus Fluid."
                },
                { MACHINES_TAG + "fluidconvertor.1",
                    "Items can be inserted from any side. " +
                    "Input fluids can be inserted from the left, top or front. " +
                    "Output fluids can be removed from the bottom, back and right sides."
                },

                // Injection Press
                { MACHINES_TAG + "infuser.name", "Injection Press" },
                { MACHINES_TAG + "infuser.0",
                     "$(item)The Injection Press$(0) injects fluids into items. " +
                     "It can be used with Pure Dye to create Dye Plates. " +
                     "It can be used with Liquid Enchant to create factory upgrade plates and enchanted books."
                },
                { MACHINES_TAG + "infuser.1",
                    "Items can be inserted from the top, left and front sides. " +
                    "Output items can be removed from the bottom, right and back sides. " +
                    "Fluids can be inserted via any side."
                }
            };

            for (String[] strings : text) provider.add(strings[0], strings[1]);
        }

        /**********************************************************************
         * Entries - Perks
         */
        {
            final String PERKS_TAG = GUIDE_TAG + "perks.";
            String[][] text = {
                // Applying Perks
                // Crusher
                { PERKS_TAG + "crusher.name", "Pressing Engagement" },
                { PERKS_TAG + "crusher.0",
                        "This perk generates Industrial Foregoing essence in tanks adjacent to the Loot Exporter. " +
                        "Higher levels of this perk will increase the generated amount of fluid."
                },
                { PERKS_TAG + "crusher.1",
                        "$(li)Tier I - " + FactoryConfiguration.CRUSHER_1.get() + "%%" +
                                "$(li)Tier II - " + FactoryConfiguration.CRUSHER_2.get() + "%%" +
                                "$(li)Tier III - " + FactoryConfiguration.CRUSHER_3.get() + "%%"
                },
                // Efficiency
                { PERKS_TAG + "efficiency.name", "Stoke The Boiler" },
                { PERKS_TAG + "efficiency.0",
                    "This perk decreases the amount of Conatus Fluid required by the factory. " +
                    "Higher levels of this perk will decrease the fluid requirement further."
                },
                { PERKS_TAG + "efficiency.1",
                    "$(li)Tier I - " + FactoryConfiguration.EFFICIENCY_1.get() + "%% decrease" +
                    "$(li)Tier II - " + FactoryConfiguration.EFFICIENCY_2.get() + "%% decrease" +
                    "$(li)Tier III - " + FactoryConfiguration.EFFICIENCY_3.get() + "%% decrease"
                },
                // Headless
                { PERKS_TAG + "headless.name", "Head Start" },
                { PERKS_TAG + "headless.0",
                    "The $(item)Head Start$(0) perk drops skulls. " +
                    "Higher levels of this perk give a better chance of the drop."
                },
                { PERKS_TAG + "headless.1",
                    "$(li)Tier I - " + FactoryConfiguration.HEADLESS_1.get() + "%% chance" +
                    "$(li)Tier II - " + FactoryConfiguration.HEADLESS_2.get() + "%% chance" +
                    "$(li)Tier III - " + FactoryConfiguration.HEADLESS_3.get() + "%% chance"
                },
                // Looting
                { PERKS_TAG + "looting.name", "Treasure Island" },
                { PERKS_TAG + "looting.0",
                    "The $(item)Treasure Island$(0) perk sets the looting level that the factory will use. " +
                    "The looting levels vary from Looting I to Looting III."
                },
                // Mass
                { PERKS_TAG + "mass.name", "Rampage" },
                { PERKS_TAG + "mass.0",
                    "The $(item)Rampage$(0) perk increases the number of mobs spawned by the factory. " +
                    "Higher levels of this perk will spawn more mobs. " +
                    "When a factory contains multiple mobs, then the mob with the smallest mass number will be used."
                },
                { PERKS_TAG + "mass.1",
                    "$(li)Tier I - " + FactoryConfiguration.MASS_COUNT_1.get() + " mobs" +
                    "$(li)Tier II - " + FactoryConfiguration.MASS_COUNT_2.get() + " mobs" +
                    "$(li)Tier III - " + FactoryConfiguration.MASS_COUNT_3.get() + " mobs"
                },
                // Rate
                { PERKS_TAG + "rate.name", "Feeding Frenzy" },
                { PERKS_TAG + "rate.0",
                    "The $(item)Feeding Frenzy$(0) perk decreases the time to generate the mobs. " +
                    "Higher levels of this perk will decrease the time further. " +
                    "When a factory contains multiple mobs, then the mob with the smallest rate reduction will be used."
                },
                { PERKS_TAG + "rate.1",
                    "$(li)Tier I - " + FactoryConfiguration.RATE_1.get() + "%% decrease" +
                    "$(li)Tier II - " + FactoryConfiguration.RATE_2.get() + "%% decrease" +
                    "$(li)Tier III - " + FactoryConfiguration.RATE_3.get() + "%% decrease"
                },
                // Slaughter
                { PERKS_TAG + "slaughter.name", "Tumbling Doll Of Flesh" },
                { PERKS_TAG + "slaughter.0",
                        "This perk generates Industrial Foregoing pink slime and liquid meat in tanks adjacent to the Loot Exporter. " +
                        "Higher levels of this perk will increase the generated amount of the fluids."
                },
                { PERKS_TAG + "slaughter.1",
                        "$(li)Tier I - " + FactoryConfiguration.CRUSHER_1.get() + "%%" +
                                "$(li)Tier II - " + FactoryConfiguration.CRUSHER_2.get() + "%%" +
                                "$(li)Tier III - " + FactoryConfiguration.CRUSHER_3.get() + "%%"
                },
                // Flayed
                { PERKS_TAG + "flayed.name", "Flayed" },
                { PERKS_TAG + "flayed.0",
                        "This perk allows mobs being processed by the Woot factory to be used by the Martyr ritual to generate LP. " +
                                "Higher levels of this perk will increase the mob health available to the ritual."
                },
                { PERKS_TAG + "flayed.1",
                        "$(li)Tier I - " + FactoryConfiguration.FLAYED_1.get() + "%%" +
                                "$(li)Tier II - " + FactoryConfiguration.FLAYED_2.get() + "%%" +
                                "$(li)Tier III - " + FactoryConfiguration.FLAYED_3.get() + "%%"
                },
                // Laser
                { PERKS_TAG + "laser.name", "Frickin Laser Breams" },
                { PERKS_TAG + "laser.0",
                        "This perk generates Industrial Foregoing ether gas in tanks adjacent to the Loot Exporter. " +
                                "Higher levels of this perk will increase the generated amount of the gas." +
                                    "As per the original mod, only generates from Withers"
                },
                { PERKS_TAG + "laser.1",
                        "$(li)Tier I - " + FactoryConfiguration.CRUSHER_1.get() + "%%" +
                                "$(li)Tier II - " + FactoryConfiguration.CRUSHER_2.get() + "%%" +
                                "$(li)Tier III - " + FactoryConfiguration.CRUSHER_3.get() + "%%"
                },
                // Tier Shard
                { PERKS_TAG + "tier_shard.name", "Scale The Summit" },
                {PERKS_TAG + "tier_shard.0",
                    "To create higher tier versions of the Woot factory you need to use essence to create the blocks. " +
                    "These essence are generated by the factory when this perk is installed. " +
                    "Each level of this perk defines a number of rolls for the essence generation and a drop chance for each of the essence types. " +
                    "The factory tier determines what the chances are to drop each essence type."
                },
                { PERKS_TAG + "tier_shard.1",
                    "$(li)Tier I - " + FactoryConfiguration.TIER_SHARD_1.get() + " chances to drop essence" +
                    "$(li)Tier II - " + FactoryConfiguration.TIER_SHARD_2.get() + " chances to drop essence" +
                    "$(li)Tier III - " + FactoryConfiguration.TIER_SHARD_3.get() + " chances to drop essence"
                },
                { PERKS_TAG + "tier_shard.2",
                    "A Tier I Factory has a " + FactoryConfiguration.T1_FARM_DROP_CHANCE.get() + "%% chance to drop a shard." +
                    "$(li)Celadon - weight " + FactoryConfiguration.T1_FARM_DROP_SHARD_WEIGHTS.get().get(0) +
                    "$(li)Cerulean - weight " + FactoryConfiguration.T1_FARM_DROP_SHARD_WEIGHTS.get().get(1) +
                    "$(li)Byzantium - weight " + FactoryConfiguration.T1_FARM_DROP_SHARD_WEIGHTS.get().get(2)
                },
                { PERKS_TAG + "tier_shard.3",
                    "A Tier II Factory has a " + FactoryConfiguration.T2_FARM_DROP_CHANCE.get() + "%% chance to drop a shard." +
                    "$(li)Celadon - weight " + FactoryConfiguration.T2_FARM_DROP_SHARD_WEIGHTS.get().get(0) +
                    "$(li)Cerulean - weight " + FactoryConfiguration.T2_FARM_DROP_SHARD_WEIGHTS.get().get(1) +
                    "$(li)Byzantium - weight " + FactoryConfiguration.T2_FARM_DROP_SHARD_WEIGHTS.get().get(2)
                },
                { PERKS_TAG + "tier_shard.4",
                    "A Tier III Factory has a " + FactoryConfiguration.T3_FARM_DROP_CHANCE.get() + "%% chance to drop a shard." +
                    "$(li)Celadon - weight " + FactoryConfiguration.T3_FARM_DROP_SHARD_WEIGHTS.get().get(0) +
                    "$(li)Cerulean - weight " + FactoryConfiguration.T3_FARM_DROP_SHARD_WEIGHTS.get().get(1) +
                    "$(li)Byzantium - weight " + FactoryConfiguration.T3_FARM_DROP_SHARD_WEIGHTS.get().get(2)
                },
                { PERKS_TAG + "tier_shard.5",
                    "A Tier IV Factory has a " + FactoryConfiguration.T4_FARM_DROP_CHANCE.get() + "%% chance to drop a shard." +
                    "$(li)Celadon - weight " + FactoryConfiguration.T4_FARM_DROP_SHARD_WEIGHTS.get().get(0) +
                    "$(li)Cerulean - weight " + FactoryConfiguration.T4_FARM_DROP_SHARD_WEIGHTS.get().get(1) +
                    "$(li)Byzantium - weight " + FactoryConfiguration.T4_FARM_DROP_SHARD_WEIGHTS.get().get(2)
                },
                { PERKS_TAG + "tier_shard.6",
                    "A Tier V Factory has a " + FactoryConfiguration.T5_FARM_DROP_CHANCE.get() + "%% chance to drop a shard." +
                    "$(li)Celadon - weight " + FactoryConfiguration.T5_FARM_DROP_SHARD_WEIGHTS.get().get(0) +
                    "$(li)Cerulean - weight " + FactoryConfiguration.T5_FARM_DROP_SHARD_WEIGHTS.get().get(1) +
                    "$(li)Byzantium - weight " + FactoryConfiguration.T5_FARM_DROP_SHARD_WEIGHTS.get().get(2)
                },
                // Xp
                { PERKS_TAG + "xp.name", "Wisdom" },
                { PERKS_TAG + "xp.0",
                     "By default the factory does not generate experience from mob kills. " +
                     "With the $(item)Wisdom$(0) perk, the factory will generate $(item)Experience Shards$(0) and $(item)Experience Splinters$(0). " +
                     "Higher levels of this perk will generate more experience items." },
                { PERKS_TAG + "xp.1",
                    "$(li)Tier I - " + FactoryConfiguration.XP_1.get() + "%%" +
                    "$(li)Tier II - " + FactoryConfiguration.XP_2.get() + "%%" +
                    "$(li)Tier III - " + FactoryConfiguration.XP_3.get() + "%%"
                }
        };

            for (String[] strings : text) provider.add(strings[0], strings[1]);
        }

        /**********************************************************************
         * Entries - Tools
         */
        {
            final String TOOLS_TAG = GUIDE_TAG + "tools.";
            String[][] text = {

                // Intern
                { TOOLS_TAG + "intern.name", "The Intern" },
                { TOOLS_TAG + "intern.craft.0", "Why bark when you have a dog." },
                { TOOLS_TAG + "intern.0",
                        "$(item)The Intern$(0) is there to replace that tedious task of manually building and upgrading the factory structure. " +
                        "Not only can it build but also, with no extra payment, validate an existing structure."
                },
                { TOOLS_TAG + "intern.1",
                        "When in one of the build modes, $(item)The Intern$(0) will take the factory blocks from your inventory and place them into the world. " +
                        "The tier of factory built will depend on the mode that $(item)The Intern$(0) is in."
                },
                { TOOLS_TAG + "intern.2",
                        "$(li)The tool should be used on a pre-placed Heart." + "" +
                        "$(li)The current mode can be cycled by left-clicking in the air."
                },

                // Layout Guide
                { TOOLS_TAG + "layout.name", "Layout Guide" },
                { TOOLS_TAG + "layout.0",
                        "The $(item)Layout Guide$(0) will show you where the blocks need to be placed for a factory. " +
                        "It can be placed on top of an existing Factory Heart or on its own."
                },
                { TOOLS_TAG + "layout.1" ,
                        "$(li)Right clicking will step through the different tiers" +
                        "$(li)Sneak right click will step through the y-levels of the current tier" },

                // Hammer
                { TOOLS_TAG + "hammer.name", "YA Hammer" },
                { TOOLS_TAG + "hammer.craft.0", "Yet Another Hammer" },
                { TOOLS_TAG + "hammer.0",
                        "The $(item)YA Hammer$(0) has two main usages. " +
                        "The first is for crafting on the anvil. " +
                        "The second is for use in vanilla crafting. " + "" +
                        "The $(item)YA Hammer$(0) is never consumed when used in recipes."
                },

                // Oracle
                { TOOLS_TAG + "oracle.name", "Dee's Oracle" },
                { TOOLS_TAG + "oracle.0",
                    "$(item)Dee's Oracle$(0) allows you to view any simulated mob, the associated drops and drop chance at each looting level."
                }
            };

            for (String[] strings : text) provider.add(strings[0], strings[1]);
        }
    }

}
