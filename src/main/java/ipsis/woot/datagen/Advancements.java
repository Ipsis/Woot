package ipsis.woot.datagen;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ipsis.woot.Woot;
import ipsis.woot.advancements.ApplyPerkTrigger;
import ipsis.woot.advancements.MobCaptureTrigger;
import ipsis.woot.advancements.TierValidateTrigger;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.FakeMob;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Internal Minecraft advancement generator cannot currently be hooked into
 *
 * Testing:
 */

public class Advancements implements IDataProvider {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;


    public Advancements(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    private Advancement registerSimpleInvAdvancement(Consumer<Advancement> consumer, String name, Advancement parent, IItemProvider itemProvider, FrameType frameType) {
        return register(consumer,
                new ResourceLocation(Woot.MODID, "main/" + name),
                Advancement.Builder.builder()
                        .withParent(parent)
                        .withDisplay(
                                new ItemStack(itemProvider),
                                new TranslationTextComponent("advancements.woot." + name + ".title"),
                                new TranslationTextComponent("advancements.woot." + name + ".description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                frameType, false, true, false)
                        .withCriterion(name, InventoryChangeTrigger.Instance.forItems(itemProvider)));
    }

    private Advancement registerSimpleInvAdvancement(Consumer<Advancement> consumer, String name, Advancement parent, IItemProvider itemProvider) {
        return registerSimpleInvAdvancement(consumer, name, parent, itemProvider, FrameType.TASK);
    }

    private Advancement registerTier(Consumer<Advancement> consumer, String name, Advancement parent, IItemProvider itemProvider, Tier tier) {
        return register(consumer,
                new ResourceLocation(Woot.MODID, "main/" + name),
                Advancement.Builder.builder()
                        .withParent(parent)
                        .withDisplay(
                                new ItemStack(itemProvider),
                                new TranslationTextComponent("advancements.woot." + name + ".title"),
                                new TranslationTextComponent("advancements.woot." + name + ".description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                FrameType.GOAL, false, true, false)
                        .withRewards(AdvancementRewards.Builder.experience(5))
                        .withCriterion(
                                name,
                                TierValidateTrigger.Instance.forTier(tier)));
    }

    private Advancement registerPerk(Consumer<Advancement> consumer, String name, Advancement parent, IItemProvider itemProvider, Perk perk) {
        return register(consumer,
                new ResourceLocation(Woot.MODID, "main/" + name),
                Advancement.Builder.builder()
                        .withParent(parent)
                        .withDisplay(
                                new ItemStack(itemProvider),
                                new TranslationTextComponent("advancements.woot." + name + ".title"),
                                new TranslationTextComponent("advancements.woot." + name + ".description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                FrameType.TASK, false, true, false)
                        .withRewards(AdvancementRewards.Builder.experience(5))
                        .withCriterion(
                                name,
                                ApplyPerkTrigger.Instance.forPerk(perk)));
    }

    private void registerAdvancements(Consumer<Advancement> consumer) {

        Advancement root = register(consumer,
                new ResourceLocation(Woot.MODID, "main/root"),
                Advancement.Builder.builder()
                        .withParent(null)
                        .withDisplay(
                                new ItemStack(FactorySetup.HEART_BLOCK.get()),
                                new TranslationTextComponent("advancements.woot.root.title"),
                                new TranslationTextComponent("advancements.woot.root.description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                FrameType.TASK, false, true, false)
                        .withCriterion(
                                "killed_something",
                                KilledTrigger.Instance.playerKilledEntity()));


        Advancement oracle = registerSimpleInvAdvancement(consumer, "oracle", root, OracleSetup.ORACLE_BLOCK.get());

        Advancement si_ingot = registerSimpleInvAdvancement(consumer, "si_ingot", root, GenericSetup.SI_INGOT_ITEM.get());
        Advancement si_anvil = registerSimpleInvAdvancement(consumer, "si_anvil", si_ingot, AnvilSetup.ANVIL_BLOCK.get());
        Advancement ya_hammer = registerSimpleInvAdvancement(consumer, "ya_hammer", si_ingot, AnvilSetup.HAMMER_ITEM.get());
        Advancement si_dust = registerSimpleInvAdvancement(consumer, "si_dust", ya_hammer, GenericSetup.SI_DUST_ITEM.get());

        Advancement layout = registerSimpleInvAdvancement(consumer, "layout", root, LayoutSetup.LAYOUT_BLOCK.get());
        Advancement intern = registerSimpleInvAdvancement(consumer, "intern", layout, LayoutSetup.INTERN_ITEM.get());

        Advancement shard_die = registerSimpleInvAdvancement(consumer, "shard_die", si_anvil, AnvilSetup.SHARD_DIE_ITEM.get());
        Advancement plate_die = registerSimpleInvAdvancement(consumer, "plate_die", si_anvil, AnvilSetup.PLATE_DIE_ITEM.get());
        Advancement dye_die = registerSimpleInvAdvancement(consumer, "dye_die", si_anvil, AnvilSetup.DYE_DIE_ITEM.get());
        Advancement si_plate = registerSimpleInvAdvancement(consumer, "si_plate", plate_die, GenericSetup.SI_PLATE_ITEM.get());
        Advancement casing = registerSimpleInvAdvancement(consumer, "casing", si_plate, GenericSetup.MACHINE_CASING_ITEM.get());

        Advancement squeezer = registerSimpleInvAdvancement(consumer, "squeezer", casing, SqueezerSetup.SQUEEZER_BLOCK.get());
        Advancement pure_dye = registerSimpleInvAdvancement(consumer, "pure_dye", squeezer, FluidSetup.PUREDYE_FLUID_BUCKET.get(), FrameType.GOAL);

        Advancement infuser = registerSimpleInvAdvancement(consumer, "infuser", pure_dye, InfuserSetup.INFUSER_BLOCK.get());
        Advancement prism = registerSimpleInvAdvancement(consumer, "prism", infuser, GenericSetup.PRISM_ITEM.get());
        Advancement magenta = registerSimpleInvAdvancement(consumer, "magenta", infuser, InfuserSetup.MAGENTA_DYE_PLATE_ITEM.get());
        Advancement factory_a = registerSimpleInvAdvancement(consumer, "factory_a", magenta, FactorySetup.FACTORY_A_BLOCK.get());
        Advancement factory_b = registerSimpleInvAdvancement(consumer, "factory_b", factory_a, FactorySetup.FACTORY_B_BLOCK.get());

        Advancement vat = registerSimpleInvAdvancement(consumer, "vat", casing, FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get());
        Advancement purge_fluid = registerSimpleInvAdvancement(consumer, "purge_fluid", vat, FluidSetup.MOB_ESSENCE_FLUID_BUCKET.get());
        Advancement conatus_fluid = registerSimpleInvAdvancement(consumer, "conatus_fluid", purge_fluid, FluidSetup.CONATUS_FLUID_BUCKET.get(), FrameType.GOAL);
        Advancement cell_1 = registerSimpleInvAdvancement(consumer, "cell_1", conatus_fluid, FactorySetup.CELL_1_BLOCK.get());
        Advancement cell_2 = registerSimpleInvAdvancement(consumer, "cell_2", cell_1, FactorySetup.CELL_2_BLOCK.get());
        Advancement cell_3 = registerSimpleInvAdvancement(consumer, "cell_3", cell_2, FactorySetup.CELL_3_BLOCK.get());
        Advancement cell_4 = registerSimpleInvAdvancement(consumer, "cell_4", cell_3, FactorySetup.CELL_4_BLOCK.get());

        Advancement ench_squeezer = registerSimpleInvAdvancement(consumer, "ench_squeezer", casing, SqueezerSetup.ENCHANT_SQUEEZER_BLOCK.get());
        Advancement ench_fluid = registerSimpleInvAdvancement(consumer, "ench_fluid", ench_squeezer, FluidSetup.ENCHANT_FLUID_BUCKET.get());
        Advancement ench_plate_1 = registerSimpleInvAdvancement(consumer, "ench_plate_1", ench_fluid, GenericSetup.ENCH_PLATE_1.get());
        Advancement ench_plate_2 = registerSimpleInvAdvancement(consumer, "ench_plate_2", ench_fluid, GenericSetup.ENCH_PLATE_2.get());
        Advancement ench_plate_3 = registerSimpleInvAdvancement(consumer, "ench_plate_3", ench_fluid, GenericSetup.ENCH_PLATE_3.get());

        Advancement eff_perk_1 = registerPerk(consumer, "eff_perk_1", ench_plate_1, FactorySetup.EFFICIENCY_1_ITEM.get(), Perk.EFFICIENCY_1);
        Advancement head_perk_1 = registerPerk(consumer, "head_perk_1", ench_plate_1, FactorySetup.HEADLESS_1_ITEM.get(), Perk.HEADLESS_1);
        Advancement looting_perk_1 = registerPerk(consumer, "looting_perk_1", ench_plate_1, FactorySetup.LOOTING_1_ITEM.get(), Perk.LOOTING_1);
        Advancement mass_perk_1 = registerPerk(consumer, "mass_perk_1", ench_plate_1, FactorySetup.MASS_1_ITEM.get(), Perk.MASS_1);
        Advancement rate_perk_1 = registerPerk(consumer, "rate_perk_1", ench_plate_1, FactorySetup.RATE_1_ITEM.get(), Perk.RATE_1);
        Advancement shard_perk_1 = registerPerk(consumer, "shard_perk_1", ench_plate_1, FactorySetup.TIER_SHARD_1_ITEM.get(), Perk.TIER_SHARD_1);
        Advancement tier_1_shard = registerSimpleInvAdvancement(consumer, "tier_1_shard", shard_perk_1, GenericSetup.T1_SHARD_ITEM.get());
        Advancement factory_c_block = registerSimpleInvAdvancement(consumer, "factory_c_block", tier_1_shard, FactorySetup.FACTORY_C_BLOCK.get());
        Advancement tier_2_shard = registerSimpleInvAdvancement(consumer, "tier_2_shard", shard_perk_1, GenericSetup.T2_SHARD_ITEM.get());
        Advancement factory_d_block = registerSimpleInvAdvancement(consumer, "factory_d_block", tier_2_shard, FactorySetup.FACTORY_D_BLOCK.get());
        Advancement tier_3_shard = registerSimpleInvAdvancement(consumer, "tier_3_shard", shard_perk_1, GenericSetup.T3_SHARD_ITEM.get());
        Advancement factory_e_block = registerSimpleInvAdvancement(consumer, "factory_e_block", tier_3_shard, FactorySetup.FACTORY_E_BLOCK.get());
        Advancement xp_perk_1 = registerPerk(consumer, "xp_perk_1", ench_plate_1, FactorySetup.XP_1_ITEM.get(), Perk.XP_1);

        Advancement xp_shard = register(consumer,
                new ResourceLocation(Woot.MODID, "main/xp_shard"),
                Advancement.Builder.builder()
                        .withParent(xp_perk_1)
                        .withDisplay(
                                new ItemStack(FactorySetup.XP_SHARD_ITEM.get()),
                                new TranslationTextComponent("advancements.woot.xp_shard.title"),
                                new TranslationTextComponent("advancements.woot.xp_shard.description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                FrameType.TASK, false, true, false)
                        .withCriterion("xp_shard", ConsumeItemTrigger.Instance.forItem(FactorySetup.XP_SHARD_ITEM.get())));

        Advancement xp_splinter = register(consumer,
                new ResourceLocation(Woot.MODID, "main/xp_splinter"),
                Advancement.Builder.builder()
                        .withParent(xp_perk_1)
                        .withDisplay(
                                new ItemStack(FactorySetup.XP_SPLINTER_ITEM.get()),
                                new TranslationTextComponent("advancements.woot.xp_splinter.title"),
                                new TranslationTextComponent("advancements.woot.xp_splinter.description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                FrameType.TASK, false, true, false)
                        .withCriterion("xp_splinter", ConsumeItemTrigger.Instance.forItem(FactorySetup.XP_SPLINTER_ITEM.get())));

        Advancement slaughter = register(consumer,
                new ResourceLocation(Woot.MODID, "main/slaughter"),
                Advancement.Builder.builder()
                        .withParent(ench_plate_1)
                        .withDisplay(
                                new ItemStack(FactorySetup.SLAUGHTER_1_ITEM.get()),
                                new TranslationTextComponent("advancements.woot.slaughter_perk_1.title"),
                                new TranslationTextComponent("advancements.woot.slaughter_perk_1.description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                FrameType.TASK, false, true, false)
                        .withCriterion("slaughter",
                                ApplyPerkTrigger.Instance.forPerk(Perk.SLAUGHTER_1)));

        Advancement crusher = register(consumer,
                new ResourceLocation(Woot.MODID, "main/crusher"),
                Advancement.Builder.builder()
                        .withParent(ench_plate_1)
                        .withDisplay(
                                new ItemStack(FactorySetup.SLAUGHTER_1_ITEM.get()),
                                new TranslationTextComponent("advancements.woot.crusher_perk_1.title"),
                                new TranslationTextComponent("advancements.woot.crusher_perk_1.description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                FrameType.TASK, false, true, false)
                        .withCriterion("crusher",
                                ApplyPerkTrigger.Instance.forPerk(Perk.CRUSHER_1)));

        Advancement capture_mob = register(consumer,
                new ResourceLocation(Woot.MODID, "main/capture_mob"),
                Advancement.Builder.builder()
                        .withParent(shard_die)
                        .withDisplay(
                                new ItemStack(FactorySetup.MOB_SHARD_ITEM.get()),
                                new TranslationTextComponent("advancements.woot.capture_mob.title"),
                                new TranslationTextComponent("advancements.woot.capture_mob.description"),
                                new ResourceLocation("textures/block/black_concrete_powder.png"),
                                FrameType.TASK, false, true, false)
                        .withCriterion(
                                "capture_mob",
                                MobCaptureTrigger.Instance.forMob(new FakeMob("minecraft:sheep"))));
        Advancement controller = registerSimpleInvAdvancement(consumer, "controller", prism, FactorySetup.CONTROLLER_BLOCK.get(), FrameType.GOAL);

        Advancement tier1 = registerTier(consumer, "tier1", factory_a, FactorySetup.FACTORY_A_BLOCK.get(), Tier.TIER_1);
        Advancement tier2 = registerTier(consumer, "tier2", factory_b, FactorySetup.CAP_A_BLOCK.get(), Tier.TIER_2);
        Advancement tier3 = registerTier(consumer, "tier3", factory_c_block, FactorySetup.CAP_B_BLOCK.get(), Tier.TIER_3);
        Advancement tier4 = registerTier(consumer, "tier4", factory_d_block, FactorySetup.CAP_C_BLOCK.get(), Tier.TIER_4);
        Advancement tier5 = registerTier(consumer, "tier5", factory_e_block, FactorySetup.CAP_D_BLOCK.get(), Tier.TIER_5);

        // TODO Perk advancements on applying them to a factory
    }

    private Advancement register(Consumer<Advancement> consumer, ResourceLocation resourceLocation, Advancement.Builder builder) {
        return builder.register(consumer, resourceLocation.toString());
    }

    public void act(DirectoryCache cache) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path path1 = getPath(path, advancement);

                try {
                    IDataProvider.save(GSON, cache, advancement.copy().serialize(), path1);
                } catch (IOException ioexception) {
                    Woot.setup.getLogger().error("Couldn't save advancement {}", path1, ioexception);
                }

            }
        };

        registerAdvancements(consumer);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    public String getName() {
        return "Woot Advancements";
    }
}
