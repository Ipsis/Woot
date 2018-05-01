package ipsis.woot.plugins.evilcraft;

import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.WootMobName;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class EvilCraftHelper {

    public static @Nonnull
    FluidStack createBlood(World world, WootMobName wootMobName, int numMobs, int bloodMBPerHP) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_BM_LE, "createBlood", wootMobName + ":" + numMobs);

        FluidStack out = new FluidStack(EvilCraft.blood, 0);

        /**
         * EvilCraft blood stained block:
         *
         * When entity dies is stains the block with its blood.
         * That amount of stain =  bloodMBPerHP * entity max health
         * The Sanguinary Pedastal will then unstain that block
         */

        int health = Woot.mobCosting.getMobSpawnCost(world, wootMobName);
        if (health > 0) {
            out.amount = health * bloodMBPerHP;
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_EC, "createBlood", "health:" + health + " bloodMBPerHp:" + bloodMBPerHP + " out:" + out);
        }

        return out;
    }
}
