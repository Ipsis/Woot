package ipsis.woot.compat.hwyla;

import ipsis.woot.modules.factory.Exotic;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
import mcjty.theoneprobe.api.CompoundText;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class HeartDataProvider implements IServerDataProvider<TileEntity>, IComponentProvider {

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        CompoundNBT nbt = accessor.getServerData();
        if (nbt != null) {

            if (nbt.getBoolean("formed")) {
                Tier tier = Tier.byIndex(nbt.getInt("tier"));
                if (tier != Tier.UNKNOWN) {
                    tooltip.add(new StringTextComponent(
                            StringHelper.translate("top.woot.heart.tier.label") + ": " +
                                    StringHelper.translate(tier.getTranslationKey())));

                    for (int i = 0; i < nbt.getInt("perks"); i++) {
                        CompoundNBT nbt1 = nbt.getCompound("perks" + i);
                        int level = nbt1.getInt("level");
                        PerkType perkType = PerkType.byIndex(nbt1.getInt("perk"));
                        ItemStack itemStack = PerkItem.getItemStack(perkType, level);
                        tooltip.add(new StringTextComponent(
                                StringHelper.translate("top.woot.heart.perk.label") + ": " +
                                        StringHelper.translate(itemStack.getItem().getTranslationKey())));
                    }

                    Exotic exotic = Exotic.getExotic(nbt.getInt("exotic"));
                    if (exotic != Exotic.NONE) {
                        ItemStack itemStack = exotic.getItemStack();
                        tooltip.add(new StringTextComponent(
                                StringHelper.translate("top.woot.heart.exotic.label") + ": " +
                                        StringHelper.translate(itemStack.getItem().getTranslationKey())));
                    }

                    int progress = nbt.getInt("progress");
                    tooltip.add(new StringTextComponent(
                            StringHelper.translate("top.woot.heart.progress.label") + ": " +
                                    StringHelper.translateFormat("top.woot.heart.progress.0", progress)));

                    for (int i = 0; i < nbt.getInt("mobs"); i++) {
                        CompoundNBT nbt1 = nbt.getCompound("mobs" + i);
                        FakeMob fakeMob = new FakeMob(nbt1);
                        tooltip.add(new StringTextComponent(
                                StringHelper.translate("top.woot.heart.mob.label") + ": " +
                                StringHelper.translate(fakeMob)));
                    }
                }

            } else {
                tooltip.add(new StringTextComponent(StringHelper.translate("top.woot.heart.unformed")));
            }
        }

    }

    @Override
    public void appendServerData(CompoundNBT compoundNBT, ServerPlayerEntity serverPlayerEntity, World world, TileEntity tileEntity) {

        if (tileEntity instanceof HeartTileEntity) {
            HeartTileEntity heartTileEntity = (HeartTileEntity)tileEntity;
            if (heartTileEntity.isFormed()) {
                compoundNBT.putBoolean("formed", true);
                compoundNBT.putInt("tier", heartTileEntity.getTier().ordinal());
                compoundNBT.putInt("perks", heartTileEntity.getPerks().keySet().size());
                int i = 0;
                for (PerkType perkType : heartTileEntity.getPerks().keySet()) {
                    int level = heartTileEntity.getPerks().get(perkType);
                    CompoundNBT nbt2 = new CompoundNBT();
                    nbt2.putInt("level", level);
                    nbt2.putInt("perk", perkType.ordinal());
                    compoundNBT.put("perks" + i, nbt2);
                    i++;
                }

                compoundNBT.putInt("exotic", heartTileEntity.getExotic().ordinal());
                compoundNBT.putInt("progress", heartTileEntity.getProgress());

                compoundNBT.putInt("mobs", heartTileEntity.getFormedMobs().size());
                i = 0;
                for (FakeMob fakeMob : heartTileEntity.getFormedMobs()) {
                    CompoundNBT nbt2 = new CompoundNBT();
                    FakeMob.writeToNBT(fakeMob, nbt2);
                    compoundNBT.put("mobs" + i, nbt2);
                    i++;
                }

            } else {
                compoundNBT.putBoolean("formed", false);
            }
        }
    }
}
