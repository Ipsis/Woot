package ipsis.woot.server.command;

import ipsis.Woot;
import ipsis.woot.util.Debug;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandDebug extends CommandBase {

    @Override
    public String getName() {
        return "debug";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.woot.debug.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException  {

        if (args.length == 0) {
            // Currently enabled debug
            sender.sendMessage(new TextComponentString(Woot.debugging.toString()));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                Woot.debugging.set(args[1]);
                sender.sendMessage(new TextComponentString(Woot.debugging.toString()));
            } else if (args[0].equalsIgnoreCase("clear")) {
                Woot.debugging.clear(args[1]);
                sender.sendMessage(new TextComponentString(Woot.debugging.toString()));
            } else {
                throw new WrongUsageException(getUsage(sender));
            }
        } else {
            throw new WrongUsageException(getUsage(sender));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

        if (args.length == 2)
            return getListOfStringsMatchingLastWord(args, Debug.ALL_GROUPS);

        return Collections.emptyList();
    }
}
