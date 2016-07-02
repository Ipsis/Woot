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
        } else {
            throw new WrongUsageException("commands.Woot:woot.usage");
        }
    }

    private void dumpTable(ICommandSender sender, String[] args) throws CommandException {

        if (args.length < 2)
            throw new WrongUsageException("commands.Woot:woot.usage.dump");

        String type = args[1];

        if ("table".equals(type))
            dumpLootTable(sender, args);
        else if ("mobs".equals(type))
            Woot.LOOT_TABLE_MANAGER.dumpMobs(sender);
        else if ("tiers".equals(type))
            Woot.tierMapper.cmdDumpTiers(sender);
        else if ("prism".equals(type))
            Woot.mobRegistry.cmdDumpPrism(sender);
        else
            throw new WrongUsageException("commands.Woot:woot.usage.dump");
    }

    private void dumpLootTable(ICommandSender sender, String[] args) throws CommandException {

        if (args.length < 4)
            throw new WrongUsageException("commands.Woot:woot.usage.dump.table");

        String mobName = args[2];
        String key = args[3];

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
            throw new WrongUsageException("commands.Woot:woot.dump.table");

        boolean detail = false;
        if (args.length == 5 && args[4].equals("detail"))
            detail = true;

        Woot.LOOT_TABLE_MANAGER.dumpDrops(sender, mobName, enumEnchantKey, detail);
    }
}
