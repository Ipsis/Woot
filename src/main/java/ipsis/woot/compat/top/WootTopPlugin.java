package ipsis.woot.compat.top;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryComponent;
import ipsis.woot.modules.factory.blocks.ControllerBlock;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.blocks.HeartBlock;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.modules.layout.blocks.LayoutBlock;
import ipsis.woot.modules.layout.blocks.LayoutTileEntity;
import ipsis.woot.modules.layout.items.InternItem;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerBlock;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerTileEntity;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
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
                    Block block = blockState.getBlock();
                    if (block instanceof ControllerBlock) {
                        TileEntity te = world.getTileEntity(iProbeHitData.getPos());
                        if (te instanceof ControllerTileEntity) {
                            ControllerTileEntity cte = (ControllerTileEntity)te;
                            FakeMob fakeMob = cte.getFakeMob();
                            if (probeMode == ProbeMode.DEBUG) {
                                iProbeInfo.text(
                                        TextStyleClass.LABEL + "Mob: " +
                                        TextStyleClass.INFO + fakeMob.toString());
                            } else {
                                EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
                                if (entityType != null) {
                                    String name = new TranslationTextComponent(entityType.getTranslationKey()).getFormattedText();
                                    if (fakeMob.hasTag())
                                        name += " " + new StringTextComponent("[" + fakeMob.getTag() + "]");
                                    iProbeInfo.text(
                                            TextStyleClass.LABEL + "Mob: " +
                                            TextStyleClass.INFO + name);
                                }
                            }
                        }
                    } else if (block instanceof DyeSqueezerBlock) {
                        TileEntity te = world.getTileEntity(iProbeHitData.getPos());
                        if (te instanceof DyeSqueezerTileEntity) {
                            DyeSqueezerTileEntity dte = (DyeSqueezerTileEntity)te;
                            iProbeInfo.text(TextStyleClass.LABEL + "Red:    " + TextStyleClass.INFO + dte.getRed() + "mb");
                            iProbeInfo.text(TextStyleClass.LABEL + "Yellow: " + TextStyleClass.INFO + dte.getYellow() + "mb");
                            iProbeInfo.text(TextStyleClass.LABEL + "Blue:   " + TextStyleClass.INFO + dte.getBlue() + "mb");
                            iProbeInfo.text(TextStyleClass.LABEL + "White:  " + TextStyleClass.INFO + dte.getWhite() + "mb");
                        }
                    } else if (block instanceof HeartBlock) {
                        TileEntity te = world.getTileEntity(iProbeHitData.getPos());
                        if (te instanceof HeartTileEntity) {
                            HeartTileEntity hte = (HeartTileEntity)te;
                            if (hte.isFormed()) {
                                iProbeInfo.text(TextStyleClass.LABEL + "Progress: " + TextStyleClass.INFO + hte.getProgress() + "%");
                                for (FakeMob fakeMob : hte.getFormedMobs()) {
                                    EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
                                    if (entityType != null) {
                                        String name = new TranslationTextComponent(entityType.getTranslationKey()).getFormattedText();
                                        if (fakeMob.hasTag())
                                            name += " " + new StringTextComponent("[" + fakeMob.getTag() + "]");
                                        iProbeInfo.text(
                                                TextStyleClass.LABEL + "Mob: " +
                                                        TextStyleClass.INFO + name);
                                    }
                                }
                            }
                        }
                    } else if (block instanceof LayoutBlock) {
                        TileEntity te = world.getTileEntity(iProbeHitData.getPos());
                        if (te instanceof LayoutTileEntity) {
                            LayoutTileEntity lte = (LayoutTileEntity)te;
                            iProbeInfo.text(TextStyleClass.LABEL + "Tier: " + TextStyleClass.INFO + StringHelper.translate(lte.getTier().getTranslationKey()));

                            if (playerEntity.isCrouching()) {
                                PatternRepository.Pattern pattern = PatternRepository.get().getPattern(lte.getTier());
                                if (pattern != null) {
                                    for (FactoryComponent component : FactoryComponent.VALUES) {
                                        int count = pattern.getFactoryBlockCount((component));
                                        if (count > 0) {
                                            String text = String.format("%2d * %s", count, StringHelper.translate(component.getTranslationKey()));
                                            if (component == FactoryComponent.CELL)
                                                text = String.format("%2d * %s", count, StringHelper.translate("info.woot.intern.cell"));
                                            else if (component == FactoryComponent.CONTROLLER)
                                                text = String.format(" 1-%d * %s", count, StringHelper.translate(component.getTranslationKey()));

                                            iProbeInfo.text(TextStyleClass.INFO + text);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });

            return null;
        }
    }
}
