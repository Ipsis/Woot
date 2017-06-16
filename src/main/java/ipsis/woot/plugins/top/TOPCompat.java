package ipsis.woot.plugins.top;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
//import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import javax.annotation.Nullable;

/**
 * Straight from McJJty's tutorial
 */
//public class TOPCompat {
//
//    private static boolean registered;
//
//    public static void register() {
//
//        if (!registered) {
//            registered = true;
//            FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe",
//                    "ipsis.woot.plugins.top.TOPCompat$GetTheOneProbe");
//        }
//    }
//
//    public static class GetTheOneProbe implements com.google.common.base.Function<ITheOneProbe, Void> {
//
//        public static ITheOneProbe probe;
//
//        @Nullable
//        @Override
//        public Void apply(ITheOneProbe theOneProbe) {
//
//            probe = theOneProbe;
//            LogHelper.info("Enabled The One Probe support");
//
//            probe.registerProvider(new IProbeInfoProvider() {
//                @Override
//                public String getID() {
//                    return Reference.MOD_ID;
//                }
//
//                @Override
//                public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
//
//                    if (blockState.getBlock() instanceof ITOPInfoProvider) {
//                        ITOPInfoProvider provider = (ITOPInfoProvider)blockState.getBlock();
//                        provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
//                    }
//                }
//            });
//            return null;
//        }
//    }
//}
