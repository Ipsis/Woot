package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.reference.Localization;
import ipsis.woot.util.CommandHelper;
import ipsis.woot.util.DebugSetup;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.HashMap;

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

        // This needs to be in sync with getUsage so people know what they can type in!
        private static final HashMap<String, DebugSetup.EnumDebugType> devTags = new HashMap<>();
        static {
            devTags.put("event", DebugSetup.EnumDebugType.LOOT_EVENTS);
            devTags.put("cfgaccess", DebugSetup.EnumDebugType.CONFIG_ACCESS);
            devTags.put("anvilcrafting", DebugSetup.EnumDebugType.ANVIL_CRAFTING);
            devTags.put("farmscan", DebugSetup.EnumDebugType.FARM_SCAN);
            devTags.put("farmclientsync", DebugSetup.EnumDebugType.FARM_CLIENT_SYNC);
        };

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            if (args.length < 1)
                throw new WrongUsageException(getUsage(sender));

            if (args[0].equalsIgnoreCase("show")) {
                CommandHelper.display(sender, Woot.debugSetup.toString());
            } else if (args[0].equalsIgnoreCase("list")) {
                    CommandHelper.display(sender, devTags.keySet().toString());
            } else {

                boolean d = true;
                if (args[0].startsWith("!")) {
                    d = false;
                    args[0] = args[0].substring(1);
                }

                String key = args[0].toLowerCase();
                if (devTags.containsKey(key)) {
                    if (d)
                        Woot.debugSetup.setDebug(devTags.get(key));
                    else
                        Woot.debugSetup.clearDebug(devTags.get(key));

                    CommandHelper.display(sender, Woot.debugSetup.toString());
                }
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
                Woot.debugSetup.setDebug(DebugSetup.EnumDebugType.POWER);
            else if (args[0].equalsIgnoreCase("false"))
                Woot.debugSetup.clearDebug(DebugSetup.EnumDebugType.POWER);

        }
    }
}
