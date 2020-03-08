package ipsis.woot.modules.oracle.blocks;

import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.oracle.network.SimulatedMobDropsSummaryReply;
import ipsis.woot.modules.oracle.network.SimulatedMobsReply;
import ipsis.woot.setup.NetworkChannel;
import ipsis.woot.setup.ServerDataRequest;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class OracleContainer extends Container {

    public TileEntity tileEntity;

    public OracleContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(OracleSetup.ORACLE_BLOCK_CONTAINER.get(), windowId);
        tileEntity = world.getTileEntity(pos);

        /**
         * There is no player inventory as it is display only
         */
    }

    public BlockPos getPos() { return tileEntity.getPos(); }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
                playerIn, OracleSetup.ORACLE_BLOCK.get());
    }

    /**
     * Server data sync
     */
    public List<FakeMob> simulatedMobs = null;
    public List<SimulatedMobDropSummary> simulatedDrops = null;
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

    public void handleSimulatedMobsReply(SimulatedMobsReply msg) {
        this.simulatedMobs = msg.simulatedMobs;
    }

    public void handleSimulatedMobDropsSummaryReply(SimulatedMobDropsSummaryReply msg) {
        this.simulatedDrops = msg.drops;
    }
}
