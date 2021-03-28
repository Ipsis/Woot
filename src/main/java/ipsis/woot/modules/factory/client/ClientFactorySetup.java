package ipsis.woot.modules.factory.client;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.factory.Exotic;
import ipsis.woot.modules.factory.MobParam;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.NetworkHelper;
import ipsis.woot.util.oss.NetworkTools;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientFactorySetup {

    public Tier tier = Tier.TIER_1;
    public List<FakeMob> controllerMobs = new ArrayList<>();
    public List<Perk> perks = new ArrayList<>();
    public HashMap<FakeMob, MobParam> mobParams = new HashMap<>();
    public HashMap<FakeMob, Mob> mobInfo = new HashMap<>();
    public Exotic exotic = Exotic.NONE;
    public int cellCapacity = 0;
    public int looting = 0;
    public int recipeTicks = 0;
    public int recipeFluid = 0;
    public boolean perkCapped = false;

    // shards
    public int shardRolls = 1;
    public double shardDropChance = 0.0F;
    public double[] shardDrops = new double[]{ 0.0F, 0.0F, 0.0F };

    public static class Mob {
        public List<ItemStack> drops = new ArrayList<>();
    }

    public List<ItemStack> itemIng = new ArrayList<>();
    public List<FluidStack> fluidIng = new ArrayList<>();

    private ClientFactorySetup() {}

    public static ClientFactorySetup fromBytes(ByteBuf buf) {

        ClientFactorySetup factorySetup = new ClientFactorySetup();

        factorySetup.tier = Tier.byIndex(buf.readInt());
        factorySetup.cellCapacity = buf.readInt();
        buf.readInt(); /// fluid amount
        factorySetup.looting = buf.readInt();
        factorySetup.exotic = Exotic.getExotic(buf.readInt());

        factorySetup.recipeTicks = buf.readInt();
        factorySetup.recipeFluid = buf.readInt();
        factorySetup.shardRolls = buf.readInt();
        factorySetup.shardDropChance = buf.readDouble();
        factorySetup.shardDrops[0] = buf.readDouble();
        factorySetup.shardDrops[1] = buf.readDouble();
        factorySetup.shardDrops[2] = buf.readDouble();

        int mobCount = buf.readInt();
        for (int x = 0; x < mobCount; x++) {
            String mobString = NetworkTools.readString(buf);
            FakeMob fakeMob = new FakeMob(mobString);
            MobParam mobParam = new MobParam();
            mobParam.baseSpawnTicks = buf.readInt();
            mobParam.baseMassCount = buf.readInt();
            mobParam.baseFluidCost = buf.readInt();
            mobParam.setPerkRateValue(buf.readInt());
            mobParam.setPerkEfficiencyValue(buf.readInt());
            mobParam.setPerkMassValue(buf.readInt());
            mobParam.setPerkXpValue(buf.readInt());
            mobParam.setPerkHeadlessValue(buf.readInt());
            factorySetup.controllerMobs.add(fakeMob);
            factorySetup.mobParams.put(fakeMob, mobParam);

            Mob mob = new Mob();
            int drops = buf.readInt();
            for (int y = 0; y < drops; y++) {
                ItemStack itemStack = NetworkTools.readItemStack(buf);
                itemStack.setCount((int)(buf.readFloat() * 100.0F));
                if (!itemStack.isEmpty())
                    mob.drops.add(itemStack);
            }
            factorySetup.mobInfo.put(fakeMob, mob);
        }

        factorySetup.perkCapped = buf.readBoolean();
        int perkCount = buf.readInt();
        for (int x = 0; x < perkCount; x++)
            factorySetup.perks.add(Perk.byIndex(buf.readInt()));

        int itemIng = buf.readInt();
        for (int y = 0; y < itemIng; y++) {
            ItemStack itemStack = NetworkTools.readItemStack(buf);
            factorySetup.itemIng.add(itemStack);
        }

        int fluidIng = buf.readInt();
        for (int y = 0; y < fluidIng; y++) {
            FluidStack fluidStack = NetworkHelper.readFluidStack(buf);
            factorySetup.fluidIng.add(fluidStack);
        }

        return factorySetup;
    }
}
