package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

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
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {
            throw new WrongUsageException("command.Woot:woot.usage");
        } else if ("dump".equals(args[0])) {
            dumpTable(sender, args);
        } else if ("flush".equals(args[0])) {
            flushTable(sender, args);
        } else {
            throw new WrongUsageException("command.Woot:woot.usage");
        }
    }

    private void dumpTable(ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 2)
        throw new WrongUsageException("command.Woot:woot.dump.usage");

        String type = args[1];

        if ("table".equals(type))
            Woot.spawnerManager.cmdDumpTable(sender);
        else if ("mobs".equals(type))
            Woot.spawnerManager.cmdDumpMobs(sender);
        else
            throw new WrongUsageException("command.Woot:woot.usage");
    }

    private void flushTable(ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 2 && args.length != 3)
            throw new WrongUsageException("command.Woot:woot.flush.usage");

        if (args.length == 2) {
            if (args[1].equals("all"))
                Woot.spawnerManager.cmdClear(sender);
            else
                throw new WrongUsageException("command.Woot:woot.flush.usage");
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
                throw new WrongUsageException("command.Woot:woot.flush.usage");

            Woot.spawnerManager.cmdClearTableEntry(sender, mobName, enumEnchantKey);
        }
    }
}
