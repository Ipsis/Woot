package ipsis.woot.plugins.top;

import ipsis.woot.tileentity.ui.FarmUIInfo;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TOPUIInfoConvertors {

    public static void farmConvertor(FarmUIInfo farm, ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        probeInfo.text(TextFormatting.BLUE + "Tier: " + farm.tier.toString());

        /**
         * Power & Redstone state
         */
        //probeInfo.text(TextFormatting.RED + "FP: " + ElementProgress.format(info.storedRF, Config.rfFormat, "FP"));

        probeInfo.horizontal().item(new ItemStack(Items.REDSTONE), probeInfo.defaultItemStyle().width(14).height(14)).text("State: " + (farm.isRunning ? "On" : "Off"));

        /**
         * Progress
         */

        if (farm.isRunning) {
            int p = (int)((100.0F / (float)farm.recipeTotalPower) * (float)farm.consumedPower);
            p = MathHelper.clamp(p, 0, 100);
            TextFormatting form = TextFormatting.GREEN;
            probeInfo.horizontal().item(new ItemStack(Items.COMPASS), probeInfo.defaultItemStyle().width(14).height(14)).text(form + "Progress: " + p + "%");
        }

        /**
         * Recipe
         */
        probeInfo.text(TextFormatting.GREEN + "Mob: " + farm.wootMob.getDisplayName());
        probeInfo.text(TextFormatting.GREEN + "Recipe: " + farm.recipeTotalPower + " " + farm.recipeTotalTime + " " + farm.recipePowerPerTick);

    }
}
