package ipsis.woot.modules.factory.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.factory.blocks.HeartContainer;
import ipsis.woot.modules.factory.blocks.HeartRecipe;
import ipsis.woot.modules.factory.client.ClientFactorySetup;
import ipsis.woot.modules.factory.perks.Helper;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.NetworkHelper;
import ipsis.woot.util.oss.NetworkTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
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

        if (formedSetup.getAllPerks().containsKey(Perk.Group.TIER_SHARD)) {
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
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).getMobCount(formedSetup.getAllPerks().containsKey(Perk.Group.MASS), formedSetup.hasMassExotic()));
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).getPerkXpValue());
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).getPerkHeadlessValue());

            List<SimulatedMobDropSummary> drops = MobSimulator.getInstance().getDropSummary(fakeMob);
            buf.writeInt(drops.size());
            for (SimulatedMobDropSummary drop : drops) {
                ItemStack itemStack = drop.itemStack.copy();
                itemStack.setCount((int)(drop.chanceToDrop[formedSetup.getLootingLevel()] * 1000.0F));
                NetworkTools.writeItemStack(buf, itemStack);
                buf.writeFloat(drop.chanceToDrop[formedSetup.getLootingLevel()]);
            }
        }

        buf.writeBoolean(formedSetup.isPerkCapped());
        buf.writeInt(formedSetup.getAllPerks().size());
        for (Map.Entry<Perk.Group, Integer> e : formedSetup.getAllPerks().entrySet()) {
            Perk perk = Helper.getPerk(e.getKey(), e.getValue());
            buf.writeInt(perk.ordinal());
        }

        // Ingredients are the sum from all the mobs
        buf.writeInt(recipe.recipeItems.size());
        recipe.recipeItems.forEach(i -> NetworkTools.writeItemStack(buf, i));
        buf.writeInt(recipe.recipeFluids.size());
        recipe.recipeFluids.forEach(i -> NetworkHelper.writeFluidStack(buf, i));
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
