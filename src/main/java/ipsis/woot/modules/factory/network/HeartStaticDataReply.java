package ipsis.woot.modules.factory.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.Perk;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.blocks.HeartContainer;
import ipsis.woot.modules.factory.recipe.HeartRecipe;
import ipsis.woot.modules.factory.client.ClientFactorySetup;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.NetworkHelper;
import ipsis.woot.util.oss.NetworkTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HeartStaticDataReply {

    public FormedSetup formedSetup;
    public HeartRecipe recipe;
    public ClientFactorySetup clientFactorySetup;

    public HeartStaticDataReply() { }
    public HeartStaticDataReply(FormedSetup formedSetup, HeartRecipe recipe) {
        this.formedSetup = formedSetup;
        this.recipe = recipe;
    }

    public static HeartStaticDataReply fromBytes(ByteBuf buf) {
        HeartStaticDataReply pkt = new HeartStaticDataReply();
        pkt.clientFactorySetup = ClientFactorySetup.fromBytes(buf);
        return pkt;
    }

    public void toBytes(ByteBuf buf) {

        buf.writeInt(formedSetup.getTier().ordinal());
        buf.writeInt(formedSetup.getCellCapacity());
        buf.writeInt(formedSetup.getCellFluidAmount());
        buf.writeInt(formedSetup.getLootingLevel());
        buf.writeInt(formedSetup.getExotic().ordinal());

        buf.writeInt(recipe.getNumTicks());
        buf.writeInt(recipe.getNumUnits());

        if (formedSetup.getAllPerks().containsKey(PerkType.TIER_SHARD)) {
            buf.writeInt(formedSetup.getPerkTierShardValue());
            buf.writeDouble(formedSetup.getShardDropChance());
            double full = formedSetup.getBasicShardWeight() + formedSetup.getAdvancedShardWeight() + formedSetup.getEliteShardWeight();
            buf.writeDouble((100.0F / full) * formedSetup.getBasicShardWeight());
            buf.writeDouble((100.0F / full) * formedSetup.getAdvancedShardWeight());
            buf.writeDouble((100.0F / full) * formedSetup.getEliteShardWeight());
        } else {
            buf.writeInt(0);
            buf.writeDouble(0.0F);
            buf.writeDouble(0.0F);
            buf.writeDouble(0.0F);
            buf.writeDouble(0.0F);
        }

        buf.writeInt(formedSetup.getAllMobs().size());
        for (FakeMob fakeMob : formedSetup.getAllMobs()) {
            NetworkTools.writeString(buf, fakeMob.toString());

            // Params
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).baseSpawnTicks);
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).baseMassCount);
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).baseFluidCost);
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).getPerkRateValue());
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).getPerkEfficiencyValue());
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).getMobCount(formedSetup.getAllPerks().containsKey(PerkType.MASS), formedSetup.hasMassExotic()));
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).getPerkXpValue());
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).getPerkHeadlessValue());

            if (recipe.items.containsKey(fakeMob)) {
                buf.writeInt(recipe.items.get(fakeMob).size());
                for (ItemStack itemStack : recipe.items.get(fakeMob))
                    NetworkTools.writeItemStack(buf, itemStack);
            } else {
                buf.writeInt(0);
            }

            if (recipe.fluids.containsKey(fakeMob)) {
                buf.writeInt(recipe.fluids.get(fakeMob).size());
                for (FluidStack fluidStack : recipe.fluids.get(fakeMob))
                    NetworkHelper.writeFluidStack(buf, fluidStack);
            } else {
                buf.writeInt(0);
            }

            List<SimulatedMobDropSummary> drops = MobSimulator.getInstance().getDropSummary(fakeMob);
            buf.writeInt(drops.size());
            for (SimulatedMobDropSummary drop : drops) {
                ItemStack itemStack = drop.itemStack.copy();
                itemStack.setCount((int)(drop.chanceToDrop[formedSetup.getLootingLevel()] * 1000.0F));
                NetworkTools.writeItemStack(buf, itemStack);
                buf.writeFloat(drop.chanceToDrop[formedSetup.getLootingLevel()]);
            }
        }

        buf.writeInt(formedSetup.getAllPerks().size());
        for (Map.Entry<PerkType, Integer> e : formedSetup.getAllPerks().entrySet()) {
            Perk perk = Perk.getPerks(e.getKey(), e.getValue());
            buf.writeInt(perk.ordinal());
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            final ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player.openContainer instanceof HeartContainer)
                ((HeartContainer) player.openContainer).handleStaticDataReply(clientFactorySetup);
            ctx.get().setPacketHandled(true);
        })) ;
    }
}
