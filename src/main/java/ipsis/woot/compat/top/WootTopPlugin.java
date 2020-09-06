package ipsis.woot.compat.top;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.*;
import ipsis.woot.modules.factory.blocks.*;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.modules.infuser.blocks.InfuserTileEntity;
import ipsis.woot.modules.layout.blocks.LayoutBlock;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerBlock;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerTileEntity;
import ipsis.woot.modules.squeezer.blocks.EnchantSqueezerTileEntity;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Function;

public class WootTopPlugin {

    private static boolean registered;

    public static void init() {
        if (!ModList.get().isLoaded("theoneprobe"))
            return;


        if (!registered) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", () -> new GetTheOneProbe());
            registered = true;
        }
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {

        public static ITheOneProbe probe;

        @Nullable
        @Override
        public Void apply(@Nullable ITheOneProbe input) {
            probe = input;
            Woot.setup.getLogger().info("Enabling support The One Probe");
            probe.registerProvider(new IProbeInfoProvider() {
                @Override
                public String getID() {
                    return Woot.MODID + ":default";
                }

                @Override
                public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData iProbeHitData) {

                    TileEntity tileEntity = world.getTileEntity(iProbeHitData.getPos());
                    if (tileEntity != null) {
                        addControllerProbeInfo(probeMode, iProbeInfo, tileEntity, playerEntity, blockState);
                        addHeartProbeInfo(probeMode, iProbeInfo, tileEntity, playerEntity, blockState);
                        addPerkProbeInfo(probeMode, iProbeInfo, tileEntity, playerEntity, blockState);
                        addDyeSqueezerProbeInfo(probeMode, iProbeInfo, tileEntity, playerEntity, blockState);
                        addEnchantSqueezerProbeInfo(probeMode, iProbeInfo, tileEntity, playerEntity, blockState);
                        addLayoutProbeInfo(probeMode, iProbeInfo, tileEntity, playerEntity, blockState);
                        addInfuserProbeInfo(probeMode, iProbeInfo, tileEntity, playerEntity, blockState);
                    }
                }
            });

            return null;
        }

        private void addPerkProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {

            if (!(te instanceof UpgradeTileEntity))
                return;

            UpgradeTileEntity upgrade = (UpgradeTileEntity) te;
            Perk perk = upgrade.getUpgrade(blockState);
            if (perk == Perk.EMPTY) {
                iProbeInfo.text(
                        TextStyleClass.LABEL +
                        StringHelper.translate("top.woot.perk.type.label").getUnformattedComponentText() +
                        ": " +
                        TextStyleClass.INFO +
                        StringHelper.translate("top.woot.perk.type.empty").getUnformattedComponentText());
            } else {
                iProbeInfo.text(
                        TextStyleClass.LABEL +
                        StringHelper.translate("top.woot.perk.type.label").getUnformattedComponentText() +
                        ": " +
                        TextStyleClass.INFO +
                        StringHelper.translate("item.woot." + perk.getName()).getUnformattedComponentText());
            }
        }

        private void addDyeSqueezerProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {

            if (!(te instanceof DyeSqueezerTileEntity))
                return;

            DyeSqueezerTileEntity squeezer = (DyeSqueezerTileEntity) te;
            iProbeInfo.text(
                    TextStyleClass.LABEL +
                    StringHelper.translate("top.woot.squeezer.red.label").getUnformattedComponentText() +
                    ": " +
                    TextStyleClass.INFO +
                    StringHelper.translate("top.woot.squeezer.dye.0", squeezer.getRed()).getUnformattedComponentText());

            iProbeInfo.text(
                    TextStyleClass.LABEL +
                    StringHelper.translate("top.woot.squeezer.yellow.label").getUnformattedComponentText() +
                    ": " +
                    TextStyleClass.INFO +
                    StringHelper.translate("top.woot.squeezer.dye.0", squeezer.getYellow()).getUnformattedComponentText());

            iProbeInfo.text(
                    TextStyleClass.LABEL +
                    StringHelper.translate("top.woot.squeezer.blue.label").getUnformattedComponentText() +
                    ": " +
                    TextStyleClass.INFO +
                    StringHelper.translate("top.woot.squeezer.dye.0", squeezer.getBlue()).getUnformattedComponentText());

            iProbeInfo.text(
                    TextStyleClass.LABEL +
                    StringHelper.translate("top.woot.squeezer.white.label").getUnformattedComponentText() +
                    ": " +
                    TextStyleClass.INFO +
                    StringHelper.translate("top.woot.squeezer.dye.0", squeezer.getWhite()).getUnformattedComponentText());

            iProbeInfo.text(
                    TextStyleClass.LABEL +
                    StringHelper.translate("top.woot.squeezer.tanks.label").getUnformattedComponentText() +
                    ": " +
                    TextStyleClass.INFO +
                    (squeezer.getDumpExcess() ?
                        StringHelper.translate("top.woot.squeezer.tanks.0").getUnformattedComponentText() :
                        StringHelper.translate("top.woot.squeezer.tanks.1").getUnformattedComponentText()));
        }

        private void addEnchantSqueezerProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {

            if (!(te instanceof EnchantSqueezerTileEntity))
                return;

            EnchantSqueezerTileEntity squeezer = (EnchantSqueezerTileEntity) te;
        }

        private void addInfuserProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {

            if (!(te instanceof InfuserTileEntity))
                return;

            InfuserTileEntity infuser = (InfuserTileEntity) te;
        }

        private void addLayoutProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {

            if (!(te instanceof LayoutTileEntity))
                return;

            LayoutTileEntity layout = (LayoutTileEntity) te;
            Tier tier = layout.getTier();

            iProbeInfo.text(
                    TextStyleClass.LABEL +
                    StringHelper.translate("top.woot.layout.tier.label").getUnformattedComponentText() +
                    ": " +
                    TextStyleClass.INFO +
                    StringHelper.translate(tier.getTranslationKey()).getUnformattedComponentText());

            if (playerEntity.isSneaking()) {
                PatternRepository.Pattern pattern = PatternRepository.get().getPattern(tier);
                if (pattern != null) {
                    for (FactoryComponent component : FactoryComponent.VALUES) {
                        int count = pattern.getFactoryBlockCount((component));
                        if (count > 0) {
                            String text = String.format("%2d * %s", count, StringHelper.translate(component.getTranslationKey()).getUnformattedComponentText());
                            if (component == FactoryComponent.CELL)
                                text = String.format("%2d * %s", count, StringHelper.translate("info.woot.intern.cell").getUnformattedComponentText());
                            else if (component == FactoryComponent.CONTROLLER)
                                text = String.format(" 1-%d * %s", count, StringHelper.translate(component.getTranslationKey()).getUnformattedComponentText());

                            iProbeInfo.text(TextStyleClass.INFO + text);
                        }
                    }
                }
            }
        }

        private void addControllerProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {

            if (!(te instanceof ControllerTileEntity))
                return;

            ControllerTileEntity controller = (ControllerTileEntity) te;

            if (probeMode == ProbeMode.DEBUG) {
                iProbeInfo.text(
                        TextStyleClass.LABEL +
                        StringHelper.translate("top.woot.controller.mob.label").getUnformattedComponentText() +
                        ": " +
                        TextStyleClass.INFO +
                        controller.getFakeMob().toString());
            } else {
                TranslationTextComponent t = StringHelper.translate(controller.getFakeMob());
                iProbeInfo.text(
                        TextStyleClass.LABEL +
                        StringHelper.translate("top.woot.controller.mob.label").getUnformattedComponentText() +
                        ": " +
                        TextStyleClass.INFO +
                        t.getUnformattedComponentText());
                iProbeInfo.text(
                        TextStyleClass.LABEL +
                        StringHelper.translate("top.woot.controller.tier.label").getUnformattedComponentText() +
                        ": " +
                        TextStyleClass.INFO +
                        StringHelper.translate(controller.getTier().getTranslationKey()).getUnformattedComponentText());
            }
        }

        private void addHeartProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {
            if (!(te instanceof HeartTileEntity))
                return;

            HeartTileEntity heart = (HeartTileEntity) te;
            if (heart.isFormed()) {
                // Add tier
                iProbeInfo.text(
                        TextStyleClass.LABEL +
                        StringHelper.translate("top.woot.heart.tier.label").getUnformattedComponentText() +
                        ": " +
                        TextStyleClass.INFO +
                        StringHelper.translate(heart.getTier().getTranslationKey()).getUnformattedComponentText());
                // Add perks
                for (PerkType perkType : heart.getPerks().keySet()) {
                    int level = heart.getPerks().get(perkType);
                    ItemStack itemStack = PerkItem.getItemStack(perkType, level);
                    if (!itemStack.isEmpty())
                        iProbeInfo.text(
                                TextStyleClass.LABEL +
                                StringHelper.translate("top.woot.heart.perk.label").getUnformattedComponentText() +
                                ": " +
                                TextStyleClass.INFO +
                                StringHelper.translate(itemStack.getItem().getTranslationKey()).getUnformattedComponentText());
                }
                // Add exotic
                if (heart.getExotic() != Exotic.NONE) {
                    iProbeInfo.text(
                            TextStyleClass.LABEL +
                            StringHelper.translate("top.woot.heart.exotic.label").getUnformattedComponentText() +
                            ": " +
                            TextStyleClass.INFO +
                            heart.getExotic().getTooltip().getUnformattedComponentText());
                }
                // Add tank
                iProbeInfo.text(
                        TextStyleClass.LABEL +
                        StringHelper.translate("top.woot.heart.progress.label").getUnformattedComponentText() +
                        ": " +
                        TextStyleClass.INFO +
                        StringHelper.translate("top.woot.heart.progress.0", heart.getProgress()).getUnformattedComponentText());

                for (FakeMob fakeMob : heart.getFormedMobs()) {
                    TranslationTextComponent t = StringHelper.translate(fakeMob);
                    iProbeInfo.text(
                            TextStyleClass.LABEL +
                            StringHelper.translate("top.woot.heart.mob.label").getUnformattedComponentText() +
                            ": " +
                            TextStyleClass.INFO +
                            t.getUnformattedComponentText());
                }
            } else {
                iProbeInfo.text(TextStyleClass.ERROR + StringHelper.translate("top.woot.heart.unformed").getUnformattedComponentText());
            }
        }
    }
}
