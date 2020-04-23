package ipsis.woot.modules.factory.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.Perk;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.blocks.HeartContainer;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.client.ClientFactorySetup;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.oss.NetworkTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HeartStaticDataReply {

    public FormedSetup formedSetup;
    public HeartTileEntity.Recipe recipe;
    public ClientFactorySetup clientFactorySetup;

    public HeartStaticDataReply() { }
    public HeartStaticDataReply(FormedSetup formedSetup, HeartTileEntity.Recipe recipe) {
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

        buf.writeInt(recipe.getNumTicks());
        buf.writeInt(recipe.getNumUnits());

        buf.writeInt(formedSetup.getAllMobs().size());
        for (FakeMob fakeMob : formedSetup.getAllMobs()) {
            NetworkTools.writeString(buf, fakeMob.toString());

            // Params
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).baseSpawnTicks);
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).baseMassCount);
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).baseFluidCost);
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).perkEfficiencyValue);
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).perkMassValue);
            buf.writeInt(formedSetup.getAllMobParams().get(fakeMob).perkXpValue);

            if (recipe.items.containsKey(fakeMob)) {
                buf.writeInt(recipe.items.get(fakeMob).size());
                for (ItemStack itemStack : recipe.items.get(fakeMob))
                    NetworkTools.writeItemStack(buf, itemStack);
            } else {
                buf.writeInt(0);
            }

            if (recipe.fluids.containsKey(fakeMob)) {
                buf.writeInt(0);
                /*
                buf.writeInt(recipe.fluids.get(fakeMob).size());
                for (FluidStack fluidStack : recipe.fluids.get(fakeMob))
                    NetworkTools.writeItemStack(buf, itemStack); */
            } else {
                buf.writeInt(0);
            }

            List<SimulatedMobDropSummary> drops = MobSimulator.getInstance().getDropSummary(fakeMob);
            buf.writeInt(drops.size());
            for (SimulatedMobDropSummary drop : drops) {
                ItemStack itemStack = drop.itemStack.copy();
                itemStack.setCount((int)(drop.chanceToDrop[formedSetup.getLootingLevel()] * 100.0F));
                NetworkTools.writeItemStack(buf, itemStack);
            }
        }

        buf.writeInt(formedSetup.getAllPerks().size());
        for (Map.Entry<PerkType, Integer> e : formedSetup.getAllPerks().entrySet()) {
            Perk perk = Perk.getPerks(e.getKey(), e.getValue());
            buf.writeInt(perk.ordinal());
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ClientPlayerEntity clientPlayerEntity = Minecraft.getInstance().player;
        if (clientPlayerEntity != null) {
            ctx.get().enqueueWork(() -> {
                if (clientPlayerEntity != null && clientPlayerEntity.openContainer instanceof HeartContainer) {
                    ((HeartContainer) clientPlayerEntity.openContainer).handleStaticDataReply(clientFactorySetup);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
