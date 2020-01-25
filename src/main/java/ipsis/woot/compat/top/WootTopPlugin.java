package ipsis.woot.compat.top;

import com.google.common.base.Function;
import ipsis.woot.Woot;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

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
            Woot.LOGGER.info("Enabling support The One Probe");
            probe.registerProvider(new IProbeInfoProvider() {
                @Override
                public String getID() {
                    return Woot.MODID + ":default";
                }

                @Override
                public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, BlockState blockState, IProbeHitData iProbeHitData) {
                }
            });

            return null;
        }
    }
}
