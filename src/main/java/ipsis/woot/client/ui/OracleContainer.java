package ipsis.woot.client.ui;

import ipsis.woot.mod.ModBlocks;
import ipsis.woot.network.DropRegistryStatusReply;
import ipsis.woot.network.NetworkChannel;
import ipsis.woot.network.ServerDataRequest;
import ipsis.woot.network.SimulatedMobDropsReply;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static ipsis.woot.mod.ModBlocks.ORACLE_CONTAINER;

public class OracleContainer extends Container {

    public TileEntity tileEntity;

    public OracleContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(ORACLE_CONTAINER, windowId);
        tileEntity = world.getTileEntity(pos);

        /**
         * There is no player inventory as it is display only
         */
    }

    public BlockPos getPos() { return tileEntity.getPos(); }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
                playerIn, ModBlocks.ORACLE_BLOCK);
    }

    /**
     * Server data sync
     */
    public List<DropRegistryStatusReply.SimMob> simulatedMobs = null;
    public List<SimulatedMobDropsReply.SimDrop> simulatedDrops = null;
    public void refreshMobs() {
        NetworkChannel.channel.sendToServer(new ServerDataRequest(ServerDataRequest.Type.DROP_REGISTRY_STATUS,
                getPos(), ""));
        simulatedMobs = null;
        simulatedDrops = null;
    }

    public void refreshDrops(FakeMob fakeMob) {
        NetworkChannel.channel.sendToServer(new ServerDataRequest(ServerDataRequest.Type.SIMULATED_MOB_DROPS,
                getPos(), fakeMob.getName()));
        simulatedDrops = null;
    }

    public int simCount = 0;
    public void handleDropRegistryStatus(DropRegistryStatusReply msg) {
        this.simCount = msg.simCount;
        this.simulatedMobs = msg.simulatedMobs;
    }

    public void handleSimulatedMobDrops(SimulatedMobDropsReply msg) {
        this.simulatedDrops = msg.simulatedDrops;
    }
}
