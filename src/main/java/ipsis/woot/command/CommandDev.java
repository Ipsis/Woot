package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Localization;
import ipsis.woot.util.CommandHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandDev extends CommandTreeBase {

    public CommandDev() {

        addSubcommand(new CommandDevPower());
        addSubcommand(new CommandDevDebug());
    }

    @Override
    public String getName() {

        return "dev";
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return Localization.TAG_COMMAND + "dev.usage";
    }

    public static class CommandDevDebug extends CommandBase {


        @Override
        public String getName() {

            return "debug";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return Localization.TAG_COMMAND + "dev.debug.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            if (args.length < 1)
                throw new WrongUsageException(getUsage(sender));

            if (args[0].equalsIgnoreCase("event"))
                Woot.debugSetup.setTraceLootEvents(true);
            else if (args[0].equalsIgnoreCase("!event"))
                Woot.debugSetup.setTraceLootEvents(false);
            else if (args[0].equalsIgnoreCase("show")) {
                if (Woot.debugSetup.getTraceLootEvents())
                    CommandHelper.display(sender, Localization.DEBUG_TRACE_LOOT_EVENT);
                if (!Woot.debugSetup.getNeedPower())
                    CommandHelper.display(sender, Localization.DEBUG_POWER_DISABLED);
            }
        }
    }

    public static class CommandDevPower extends CommandBase {

        @Override
        public String getName() {

            return "power";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return Localization.TAG_COMMAND + "dev.power.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            if (args.length < 1)
                throw new WrongUsageException(getUsage(sender));

            if (args[0].equalsIgnoreCase("true"))
                Woot.debugSetup.setNeedPower(true);
            else if (args[0].equalsIgnoreCase("false"))
                Woot.debugSetup.setNeedPower(false);

        }
    }
}
