package ipsis.woot.compat.top;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.*;
import ipsis.woot.modules.factory.blocks.*;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.infuser.blocks.InfuserTileEntity;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerTileEntity;
import ipsis.woot.modules.squeezer.blocks.EnchantSqueezerTileEntity;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;

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
                        CompoundText.createLabelInfo(
                                StringHelper.translate("top.woot.perk.type.label") + ": ",
                                StringHelper.translate("top.woot.perk.type.empty")));

            } else {
                iProbeInfo.text(
                        CompoundText.createLabelInfo(
                                StringHelper.translate("top.woot.perk.type.label") + ": ",
                                StringHelper.translate("item.woot." + perk.getLowerCaseName())));
            }
        }

        private void addDyeSqueezerProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {

            if (!(te instanceof DyeSqueezerTileEntity))
                return;

            DyeSqueezerTileEntity squeezer = (DyeSqueezerTileEntity) te;
            iProbeInfo.text(
                    CompoundText.createLabelInfo(
                            StringHelper.translate("top.woot.squeezer.red.label") + ": ",
                            StringHelper.translateFormat("top.woot.squeezer.dye.0", squeezer.getRed())));

            iProbeInfo.text(
                    CompoundText.createLabelInfo(
                            StringHelper.translate("top.woot.squeezer.yellow.label") + ": ",
                            StringHelper.translateFormat("top.woot.squeezer.dye.0", squeezer.getYellow())));

            iProbeInfo.text(
                    CompoundText.createLabelInfo(
                            StringHelper.translate("top.woot.squeezer.blue.label") + ": ",
                            StringHelper.translateFormat("top.woot.squeezer.dye.0", squeezer.getBlue())));

            iProbeInfo.text(
                    CompoundText.createLabelInfo(
                            StringHelper.translate("top.woot.squeezer.white.label") + ": ",
                            StringHelper.translateFormat("top.woot.squeezer.dye.0", squeezer.getWhite())));

            iProbeInfo.text(
                    CompoundText.createLabelInfo(
                            StringHelper.translate("top.woot.squeezer.tanks.label") + ": ",
                            (squeezer.getDumpExcess() ?
                                    StringHelper.translate("top.woot.squeezer.tanks.0") :
                                    StringHelper.translate("top.woot.squeezer.tanks.1"))));
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
                    CompoundText.createLabelInfo(
                            StringHelper.translate("top.woot.layout.tier.label") + ": ",
                            StringHelper.translate(tier.getTranslationKey())));

            if (playerEntity.isSneaking()) {
                PatternRepository.Pattern pattern = PatternRepository.get().getPattern(tier);
                if (pattern != null) {
                    for (FactoryComponent component : FactoryComponent.VALUES) {
                        int count = pattern.getFactoryBlockCount((component));
                        if (count > 0) {
                            String text = String.format("%2d * %s", count, StringHelper.translate(component.getTranslationKey()));
                            if (component == FactoryComponent.CELL)
                                text = String.format("%2d * %s", count, StringHelper.translate("info.woot.intern.cell"));
                            else if (component == FactoryComponent.CONTROLLER)
                                text = String.format(" 1-%d * %s", count, StringHelper.translate(component.getTranslationKey()));

                            iProbeInfo.text(CompoundText.create().text(text));
                        }
                    }
                }
            }
        }

        private void addControllerProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {

            if (!(te instanceof ControllerTileEntity))
                return;

            ControllerTileEntity controller = (ControllerTileEntity) te;

            if (controller.getFakeMob().isValid()) {
                if (probeMode == ProbeMode.DEBUG) {
                    iProbeInfo.text(
                            CompoundText.createLabelInfo(
                                    StringHelper.translate("top.woot.controller.mob.label") + ": ",
                                    controller.getFakeMob().toString()));
                } else {
                    iProbeInfo.text(
                            CompoundText.createLabelInfo(
                                    StringHelper.translate("top.woot.controller.mob.label") + ": ",
                                    StringHelper.translate(controller.getFakeMob())));
                    iProbeInfo.text(
                            CompoundText.createLabelInfo(
                                    StringHelper.translate("top.woot.controller.tier.label") + ": ",
                                    StringHelper.translate(controller.getTier().getTranslationKey())));
                }
            } else {
                iProbeInfo.text(
                        CompoundText.createLabelInfo(StringHelper.translate("top.woot.controller.mob.label") + ":", ""));
            }
        }

        private void addHeartProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, TileEntity te, PlayerEntity playerEntity, BlockState blockState) {
            if (!(te instanceof HeartTileEntity))
                return;

            HeartTileEntity heart = (HeartTileEntity) te;
            if (heart.isFormed()) {
                // Add tier
                iProbeInfo.text(
                        CompoundText.createLabelInfo(
                                StringHelper.translate("top.woot.heart.tier.label") + ": ",
                                StringHelper.translate(heart.getTier().getTranslationKey())));
                // Add perks
                for (Perk.Group group : heart.getPerks().keySet()) {
                    int level = heart.getPerks().get(group);
                    ItemStack itemStack = PerkItem.getItemStack(group, level);
                    if (!itemStack.isEmpty())
                        iProbeInfo.text(
                                CompoundText.createLabelInfo(
                                        StringHelper.translate("top.woot.heart.perk.label") + ": ",
                                        StringHelper.translate(itemStack.getItem().getTranslationKey())));
                }
                // Add exotic
                Exotic exotic = heart.getExotic();
                if (exotic != Exotic.NONE) {
                    ItemStack itemStack = exotic.getItemStack();
                    iProbeInfo.text(
                            CompoundText.createLabelInfo(
                                    StringHelper.translate("top.woot.heart.exotic.label") + ": ",
                                    StringHelper.translate(itemStack.getItem().getTranslationKey())));
                }
                // Add tank
                iProbeInfo.text(
                        CompoundText.createLabelInfo(
                                StringHelper.translate("top.woot.heart.progress.label") + ": ",
                                StringHelper.translateFormat("top.woot.heart.progress.0", heart.getProgress())));

                for (FakeMob fakeMob : heart.getFormedMobs()) {
                    iProbeInfo.text(
                            CompoundText.createLabelInfo(
                                    StringHelper.translate("top.woot.heart.mob.label") + ": ",
                                    StringHelper.translate(fakeMob)));
                }
            } else {
                iProbeInfo.text(CompoundText.create().error(StringHelper.translate("top.woot.heart.unformed")));
            }
        }
    }
}
