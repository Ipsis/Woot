package ipsis.woot.server.command;

import ipsis.Woot;
import ipsis.woot.dimensions.tartarus.TartarusManager;
import ipsis.woot.school.SchoolManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandStatus extends CommandBase {

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.woot.status.usage";
    }

    private static final List<String> validObjects = new ArrayList<>();
    static
    {
        validObjects.add("tartarus");
        validObjects.add("school");
        validObjects.add("droprepo");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length >= 1) {
            List<String> status = new ArrayList<>();
            if (args[0].equalsIgnoreCase("tartarus"))
                TartarusManager.getStatus(status, args);
            else if (args[0].equalsIgnoreCase("school"))
                SchoolManager.getStatus(status, args);
            else if (args[0].equalsIgnoreCase("droprepo"))
                Woot.DROP_MANAGER.getStatus(status, args);

            for (String s : status)
                sender.sendMessage(new TextComponentString(s));

        } else {
            throw new WrongUsageException(getUsage(sender));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

        if (args.length == 1)
            return validObjects;

        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
