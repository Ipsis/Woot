package ipsis.woot.server.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandGive extends CommandBase {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.woot.teleport.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException  {

    }
}
