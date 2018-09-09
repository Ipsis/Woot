package ipsis.woot.server.command;

import ipsis.woot.ModWorlds;
import ipsis.woot.dimensions.tartarus.TartarusManager;
import ipsis.woot.oss.CustomTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.FakePlayer;

public class CommandTeleport extends CommandBase {

    @Override
    public String getName() {
        return "teleport";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.woot.teleport.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException  {

        int id, x, y, z;
        if (args.length == 1 & args[0].equalsIgnoreCase("tartarus")) {
            id = ModWorlds.tartarus_id;
            x = 18;
            y = 0;
            z = 18;
        } else if (args.length == 4) {
            try {
                id = Integer.parseInt(args[0]);
                x = Integer.parseInt(args[1]);
                y = Integer.parseInt(args[2]);
                z = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                throw new WrongUsageException(getUsage(sender));
            }
        } else {
            throw new WrongUsageException(getUsage(sender));
        }

        if (sender instanceof EntityPlayer && !(sender instanceof FakePlayer))
            CustomTeleporter.teleportToDimension((EntityPlayer)sender, id, x, y, z);

    }
}
