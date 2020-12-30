package ipsis.woot.datagen;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ipsis.woot.Woot;
import ipsis.woot.advancements.MobCaptureTrigger;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.FakeMob;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.Tags;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Internal Minecraft advancement generator cannot currently be hooked into
 *
 * Testing:
 */

public class WootAdvancements implements IDataProvider {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;


    public WootAdvancements(DataGenerator dataGenerator) {
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
                                FrameType.TASK,
                                false,
                                true,
                                false)
                        .withCriterion(
                                "killed_something",
                                KilledTrigger.Instance.playerKilledEntity()));

        Advancement si_ingot = registerSimpleInvAdvancement(consumer, "si_ingot", root, GenericSetup.SI_INGOT_ITEM.get());
        Advancement si_anvil = registerSimpleInvAdvancement(consumer, "si_anvil", si_ingot, AnvilSetup.ANVIL_BLOCK.get());
        Advancement ya_hammer = registerSimpleInvAdvancement(consumer, "ya_hammer", si_ingot, AnvilSetup.HAMMER_ITEM.get());

        Advancement layout = registerSimpleInvAdvancement(consumer, "layout", root, LayoutSetup.LAYOUT_BLOCK.get());
        Advancement intern = registerSimpleInvAdvancement(consumer, "intern", layout, LayoutSetup.INTERN_ITEM.get());

        Advancement shard_die = registerSimpleInvAdvancement(consumer, "shard_die", si_anvil, AnvilSetup.SHARD_DIE_ITEM.get());
        Advancement plate_die = registerSimpleInvAdvancement(consumer, "plate_die", si_anvil, AnvilSetup.PLATE_DIE_ITEM.get());
        Advancement dye_die = registerSimpleInvAdvancement(consumer, "dye_die", si_anvil, AnvilSetup.DYE_DIE_ITEM.get());

        Advancement squeezer = registerSimpleInvAdvancement(consumer, "squeezer", plate_die, SqueezerSetup.SQUEEZER_BLOCK.get());
        Advancement pure_dye = registerSimpleInvAdvancement(consumer, "pure_dye", squeezer, FluidSetup.PUREDYE_FLUID_BUCKET.get(), FrameType.GOAL);

        Advancement infuser = registerSimpleInvAdvancement(consumer, "infuser", plate_die, InfuserSetup.INFUSER_BLOCK.get());

        Advancement vat = registerSimpleInvAdvancement(consumer, "vat", plate_die, FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get());
        Advancement purge_fluid = registerSimpleInvAdvancement(consumer, "purge_fluid", vat, FluidSetup.MOB_ESSENCE_FLUID_BUCKET.get());
        Advancement conatus_fluid = registerSimpleInvAdvancement(consumer, "conatus_fluid", purge_fluid, FluidSetup.CONATUS_FLUID_BUCKET.get(), FrameType.GOAL);
        Advancement cell_1 = registerSimpleInvAdvancement(consumer, "cell_1", conatus_fluid, FactorySetup.CELL_1_BLOCK.get());
        Advancement cell_2 = registerSimpleInvAdvancement(consumer, "cell_2", cell_1, FactorySetup.CELL_2_BLOCK.get());
        Advancement cell_3 = registerSimpleInvAdvancement(consumer, "cell_3", cell_2, FactorySetup.CELL_3_BLOCK.get());

        Advancement ench_squeezer = registerSimpleInvAdvancement(consumer, "ench_squeezer", plate_die, SqueezerSetup.ENCHANT_SQUEEZER_BLOCK.get());
        Advancement ench_plate_1 = registerSimpleInvAdvancement(consumer, "ench_plate_1", ench_squeezer, GenericSetup.ENCH_PLATE_1.get());
        Advancement ench_plate_2 = registerSimpleInvAdvancement(consumer, "ench_plate_2", ench_squeezer, GenericSetup.ENCH_PLATE_2.get());
        Advancement ench_plate_3 = registerSimpleInvAdvancement(consumer, "ench_plate_3", ench_squeezer, GenericSetup.ENCH_PLATE_3.get());

        Advancement eff_perk_1 = registerSimpleInvAdvancement(consumer, "eff_perk_1", ench_plate_1, FactorySetup.EFFICIENCY_1_ITEM.get());
        Advancement head_perk_1 = registerSimpleInvAdvancement(consumer, "head_perk_1", ench_plate_1, FactorySetup.HEADLESS_1_ITEM.get());
        Advancement looting_perk_1 = registerSimpleInvAdvancement(consumer, "looting_perk_1", ench_plate_1, FactorySetup.LOOTING_1_ITEM.get());
        Advancement mass_perk_1 = registerSimpleInvAdvancement(consumer, "mass_perk_1", ench_plate_1, FactorySetup.MASS_1_ITEM.get());
        Advancement rate_perk_1 = registerSimpleInvAdvancement(consumer, "rate_perk_1", ench_plate_1, FactorySetup.RATE_1_ITEM.get());
        Advancement shard_perk_1 = registerSimpleInvAdvancement(consumer, "shard_perk_1", ench_plate_1, FactorySetup.TIER_SHARD_1_ITEM.get());
        Advancement tier_1_shard = registerSimpleInvAdvancement(consumer, "tier_1_shard", shard_perk_1, GenericSetup.T1_SHARD_ITEM.get());
        Advancement tier_1_block = registerSimpleInvAdvancement(consumer, "tier_1_block", tier_1_shard, FactorySetup.FACTORY_C_BLOCK.get());
        Advancement tier_2_shard = registerSimpleInvAdvancement(consumer, "tier_2_shard", shard_perk_1, GenericSetup.T2_SHARD_ITEM.get());
        Advancement tier_2_block = registerSimpleInvAdvancement(consumer, "tier_2_block", tier_2_shard, FactorySetup.FACTORY_D_BLOCK.get());
        Advancement tier_3_shard = registerSimpleInvAdvancement(consumer, "tier_3_shard", shard_perk_1, GenericSetup.T3_SHARD_ITEM.get());
        Advancement tier_3_block = registerSimpleInvAdvancement(consumer, "tier_3_block", tier_3_shard, FactorySetup.FACTORY_E_BLOCK.get());
        Advancement xp_perk_1 = registerSimpleInvAdvancement(consumer, "xp_perk_1", ench_plate_1, FactorySetup.XP_1_ITEM.get());
        Advancement xp_shard = registerSimpleInvAdvancement(consumer, "xp_shard", xp_perk_1, FactorySetup.XP_SHARD_ITEM.get());
        Advancement xp_splinter = registerSimpleInvAdvancement(consumer, "xp_splinter", xp_perk_1, FactorySetup.XP_SPLINTER_ITEM.get());


        Advancement capture_mob = register(consumer,
                new ResourceLocation(Woot.MODID, "main/capture_mob"),
                Advancement.Builder.builder()
                        .withParent(shard_die)
                        .withDisplay(
                                new ItemStack(FactorySetup.MOB_SHARD_ITEM.get()),
                                new TranslationTextComponent("advancements.woot.capture_mob.title"),
                                new TranslationTextComponent("advancements.woot.capture_mob.description"),
                                null,
                                FrameType.TASK,
                                false,
                                false,
                                false)
                        .withCriterion(
                                "capture_mob",
                                MobCaptureTrigger.Instance.forMob(new FakeMob("minecraft:sheep"))));
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
