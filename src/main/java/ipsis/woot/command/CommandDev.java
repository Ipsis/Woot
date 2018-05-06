package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.oss.CustomTeleporter;
import ipsis.woot.util.CommandHelper;
import ipsis.woot.util.DebugSetup;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.HashMap;

public class CommandDev extends CommandTreeBase {

    public CommandDev() {

        addSubcommand(new CommandDevPower());
        addSubcommand(new CommandDevDebug());
        addSubcommand(new CommandDevTeleport());
    }

    @Override
    public String getName() {

        return "dev";
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return "commands.woot.dev.usage";
    }

    public static class CommandDevDebug extends CommandBase {


        @Override
        public String getName() {

            return "debug";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return "commands.woot.dev.debug.usage";
        }

        // This needs to be in sync with getUsage so people know what they can type in!
        private static final HashMap<String, DebugSetup.EnumDebugType> devTags = new HashMap<>();
        static {
            devTags.put("event", DebugSetup.EnumDebugType.LOOT_EVENTS);
            devTags.put("cfgaccess", DebugSetup.EnumDebugType.CONFIG_ACCESS);
            devTags.put("anvilcrafting", DebugSetup.EnumDebugType.ANVIL_CRAFTING);
            devTags.put("farmscan", DebugSetup.EnumDebugType.FARM_SCAN);
            devTags.put("farmbuild", DebugSetup.EnumDebugType.FARM_BUILD);
            devTags.put("farmclientsync", DebugSetup.EnumDebugType.FARM_CLIENT_SYNC);
            devTags.put("multiblock", DebugSetup.EnumDebugType.MULTIBLOCK);
            devTags.put("powercalc", DebugSetup.EnumDebugType.POWER_CALC);
            devTags.put("genxp", DebugSetup.EnumDebugType.GEN_XP);
            devTags.put("genitems", DebugSetup.EnumDebugType.GEN_ITEMS);
            devTags.put("genbmle", DebugSetup.EnumDebugType.GEN_BM_LE);
            devTags.put("genbmcrystal", DebugSetup.EnumDebugType.GEN_BM_CRYSTAL);
            devTags.put("genheads", DebugSetup.EnumDebugType.GEN_HEADS);
            devTags.put("genec", DebugSetup.EnumDebugType.GEN_EC);
            devTags.put("spawn", DebugSetup.EnumDebugType.SPAWN);
            devTags.put("learn", DebugSetup.EnumDebugType.LEARN);
            devTags.put("tartarus", DebugSetup.EnumDebugType.TARTARUS);
            devTags.put("decap", DebugSetup.EnumDebugType.DECAP);
        }

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

            return "commands.woot.dev.power.usage";
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

    public static class CommandDevTeleport extends CommandBase {

        @Override
        public String getName() {

            return "teleport";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return "commands.woot.dev.teleport.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            int dimensionId, x, y, z;
            if (args.length == 1 && args[0].equalsIgnoreCase("tartarus")) {
                dimensionId = Woot.wootDimensionManager.getDimensionId();
                x = 18;
                y = 0;
                z = 18;
            } else if (args.length == 4) {
                try {
                    dimensionId = Integer.parseInt(args[0]);
                    x = Integer.parseInt(args[1]);
                    y = Integer.parseInt(args[2]);
                    z = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    throw new WrongUsageException(getUsage(sender));
                }
            } else {
                throw new WrongUsageException(getUsage(sender));
            }

            if (sender instanceof EntityPlayer)
                CustomTeleporter.teleportToDimension((EntityPlayer)sender, dimensionId, x, y, z);
        }
    }
}
