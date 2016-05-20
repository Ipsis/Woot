package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandWoot extends CommandBase {
    @Override
    public String getCommandName() {

        return "woot";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.woot.usage";
    }

    @Override
    public int getRequiredPermissionLevel() {

        /**
         * 1 - ops can bypass spawn protection
         * 2 - ops can use clear, difficulty, effect, gamemode, gamerule, give, tp
         * 3 - ops can use ban, deop, kick, op
         * 4 - ops can use stop
         */
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {
            throw new WrongUsageException("commands.Woot:woot.usage");
        } else if ("dump".equals(args[0])) {
            dumpTable(sender, args);
        } else if ("flush".equals(args[0])) {
            flushTable(sender, args);
        } else {
            throw new WrongUsageException("commands.Woot:woot.usage");
        }
    }

    private void dumpTable(ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 2)
            throw new WrongUsageException("commands.Woot:woot.usage.dump");

        String type = args[1];

        if ("table".equals(type))
            Woot.spawnerManager.cmdDumpTable(sender);
        else if ("mobs".equals(type))
            Woot.spawnerManager.cmdDumpMobs(sender);
        else
            throw new WrongUsageException("commands.Woot:woot.usage");
    }

    private void flushTable(ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 2 && args.length != 3)
            throw new WrongUsageException("commands.Woot:woot.usage.flush");

        if (args.length == 2) {
            if (args[1].equals("all"))
                Woot.spawnerManager.cmdFlushTables(sender);
            else
                throw new WrongUsageException("commands.Woot:woot.usage.flush");
        } else {

            String mobName = args[1];
            String key = args[2];

            EnumEnchantKey enumEnchantKey;
            if (key.equals(EnumEnchantKey.NO_ENCHANT.toString()))
                enumEnchantKey = EnumEnchantKey.NO_ENCHANT;
            else if (key.equals(EnumEnchantKey.LOOTING_I.toString()))
                enumEnchantKey = EnumEnchantKey.LOOTING_I;
            else if (key.equals(EnumEnchantKey.LOOTING_II.toString()))
                enumEnchantKey = EnumEnchantKey.LOOTING_II;
            else if (key.equals(EnumEnchantKey.LOOTING_III.toString()))
                enumEnchantKey = EnumEnchantKey.LOOTING_III;
            else
                throw new WrongUsageException("commands.Woot:woot.usage.flush");

            Woot.spawnerManager.cmdFlushTableEntry(sender, mobName, enumEnchantKey);
        }
    }
}
